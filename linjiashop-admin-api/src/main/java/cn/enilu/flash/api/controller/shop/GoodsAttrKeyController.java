package cn.enilu.flash.api.controller.shop;

import cn.enilu.flash.bean.entity.shop.AttrKey;
import cn.enilu.flash.service.shop.AttrKeyService;

import cn.enilu.flash.bean.core.BussinessLog;
import cn.enilu.flash.bean.constant.factory.PageFactory;
import cn.enilu.flash.bean.dictmap.CommonDict;
import cn.enilu.flash.bean.enumeration.BizExceptionEnum;
import cn.enilu.flash.bean.exception.ApplicationException;
import cn.enilu.flash.bean.vo.front.Rets;

import cn.enilu.flash.utils.StringUtil;
import cn.enilu.flash.utils.factory.Page;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shop/goods/attr/key")
public class GoodsAttrKeyController {
	private  Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private AttrKeyService goodsAttrKeyService;

	@RequestMapping(value = "/list",method = RequestMethod.GET)
	public Object list() {
	Page<AttrKey> page = new PageFactory<AttrKey>().defaultPage();
		page = goodsAttrKeyService.queryPage(page);
		return Rets.success(page);
	}
	@RequestMapping(method = RequestMethod.POST)
	@BussinessLog(value = "编辑商品属性名", key = "name",dict= CommonDict.class)
	public Object save(@ModelAttribute AttrKey tShopGoodsAttrKey){
		if(tShopGoodsAttrKey.getId()==null){
			goodsAttrKeyService.insert(tShopGoodsAttrKey);
		}else {
			goodsAttrKeyService.update(tShopGoodsAttrKey);
		}
		return Rets.success();
	}
	@RequestMapping(method = RequestMethod.DELETE)
	@BussinessLog(value = "删除商品属性名", key = "id",dict= CommonDict.class)
	public Object remove(Long id){
		if (StringUtil.isEmpty(id)) {
			throw new ApplicationException(BizExceptionEnum.REQUEST_NULL);
		}
		goodsAttrKeyService.deleteById(id);
		return Rets.success();
	}
}
