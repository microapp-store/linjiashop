package cn.enilu.flash.service.shop;


import cn.enilu.flash.bean.constant.CfgKey;
import cn.enilu.flash.bean.entity.shop.*;
import cn.enilu.flash.bean.enumeration.shop.OrderEnum;
import cn.enilu.flash.bean.vo.SpringContextHolder;
import cn.enilu.flash.bean.vo.query.SearchFilter;
import cn.enilu.flash.dao.shop.OrderItemRepository;
import cn.enilu.flash.dao.shop.OrderLogRepository;
import cn.enilu.flash.dao.shop.OrderRepository;
import cn.enilu.flash.security.JwtUtil;
import cn.enilu.flash.service.BaseService;
import cn.enilu.flash.service.api.express.ExpressApi;
import cn.enilu.flash.service.system.CfgService;
import cn.enilu.flash.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrderService extends BaseService<Order, Long, OrderRepository> {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private OrderLogRepository orderLogRepository;
    @Autowired
    private ExpressInfoService expressInfoService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private GoodsSkuService goodsSkuService;
    @Autowired
    private CfgService cfgService;

    /**
     * 获取唯一订单号
     * 时间戳+随机数<br>
     * 建议生产环境使用redis获取唯一订单号
     *
     * @return
     */
    public String getOrderSn() {
        return StringUtil.getNewOrderNo();
    }

    public void save(Order order, List<OrderItem> itemList) {
        order.setOrderSn(getOrderSn());
        insert(order);
        for (OrderItem item : itemList) {
            item.setIdOrder(order.getId());
            //减库存
            if(item.getIdSku()!=null) {
                GoodsSku goodsSku = goodsSkuService.get(item.getIdSku());
                goodsSku.setStock(goodsSku.getStock()-item.getCount());
                if(goodsSku.getStock()<0){
                    throw  new RuntimeException("库存不足");
                }
                //更新sku的库存
                goodsSkuService.update(goodsSku);

                //更新商品的库存
                List<GoodsSku> list = goodsSkuService.queryByIdGoods(item.getIdGoods());
                int stock = 0;
                for(GoodsSku sk:list){
                    stock+= goodsSku.getStock();
                }
                Goods goods = goodsService.get(goodsSku.getIdGoods());
                goodsService.update(goods);
            }else{
                Goods goods = goodsService.get(item.getIdGoods());
                //更新商品库存
                goods.setStock(goods.getStock()-item.getCount());
                if(goods.getStock()<0){
                    throw  new RuntimeException("库存不足");
                }
                goodsService.update(goods);
            }

        }
        orderItemRepository.saveAll(itemList);
        OrderLog orderLog = new OrderLog();
        orderLog.setIdOrder(order.getId());
        orderLog.setDescript("生成订单");
        orderLogRepository.save(orderLog);

    }

    public void updateOrder(Order order) {
        update(order);
    }

    /**
     * 用户取消订单
     *
     * @param orderSn
     */
    public void cancel(String orderSn) {
        Order order = getByOrderSn(orderSn);
        order.setStatus(OrderEnum.OrderStatusEnum.CANCEL.getId());
        String descript = "用户取消订单";
        saveOrderLog(order, descript);
        updateOrder(order);

    }

    public Order getByOrderSn(String orderSn) {
        return get(SearchFilter.build("orderSn", SearchFilter.Operator.EQ, orderSn));
    }

    /**
     * 确认收货
     *
     * @param orderSn
     */
    public Order confirmReceive(String orderSn) {
        Order order = getByOrderSn(orderSn);
        order.setStatus(OrderEnum.OrderStatusEnum.FINISHED.getId());
        String descript = "客户确认收货";
        saveOrderLog(order, descript);
        updateOrder(order);
        return order;
    }

    public void startPay(Order order) {
        order.setPayStatus(OrderEnum.PayStatusEnum.PAYING.getId());
        saveOrderLog(order, "客户发起支付");
        updateOrder(order);

    }

    private void saveOrderLog(Order order, String descript) {
        OrderLog orderLog = new OrderLog();
        orderLog.setIdOrder(order.getId());
        orderLog.setDescript(descript);
        orderLogRepository.save(orderLog);
    }


    /**
     * 管理员添加备注信息
     *
     * @param order
     * @param message
     */
    public void addComment(Order order, String message) {
        order.setAdminMessage(message);
        update(order);
        OrderLog orderLog = new OrderLog();
        orderLog.setIdOrder(order.getId());
        orderLog.setDescript("管理员(" + JwtUtil.getUsername() + ")添加备注：" + message);
        orderLogRepository.save(orderLog);
    }

    /**
     * 支付成功，更新订单数据
     *
     * @param order
     * @param payType
     */
    public void paySuccess(Order order, String payType) {
        order.setPayTime(new Date());
        order.setPayStatus(OrderEnum.PayStatusEnum.UN_SEND.getId());
        order.setStatus(OrderEnum.OrderStatusEnum.UN_SEND.getId());
        order.setRealPrice(order.getTotalPrice());
        order.setPayType(payType);
        updateOrder(order);
        String descript = "用户付款成功";
        saveOrderLog(order, descript);

    }

    public void send(Order order) {
        String descript = "管理员(" + JwtUtil.getUsername() + ")已发货";
        updateOrder(order);
        saveOrderLog(order, descript);
    }


    /**
     * 查询指定订单号的快递信息
     *
     * @param orderSn
     * @return
     */
    public ExpressInfo getExpressInfo(String orderSn) {
        Order order = getByOrderSn(orderSn);
        ExpressInfo expressInfo = expressInfoService.get(SearchFilter.build("idOrder", order.getId()));

        if ((expressInfo == null
                || expressInfo.getState() != ExpressInfo.STATE_FINISH) &&
                  order.getStatus() == OrderEnum.OrderStatusEnum.SENDED.getId()) {
            //远程查询
            String expressApiProvider = cfgService.getCfgValue(CfgKey.API_EXPRESS_INFO_QUERY_PROVIDER);
            ExpressApi expressApi = SpringContextHolder.getBean(expressApiProvider);
            ExpressInfo apiResponse = expressApi.realTimeQuery(order.getShippingSn(), order.getExpress().getCode());
            if (StringUtil.isNotEmpty(apiResponse.getInfo())) {
                expressInfo = expressInfoService.get(SearchFilter.build("idOrder", order.getId()));
                if (expressInfo == null) {
                    expressInfo = new ExpressInfo();
                    expressInfo.setIdOrder(order.getId());
                    expressInfo.setExpressCompany(order.getExpress().getName());
                    expressInfo.setShippingSn(order.getShippingSn());
                    expressInfo.setState(apiResponse.getState());
                }
                expressInfo.setInfo(apiResponse.getInfo());
                expressInfo.setState(apiResponse.getState());
                expressInfoService.update(expressInfo);
            }
        }
        return expressInfo;
    }
}

