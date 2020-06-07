package cn.enilu.flash.api.controller.shop;

import cn.enilu.flash.bean.core.BussinessLog;
import cn.enilu.flash.bean.entity.shop.AttrKey;
import cn.enilu.flash.bean.entity.shop.AttrVal;
import cn.enilu.flash.bean.exception.ApplicationException;
import cn.enilu.flash.bean.exception.ApplicationExceptionEnum;
import cn.enilu.flash.bean.vo.front.Rets;
import cn.enilu.flash.bean.vo.query.SearchFilter;
import cn.enilu.flash.service.shop.AttrKeyService;
import cn.enilu.flash.service.shop.AttrValService;
import cn.enilu.flash.utils.Lists;
import cn.enilu.flash.utils.Maps;
import cn.enilu.flash.utils.StringUtil;
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

    @RequestMapping(value = "getAttrVals", method = RequestMethod.GET)
    public Object getAttrVals(@RequestParam("idAttrKey")Long idAttrKey) {
        List<AttrVal> list  = attrValService.queryAll(SearchFilter.build("idAttrKey",idAttrKey));
        return Rets.success(list);
    }
    @RequestMapping(method = RequestMethod.POST)
    @BussinessLog(value = "编辑商品属性值", key = "attrVal")
    public Object save(@RequestParam("idAttrKey")Long idAttrKey,
                       @RequestParam(value = "id",required = false)Long id,
                       @RequestParam("attrVal")String attrVal) {
        AttrVal entity = new AttrVal();
        if(id!=null){
            entity = attrValService.get(id);
        }
        entity = attrValService.get(Lists.newArrayList(
                SearchFilter.build("idAttrKey",idAttrKey),
                SearchFilter.build("attrVal",attrVal)
        ));
        if (id == null) {
            if(entity==null) {
                entity = new AttrVal();
                entity.setIdAttrKey(idAttrKey);
                entity.setAttrVal(attrVal);
                attrValService.insert(entity);
            }else{
                return  Rets.failure("不能添加重复的规格");
            }
        } else {
            if(entity!=null){
                return  Rets.failure("不能添加重复的规格");
            }else {
                entity = new AttrVal();
                entity.setIdAttrKey(idAttrKey);
                entity.setAttrVal(attrVal);
                attrValService.update(entity);
            }
        }
        return Rets.success();
    }

    @RequestMapping(method = RequestMethod.DELETE)
    @BussinessLog(value = "删除商品属性值", key = "id")
    public Object remove(Long id) {
        if (StringUtil.isEmpty(id)) {
            throw new ApplicationException(ApplicationExceptionEnum.REQUEST_NULL);
        }
        attrValService.deleteById(id);
        return Rets.success();
    }
    @RequestMapping(value="updateAttrVal",method = RequestMethod.POST)
    @BussinessLog(value = "修改商品属性值", key = "id")
    public Object updateAttrName(@RequestParam("id") Long id,@RequestParam("attrVal") String attrVal){
        if (StringUtil.isEmpty(id)) {
            throw new ApplicationException(ApplicationExceptionEnum.REQUEST_NULL);
        }
        AttrVal attrValEntity = attrValService.get(id);
        attrValEntity.setAttrVal(attrVal);
        attrValService.update(attrValEntity);
        return Rets.success();
    }
}
