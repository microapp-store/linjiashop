package cn.enilu.flash.api.controller.shop;

import cn.enilu.flash.bean.constant.factory.PageFactory;
import cn.enilu.flash.bean.core.BussinessLog;

import cn.enilu.flash.bean.entity.shop.AttrKey;
import cn.enilu.flash.bean.entity.shop.AttrVal;
import cn.enilu.flash.bean.enumeration.BizExceptionEnum;
import cn.enilu.flash.bean.exception.ApplicationException;
import cn.enilu.flash.bean.vo.front.Rets;
import cn.enilu.flash.bean.vo.query.SearchFilter;
import cn.enilu.flash.service.shop.AttrKeyService;
import cn.enilu.flash.service.shop.AttrValService;
import cn.enilu.flash.utils.Lists;
import cn.enilu.flash.utils.Maps;
import cn.enilu.flash.utils.StringUtil;
import cn.enilu.flash.utils.factory.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shop/attr/val")
public class AttrValController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private AttrValService attrValService;
    @Autowired
    private AttrKeyService attrKeyService;

    @RequestMapping(value = "/getAttrByCategoryAndGoods/{idCategory}",method = RequestMethod.GET)
    public Object getAttrByCategoryAndGoods(@PathVariable("idCategory") Long idCategory) {
        List<AttrKey> keyList = attrKeyService.queryBy(idCategory);
        List<Long> idAttrKeyList = Lists.newArrayList();
        for(AttrKey attrKey:keyList){
            idAttrKeyList.add(attrKey.getId());
        }
        List<AttrVal> valList = Lists.newArrayList();
        if(!idAttrKeyList.isEmpty()) {
            valList = attrValService.queryAll(SearchFilter.build("idAttrKey", SearchFilter.Operator.IN, idAttrKeyList));
        }
        return Rets.success(Maps.newHashMap(
                "keyList", keyList,
                "valList", valList
        ));
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Object list() {
        Page<AttrVal> page = new PageFactory<AttrVal>().defaultPage();
        page = attrValService.queryPage(page);
        return Rets.success(page);
    }

    @RequestMapping(method = RequestMethod.POST)
    @BussinessLog(value = "编辑商品属性值", key = "name")
    public Object save(@ModelAttribute AttrVal tShopGoodsAttrVal) {
        if (tShopGoodsAttrVal.getId() == null) {
            attrValService.insert(tShopGoodsAttrVal);
        } else {
            attrValService.update(tShopGoodsAttrVal);
        }
        return Rets.success();
    }

    @RequestMapping(method = RequestMethod.DELETE)
    @BussinessLog(value = "删除商品属性值", key = "id")
    public Object remove(Long id) {
        if (StringUtil.isEmpty(id)) {
            throw new ApplicationException(BizExceptionEnum.REQUEST_NULL);
        }
        attrValService.deleteById(id);
        return Rets.success();
    }
}
