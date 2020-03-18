package cn.enilu.flash.service.api;

import cn.enilu.flash.bean.entity.shop.Order;
import cn.enilu.flash.bean.entity.shop.ShopUser;
import cn.enilu.flash.utils.HttpUtil;
import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 微信支付
 * @author ：enilu
 * @date ：Created in 2020/3/17 20:26
 */
@Service
public class WeixinPayService {
    @Autowired
    private WxPayService wxPayService;
    private Logger logger = LoggerFactory.getLogger(WeixinPayService.class);
    public WxPayMpOrderResult prepare(ShopUser user, Order order){

        WxPayUnifiedOrderRequest orderRequest = new WxPayUnifiedOrderRequest();
        orderRequest.setOutTradeNo(order.getOrderSn());
        orderRequest.setOpenid(user.getWechatOpenId());
        orderRequest.setBody("订单：" + order.getOrderSn());
        BigDecimal totalPrice = order.getTotalPrice();
        orderRequest.setTotalFee(totalPrice.intValue());
        orderRequest.setSpbillCreateIp(HttpUtil.getIp());

        try {
            WxPayMpOrderResult result = wxPayService.createOrder(orderRequest);
            return  result;
        } catch (WxPayException e) {
            logger.error("微信支付异常",e);
        }
        return null;
    }
}
