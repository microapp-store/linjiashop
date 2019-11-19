package cn.enilu.flash.mobile.controller;

import cn.enilu.flash.bean.constant.factory.PageFactory;
import cn.enilu.flash.bean.entity.shop.Goods;
import cn.enilu.flash.bean.vo.front.Rets;
import cn.enilu.flash.bean.vo.query.SearchFilter;
import cn.enilu.flash.service.shop.GoodsService;
import cn.enilu.flash.utils.StringUtil;
import cn.enilu.flash.utils.factory.Page;
import cn.enilu.flash.web.controller.BaseController;
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

    /**
     * 获取指定类别下的商品列表
     * @param idCategory
     * @return
     */
    @RequestMapping(value = "/queryGoods",method = RequestMethod.GET)
    public Object queryGoods(@RequestParam("idCategory") Long idCategory){
        Page<Goods> page = new PageFactory<Goods>().defaultPage();
        page.addFilter(SearchFilter.build("idCategory", SearchFilter.Operator.EQ,idCategory));
        page.addFilter(SearchFilter.build("isOnSale",true));
        page = goodsService.queryPage(page);
        return Rets.success(page);
    }

    /**
     * 根据关键字搜索商品
     * @param key
     * @return
     */
    @RequestMapping(value = "/search",method = RequestMethod.GET)
    public Object search(@RequestParam("key") String key){
        Page<Goods> page = new PageFactory<Goods>().defaultPage();
        if(StringUtil.isNotEmpty(key)) {
            page.addFilter(SearchFilter.build("name", SearchFilter.Operator.LIKE, key));
        }
        page.addFilter(SearchFilter.build("isOnSale",true));
        page = goodsService.queryPage(page);
        return Rets.success(page);
    }

    /**
     * 查询热门商品
     * todo 暂无实现具体逻辑
     * @return
     */
    @RequestMapping(value = "/searchHot",method = RequestMethod.GET)
    public Object searchHot(){
        Page<Goods> page = new PageFactory<Goods>().defaultPage();
        page.addFilter(SearchFilter.build("isOnSale",true));
        page = goodsService.queryPage(page);
        return Rets.success(page);
    }
    @RequestMapping(value="/{id}",method = RequestMethod.GET)
    public Object get(@PathVariable Long id){
        Goods goods = goodsService.get(id);
        return Rets.success(goods);
    }
}
