package cn.enilu.flash.service.shop;


import cn.enilu.flash.bean.entity.shop.Order;
import cn.enilu.flash.bean.entity.shop.OrderItem;
import cn.enilu.flash.dao.shop.OrderItemRepository;
import cn.enilu.flash.dao.shop.OrderRepository;
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
    }

    /**
     * 取消订单
     * @param id
     */
    public void cancel(Long id) {
        Order order = get(id);
        List<OrderItem> itemList = order.getItems();
        delete(order);
        orderItemRepository.deleteInBatch(itemList);

    }
}

