package cn.enilu.flash.mobile.controller;

import cn.enilu.flash.bean.entity.shop.ShopUser;
import cn.enilu.flash.bean.vo.JwtUser;
import cn.enilu.flash.bean.vo.UserInfo;
import cn.enilu.flash.bean.vo.front.Rets;
import cn.enilu.flash.bean.vo.shop.WechatInfo;
import cn.enilu.flash.cache.CacheDao;
import cn.enilu.flash.security.UserService;
import cn.enilu.flash.service.api.WeixinService;
import cn.enilu.flash.service.shop.ShopUserService;
import cn.enilu.flash.utils.Maps;
import cn.enilu.flash.web.controller.BaseController;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

/**
 * @author ：enilu
 * @date ：Created in 2020/6/6 20:32
 */
@RestController
@RequestMapping("/wechat")
public class WeChatController extends BaseController {
    @Autowired
    private ShopUserService shopUserService;
    @Autowired
    private UserService userService;
    @Autowired
    private WeixinService weixinService;
    @Autowired
    private CacheDao cacheDao;

    @RequestMapping(value = "getWxOpenId",method = RequestMethod.POST)
    public  Object getWxOpenId(String code, HttpServletRequest request) {
        WechatInfo wechatInfo = weixinService.getWechatInfoByCode(code);
        if(wechatInfo==null){
            return Rets.failure("获取微信消息失败");
        }
        ShopUser user = shopUserService.findByWechatOpenId(wechatInfo.getOpenId());
        if(user==null){
            user = shopUserService.registerByWechatInfo(wechatInfo);
        }
        String token = userService.loginForToken(new JwtUser(user));
        user.setLastLoginTime(new Date());
        shopUserService.update(user);
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(user,userInfo);
        Map result = Maps.newHashMap(
                "user",userInfo,
                "token",token
        );
        return Rets.success(result);
    }
    @RequestMapping(value = "getWxSign", method = RequestMethod.POST)
    public Object getWxSign(@RequestParam("url") String url) {
        Map<String, String> map = weixinService.getSign(url);
        return Rets.success(map);
    }
}
