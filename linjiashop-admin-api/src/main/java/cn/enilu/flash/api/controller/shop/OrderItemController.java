package cn.enilu.flash.api.controller.shop;

import cn.enilu.flash.bean.constant.factory.PageFactory;
import cn.enilu.flash.bean.entity.shop.OrderItem;
import cn.enilu.flash.bean.vo.front.Rets;
import cn.enilu.flash.service.shop.OrderItemService;
import cn.enilu.flash.utils.factory.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
}
