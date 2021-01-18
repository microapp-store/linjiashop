package cn.enilu.flash.service.shop;


import cn.enilu.flash.bean.entity.shop.ExpressInfo;
import cn.enilu.flash.bean.entity.shop.Order;
import cn.enilu.flash.dao.shop.ExpressInfoRepository;

import cn.enilu.flash.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExpressInfoService extends BaseService<ExpressInfo,Long,ExpressInfoRepository>  {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ExpressInfoRepository expressInfoRepository;


}

