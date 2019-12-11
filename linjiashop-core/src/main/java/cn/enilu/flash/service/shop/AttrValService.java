package cn.enilu.flash.service.shop;


import cn.enilu.flash.bean.entity.shop.AttrVal;
import cn.enilu.flash.bean.vo.query.SearchFilter;
import cn.enilu.flash.dao.shop.AttrValRepository;
import cn.enilu.flash.service.BaseService;
import cn.enilu.flash.utils.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttrValService extends BaseService<AttrVal,Long, AttrValRepository>  {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private AttrValRepository attrValRepository;

    public List<AttrVal> queryBy(Long idCategory, Long idGoods) {
        return queryAll(Lists.newArrayList(
                SearchFilter.build("idCategory",idCategory),
                SearchFilter.build("idGoods",idGoods, SearchFilter.Join.or)
        ));
    }
}

