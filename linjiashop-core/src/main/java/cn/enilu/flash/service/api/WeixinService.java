package cn.enilu.flash.service.api;

import cn.enilu.flash.bean.constant.CfgKey;
import cn.enilu.flash.bean.entity.shop.ShopUser;
import cn.enilu.flash.bean.entity.system.Cfg;
import cn.enilu.flash.bean.exception.ApplicationException;
import cn.enilu.flash.bean.exception.ApplicationExceptionEnum;
import cn.enilu.flash.bean.vo.shop.WechatInfo;
import cn.enilu.flash.cache.CacheDao;
import cn.enilu.flash.service.shop.ShopUserService;
import cn.enilu.flash.service.system.CfgService;
import cn.enilu.flash.utils.HttpUtil;
import cn.enilu.flash.utils.StringUtil;
import org.nutz.http.Http;
import org.nutz.json.Json;
import org.nutz.mapl.Mapl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author ：enilu
 * @date ：Created in 1/19/2020 4:31 PM
 */
@Service
public class WeixinService {
    @Autowired
    private CfgService cfgService;
    @Autowired
    private ShopUserService shopUserService;
    @Autowired
    private CacheDao cacheDao;
    private Logger logger = LoggerFactory.getLogger(WeixinService.class);

    public void updateWeixinToken() {
        HttpUtil.trustEveryone();
        String accessToken = getAccessToken();
        String jsapiTicket = getJsapiTicket(accessToken);

        Cfg tokenCfg = cfgService.getByCfgName(CfgKey.WX_ACCESS_TOKEN);
        if (tokenCfg == null) {
            tokenCfg = new Cfg();
            tokenCfg.setCfgName(CfgKey.WX_ACCESS_TOKEN);
            tokenCfg.setCfgDesc("微信token，通过定时任务获取");
        }
        tokenCfg.setCfgValue(accessToken);
        cfgService.saveOrUpdate(tokenCfg);

        Cfg tidCfg = cfgService.getByCfgName(CfgKey.WX_JS_API_TICKET);
        if (tidCfg == null) {
            tidCfg = new Cfg();
            tidCfg.setCfgName(CfgKey.WX_JS_API_TICKET);
            tidCfg.setCfgDesc("微信ticket，通过定时任务获取");
        }
        tidCfg.setCfgValue(jsapiTicket);
        cfgService.saveOrUpdate(tidCfg);
    }

    public String getAccessToken() {
        String appId = cfgService.getCfgValue(CfgKey.WX_APP_ID);
        String appSecret = cfgService.getCfgValue(CfgKey.WX_APP_SECRET);
        String accessTokenUrl = cfgService.getCfgValue(CfgKey.WX_ACCESS_TOKEN_URL);
        String url = String.format(accessTokenUrl, appId, appSecret);
        String result = Http.get(url).getContent();
        logger.info("获取微信token，\r\nurl : {},\r\n result : {}", url, result);
        Object object = Json.fromJson(StringUtil.sNull(result));
        String access_token = (String) Mapl.cell(object, "access_token");
        return access_token;
    }

    public String getJsapiTicket(String accessToken) {
        String jsapiTicketUrl = cfgService.getCfgValue(CfgKey.WX_JS_API_TICKET_URL);
        String url = String.format(jsapiTicketUrl, accessToken);
        String result = Http.get(url).getContent();
        Object object = Json.fromJson(StringUtil.sNull(result));
        String ticket = (String) Mapl.cell(object, "ticket");
        return ticket;
    }

    public Map<String, Object> getPrivateAccessToken(String code) {
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token";
        url += "?appid=" + cfgService.getCfgValue(CfgKey.WX_APP_ID);
        url += "&secret=" + cfgService.getCfgValue(CfgKey.WX_APP_SECRET);
        url += "&code=" + code;
        url += "&grant_type=authorization_code";
        try {
            String result = Http.get(url).getContent();
            Object object = Json.fromJson(StringUtil.sNull(result));

            logger.info("url:" + url + ";  response:" + Json.toJson(object));
            String access_token = (String) Mapl.cell(object, "access_token");
            if (StringUtil.isNotEmpty(access_token)) {
                return (Map) object;
            }
        } catch (Exception e) {
            logger.error("获取token失败", e);
        }
        return null;
    }

