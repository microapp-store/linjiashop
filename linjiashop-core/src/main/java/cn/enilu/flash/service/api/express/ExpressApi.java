package cn.enilu.flash.service.api.express;

import cn.enilu.flash.bean.entity.shop.ExpressInfo;
import cn.enilu.flash.service.api.express.kdniao.KdniaoResponse;

/**
 * @author ：enilu
 * @date ：Created in 2021/1/2 23:13
 */
public interface ExpressApi {
    /**
     * 查询快递实时信息
     * @param orderNo
     * @param company
     * @return
     */
    ExpressInfo   realTimeQuery(String orderNo, String company) ;
}
