package cn.enilu.flash.mobile.controller;

import cn.enilu.flash.bean.constant.factory.PageFactory;
import cn.enilu.flash.bean.entity.shop.Goods;
import cn.enilu.flash.bean.vo.front.Rets;
import cn.enilu.flash.bean.vo.query.SearchFilter;
import cn.enilu.flash.service.shop.GoodsService;
import cn.enilu.flash.utils.factory.Page;
import cn.enilu.flash.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author ：enilu
 * @date ：Created in 11/5/2019 11:16 AM
 */
@RestController
@RequestMapping("/goods")
public class GoodsController extends BaseController {
    @Autowired
    private GoodsService goodsService;
    @RequestMapping(value = "/queryGoods",method = RequestMethod.GET)
    public Object queryGoods(@RequestParam("idCategory") Long idCategory){
        Page<Goods> page = new PageFactory<Goods>().defaultPage();
        page.addFilter(SearchFilter.build("idCategory", SearchFilter.Operator.EQ,idCategory));
        page = goodsService.queryPage(page);
        return Rets.success(page);
    }
    @RequestMapping(value="/{id}",method = RequestMethod.GET)
    public Object get(@PathVariable Long id){
        Goods goods = goodsService.get(id);
        return Rets.success(goods);
    }
}
