package cn.enilu.flash.api.controller.shop;

import cn.enilu.flash.bean.entity.shop.OrderItem;
import cn.enilu.flash.service.shop.OrderItemService;

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
@RequestMapping("/shop/order/item")
public class OrderItemController {
	private  Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private OrderItemService orderItemService;

	@RequestMapping(value = "/list",method = RequestMethod.GET)
	public Object list() {
	Page<OrderItem> page = new PageFactory<OrderItem>().defaultPage();
		page = orderItemService.queryPage(page);
		return Rets.success(page);
	}
	@RequestMapping(method = RequestMethod.POST)
	@BussinessLog(value = "编辑订单明细", key = "name",dict= CommonDict.class)
	public Object save(@ModelAttribute OrderItem tShopOrderItem){
		if(tShopOrderItem.getId()==null){
			orderItemService.insert(tShopOrderItem);
		}else {
			orderItemService.update(tShopOrderItem);
		}
		return Rets.success();
	}
	@RequestMapping(method = RequestMethod.DELETE)
	@BussinessLog(value = "删除订单明细", key = "id",dict= CommonDict.class)
	public Object remove(Long id){
		if (id == null) {
			throw new ApplicationException(BizExceptionEnum.REQUEST_NULL);
		}
		orderItemService.delete(id);
		return Rets.success();
	}
}