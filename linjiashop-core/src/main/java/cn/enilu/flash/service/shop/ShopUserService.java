package cn.enilu.flash.service.shop;


import cn.enilu.flash.bean.entity.shop.ShopUser;
import cn.enilu.flash.dao.shop.ShopUserRepository;

import cn.enilu.flash.service.BaseService;
import cn.enilu.flash.utils.HttpUtil;
import cn.enilu.flash.utils.RandomUtil;
import cn.enilu.flash.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShopUserService extends BaseService<ShopUser,Long,ShopUserRepository>  {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ShopUserRepository shopUserRepository;

    public ShopUser findByMobile(String mobile) {
        return shopUserRepository.findByMobile(mobile);
    }

    public Boolean validateSmsCode(String mobile, String smsCode) {
        //todo 测试验证逻辑，暂不实现
        String smsCode2 = (String) HttpUtil.getRequest().getSession().getAttribute(mobile+"_smsCode");
        return StringUtil.equals(smsCode,smsCode2);
    }

    public String sendSmsCode(String mobile) {
        //todo 发送短信验证码逻辑，暂不实现
        String smsCode = RandomUtil.getRandomNumber(4);
        HttpUtil.getRequest().getSession().setAttribute(mobile+"_smsCode",smsCode);
        return smsCode;
    }
}

