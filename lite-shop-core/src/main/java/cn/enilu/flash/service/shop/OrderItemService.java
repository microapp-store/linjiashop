package cn.enilu.flash.service.shop;


import cn.enilu.flash.bean.entity.shop.OrderItem;
import cn.enilu.flash.dao.shop.OrderItemRepository;

import cn.enilu.flash.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderItemService extends BaseService<OrderItem,Long,OrderItemRepository>  {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private OrderItemRepository orderItemRepository;

}

