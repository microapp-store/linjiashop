package cn.enilu.flash.mobile.controller;

import cn.enilu.flash.bean.entity.cms.Banner;
import cn.enilu.flash.bean.entity.shop.Category;
import cn.enilu.flash.bean.entity.shop.CategoryBannerRel;
import cn.enilu.flash.bean.vo.front.Rets;
import cn.enilu.flash.bean.vo.query.SearchFilter;
import cn.enilu.flash.service.shop.CategoryBannerRelService;
import cn.enilu.flash.service.shop.CategoryService;
import cn.enilu.flash.utils.Lists;
import cn.enilu.flash.web.controller.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ：enilu
 * @date ：Created in 11/4/2019 9:06 PM
 */
@RestController
@RequestMapping("/category")
public class CategoryController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CategoryBannerRelService categoryBannerRelService;
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public Object list() {
        List<Category> list = categoryService.queryAll();
        list.forEach(item->{
            List<CategoryBannerRel> relList = categoryBannerRelService.queryAll(SearchFilter.build("idCategory",item.getId()));
            List<Banner> bannerList = Lists.newArrayList();
            relList.forEach( relItem->{
                bannerList.add(relItem.getBanner());
            });

            item.setBannerList(bannerList);
        });
        return Rets.success(list);
    }
}
