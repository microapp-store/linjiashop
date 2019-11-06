package cn.enilu.flash.mobile.controller;

import cn.enilu.flash.bean.entity.shop.ShopUser;
import cn.enilu.flash.bean.vo.JwtUser;
import cn.enilu.flash.bean.vo.UserInfo;
import cn.enilu.flash.bean.vo.front.Rets;
import cn.enilu.flash.security.JwtUtil;
import cn.enilu.flash.service.shop.ShopUserService;
import cn.enilu.flash.utils.MD5;
import cn.enilu.flash.web.controller.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    private Logger logger = LoggerFactory.getLogger(LoginController.class);

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
            if (user == null) {
                return Rets.failure("该用户不存在");
            }
            Boolean validateRet = shopUserService.validateSmsCode(mobile,smsCode);
            if(validateRet) {
                String token = JwtUtil.sign(new JwtUser(user));
                Map<String, String> result = new HashMap<>(1);
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
            String token = JwtUtil.sign(new JwtUser(user));
            Map<String, Object> result = new HashMap<>(1);
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
}
