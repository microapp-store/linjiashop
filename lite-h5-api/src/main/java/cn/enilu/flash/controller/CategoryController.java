package cn.enilu.flash.controller;

import cn.enilu.flash.bean.constant.factory.PageFactory;
import cn.enilu.flash.bean.entity.shop.Category;
import cn.enilu.flash.bean.vo.front.Rets;
import cn.enilu.flash.service.shop.CategoryService;
import cn.enilu.flash.utils.factory.Page;
import cn.enilu.flash.web.BaseController;
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
@RequestMapping("/shop/category")
public class CategoryController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public Object list() {
        List<Category> list = categoryService.queryAll();
        return Rets.success(list);
    }
}
