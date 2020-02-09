package cn.enilu.flash.service.shop;


import cn.enilu.flash.bean.entity.shop.Order;
import cn.enilu.flash.bean.entity.shop.OrderItem;
import cn.enilu.flash.bean.entity.shop.OrderLog;
import cn.enilu.flash.bean.enumeration.shop.OrderEnum;
import cn.enilu.flash.bean.vo.query.SearchFilter;
import cn.enilu.flash.dao.shop.OrderItemRepository;
import cn.enilu.flash.dao.shop.OrderLogRepository;
import cn.enilu.flash.dao.shop.OrderRepository;
import cn.enilu.flash.security.JwtUtil;
import cn.enilu.flash.service.BaseService;
import cn.enilu.flash.utils.DateUtil;
import cn.enilu.flash.utils.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService extends BaseService<Order, Long, OrderRepository> {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private OrderLogRepository orderLogRepository;

    /**
     * 获取唯一订单号
     * 时间戳+随机数<br>
     * 建议生产环境使用redis获取唯一订单号
     *
     * @return
     */
    public String getOrderSn() {
        return DateUtil.getAllTime() + RandomUtil.getRandomNumber(6);
    }

    public void save(Order order, List<OrderItem> itemList) {
        order.setOrderSn(getOrderSn());
        insert(order);
        for(OrderItem item:itemList){
            item.setIdOrder(order.getId());
        }
        orderItemRepository.saveAll(itemList);
        OrderLog orderLog = new OrderLog();
        orderLog.setIdOrder(order.getId());
        orderLog.setDescript("生成订单");
        orderLogRepository.save(orderLog);

    }

    /**
     * 用户取消订单
     * @param orderSn
     */
    public void cancel(String orderSn) {
        Order order = getByOrderSn(orderSn);
        order.setStatus(OrderEnum.OrderStatusEnum.CANCEL.getId());
       updateOrder(order);

    }

    public Order getByOrderSn(String orderSn) {
        return get(SearchFilter.build("orderSn", SearchFilter.Operator.EQ,orderSn));
    }

    /**
     * 确认收货
     * @param orderSn
     */
    public Order confirmReceive(String orderSn) {
        Order order = getByOrderSn(orderSn);
        order.setStatus(OrderEnum.OrderStatusEnum.FINISHED.getId());
        updateOrder(order);
        return order;
    }

    public void updateOrder(Order order) {
        int status = order.getStatus();
        String descript = "";

        if(status == OrderEnum.OrderStatusEnum.CANCEL.getId().intValue()){
            descript = "用户取消订单";
        }
        if(status == OrderEnum.OrderStatusEnum.SENDED.getId().intValue()){
            descript = "管理员("+JwtUtil.getUsername()+")已发货";
         }
        if(status == OrderEnum.OrderStatusEnum.FINISHED.getId().intValue()){
            descript = "用户确认收货";
        }
         OrderLog orderLog = new OrderLog();
         orderLog.setIdOrder(order.getId());
         orderLog.setDescript(descript);
         orderLogRepository.save(orderLog);
         update(order);
    }


    /**
     * 管理员添加备注信息
     * @param order
     * @param message
     */
    public void addComment(Order order, String message) {
        order.setAdminMessage(message);
        update(order);
        OrderLog orderLog = new OrderLog();
        orderLog.setIdOrder(order.getId());
        orderLog.setDescript("管理员("+ JwtUtil.getUsername() +")添加备注："+message);
        orderLogRepository.save(orderLog);
    }
}

