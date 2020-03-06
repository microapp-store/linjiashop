package cn.enilu.flash.api.controller.shop;

import cn.enilu.flash.bean.constant.factory.PageFactory;
import cn.enilu.flash.bean.entity.shop.Address;
import cn.enilu.flash.bean.vo.front.Rets;
import cn.enilu.flash.service.shop.AddressService;
import cn.enilu.flash.utils.factory.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shop/address")
public class AddressController {
	private  Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private AddressService addressService;

	@RequestMapping(value = "/list",method = RequestMethod.GET)
	public Object list(@RequestParam(required = false)Long idUser) {
		Page<Address> page = new PageFactory<Address>().defaultPage();
		page.addFilter("idUser",idUser);
		page = addressService.queryPage(page);
		return Rets.success(page);
	}
}
