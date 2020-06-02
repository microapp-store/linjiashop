package cn.enilu.flash.api.controller.shop;

import cn.enilu.flash.bean.constant.CfgKey;
import cn.enilu.flash.bean.constant.factory.PageFactory;
import cn.enilu.flash.bean.core.BussinessLog;
import cn.enilu.flash.bean.entity.shop.Order;
import cn.enilu.flash.bean.entity.system.FileInfo;
import cn.enilu.flash.bean.enumeration.shop.OrderEnum;
import cn.enilu.flash.bean.exception.ApplicationException;
import cn.enilu.flash.bean.exception.ApplicationExceptionEnum;
import cn.enilu.flash.bean.vo.front.Rets;
import cn.enilu.flash.bean.vo.query.SearchFilter;
import cn.enilu.flash.security.JwtUtil;
import cn.enilu.flash.service.api.KdniaoResponse;
import cn.enilu.flash.service.api.KdniaoService;
import cn.enilu.flash.service.shop.OrderService;
import cn.enilu.flash.service.system.CfgService;
import cn.enilu.flash.service.system.FileService;
import cn.enilu.flash.utils.DateUtil;
import cn.enilu.flash.utils.Lists;
import cn.enilu.flash.utils.Maps;
import cn.enilu.flash.utils.StringUtil;
import cn.enilu.flash.utils.factory.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/shop/order")
public class OrderController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private OrderService orderService;
    @Autowired
    private FileService fileService;
    @Autowired
    private KdniaoService kdniaoService;
    @Autowired
    private CfgService cfgService;

    /**
     * 获取订单统计信息
     * todo 真实生产可以考虑将订单数量信息通过队列形式更新在redis等缓存中，然后从缓存获取，这里暂时从数据库直接统计
     * @return
     */
    @RequestMapping(value = "/getOrderStatistic", method = RequestMethod.GET)
    public Object getOrderStatistic(){
        List<Map> list = orderService.queryBySql("SELECT status,count(1) as count FROM `t_shop_order` GROUP BY status");
        Map result = Maps.newHashMap();
        for(Map map:list){
            String statusStr = OrderEnum.getStatusStr((Integer) map.get("status"));
            result.put(statusStr,map.get("count"));
        }
        return Rets.success(result);
    }
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Object list(@RequestParam(value = "mobile", required = false) String mobile,
                       @RequestParam(value = "orderSn", required = false) String orderSn,
                        @RequestParam(value = "status", required = false) String status,
                       @RequestParam(value = "date", required = false) String date,
                       @RequestParam(value = "startDate", required = false) String startDate,
                       @RequestParam(value = "endDate", required = false) String endDate) {
        Page<Order> page = new PageFactory<Order>().defaultPage();
        page.addFilter("user.mobile", mobile);
        page.addFilter("orderSn", orderSn);
        if(StringUtil.isNotEmpty(status)){
            page.addFilter("status",OrderEnum.getStatusByStr(status));
        }
        if(StringUtil.isNotEmpty(date)){
            Date[] rangeDate = DateUtil.getDateRange(date);
            page.addFilter("createTime", SearchFilter.Operator.GTE,rangeDate[0]);
            page.addFilter("createTime", SearchFilter.Operator.LTE,rangeDate[1]);
        }
        if(StringUtil.isNotEmpty(startDate) && StringUtil.isNotEmpty(endDate)){
            page.addFilter("createTime", SearchFilter.Operator.GTE,DateUtil.parseDate(startDate));
            page.addFilter("createTime", SearchFilter.Operator.LTE,DateUtil.parseDate(endDate));
        }
        page = orderService.queryPage(page);
        return Rets.success(page);
    }

    @RequestMapping(value = "/export",method = RequestMethod.GET)
    public Object export(@RequestParam(value = "mobile", required = false) String mobile,
                         @RequestParam(value = "orderSn", required = false) String orderSn) {
        List<SearchFilter> filters = Lists.newArrayList();
        if(StringUtil.isNotEmpty(mobile)){

            filters.add(SearchFilter.build("user.mobile", mobile));
        }
        if(StringUtil.isNotEmpty(orderSn)){
            filters.add(SearchFilter.build("orderSn",orderSn));
        }
        List<Order> orderList = orderService.queryAll(filters);
        Map data =  Maps.newHashMap("list",orderList);
        String now = DateUtil.formatDate(new Date(),DateUtil.DATE_TIME_FMT);
        data.put("exportTime",now);
        data.put("userName", JwtUtil.getUsername());
        FileInfo fileInfo = fileService.createExcel("templates/orderList.xlsx","订单列表.xlsx",data);
        return Rets.success(fileInfo);
    }
    @RequestMapping(value = "/sendOut/{id}", method = RequestMethod.POST)
    @BussinessLog(value = "发货", key = "id")
    public Object sendOut(@PathVariable("id") Long id,
                          @RequestParam("idExpress") Long idExpress,
                          @RequestParam("shippingSn") String shippingSn,
                          @RequestParam(value = "shippingAmount",defaultValue = "0",required = false) String shippingAmount) {
        Order order = orderService.get(id);
        order.setIdExpress(idExpress);
        order.setShippingSn(shippingSn);
        order.setShippingAmount(new BigDecimal(shippingAmount));
        order.setStatus(OrderEnum.OrderStatusEnum.SENDED.getId());
        orderService.send(order);
        return Rets.success();
    }
    @RequestMapping(value = "/comment/{id}", method = RequestMethod.POST)
    @BussinessLog(value = "管理员添加备注", key = "id")
    public Object comment(@PathVariable("id") Long id,@RequestParam("message") String message) {
        Order order = orderService.get(id);
        order.setAdminMessage(message);
        orderService.addComment(order,message);
        return Rets.success();
    }


    @RequestMapping(value = "{orderSn}", method = RequestMethod.GET)
    public Object get(@PathVariable("orderSn") String orderSn) {
        if (orderSn == null) {
            throw new ApplicationException(ApplicationExceptionEnum.REQUEST_NULL);
        }
        Order order = orderService.getByOrderSn(orderSn);
        return Rets.success(order);
    }
    @RequestMapping(value="/getShippingInfo/{shippingSn}/{shipperCode}")
    public Object getShippingInfo(String shippingSn,String shipperCode){
        String apiKdniaoUserid = cfgService.getCfgValue(CfgKey.API_KDNIAO_USERID);
        if (StringUtil.isEmpty(apiKdniaoUserid)) {
            return Rets.failure("你没有配置快递鸟服务参数");
        }
        KdniaoResponse response = kdniaoService.realTimeQuery(shippingSn,shipperCode);
        return Rets.success(response);
    }
}
