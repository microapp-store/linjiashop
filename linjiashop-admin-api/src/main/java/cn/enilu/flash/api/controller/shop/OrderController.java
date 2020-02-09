package cn.enilu.flash.api.controller.shop;

import cn.enilu.flash.bean.constant.factory.PageFactory;
import cn.enilu.flash.bean.core.BussinessLog;
import cn.enilu.flash.bean.dictmap.CommonDict;
import cn.enilu.flash.bean.entity.shop.Order;
import cn.enilu.flash.bean.entity.system.FileInfo;
import cn.enilu.flash.bean.enumeration.BizExceptionEnum;
import cn.enilu.flash.bean.enumeration.shop.OrderEnum;
import cn.enilu.flash.bean.exception.ApplicationException;
import cn.enilu.flash.bean.vo.front.Rets;
import cn.enilu.flash.bean.vo.query.SearchFilter;
import cn.enilu.flash.security.JwtUtil;
import cn.enilu.flash.service.shop.OrderService;
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

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Object list(@RequestParam(value = "mobile", required = false) String mobile,
                       @RequestParam(value = "orderSn", required = false) String orderSn) {
        Page<Order> page = new PageFactory<Order>().defaultPage();
        page.addFilter("user.mobile", mobile);
        page.addFilter("orderSn", orderSn);
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
    @BussinessLog(value = "发货", key = "id", dict = CommonDict.class)
    public Object sendOut(@PathVariable("id") Long id,
                          @RequestParam("idExpress") Long idExpress,
                          @RequestParam("shippingSn") String shippingSn,
                          @RequestParam(value = "shippingAmount",defaultValue = "0",required = false) String shippingAmount) {
        Order order = orderService.get(id);
        order.setIdExpress(idExpress);
        order.setShippingSn(shippingSn);
        order.setShippingAmount(new BigDecimal(shippingAmount));
        order.setStatus(OrderEnum.OrderStatusEnum.SENDED.getId());
        orderService.updateOrder(order);
        return Rets.success();
    }
    @RequestMapping(value = "/comment/{id}", method = RequestMethod.POST)
    @BussinessLog(value = "管理员添加备注", key = "id", dict = CommonDict.class)
    public Object comment(@PathVariable("id") Long id,@RequestParam("message") String message) {
        Order order = orderService.get(id);
        order.setAdminMessage(message);
        orderService.addComment(order,message);
        return Rets.success();
    }

    @RequestMapping(method = RequestMethod.DELETE)
    @BussinessLog(value = "删除订单", key = "id", dict = CommonDict.class)
    public Object remove(Long id) {
        if (id == null) {
            throw new ApplicationException(BizExceptionEnum.REQUEST_NULL);
        }
        Order order = orderService.get(id);
        order.setStatus(OrderEnum.OrderStatusEnum.CANCEL.getId());
        orderService.updateOrder(order);
        return Rets.success();
    }

    @RequestMapping(value = "{orderSn}", method = RequestMethod.GET)
    public Object get(@PathVariable("orderSn") String orderSn) {
        if (orderSn == null) {
            throw new ApplicationException(BizExceptionEnum.REQUEST_NULL);
        }
        Order order = orderService.getByOrderSn(orderSn);
        return Rets.success(order);
    }
}
