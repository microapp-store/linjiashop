package cn.enilu.flash.service.shop;


import cn.enilu.flash.bean.entity.shop.Goods;
import cn.enilu.flash.dao.shop.GoodsRepository;

import cn.enilu.flash.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GoodsService extends BaseService<Goods,Long,GoodsRepository>  {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private GoodsRepository goodsRepository;

}

