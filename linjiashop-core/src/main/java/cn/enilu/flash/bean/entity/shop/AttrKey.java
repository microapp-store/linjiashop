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
 * @date ：Created in 12/8/2019 10:31 PM
 */

@Data
@Table(appliesTo = "t_shop_attr_key",comment = "商品属性名")
@Entity(name="t_shop_attr_key")
@EntityListeners(AuditingEntityListener.class)
public class AttrKey extends BaseEntity {
    @Column(name="attr_name",columnDefinition = "VARCHAR(32) COMMENT '属性名'")
    private String attrName;
    @Column(name="id_category",columnDefinition = "BIGINT COMMENT '商品类别id'")
    private Long idCategory;
    @Column(name="id_goods",columnDefinition = "BIGINT COMMENT '商品id'")
    private Long idGoods;

}
