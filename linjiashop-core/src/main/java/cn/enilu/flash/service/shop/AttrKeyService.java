package cn.enilu.flash.service.shop;


import cn.enilu.flash.bean.constant.cache.Cache;
import cn.enilu.flash.bean.entity.shop.AttrKey;
import cn.enilu.flash.bean.vo.query.SearchFilter;
import cn.enilu.flash.dao.shop.AttrKeyRepository;
import cn.enilu.flash.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttrKeyService extends BaseService<AttrKey,Long, AttrKeyRepository>  {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private AttrKeyRepository attrKeyRepository;
    @CacheEvict(value = Cache.APPLICATION, key = "#root.targetClass.simpleName+':'+#idCategory")
    public List<AttrKey> queryBy(Long idCategory ){
        return queryAll(SearchFilter.build("idCategory",idCategory));
    }

}

