package cn.enilu.flash.mobile.controller;

import cn.enilu.flash.bean.constant.factory.PageFactory;
import cn.enilu.flash.bean.entity.shop.AttrKey;
import cn.enilu.flash.bean.entity.shop.AttrVal;
import cn.enilu.flash.bean.entity.shop.Goods;
import cn.enilu.flash.bean.entity.shop.GoodsSku;
import cn.enilu.flash.bean.vo.front.Rets;
import cn.enilu.flash.bean.vo.query.SearchFilter;
import cn.enilu.flash.service.shop.AttrKeyService;
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

    @RequestMapping(value = "/searchNew", method = RequestMethod.GET)
    public Object searchNew() {
        List<Goods> list = goodsService.queryAll(Lists.newArrayList(
                SearchFilter.build("isNew", true),
                SearchFilter.build("isOnSale", true)
        ));
        return Rets.success(list);
    }

    /**
     * 查询热门商品
     *
     * @return
     */
    @RequestMapping(value = "/searchHot", method = RequestMethod.GET)
    public Object searchHot() {
        List<Goods> list = goodsService.queryAll(Lists.newArrayList(
                SearchFilter.build("isHot", true),
                SearchFilter.build("isOnSale", true)
        ));
        return Rets.success(list);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Object get(@PathVariable Long id) {
        Goods goods = goodsService.get(id);
        List<GoodsSku> skuList = goodsSkuService.queryAll(Lists.newArrayList(
                SearchFilter.build("idGoods", id),
                SearchFilter.build("isDeleted",false)
        ));


        Map skuMap = Maps.newHashMap();

        List<Map> tree = Lists.newArrayList();

        if (!skuList.isEmpty()) {
            List<AttrVal> attrValList = Lists.newArrayList();
            List<AttrKey> attrKeyList = attrKeyService.queryBy(goods.getIdCategory());
            for (AttrKey attrKey : attrKeyList) {
                Map treeNode = Maps.newHashMap();
                treeNode.put("k", attrKey.getAttrName());
                List<Map> v = Lists.newArrayList();
                List<AttrVal> attrValListChildren = attrKey.getAttrVals();
                attrValList.addAll(attrValListChildren);
                for (AttrVal attrVal : attrValListChildren) {
                    v.add(Maps.newHashMap(
                            "id", attrVal.getId(),
                            "name", attrVal.getAttrVal(),
                            "plain", true
                    ));
                }
                treeNode.put("v", v);
                treeNode.put("k_s", "s" + attrKey.getId());
                tree.add(treeNode);
            }
            Map<Long, AttrVal> attrValMap = Lists.toMap(attrValList, "id");
            List<Map> skuList2 = Lists.newArrayList();

            for (GoodsSku sku : skuList) {
                Map oneSkuMap = Maps.newHashMap();
                oneSkuMap.put("id", sku.getId());
                oneSkuMap.put("price", sku.getPrice());
                String[] attrValIdArr = sku.getCode().split(",");
                for (String attrValId : attrValIdArr) {
                    AttrVal attrVal = attrValMap.get(Long.valueOf(attrValId));
                    oneSkuMap.put("s" + attrVal.getIdAttrKey(), attrVal.getId());
                }
                oneSkuMap.put("stock_num", sku.getStock());
                oneSkuMap.put("code", sku.getCode());
                skuList2.add(oneSkuMap);
            }
            skuMap.put("list", skuList2);
            skuMap.put("price", skuList.get(0).getPrice());
            skuMap.put("collection_id", skuList.get(0).getId());
            skuMap.put("none_sku", false);
        } else {
            skuMap.put("price", goods.getPrice());
            skuMap.put("collection_id", goods.getId());
            skuMap.put("none_sku", true);
        }
        skuMap.put("tree", tree);
        skuMap.put("stock_num", goods.getStock());
        skuMap.put("hide_stock", false);
        return Rets.success(Maps.newHashMap(
                "goods", goods,
                "sku", skuMap
        ));
    }
}
