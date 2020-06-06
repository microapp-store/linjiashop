package cn.enilu.flash.service.shop;


import cn.enilu.flash.bean.entity.shop.ShopUser;
import cn.enilu.flash.bean.enumeration.MessageTemplateEnum;
import cn.enilu.flash.bean.exception.ApplicationException;
import cn.enilu.flash.bean.exception.ApplicationExceptionEnum;
import cn.enilu.flash.bean.vo.shop.WechatInfo;
import cn.enilu.flash.cache.CacheDao;
import cn.enilu.flash.dao.shop.ShopUserRepository;
import cn.enilu.flash.security.JwtUtil;
import cn.enilu.flash.service.BaseService;
import cn.enilu.flash.service.message.MessageService;
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
    @Autowired
    private MessageService messageService;

    public ShopUser findByMobile(String mobile) {
        return shopUserRepository.findByMobile(mobile);
    }

    public Boolean validateSmsCode(String mobile, String smsCode) {
        //todo 测试验证逻辑，暂不实现
        String key = mobile+"_smsCode";
        String smsCode2 = (String) cacheDao.hget(CacheDao.HOUR,key);
        return StringUtil.equals(smsCode,smsCode2);
    }

    public boolean  sendSmsCode(String mobile) {
        String smsCode = RandomUtil.getRandomNumber(4);
        String key =  mobile+"_smsCode";
        String timesKey = key+"_times";
        String oldSmsCode = (String) cacheDao.hget(CacheDao.HOUR,key);
        if(StringUtil.isNotEmpty(oldSmsCode)){
            logger.info("{}:一分钟内已经发送过短信验证码，不再重复发送",oldSmsCode);
            throw  new ApplicationException(ApplicationExceptionEnum.REQUEST_TOO_MANY);
        }
        Integer sendTimes =  cacheDao.hget(CacheDao.DAY,timesKey,Integer.class);
        sendTimes = sendTimes==null?0:sendTimes;
        if(sendTimes!=null&&sendTimes>3){
            logger.info("{}:当天发送短信验证码次数超限",mobile);
            throw  new ApplicationException(ApplicationExceptionEnum.REQUEST_TOO_MANY);
        }
        cacheDao.hset(CacheDao.HOUR,key,smsCode);
        cacheDao.hset(CacheDao.DAY,timesKey,sendTimes++);

        messageService.sendSms(MessageTemplateEnum.REGISTER_CODE.getCode(),mobile,smsCode);
        return true;
    }
    //todo 该方法仅作测试环境使用
    public String sendSmsCodeForOldMobile(String mobile) {

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

    public ShopUser findByWechatOpenId(String openId) {
        return shopUserRepository.findByWechatOpenId(openId);
    }

    public ShopUser registerByWechatInfo(WechatInfo wechatInfo) {
        ShopUser user = new ShopUser();
        user.setWechatOpenId(wechatInfo.getOpenId());
        user.setWechatHeadImgUrl(wechatInfo.getHeadUrl());
        user.setNickName(wechatInfo.getNickName());
        user.setCreateTime(new Date());
        insert(user);
        return user;
    }
}

