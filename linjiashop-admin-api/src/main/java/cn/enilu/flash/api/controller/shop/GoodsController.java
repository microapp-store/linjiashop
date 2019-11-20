package cn.enilu.flash.api.controller.shop;

import cn.enilu.flash.bean.constant.factory.PageFactory;
import cn.enilu.flash.bean.core.BussinessLog;
import cn.enilu.flash.bean.dictmap.CommonDict;
import cn.enilu.flash.bean.entity.shop.Goods;
import cn.enilu.flash.bean.enumeration.BizExceptionEnum;
import cn.enilu.flash.bean.enumeration.Permission;
import cn.enilu.flash.bean.exception.ApplicationException;
import cn.enilu.flash.bean.vo.front.Rets;
import cn.enilu.flash.bean.vo.query.SearchFilter;
import cn.enilu.flash.service.shop.GoodsService;
import cn.enilu.flash.utils.factory.Page;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.nutz.json.Json;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/shop/goods")
public class GoodsController {
	private  Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private GoodsService goodsService;

	@RequestMapping(value = "/list",method = RequestMethod.GET)
	public Object list(@RequestParam(value = "name",required = false) String name) {
	Page<Goods> page = new PageFactory<Goods>().defaultPage();
		page.addFilter("name", SearchFilter.Operator.LIKE,name);
		page = goodsService.queryPage(page);
		return Rets.success(page);
	}
	@RequestMapping(method = RequestMethod.POST)
	@BussinessLog(value = "编辑商品", key = "name",dict= CommonDict.class)
	@RequiresPermissions(value = {Permission.GOODS_EDIT})
	public Object save(@RequestBody @Valid Goods tShopGoods){
		logger.info(Json.toJson(tShopGoods));

		if(tShopGoods.getId()==null){
			goodsService.insert(tShopGoods);
		}else {
			goodsService.update(tShopGoods);
		}
		return Rets.success();
	}
	@RequestMapping(method = RequestMethod.DELETE)
	@BussinessLog(value = "删除商品", key = "id",dict= CommonDict.class)
	@RequiresPermissions(value = {Permission.GOODS_EDIT})
	public Object remove(Long id){
		if (id == null) {
			throw new ApplicationException(BizExceptionEnum.REQUEST_NULL);
		}
		goodsService.deleteById(id);
		return Rets.success();
	}
	@RequestMapping(method = RequestMethod.GET)
	public Object get(Long id){
		if (id == null) {
			throw new ApplicationException(BizExceptionEnum.REQUEST_NULL);
		}
		return Rets.success(goodsService.get(id));
	}
	@RequestMapping(value="/changeIsOnSale",method = RequestMethod.POST)
	@RequiresPermissions(value = {Permission.GOODS_EDIT})
	public Object changeIsOnSale(@RequestParam("id")  Long id,@RequestParam("isOnSale") Boolean isOnSale){
		if (id == null) {
			throw new ApplicationException(BizExceptionEnum.REQUEST_NULL);
		}
		goodsService.changeIsOnSale(id,isOnSale);
		return Rets.success(goodsService.get(id));
	}
}
