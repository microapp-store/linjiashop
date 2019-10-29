package cn.enilu.flash.service.shop;


import cn.enilu.flash.bean.entity.shop.ShopUser;
import cn.enilu.flash.dao.shop.ShopUserRepository;

import cn.enilu.flash.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShopUserService extends BaseService<ShopUser,Long,ShopUserRepository>  {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ShopUserRepository shopUserRepository;

}

