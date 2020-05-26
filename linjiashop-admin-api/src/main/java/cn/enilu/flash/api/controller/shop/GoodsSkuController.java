package cn.enilu.flash.api.controller.shop;

import cn.enilu.flash.bean.constant.factory.PageFactory;
import cn.enilu.flash.bean.core.BussinessLog;
import cn.enilu.flash.bean.entity.shop.GoodsSku;
import cn.enilu.flash.bean.enumeration.Permission;
import cn.enilu.flash.bean.exception.ApplicationException;
import cn.enilu.flash.bean.exception.ApplicationExceptionEnum;
import cn.enilu.flash.bean.vo.front.Rets;
import cn.enilu.flash.bean.vo.query.SearchFilter;
import cn.enilu.flash.service.shop.GoodsSkuService;
import cn.enilu.flash.service.system.LogObjectHolder;
import cn.enilu.flash.utils.Lists;
import cn.enilu.flash.utils.StringUtil;
import cn.enilu.flash.utils.factory.Page;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/shop/goods/sku")
public class GoodsSkuController {
	private  Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private GoodsSkuService goodsSkuService;

	@RequestMapping(value = "/list",method = RequestMethod.GET)
	public Object list() {
		Page<GoodsSku> page = new PageFactory<GoodsSku>().defaultPage();
		page.addFilter("isDeleted",false);
		page = goodsSkuService.queryPage(page);
		return Rets.success(page);
	}
	@RequestMapping(method = RequestMethod.POST)
	@BussinessLog(value = "编辑商品SKU", key = "name")
	@RequiresPermissions(value = {Permission.GOODS_EDIT})
	public Object save(@RequestBody GoodsSku sku){
		List<String> codeArr = Arrays.asList(sku.getCode().split(","));
		List<String> codeNameArr = Arrays.asList(sku.getCodeName().split(","));
		Collections.sort(codeArr, new Comparator<String>() {
			@Override
			public int compare(String  s1, String s2) {
				return s1.compareTo(s2);
			}
		});
		Collections.sort(codeNameArr, new Comparator<String>() {
			@Override
			public int compare(String  s1, String s2) {
				return s1.compareTo(s2);
			}
		});
		String code = Lists.concat(codeArr,",");
		String codeName = Lists.concat(codeNameArr,",");
		sku.setCode(code);
		sku.setCodeName(codeName);
		GoodsSku oldSku = goodsSkuService.get(Lists.newArrayList(
				SearchFilter.build("idGoods",sku.getIdGoods()),
				SearchFilter.build("code",code)
		));
		if(oldSku!=null){
			LogObjectHolder.me().set(oldSku);
			oldSku.setMarketingPrice(sku.getMarketingPrice());
			oldSku.setPrice(sku.getPrice());
			oldSku.setStock(sku.getStock());
			goodsSkuService.update(oldSku);
			return Rets.success(oldSku);
		}else{
			goodsSkuService.insert(sku);
			return Rets.success(sku);
		}

	}
	@RequestMapping(method = RequestMethod.DELETE)
	@BussinessLog(value = "删除商品SKU", key = "id")
	@RequiresPermissions(value = {Permission.GOODS_EDIT})
	public Object remove(Long id){
		if (StringUtil.isEmpty(id)) {
			throw new ApplicationException(ApplicationExceptionEnum.REQUEST_NULL);
		}
		GoodsSku sku = goodsSkuService.get(id);
		sku.setIsDeleted(true);
		goodsSkuService.update(sku);
		return Rets.success();
	}
}
