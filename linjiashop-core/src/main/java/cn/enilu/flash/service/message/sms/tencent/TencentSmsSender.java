package cn.enilu.flash.service.message.sms.tencent;

import cn.enilu.flash.bean.constant.CfgKey;
import cn.enilu.flash.service.message.sms.SmsSender;
import cn.enilu.flash.service.system.CfgService;
import cn.enilu.flash.utils.StringUtil;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TencentSmsSender implements SmsSender {
    private Logger logger = LoggerFactory.getLogger(TencentSmsSender.class);
    @Autowired
    private CfgService cfgService;
    @Override
    public boolean sendSms(String tplCode, String receiver, String[] args, String content) {
        Integer appid = Integer.valueOf( cfgService.getCfgValue(CfgKey.API_TENCENT_SMS_APPID));
        String appkey = cfgService.getCfgValue(CfgKey.API_TENCENT_SMS_APPKEY);
        String smsSign = cfgService.getCfgValue(CfgKey.API_TENCENT_SMS_SIGN);
        SmsSingleSender ssender = new SmsSingleSender(appid, appkey);
        SmsSingleSenderResult result = null;
        try{
            if(StringUtil.isNotEmpty(tplCode)){
                //根据指定模板id发送短信
                // 签名参数未提供或者为空时，会使用默认签名发送短信
                result = ssender.sendWithParam("86", receiver,
                    Integer.valueOf(tplCode), args, smsSign, "", "");
            }else {
                //发送固定内容短信
                result = ssender.send(0, "86", receiver,
                        content, "", "");

            }
            logger.info("腾讯短信发送结果:{}",result.errMsg);
            return result.result == 0;
        } catch (Exception e) {
                logger.error("发送短信异常",e);
        }

        return false;
    }
}
