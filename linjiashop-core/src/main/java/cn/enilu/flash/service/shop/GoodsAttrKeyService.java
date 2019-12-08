package cn.enilu.flash.service.shop;


import cn.enilu.flash.bean.entity.shop.GoodsAttrKey;
import cn.enilu.flash.dao.shop.GoodsAttrKeyRepository;

import cn.enilu.flash.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GoodsAttrKeyService extends BaseService<GoodsAttrKey,Long,GoodsAttrKeyRepository>  {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private GoodsAttrKeyRepository goodsAttrKeyRepository;

}

