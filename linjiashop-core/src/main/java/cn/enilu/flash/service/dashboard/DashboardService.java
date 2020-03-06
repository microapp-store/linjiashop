package cn.enilu.flash.service.dashboard;

import cn.enilu.flash.bean.enumeration.shop.OrderEnum;
import cn.enilu.flash.service.shop.CartService;
import cn.enilu.flash.service.shop.OrderService;
import cn.enilu.flash.service.shop.ShopUserService;
import cn.enilu.flash.utils.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author ：enilu
 * @date ：Created in 1/7/2020 2:25 PM
 */
@Service
public class DashboardService {
    @Autowired
    private OrderService orderService;
    @Autowired
    private ShopUserService shopUserService;
    @Autowired
    private CartService cartService;
    public Map getDashboardData(){
        long orderCount = orderService.count();
        long userCount = shopUserService.count();
        long cartCount = cartService.count();

        Map orderSumPrice = orderService.getMapBySql("select sum(real_price) as realPrice from t_shop_order where status<>"+ OrderEnum.OrderStatusEnum.UN_PAY.getId());
        Map result = Maps.newHashMap(
                "orderCount",orderCount,
                "userCount",userCount,
                "cartCount",cartCount,
                "orderSumPrice", orderSumPrice!=null?(Double.valueOf(orderSumPrice.get("realPrice").toString())/100):"0"
        );
        return result;
    }
}
