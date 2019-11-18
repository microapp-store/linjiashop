package cn.enilu.flash.api.controller.shop;

import cn.enilu.flash.bean.constant.factory.PageFactory;
import cn.enilu.flash.bean.core.BussinessLog;
import cn.enilu.flash.bean.dictmap.CommonDict;
import cn.enilu.flash.bean.entity.shop.ShopUser;
import cn.enilu.flash.bean.enumeration.BizExceptionEnum;
import cn.enilu.flash.bean.exception.ApplicationException;
import cn.enilu.flash.bean.vo.front.Rets;
import cn.enilu.flash.bean.vo.query.SearchFilter;
import cn.enilu.flash.service.shop.CartService;
import cn.enilu.flash.service.shop.OrderService;
import cn.enilu.flash.service.shop.ShopUserService;
import cn.enilu.flash.utils.Maps;
import cn.enilu.flash.utils.factory.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/shop/user")
public class ShopUserController {
	private  Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private ShopUserService shopUserService;
	@Autowired
	private CartService cartService;
	@Autowired
	private OrderService orderService;

	@RequestMapping(value = "/list",method = RequestMethod.GET)
	public Object list(@RequestParam(required = false) String mobile, @RequestParam(required = false) String nickName) {
		Page<ShopUser> page = new PageFactory<ShopUser>().defaultPage();
		page.addFilter("mobile",mobile);
		page.addFilter("nickName",nickName);
		page = shopUserService.queryPage(page);
		return Rets.success(page);
	}
	@RequestMapping(method = RequestMethod.POST)
	@BussinessLog(value = "编辑用户", key = "name",dict= CommonDict.class)
	public Object save(@ModelAttribute ShopUser tShopUser){
		if(tShopUser.getId()==null){
			shopUserService.insert(tShopUser);
		}else {
			shopUserService.update(tShopUser);
		}
		return Rets.success();
	}

	@RequestMapping(value="{id}",method = RequestMethod.GET)
	public Object get(@PathVariable("id") Long id){
		if (id == null) {
			throw new ApplicationException(BizExceptionEnum.REQUEST_NULL);
		}
		return Rets.success(shopUserService.get(id));
	}

	@RequestMapping(value="/info/{id}",method = RequestMethod.GET)
	public Object getInfo(@PathVariable("id") Long id){
		if (id == null) {
			throw new ApplicationException(BizExceptionEnum.REQUEST_NULL);
		}
		ShopUser shopUser = shopUserService.get(id);

		SearchFilter filter = SearchFilter.build("idUser",id);
		Long  cartCount = cartService.count(filter);
		Long orderCount = orderService.count(filter);
		Map<String,Object> data = Maps.newHashMap(
				"cartCount",cartCount,
				"orderCount",orderCount,
				"info",shopUser
		);
		return Rets.success(data);
	}
	@RequestMapping(method = RequestMethod.DELETE)
	@BussinessLog(value = "删除用户", key = "id",dict= CommonDict.class)
	public Object remove(Long id){
		if (id == null) {
			throw new ApplicationException(BizExceptionEnum.REQUEST_NULL);
		}
		shopUserService.deleteById(id);
		return Rets.success();
	}
}
