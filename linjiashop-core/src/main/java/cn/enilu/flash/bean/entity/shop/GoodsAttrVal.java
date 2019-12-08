package cn.enilu.flash.bean.entity.shop;

import cn.enilu.flash.bean.entity.BaseEntity;
import lombok.Data;
import org.hibernate.annotations.Table;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;

/**
 * @author ：enilu
 * @date ：Created in 12/8/2019 10:36 PM
 */

@Data
@Table(appliesTo = "t_shop_goods_attr_val",comment = "商品属性值")
@Entity(name="t_shop_goods_attr_val")
@EntityListeners(AuditingEntityListener.class)
public class GoodsAttrVal extends BaseEntity {
    @Column(name="id_attr_key",columnDefinition = "BIGINT COMMENT '属性id'")
    private Long idAttrKey;
    @Column(name="id_goods",columnDefinition = "BIGINT COMMENT '商品id'")
    private Long idGoods;
    @Column(name="attr_val",columnDefinition = "VARCHAR(32) COMMENT '属性值'")
    private String attrVal;
    /**
     * 该字段是对指定商品下的的属性值的一个序号标记, 是为了提高检索效率。
     */
    @Column(name="symbol",columnDefinition = "INT COMMENT '属性编码'")
    private Integer symbol;

}
