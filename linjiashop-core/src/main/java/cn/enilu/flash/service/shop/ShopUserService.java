package cn.enilu.flash.service.shop;


import cn.enilu.flash.bean.entity.shop.ShopUser;
import cn.enilu.flash.cache.CacheDao;
import cn.enilu.flash.dao.shop.ShopUserRepository;
import cn.enilu.flash.security.JwtUtil;
import cn.enilu.flash.service.BaseService;
import cn.enilu.flash.utils.HttpUtil;
import cn.enilu.flash.utils.MD5;
import cn.enilu.flash.utils.RandomUtil;
import cn.enilu.flash.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ShopUserService extends BaseService<ShopUser,Long,ShopUserRepository>  {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ShopUserRepository shopUserRepository;
    @Autowired
    private CacheDao cacheDao;

    public ShopUser findByMobile(String mobile) {
        return shopUserRepository.findByMobile(mobile);
    }

    public Boolean validateSmsCode(String mobile, String smsCode) {
        //todo 测试验证逻辑，暂不实现
        String smsCode2 = (String) cacheDao.hget(CacheDao.SESSION,mobile+"_smsCode");
        return StringUtil.equals(smsCode,smsCode2);
    }

    public String sendSmsCode(String mobile) {
        //todo 发送短信验证码逻辑，暂不实现
        String smsCode = RandomUtil.getRandomNumber(4);
        cacheDao.hset(CacheDao.SESSION,mobile+"_smsCode",smsCode);
        HttpUtil.getRequest().getSession().setAttribute(mobile+"_smsCode",smsCode);
        return smsCode;
    }

    public String sendSmsCodeForOldMobile(String mobile) {
        //todo 发送短信验证码逻辑，暂不实现
        String smsCode = RandomUtil.getRandomNumber(4);
        cacheDao.hset(CacheDao.SESSION,mobile+"_smsCode",smsCode);
        HttpUtil.getRequest().getSession().setAttribute(mobile+"_smsCode",smsCode);
        return smsCode;
    }


    public ShopUser register(String mobile,String initPwd) {
        ShopUser user = new ShopUser();
        user.setMobile(mobile);
        user.setNickName(mobile);
        user.setCreateTime(new Date());
        user.setSalt(RandomUtil.getRandomString(5));

        user.setPassword(MD5.md5(initPwd, user.getSalt()));

        insert(user);
        return user;
    }

    public ShopUser getCurrentUser() {
        return get(JwtUtil.getUserId());
    }
}

