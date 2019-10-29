package cn.enilu.flash.service.shop;


import cn.enilu.flash.bean.entity.shop.Cart;
import cn.enilu.flash.dao.shop.CartRepository;

import cn.enilu.flash.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService extends BaseService<Cart,Long,CartRepository>  {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private CartRepository cartRepository;

}

