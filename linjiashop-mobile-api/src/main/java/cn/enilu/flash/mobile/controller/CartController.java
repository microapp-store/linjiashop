package cn.enilu.flash.mobile.controller;

import cn.enilu.flash.bean.entity.shop.Cart;
import cn.enilu.flash.bean.vo.front.Rets;
import cn.enilu.flash.bean.vo.query.SearchFilter;
import cn.enilu.flash.bean.vo.shop.CartVo;
import cn.enilu.flash.service.shop.CartService;
import cn.enilu.flash.service.shop.GoodsService;
import cn.enilu.flash.service.shop.GoodsSkuService;
import cn.enilu.flash.utils.HttpUtil;
import cn.enilu.flash.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    private GoodsSkuService goodsSkuService;
    @Autowired
    private GoodsService goodsService;
    @RequestMapping(value = "/queryByUser",method = RequestMethod.GET)
    public Object getByUser(){
        Long idUser = getIdUser(HttpUtil.getRequest());
        List<Cart> list = cartService.queryAll(SearchFilter.build("idUser", SearchFilter.Operator.EQ,idUser));
        return Rets.success(list);
    }
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Object add(@RequestBody CartVo cartVo){
        Long idUser = getIdUser();
        cartVo.setIdUser(idUser);
       Integer result = cartService.add(cartVo);
        return Rets.success(result);
    }
    @RequestMapping(value="/count",method = RequestMethod.GET)
    public Object count(){
        Long idUser = getIdUser(HttpUtil.getRequest());
        List<Cart> list = cartService.queryAll(SearchFilter.build("idUser", SearchFilter.Operator.EQ,idUser));
        return Rets.success(list.size());
    }

    @RequestMapping(value = "/update/{id}/{count}",method = RequestMethod.POST)
    public Object update(@PathVariable("id") Long id,
                          @PathVariable("count") String count){
        Cart cart = cartService.get(id);
        cart.setCount(new BigDecimal(count));
        cartService.update(cart);
        return Rets.success();
    }
    @RequestMapping(method = RequestMethod.DELETE)
    public Object remove(@RequestParam Long id){
        Long idUser = getIdUser();
        Cart cart = cartService.get(id);
        if(cart.getIdUser().intValue() == idUser.intValue()){
            cartService.delete(cart);
        }
        return Rets.success();
    }
}
