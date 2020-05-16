package cn.enilu.flash.mobile.controller;

import cn.enilu.flash.bean.entity.shop.ShopUser;
import cn.enilu.flash.bean.vo.JwtUser;
import cn.enilu.flash.bean.vo.UserInfo;
import cn.enilu.flash.bean.vo.front.Rets;
import cn.enilu.flash.security.UserService;
import cn.enilu.flash.service.shop.ShopUserService;
import cn.enilu.flash.utils.MD5;
import cn.enilu.flash.utils.RandomUtil;
import cn.enilu.flash.web.controller.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ：enilu
 * @date ：Created in 11/5/2019 9:01 PM
 */
@RestController
@RequestMapping("/")
public class LoginController extends BaseController {
    @Autowired
    private ShopUserService shopUserService;
    @Autowired
    private UserService userService;
    private Logger logger = LoggerFactory.getLogger(LoginController.class);
    @RequestMapping(value = "sendSmsCode",method = RequestMethod.POST)
    public Object sendSmsCode(@RequestParam String mobile){
        shopUserService.sendSmsCode(mobile);
        return Rets.success();
    }
    /**
     * 使用手机号和短信验证码登录或者注册
     * @param mobile
     * @param smsCode
     * @return
     */
    @RequestMapping(value = "loginOrReg",method = RequestMethod.POST)
    public Object loginOrReg(@RequestParam String mobile,@RequestParam String smsCode){
        try {
            logger.info("用户登录:" + mobile + ",短信验证码:" + smsCode);
            //1,
            ShopUser user = shopUserService.findByMobile(mobile);
            Boolean validateRet = shopUserService.validateSmsCode(mobile,smsCode);

            Map<String, Object> result = new HashMap<>(6);
            if(validateRet) {
                if(user==null){
                    //初始化6位密码
                    String initPassword = RandomUtil.getRandomString(6);
                    user = shopUserService.register(mobile,initPassword);
                    result.put("initPassword",initPassword);
                }
                String token = userService.loginForToken(new JwtUser(user));
                user.setLastLoginTime(new Date());
                shopUserService.update(user);
                UserInfo userInfo = new UserInfo();
                BeanUtils.copyProperties(user,userInfo);
                result.put("user",userInfo);
                logger.info("token:{}",token);
                result.put("token", token);
                return Rets.success(result);
            }

            return Rets.failure("短信验证码错误");


        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return Rets.failure("登录时失败");
    }

    /**
     * 使用手机号和密码登录
     * @param mobile
     * @param password
     * @return
     */
    @RequestMapping(value = "loginByPass",method = RequestMethod.POST)
    public Object loginByPass(@RequestParam String mobile,@RequestParam String password){
        try {
            logger.info("用户登录:" + mobile + ",密码:" + password);
            //1,
            ShopUser user = shopUserService.findByMobile(mobile);
            if (user == null) {
                return Rets.failure("该用户不存在");
            }

            String passwdMd5 = MD5.md5(password, user.getSalt());
            //2,
            if (!user.getPassword().equals(passwdMd5)) {
                return Rets.failure("输入的密码错误");
            }
            String token = userService.loginForToken(new JwtUser(user));
            Map<String, Object> result = new HashMap<>(1);
            user.setLastLoginTime(new Date());
            shopUserService.update(user);
            UserInfo userInfo = new UserInfo();
            BeanUtils.copyProperties(user,userInfo);
            logger.info("token:{}",token);
            result.put("token", token);
            result.put("user",userInfo);
            return Rets.success(result);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return Rets.failure("登录时失败");
    }

    /**
     * 退出登录
     * @return
     */
    @RequestMapping(value = "/logout",method = RequestMethod.POST)
    public Object logout(){
        logger.info("处理额外的退出登录逻辑");
        return Rets.success();
    }
}
