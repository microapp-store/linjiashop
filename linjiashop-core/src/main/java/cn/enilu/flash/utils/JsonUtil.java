package cn.enilu.flash.utils;

import com.alibaba.fastjson.JSON;

/**
 * @author ：enilu
 * @date ：Created in 2020/5/31 21:55
 */
public class JsonUtil {
    public static  String toJsonString(Object obj){
        return obj==null?"":JSON.toJSONString(obj);
    }
    public static  Object fromJson(String jsonStr){
        return StringUtil.isNotEmpty(jsonStr)?JSON.parse(jsonStr):null;
    }

    public static  <T> T  fromJson(Class<T>  klass, String content) {
        return JSON.parseObject(content,klass);
    }
}
