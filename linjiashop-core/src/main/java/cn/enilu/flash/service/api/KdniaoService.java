package cn.enilu.flash.service.api;

import cn.enilu.flash.bean.constant.CfgKey;
import cn.enilu.flash.bean.exception.ApplicationException;
import cn.enilu.flash.bean.exception.ApplicationExceptionEnum;
import cn.enilu.flash.cache.CacheDao;
import cn.enilu.flash.service.system.CfgService;
import cn.enilu.flash.utils.Base64Util;
import cn.enilu.flash.utils.JsonUtil;
import cn.enilu.flash.utils.MD5;
import cn.enilu.flash.utils.Maps;
import org.nutz.http.Http;
import org.nutz.http.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.util.Map;

/**
 * 快递鸟查询服务
 *
 * @author ：enilu
 * @date ：Created in 2020/5/31 10:27
 */
@Service
public class KdniaoService {
    private Logger logger = LoggerFactory.getLogger(KdniaoService.class);
    @Autowired
    private CfgService cfgService;
    @Autowired
    private CacheDao cacheDao;

    /**
     * 查询快递实时信息
     *
     * @param orderCode   快递单号
     * @param shipperCode 快递公司编号
     * @return
     */
    public KdniaoResponse realTimeQuery(String orderCode, String shipperCode) {

        KdniaoResponse kdniaoResponse = cacheDao.hget(CacheDao.HOUR, orderCode, KdniaoResponse.class);
        if (kdniaoResponse != null) {
            return kdniaoResponse;
        }
        String url = cfgService.getCfgValue(CfgKey.API_KDNIAO_URL);
        String userId = cfgService.getCfgValue(CfgKey.API_KDNIAO_USERID);
        String apiKey = cfgService.getCfgValue(CfgKey.API_KDNIAO_APIKEY);
        logger.info("url:{}\nuserId:{}\napiKey:{}", url, userId, apiKey);
        Map appParams = Maps.newHashMap(
                "OrderCode", "",
                "ShipperCode", shipperCode,
                "LogisticCode", orderCode
        );
        String jsonStr = JsonUtil.toJson(appParams);
        String datasign = null;
        try {
            datasign = URLEncoder.encode(Base64Util.base64Encode(MD5.getMD5String((jsonStr + apiKey)).toLowerCase().getBytes()));
            Map params = Maps.newHashMap(
                    "RequestType", "1002",
                    "EBusinessID", userId,
                    "RequestData", URLEncoder.encode(jsonStr, "UTF-8"),
                    "DataSign", datasign,
                    "DataType", "2"
            );
            Response response = Http.post2(url, params, 6000);
            if (response.isOK()) {
                String content = response.getContent();
                KdniaoResponse obj = JsonUtil.fromJson(KdniaoResponse.class, content);
                cacheDao.hset(CacheDao.HOUR, orderCode, obj);
                return obj;
            }
        } catch (Exception e) {
            throw new ApplicationException(ApplicationExceptionEnum.SERVER_ERROR);
        }
        return null;

    }
}
