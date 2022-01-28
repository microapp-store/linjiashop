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
import cn.enilu.flash.utils.StringUtil;
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

    @RequestMapping(value = "getWxOpenId", method = RequestMethod.POST)
    public Object getWxOpenId(String code, HttpServletRequest request) {
        WechatInfo wechatInfo = weixinService.getWechatInfoByCode(code);
        if (wechatInfo == null) {
            return Rets.failure("获取微信消息失败");
        }
        ShopUser currentWechatUser = shopUserService.findByWechatOpenId(wechatInfo.getOpenId());

        ShopUser loginUser = shopUserService.getCurrentUser();
        Map result = null;
        if (currentWechatUser != null) {
            if (loginUser != null) {
                if (StringUtil.isNotEmpty(loginUser.getWechatOpenId())
                        && !loginUser.getWechatOpenId().equals(wechatInfo.getOpenId())) {
                    return Rets.failure("当前微信用户已绑定其他手机号");
                }else{
                    //当前用户通过手机登陆后获取微信信息
                    loginUser.setWechatOpenId(wechatInfo.getOpenId());
                    loginUser.setWechatHeadImgUrl(wechatInfo.getHeadUrl());
                    loginUser.setNickName(wechatInfo.getNickName());
                    shopUserService.update(loginUser);
                    result =  login(loginUser);
                }
            }else{
                //当前用户通过微信自动登录
                result =  login(currentWechatUser);
            }
        }else{
            if(loginUser==null){
                //用户手册通过微信自动登录+注册
               loginUser =  shopUserService.registerByWechatInfo(wechatInfo);
            }
            result = login(loginUser);
        }
        return Rets.success(result);
    }
    private Map login(ShopUser shopUser){
        String token = userService.loginForToken(new JwtUser(shopUser));
        shopUser.setLastLoginTime(new Date());
        shopUserService.update(shopUser);
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(shopUser, userInfo);
        userInfo.setRefreshWechatInfo(false);
        Map result = Maps.newHashMap(
                "user", userInfo,
                "token", token
        );
        return result;
    }

    @RequestMapping(value = "getWxSign", method = RequestMethod.POST)
    public Object getWxSign(@RequestParam("url") String url) {
        Map<String, String> map = weixinService.getSign(url);
        return Rets.success(map);
    }
}
