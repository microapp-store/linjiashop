package cn.enilu.flash.api.controller.shop;

import cn.enilu.flash.bean.entity.shop.Address;
import cn.enilu.flash.service.shop.AddressService;

import cn.enilu.flash.bean.core.BussinessLog;
import cn.enilu.flash.bean.constant.factory.PageFactory;
import cn.enilu.flash.bean.dictmap.CommonDict;
import cn.enilu.flash.bean.enumeration.BizExceptionEnum;
import cn.enilu.flash.bean.exception.ApplicationException;
import cn.enilu.flash.bean.vo.front.Rets;

import cn.enilu.flash.utils.Maps;
import cn.enilu.flash.utils.ToolUtil;
import cn.enilu.flash.utils.factory.Page;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shop/address")
public class AddressController {
	private  Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private AddressService addressService;

	@RequestMapping(value = "/list",method = RequestMethod.GET)
	public Object list() {
	Page<Address> page = new PageFactory<Address>().defaultPage();
		page = addressService.queryPage(page);
		return Rets.success(page);
	}
	@RequestMapping(method = RequestMethod.POST)
	@BussinessLog(value = "编辑收货地址", key = "name",dict= CommonDict.class)
	public Object save(@ModelAttribute Address tShopAddress){
		if(tShopAddress.getId()==null){
			addressService.insert(tShopAddress);
		}else {
			addressService.update(tShopAddress);
		}
		return Rets.success();
	}
	@RequestMapping(method = RequestMethod.DELETE)
	@BussinessLog(value = "删除收货地址", key = "id",dict= CommonDict.class)
	public Object remove(Long id){
		if (ToolUtil.isEmpty(id)) {
			throw new ApplicationException(BizExceptionEnum.REQUEST_NULL);
		}
		addressService.delete(id);
		return Rets.success();
	}
}