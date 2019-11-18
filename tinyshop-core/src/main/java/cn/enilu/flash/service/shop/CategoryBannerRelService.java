package cn.enilu.flash.service.shop;


import cn.enilu.flash.bean.entity.shop.CategoryBannerRel;
import cn.enilu.flash.dao.shop.CategoryBannerRelRepository;

import cn.enilu.flash.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryBannerRelService extends BaseService<CategoryBannerRel,Long,CategoryBannerRelRepository>  {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private CategoryBannerRelRepository categoryBannerRelRepository;

}

