package cn.enilu.flash.mobile.controller;

import cn.enilu.flash.bean.constant.factory.PageFactory;
import cn.enilu.flash.bean.entity.shop.AttrKey;
import cn.enilu.flash.bean.entity.shop.AttrVal;
import cn.enilu.flash.bean.entity.shop.Goods;
import cn.enilu.flash.bean.entity.shop.GoodsSku;
import cn.enilu.flash.bean.vo.front.Rets;
import cn.enilu.flash.bean.vo.query.SearchFilter;
import cn.enilu.flash.service.shop.AttrKeyService;
import cn.enilu.flash.service.shop.AttrValService;
import cn.enilu.flash.service.shop.GoodsService;
import cn.enilu.flash.service.shop.GoodsSkuService;
import cn.enilu.flash.utils.Lists;
import cn.enilu.flash.utils.Maps;
import cn.enilu.flash.utils.StringUtil;
import cn.enilu.flash.utils.factory.Page;
import cn.enilu.flash.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author ：enilu
 * @date ：Created in 11/5/2019 11:16 AM
 */
@RestController
@RequestMapping("/goods")
public class GoodsController extends BaseController {
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private GoodsSkuService goodsSkuService;
    @Autowired
    private AttrValService attrValService;
    @Autowired
    private AttrKeyService attrKeyService;

    /**
     * 获取指定类别下的商品列表
     *
     * @param idCategory
     * @return
     */
    @RequestMapping(value = "/queryGoods", method = RequestMethod.GET)
    public Object queryGoods(@RequestParam("idCategory") Long idCategory) {
        Page<Goods> page = new PageFactory<Goods>().defaultPage();
        page.addFilter(SearchFilter.build("idCategory", SearchFilter.Operator.EQ, idCategory));
        page.addFilter(SearchFilter.build("isOnSale", true));
        page = goodsService.queryPage(page);
        return Rets.success(page);
    }

    /**
     * 根据关键字搜索商品
     *
     * @param key
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public Object search(@RequestParam("key") String key) {
        Page<Goods> page = new PageFactory<Goods>().defaultPage();
        if (StringUtil.isNotEmpty(key)) {
            page.addFilter(SearchFilter.build("name", SearchFilter.Operator.LIKE, key));
        }
        page.addFilter(SearchFilter.build("isOnSale", true));
        page = goodsService.queryPage(page);
        return Rets.success(page);
    }

    /**
     * 查询热门商品
     * todo 暂无实现具体逻辑
     *
     * @return
     */
    @RequestMapping(value = "/searchHot", method = RequestMethod.GET)
    public Object searchHot() {
        Page<Goods> page = new PageFactory<Goods>().defaultPage();
        page.addFilter(SearchFilter.build("isOnSale", true));
        page = goodsService.queryPage(page);
        return Rets.success(page);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Object get(@PathVariable Long id) {
        Goods goods = goodsService.get(id);
        List<GoodsSku> skuList = goodsSkuService.queryAll(Lists.newArrayList(
                SearchFilter.build("idGoods", id)
        ));
        List<AttrVal> attrValList = attrValService.queryBy(goods.getIdCategory());
        List<AttrKey> attrKeyList = attrKeyService.queryBy(goods.getIdCategory());

        Map<Long, List<AttrVal>> group = Lists.group(attrValList, "idAttrKey");

        Map skuVo = Maps.newHashMap();
        List<Map> tree = Lists.newArrayList();
        for (AttrKey attrKey : attrKeyList) {
            Map treeNode = Maps.newHashMap();
            treeNode.put("k", attrKey.getAttrName());
            List<Map> v = Lists.newArrayList();
            List<AttrVal> attrValListChildren = group.get(attrKey.getId());
            for (AttrVal attrVal : attrValListChildren) {
                v.add(Maps.newHashMap(
                        "id", attrVal.getId(),
                        "name", attrVal.getAttrVal()
                ));
            }
            treeNode.put("v", v);
            tree.add(treeNode);
        }
        skuVo.put("tree", tree);
        return Rets.success(Maps.newHashMap(
                "goods", goods,
                "skuList", skuList
        ));
    }
}
