package cn.enilu.flash.service.message.sms.aliyun;

import cn.enilu.flash.bean.constant.CfgKey;
import cn.enilu.flash.service.message.sms.SmsSender;
import cn.enilu.flash.service.system.CfgService;
import cn.enilu.flash.utils.JsonUtil;
import cn.enilu.flash.utils.StringUtil;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 阿里云短信发送器
 * @author enilu
 * Created on 2020/12/16
 **/
@Service
public class AliSmsSender implements SmsSender {
    private Logger logger = LoggerFactory.getLogger(AliSmsSender.class);
    @Autowired
    private CfgService cfgService;

    /**
     * @param tplCode  短信运营商模板号码
     * @param receiver
     * @param params   阿里云短信是键值对形式，因此args格式应为 k1:v1 k2:v2 以便代码将其转为Map<k,v>
     * @param content
     * @return
     */
    @Override
    public boolean sendSms(String tplCode, String receiver, LinkedHashMap params, String content) {
        String accessKeyId =   cfgService.getCfgValue(CfgKey.API_ALIYUN_SMS_ACCESS_KEY_ID);
        String accessSecret =  cfgService.getCfgValue(CfgKey.API_ALIYUN_SMS_ACCESS_SECRET);
        String regionId =  cfgService.getCfgValue(CfgKey.API_ALIYUN_SMS_REGION_ID);
        String signName =  cfgService.getCfgValue(CfgKey.API_ALIYUN_SMS_SIGN_NAME);
//        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "<accessKeyId>", "<accessSecret>");
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessSecret);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", regionId);
        request.putQueryParameter("PhoneNumbers", receiver);
        request.putQueryParameter("SignName", signName);
        request.putQueryParameter("TemplateCode", tplCode);
        request.putQueryParameter("TemplateParam", JsonUtil.toJson(params));
        try {
            CommonResponse response = client.getCommonResponse(request);
            String data = response.getData();
            Map result = JsonUtil.fromJson(Map.class, data);
            if (StringUtil.equals("OK", StringUtil.sNull(result.get("Code")))) {
                return true;
            }
            logger.error("发送短信失败:{}", data);
            return false;
        } catch (ServerException e) {
            logger.error("服务端异常", e);
        } catch (ClientException e) {
            logger.error("客户端异常", e);
        }
        return false;
    }
}
