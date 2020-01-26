package cn.enilu.flash.service.shop;


import cn.enilu.flash.bean.entity.shop.Favorite;
import cn.enilu.flash.bean.vo.query.SearchFilter;
import cn.enilu.flash.dao.shop.FavoriteRepository;

import cn.enilu.flash.service.BaseService;
import cn.enilu.flash.utils.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FavoriteService extends BaseService<Favorite,Long,FavoriteRepository>  {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private FavoriteRepository favoriteRepository;

    public Favorite get(Long idUser, Long idGoods) {
        return get(Lists.newArrayList(
                SearchFilter.build("idUser",idUser),
                SearchFilter.build("idGoods",idGoods)
        ));

    }
}

