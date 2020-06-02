package cn.enilu.flash.bean.constant;

/**
 * 系统参数key列表
 * 所有“系统管理”-“参数管理”中配置的参数的key都在此定义
 * @author ：enilu
 * @date ：Created in 1/19/2020 4:37 PM
 */
public interface CfgKey {
    String SYSTEM_FILE_UPLOAD_PATH = "system.file.upload.path";
    String SYSTEM_APP_NAME = "system.app.name";

    String API_TENCENT_SMS_APPID = "api.tencent.sms.appid";
    String API_TENCENT_SMS_APPKEY = "api.tencent.sms.appkey";
    String API_TENCENT_SMS_SIGN = "api.tencent.sms.sign";

    String API_KDNIAO_URL = "api.kdniao.url";
    String API_KDNIAO_USERID = "api.kdniao.userid";
    String API_KDNIAO_APIKEY = "api.kdniao.apikey";

    String WX_APP_ID = "weixin.app.id";
    String WX_APP_SECRET = "weixin.app.secret";
    String WX_ACCESS_TOKEN_URL = "weixin.access.token.url";
    String WX_JS_API_TICKET_URL = "weixin.js.api.ticket.url";
    String WX_ACCESS_TOKEN = "weixin.access.token";
    String WX_JS_API_TICKET = "weixin.js.api.ticket";


}
