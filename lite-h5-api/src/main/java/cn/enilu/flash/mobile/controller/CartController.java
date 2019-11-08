package cn.enilu.flash.mobile.controller;

import cn.enilu.flash.bean.entity.shop.Cart;
import cn.enilu.flash.bean.vo.front.Rets;
import cn.enilu.flash.bean.vo.query.SearchFilter;
import cn.enilu.flash.service.shop.CartService;
import cn.enilu.flash.utils.HttpUtil;
import cn.enilu.flash.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author ：enilu
 * @date ：Created in 11/5/2019 7:36 PM
 */
@RestController
@RequestMapping("/user/cart")
public class CartController extends BaseController {
    @Autowired
    private CartService cartService;
    @RequestMapping(value = "/queryByUser",method = RequestMethod.GET)
    public Object getByUser(){
        Long idUser = getIdUser(HttpUtil.getRequest());
        List<Cart> list = cartService.queryAll(SearchFilter.build("idUser", SearchFilter.Operator.EQ,idUser));
        return Rets.success(list);
    }
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Object add(@RequestParam("idGoods") Long idGoods,
                          @RequestParam("count") String count){
        Long idUser = getIdUser(HttpUtil.getRequest());
        Cart cart = new Cart();
        cart.setIdGoods(idGoods);
        cart.setCount(new BigDecimal(count));
        cart.setIdUser(idUser);
        cartService.insert(cart);
        return Rets.success();
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Object update(@RequestParam("id") Long id,
                          @RequestParam("count") String count){
        Cart cart = cartService.get(id);
        cart.setCount(new BigDecimal(count));
        cartService.update(cart);
        return Rets.success();
    }
}
