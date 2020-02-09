package cn.enilu.flash.service.shop;


import cn.enilu.flash.bean.entity.shop.OrderLog;
import cn.enilu.flash.dao.shop.OrderLogRepository;

import cn.enilu.flash.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderLogService extends BaseService<OrderLog,Long,OrderLogRepository>  {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private OrderLogRepository orderLogRepository;

}

