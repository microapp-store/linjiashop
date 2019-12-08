package cn.enilu.flash.api.controller.shop;

import cn.enilu.flash.bean.entity.shop.GoodsAttrVal;
import cn.enilu.flash.service.shop.GoodsAttrValService;

import cn.enilu.flash.bean.core.BussinessLog;
import cn.enilu.flash.bean.constant.factory.PageFactory;
import cn.enilu.flash.bean.dictmap.CommonDict;
import cn.enilu.flash.bean.enumeration.BizExceptionEnum;
import cn.enilu.flash.bean.exception.ApplicationException;
import cn.enilu.flash.bean.vo.front.Rets;

import cn.enilu.flash.utils.Maps;
import cn.enilu.flash.utils.StringUtil;
import cn.enilu.flash.utils.ToolUtil;
import cn.enilu.flash.utils.factory.Page;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shop/goods/attr/val")
public class GoodsAttrValController {
	private  Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private GoodsAttrValService goodsAttrValService;

	@RequestMapping(value = "/list",method = RequestMethod.GET)
	public Object list() {
	Page<GoodsAttrVal> page = new PageFactory<GoodsAttrVal>().defaultPage();
		page = goodsAttrValService.queryPage(page);
		return Rets.success(page);
	}
	@RequestMapping(method = RequestMethod.POST)
	@BussinessLog(value = "编辑商品属性值", key = "name",dict= CommonDict.class)
	public Object save(@ModelAttribute GoodsAttrVal tShopGoodsAttrVal){
		if(tShopGoodsAttrVal.getId()==null){
			goodsAttrValService.insert(tShopGoodsAttrVal);
		}else {
			goodsAttrValService.update(tShopGoodsAttrVal);
		}
		return Rets.success();
	}
	@RequestMapping(method = RequestMethod.DELETE)
	@BussinessLog(value = "删除商品属性值", key = "id",dict= CommonDict.class)
	public Object remove(Long id){
		if (StringUtil.isEmpty(id)) {
			throw new ApplicationException(BizExceptionEnum.REQUEST_NULL);
		}
		goodsAttrValService.deleteById(id);
		return Rets.success();
	}
}
