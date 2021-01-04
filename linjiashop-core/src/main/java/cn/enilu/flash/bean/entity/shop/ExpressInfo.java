package cn.enilu.flash.bean.entity.shop;

import cn.enilu.flash.bean.entity.BaseEntity;
import cn.enilu.flash.utils.JsonUtil;
import cn.enilu.flash.utils.Lists;
import cn.enilu.flash.utils.StringUtil;
import lombok.Data;
import org.hibernate.annotations.Table;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.List;
import java.util.Map;

/**
 * @author ：enilu
 * @date ：Created in 2020/01/02 22:54
 */
@Data
@Table(appliesTo = "t_shop_express_info",comment = "快递信息")
@Entity(name="t_shop_express_info")
public class ExpressInfo extends BaseEntity {
    /**
     * 在途中
     */
    public static  final Integer STATE_ING=0;
    /**
     * 已签收
     */
    public static  final Integer STATE_FINISH=1;
    /**
     * 问题件
     */
    public static  final Integer STATE_ERROR=-1;

    @Column(name = "id_order", columnDefinition = "BIGINT COMMENT '所属订单id'")
    private Long idOrder;
    @Column(name="shipping_sn",columnDefinition = "VARCHAR(32) COMMENT '快递单号'")
    private String shippingSn;
    @Column(name="express_company",columnDefinition = "VARCHAR(32) COMMENT '快递公司'")
    private String expressCompany;
    @Column(name = "state", columnDefinition = "INT COMMENT '状态:0在途中,1:已签收,-1问题件'")
    private Integer state;
    @Column(name="info",columnDefinition = "TEXT COMMENT '详细信息'")
    private String info;

    @Transient
    public List<Map> infoList;

    public  List<Map>  getInfoList(){
        if(StringUtil.isNotEmpty(info)){
            return JsonUtil.fromJsonAsList(Map.class,getInfo());
        }
        return Lists.newArrayList();
    }

    public String getStateStr(){
        String result = "";
        switch (state){
            case 0:
               result = "在途中";
               break;
            case 1:
                result = "已签收";
                break;
            case -1:
                result = "问题件";
                break;

        }
        return result;
    }
}
