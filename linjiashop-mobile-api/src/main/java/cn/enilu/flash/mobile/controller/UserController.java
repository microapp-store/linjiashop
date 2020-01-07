package cn.enilu.flash.mobile.controller;

import cn.enilu.flash.bean.entity.shop.ShopUser;
import cn.enilu.flash.bean.vo.UserInfo;
import cn.enilu.flash.bean.vo.front.Rets;
import cn.enilu.flash.security.JwtUtil;
import cn.enilu.flash.service.shop.ShopUserService;
import cn.enilu.flash.utils.MD5;
import cn.enilu.flash.utils.StringUtil;
import cn.enilu.flash.web.controller.BaseController;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author ：enilu
 * @date ：Created in 11/6/2019 4:20 PM
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {
    @Autowired
    private ShopUserService shopUserService;
    @RequestMapping(value = "/getInfo",method = RequestMethod.GET)
    public Object getInfo(){
        String token = getToken();
        Long idUser = JwtUtil.getUserId(token);
         ShopUser shopUser = shopUserService.get(idUser);
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(shopUser,userInfo);
        return Rets.success(shopUser);
    }
    @RequestMapping(value = "/updateUserName/{userName}",method = RequestMethod.POST)
    public Object updateUserName(@PathVariable("userName") String userName){
        ShopUser user = shopUserService.getCurrentUser();
        user.setNickName(userName);
        shopUserService.update(user);
        return Rets.success();
    }

    @RequestMapping(value = "/updateGender/{gender}",method = RequestMethod.POST)
    public Object updateGender(@PathVariable("gender") String gender){
        ShopUser user = shopUserService.getCurrentUser();
        user.setGender(gender);
        shopUserService.update(user);
        return Rets.success();
    }

    @RequestMapping(value = "/updatePassword/{oldPwd}/{password}/{rePassword}",method = RequestMethod.POST)
    public Object updatePassword(@PathVariable("oldPwd") String oldPwd,
                                 @PathVariable("password") String password,
                                 @PathVariable("rePassword") String rePassword){
        if(StringUtil.isEmpty(oldPwd) || StringUtil.isEmpty(password) || StringUtil.isEmpty(rePassword)){
            return  Rets.failure("项目并能为空");
        }
        if(!StringUtil.equals(password,rePassword)){
            return Rets.failure("密码前后不一致");
        }
        ShopUser user = shopUserService.getCurrentUser();
        String oldPasswdMd5 = MD5.md5(oldPwd, user.getSalt());
        if(!StringUtil.equals(oldPasswdMd5,user.getPassword())){
            return Rets.failure("旧密码不正确");
        }
        user.setPassword(MD5.md5(password,user.getSalt()));
        shopUserService.update(user);
        return Rets.success();
    }
    @RequestMapping(value = "sendSmsCode",method = RequestMethod.POST)
    public Object sendSmsCode(@RequestParam String mobile){
        String smsCode = shopUserService.sendSmsCodeForOldMobile(mobile);
        //todo 测试环境直接返回验证码，生成环境切忌返回该验证码
        return Rets.success(smsCode);
    }
}
