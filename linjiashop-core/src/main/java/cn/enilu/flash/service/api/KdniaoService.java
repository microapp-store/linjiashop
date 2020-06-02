package cn.enilu.flash.service.api;

import cn.enilu.flash.bean.constant.CfgKey;
import cn.enilu.flash.bean.exception.ApplicationException;
import cn.enilu.flash.bean.exception.ApplicationExceptionEnum;
import cn.enilu.flash.cache.CacheDao;
import cn.enilu.flash.service.system.CfgService;
import cn.enilu.flash.utils.*;
import org.nutz.http.Http;
import org.nutz.http.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
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
        //todo 测试数据
        String txt = "{\"LogisticCode\":\"YT45782342348\",\"ShipperCode\":\"YTO\",\"Traces\":[{\"AcceptStation\":\"【上海市奉贤区南桥公司】 已收件 取件人: 仰琪锋 (02167190359)\",\"AcceptTime\":\"2020-05-25 15:59:59\"},{\"AcceptStation\":\"【宁波转运中心公司】 已收入\",\"AcceptTime\":\"2020-05-26 03:01:31\"},{\"AcceptStation\":\"【宁波转运中心】 已发出 下一站 【上海市浦东新区博兴公司】\",\"AcceptTime\":\"2020-05-26 03:22:31\"},{\"AcceptStation\":\"【浙江省舟山市公司】 已收入\",\"AcceptTime\":\"2020-05-26 06:31:51\"},{\"AcceptStation\":\"快件已由上海市莱阳路431号店菜鸟驿站代收，请及时取件，如有疑问请联系13262707273\",\"AcceptTime\":\"2020-05-28 11:33:33\"},{\"AcceptStation\":\"客户签收人: 已签收，签收人凭取货码签收。 已签收  感谢使用圆通速递，期待再次为您服务 如有疑问请联系：13381798120，投诉电话：18521102150\",\"AcceptTime\":\"2020-05-28 18:59:55\"}],\"State\":\"3\",\"EBusinessID\":\"1627883\",\"Success\":true}\n";
        kdniaoResponse = JsonUtil.fromJson(KdniaoResponse.class,txt);
        //按照日期进行倒叙排序
        Collections.sort(kdniaoResponse.getTraces(), new Comparator<KdniaoResponse.Trace>() {
            @Override
            public int compare(KdniaoResponse.Trace t1, KdniaoResponse.Trace t2) {
                Date d1 = DateUtil.parseTime(t1.getAcceptTime());
                Date d2 = DateUtil.parseTime(t2.getAcceptTime());
                return d1.getTime()<d2.getTime()?1:-1;
            }
        });

        //todo 测试
        if(1==1){
            cacheDao.hset(CacheDao.HOUR,orderCode,kdniaoResponse);
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
        String jsonStr = JsonUtil.toJsonString(appParams);
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
