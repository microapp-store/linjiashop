package cn.enilu.flash.service.shop;


import cn.enilu.flash.bean.entity.shop.Category;
import cn.enilu.flash.dao.shop.CategoryRepository;

import cn.enilu.flash.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService extends BaseService<Category,Long,CategoryRepository>  {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private CategoryRepository categoryRepository;

}