    public boolean isAuth(ShopUser user, String code) {
        WechatInfo wechatInfo = null;
        if (user != null) {
            wechatInfo = cacheDao.hget(CacheDao.SESSION, "WECHAT_INFO" + user.getId(), WechatInfo.class);
        }
        if (wechatInfo != null) {
            logger.info("从缓存获取微信用户信息", Json.toJson(wechatInfo));
            return true;
        }
        if (StringUtil.isNotEmpty(code)) {
            wechatInfo = getWechatInfoByCode(code);
            if (wechatInfo != null) {

                ShopUser old = shopUserService.findByWechatOpenId(wechatInfo.getOpenId());
                if(old.getId().intValue()!=user.getId().intValue()){
                    throw new ApplicationException(ApplicationExceptionEnum.WECHAT_BIND_ANOTHER);
                }
                user.setWechatNickName(StringUtil.getValidChar(wechatInfo.getNickName()));
                user.setWechatHeadImgUrl(wechatInfo.getHeadUrl());
                if (StringUtil.equals(user.getNickName(), user.getMobile())) {
                    user.setNickName(user.getWechatNickName());
                }
                shopUserService.update(user);
                cacheDao.hset(CacheDao.SESSION, "WECHAT_INFO" + user.getId(), wechatInfo);
                return true;
            }
        }

        return false;
    }

    public WechatInfo getWechatInfoByCode(String code) {
        Map<String, Object> ret = getPrivateAccessToken(code);
        logger.info("获取token:{}", Json.toJson(ret));
        if (ret != null && ret.get("errcode") == null) {
            String openId = StringUtil.sNull(ret.get("openid"));
            return getWechatInfo(openId);
        }
        return null;
    }

    public WechatInfo getWechatInfo(String openId) {
        String accessToken = cfgService.getCfgValue(CfgKey.WX_ACCESS_TOKEN);
        String url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=" + accessToken + "&openid=" + openId;
        String result = Http.get(url).getContent();

        Object object = Json.fromJson(StringUtil.sNull(result));

        logger.info("getWecchatInfo====url:" + url + ";  response:" + Json.toJson(object));
        if (Mapl.cell(object, "errcode") != null) {
            logger.info("获取微信用户基本信息失败", Mapl.cell(object, "errmsg"));
        } else {
            if (StringUtil.equals(StringUtil.sNull(Mapl.cell(object, "errcode")), "0")) {
                logger.error("用户:{}没有关注该公众号", openId);
            } else {
                WechatInfo wechatInfo = new WechatInfo();
                wechatInfo.setOpenId(openId);
                wechatInfo.setNickName(StringUtil.sNull(Mapl.cell(object, "nickname")));
                wechatInfo.setHeadUrl(StringUtil.sNull(Mapl.cell(object, "headimgurl")));
                return wechatInfo;
            }
        }
        return null;
    }

    private String createNonceStr() {
        return UUID.randomUUID().toString();
    }

    private String createTimestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }

    private String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    public Map<String, String> getSign(String url) {
        Map<String, String> map = getSign(cfgService.getCfgValue(CfgKey.WX_JS_API_TICKET), url);
        map.put("appId", cfgService.getCfgValue(CfgKey.WX_APP_ID));
        return map;
    }

    public Map<String, String> getSign(String jsapi_ticket, String url) {
        Map<String, String> ret = new HashMap<String, String>();
        String nonce_str = createNonceStr();
        String timestamp = createTimestamp();
        String string1;
        String signature = "";

        // 注意这里参数名必须全部小写，且必须有序
        string1 = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + nonce_str + "&timestamp=" + timestamp + "&url=" + url;
        logger.info(string1);
        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        ret.put("url", url);
        ret.put("jsapi_ticket", jsapi_ticket);
        ret.put("nonceStr", nonce_str);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);

        return ret;
    }
}
