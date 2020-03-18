package cn.enilu.flash.mobile.controller;

import cn.enilu.flash.bean.entity.shop.Order;
import cn.enilu.flash.bean.entity.shop.ShopUser;
import cn.enilu.flash.bean.vo.front.Rets;
import cn.enilu.flash.service.api.WeixinPayService;
import cn.enilu.flash.service.shop.OrderService;
import cn.enilu.flash.service.shop.ShopUserService;
import cn.enilu.flash.utils.Maps;
import cn.enilu.flash.utils.StringUtil;
import cn.enilu.flash.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author ：enilu
 * @date ：Created in 2020/3/17 20:15
 */
@RestController
@RequestMapping("/pay")
public class PayController extends BaseController {
    @Autowired
    private ShopUserService shopUserService;
    @Autowired
    private WeixinPayService weixinPayService;
    @Autowired
    private OrderService orderService;
    @RequestMapping(value = "wx/prepare",method = RequestMethod.POST)
    public Object wxPrepare(@RequestParam("orderSn")String orderSn){
        ShopUser user = shopUserService.getCurrentUser();
        if(StringUtil.isEmpty(user.getWechatOpenId())){
            return Rets.failure("非微信用户");
        }
        Order order = orderService.getByOrderSn(orderSn);
//        WxPayMpOrderResult wxOrder = weixinPayService.prepare(user,order);
        Map map = Maps.newHashMap(
                "appId","aa",
                "nonceStr","aaa",
                "package","aaa",
                "paySign","aaa",
                "signType","aaa",
                "timeStamp","aa"
        );
        return Rets.success(map);
    }
}
