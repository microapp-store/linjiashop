package cn.enilu.flash.mobile.controller;

import cn.enilu.flash.bean.constant.factory.PageFactory;
import cn.enilu.flash.bean.entity.shop.Address;
import cn.enilu.flash.bean.entity.shop.Cart;
import cn.enilu.flash.bean.entity.shop.Order;
import cn.enilu.flash.bean.vo.front.Rets;
import cn.enilu.flash.bean.vo.query.SearchFilter;
import cn.enilu.flash.service.shop.AddressService;
import cn.enilu.flash.service.shop.CartService;
import cn.enilu.flash.service.shop.OrderService;
import cn.enilu.flash.utils.HttpUtil;
import cn.enilu.flash.utils.Maps;
import cn.enilu.flash.utils.factory.Page;
import cn.enilu.flash.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ：enilu
 * @date ：Created in 11/6/2019 5:07 PM
 */
@RestController
@RequestMapping("/user/order")
public class OrderController extends BaseController {
    @Autowired
    private OrderService orderService ;
    @Autowired
    private CartService cartService;
    @Autowired
    private AddressService addressService;
    @RequestMapping(value = "/getOrders",method = RequestMethod.GET)
    public Object getOrders(@RequestParam(value = "status",required = false) Integer status){
        Long idUser = getIdUser(HttpUtil.getRequest());
        Page<Order> page = new PageFactory<Order>().defaultPage();
        page.addFilter(SearchFilter.build("idUser", SearchFilter.Operator.EQ,idUser));
        page.setSort(Sort.by(Sort.Direction.DESC,"id"));
        if(status!=null){
            page.addFilter(SearchFilter.build("status", SearchFilter.Operator.EQ,status));
        }
        page = orderService.queryPage(page);
        return Rets.success(page);
    }

    @RequestMapping(value = "/prepareCheckout",method = RequestMethod.GET)
    public Object prepareCheckout(){
        Long idUser = getIdUser(HttpUtil.getRequest());
        List<Cart> list = cartService.queryAll(SearchFilter.build("idUser", SearchFilter.Operator.EQ,idUser));
        Address address = addressService.getDefaultAddr(idUser);
        return Rets.success(Maps.newHashMap(
                "list",list,"addr",address
        ));
    }
}
