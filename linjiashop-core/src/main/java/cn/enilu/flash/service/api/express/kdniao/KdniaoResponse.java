package cn.enilu.flash.service.api.express.kdniao;

import cn.enilu.flash.utils.JsonUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * @author ：enilu
 * @date ：Created in 2020/6/1 0:22
 */
@Data
public class KdniaoResponse {
    @JsonProperty("LogisticCode")
    private String logisticCode;
    @JsonProperty("ShipperCode")
    private String shipperCode;
    @JsonProperty("EBusinessID")
    private String eBusinessID;
    @JsonProperty("Success")
    private Boolean success;
    @JsonProperty("State")
    private String state;
    @JsonProperty("StateEx")
    private String stateEx;
    @JsonProperty("Location")
    private String location;
    @JsonProperty("Traces")
    private List<Trace> traces;

    public static void main(String[] args) {
        String json = "{\n" +
                "    \"LogisticCode\":\"YT9420173297830\",\n" +
                "    \"ShipperCode\":\"YTO\",\n" +
                "    \"Traces\":[\n" +
                "        {\n" +
                "            \"AcceptStation\":\"【广东省广州市白云区大源公司】 已收件 取件人: 王仟 (15112094169)\",\n" +
                "            \"AcceptTime\":\"2020-12-25 23:02:51\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"AcceptStation\":\"【广东省广州市白云区大源】 已发出 下一站 【广州转运中心公司】\",\n" +
                "            \"AcceptTime\":\"2020-12-26 01:00:51\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"AcceptStation\":\"【广州转运中心公司】 已收入\",\n" +
                "            \"AcceptTime\":\"2020-12-26 02:12:34\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"AcceptStation\":\"【广州转运中心】 已发出\",\n" +
                "            \"AcceptTime\":\"2020-12-26 03:02:34\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"AcceptStation\":\"【浦东转运中心公司】 已收入\",\n" +
                "            \"AcceptTime\":\"2020-12-28 04:26:14\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"AcceptStation\":\"【浦东转运中心】 已发出 下一站 【上海市浦东新区博兴菏泽路分部公司】\",\n" +
                "            \"AcceptTime\":\"2020-12-28 04:42:09\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"AcceptStation\":\"【上海市浦东新区博兴菏泽路分部公司】 已收入\",\n" +
                "            \"AcceptTime\":\"2020-12-28 07:33:45\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"AcceptStation\":\"【上海市浦东新区博兴菏泽路分部公司】 派件中  派件人: 李红彬 电话 13381798120  如有疑问，请联系：18521102150\",\n" +
                "            \"AcceptTime\":\"2020-12-28 08:41:53\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"AcceptStation\":\"快件已由上海市莱阳路431号店菜鸟驿站代收，请及时取件，如有疑问请联系13262707273\",\n" +
                "            \"AcceptTime\":\"2020-12-28 11:54:00\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"AcceptStation\":\"客户签收人: 已签收，签收人凭取货码签收。 已签收  感谢使用圆通速递，期待再次为您服务 如有疑问请联系：13381798120，投诉电话：18521102150\",\n" +
                "            \"AcceptTime\":\"2020-12-28 19:59:21\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"State\":\"3\",\n" +
                "    \"EBusinessID\":\"1627883\",\n" +
                "    \"Success\":true\n" +
                "}";
        KdniaoResponse response = JsonUtil.fromJson(KdniaoResponse.class,json);
        System.out.println(JsonUtil.toJson(response));
    }
}
