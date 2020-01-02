package cn.enilu.flash.api.controller.front.shop;

import cn.enilu.flash.web.controller.BaseController;
import cn.enilu.flash.bean.entity.shop.ShopUser;
import cn.enilu.flash.bean.vo.JwtUser;
import cn.enilu.flash.bean.vo.front.Rets;
import cn.enilu.flash.security.JwtUtil;
import cn.enilu.flash.service.shop.ShopUserService;
import cn.enilu.flash.utils.MD5;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ：enilu
 * @date ：Created in 11/4/2019 8:12 PM
 */

@RestController("/shop")
public class LoginController extends BaseController {

    @Value("${jwt.token.expire.time}")
    private Long tokenExpireTime ;
    @Autowired
    private ShopUserService shopUserService;
    private Logger logger = LoggerFactory.getLogger(LoginController.class);
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Object login(@RequestParam("mobile") String mobile,
                        @RequestParam("password") String password){
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

            String token = JwtUtil.sign(new JwtUser(user),tokenExpireTime);
            Map<String, String> result = new HashMap<>(1);
            logger.info("token:{}",token);
            result.put("token", token);

            return Rets.success(result);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return Rets.failure("登录时失败");
    }
}
