package cn.enilu.flash.bean.entity.shop;

import cn.enilu.flash.bean.entity.ShopBaseEntity;
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
@Table(appliesTo = "t_shop_goods_sku",comment = "商品SKU")
@Entity(name="t_shop_goods_attr_sku")
public class GoodsSku extends ShopBaseEntity {
    @Column(name="attr_symbol_path",columnDefinition = "BIGINT COMMENT '属性搭配方式'")
    private Long idAttrKey;
    @Column(name="id_goods",columnDefinition = "BIGINT COMMENT '商品id'")
    private Long idGoods;
    @Column(name="price",columnDefinition = "VARCHAR(32) COMMENT '价格'")
    private String price;
    @Column(name="stock",columnDefinition = "INT COMMENT '库存'")
    private Integer stock;

}
