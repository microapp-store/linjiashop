package cn.enilu.flash.api.controller.shop;

import cn.enilu.flash.bean.constant.factory.PageFactory;
import cn.enilu.flash.bean.core.BussinessLog;
import cn.enilu.flash.bean.entity.shop.AttrKey;
import cn.enilu.flash.bean.exception.ApplicationException;
import cn.enilu.flash.bean.exception.ApplicationExceptionEnum;
import cn.enilu.flash.bean.vo.front.Rets;
import cn.enilu.flash.service.shop.AttrKeyService;
import cn.enilu.flash.utils.StringUtil;
import cn.enilu.flash.utils.factory.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shop/goods/attr/key")
public class AttrKeyController {
	private  Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private AttrKeyService attrKeyService;

	@RequestMapping(value = "/list",method = RequestMethod.GET)
	public Object list() {
	Page<AttrKey> page = new PageFactory<AttrKey>().defaultPage();
		page = attrKeyService.queryPage(page);
		return Rets.success(page);
	}
	@RequestMapping(method = RequestMethod.POST)
	@BussinessLog(value = "编辑商品属性名", key = "name")
	public Object save(@ModelAttribute AttrKey tShopGoodsAttrKey){
		if(tShopGoodsAttrKey.getId()==null){
			attrKeyService.insert(tShopGoodsAttrKey);
		}else {
			attrKeyService.update(tShopGoodsAttrKey);
		}
		return Rets.success();
	}
	@RequestMapping(method = RequestMethod.DELETE)
	@BussinessLog(value = "删除商品属性名", key = "id")
	public Object remove(Long id){
		if (StringUtil.isEmpty(id)) {
			throw new ApplicationException(ApplicationExceptionEnum.REQUEST_NULL);
		}
		AttrKey attrKey = attrKeyService.get(id);
		if(!attrKey.getAttrVals().isEmpty()){
			throw new ApplicationException(ApplicationExceptionEnum.DATA_CANNOT_REMOVE);
		}
		attrKeyService.deleteById(id);
		return Rets.success();
	}
	@RequestMapping(value="updateAttrName",method = RequestMethod.POST)
	@BussinessLog(value = "修改商品属性名", key = "id")
	public Object updateAttrName(@RequestParam("id") Long id,@RequestParam("attrName") String attrName){
		if (StringUtil.isEmpty(id)) {
			throw new ApplicationException(ApplicationExceptionEnum.REQUEST_NULL);
		}
		AttrKey attrKey = attrKeyService.get(id);
		attrKey.setAttrName(attrName);
		attrKeyService.update(attrKey);
		return Rets.success();
	}
}
