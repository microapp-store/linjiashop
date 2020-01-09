package cn.enilu.flash.bean.entity.shop;

import cn.enilu.flash.bean.entity.BaseEntity;
import lombok.Data;
import org.hibernate.annotations.Table;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * @author ：enilu
 * @date ：Created in 12/8/2019 10:36 PM
 */

@Data
@Table(appliesTo = "t_shop_attr_val",comment = "商品属性值")
@Entity(name="t_shop_attr_val")
@EntityListeners(AuditingEntityListener.class)
public class AttrVal extends BaseEntity {
    @Column(name="id_attr_key",columnDefinition = "BIGINT COMMENT '属性id'")
    private Long idAttrKey;
    @Column(name="attr_val",columnDefinition = "VARCHAR(32) COMMENT '属性值'")
    private String attrVal;

}
