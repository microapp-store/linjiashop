package cn.enilu.flash.bean.entity.shop;

import cn.enilu.flash.bean.entity.BaseEntity;
import lombok.Data;
import org.hibernate.annotations.Table;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

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
    @OneToMany(cascade = {CascadeType.ALL})
    @JoinColumn(name="id_attr_key")
    private List<AttrVal> attrVals;

}
