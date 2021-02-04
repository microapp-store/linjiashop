package cn.enilu.flash.service.task.job;

import cn.enilu.flash.bean.entity.shop.Order;
import cn.enilu.flash.bean.entity.shop.OrderLog;
import cn.enilu.flash.bean.enumeration.shop.OrderEnum;
import cn.enilu.flash.bean.vo.query.SearchFilter;
import cn.enilu.flash.service.shop.OrderLogService;
import cn.enilu.flash.service.shop.OrderService;
import cn.enilu.flash.service.task.JobExecuter;
import cn.enilu.flash.utils.DateUtil;
import cn.enilu.flash.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 将超时未支付的订单设置未取消状态
 * 定时任务配置需要传超时时间，单位为：小时:{"hour":24},定时任务配置频率建议为一小时执行一次
 */
@Component
public class ProcessInvalidOrdersJob extends JobExecuter {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderLogService orderLogService;
    @Override
    public void execute(Map<String, Object> dataMap) throws Exception {
        Integer hour = Integer.valueOf(dataMap.get("hour").toString());
        Date createTime = DateUtil.getDateBefore(hour, Calendar.HOUR);
        System.out.println(createTime);
        List<Order> list = orderService.queryAll(
                Lists.newArrayList(
                        SearchFilter.build("status", OrderEnum.OrderStatusEnum.UN_PAY.getId()),
                        SearchFilter.build("createTime", SearchFilter.Operator.LTE,createTime)
                )
        );
        for(Order order:list){
            order.setStatus(OrderEnum.OrderStatusEnum.CANCEL.getId());
            OrderLog orderLog = new OrderLog();
            orderLog.setIdOrder(order.getId());
            orderLog.setDescript("系统超时取消订单");
            orderLogService.insert(orderLog);
            orderService.updateOrder(order);
        }
    }
}
