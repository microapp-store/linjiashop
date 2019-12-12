package cn.enilu.flash.bean.entity.shop;

import cn.enilu.flash.bean.entity.BaseEntity;
import lombok.Data;
import org.hibernate.annotations.Table;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import java.math.BigDecimal;

/**
 * @author ：enilu
 * @date ：Created in 12/8/2019 10:36 PM
 */

@Data
@Table(appliesTo = "t_shop_goods_sku",comment = "商品SKU")
@Entity(name="t_shop_goods_sku")
@EntityListeners(AuditingEntityListener.class)
public class GoodsSku extends BaseEntity {
    @Column(name="code",columnDefinition = "VARCHAR(32) COMMENT 'sku编码,格式:逗号分割的属性值id'")
    private String code;
    @Column(name="code_name",columnDefinition = "VARCHAR(32) COMMENT 'sku名称,格式:逗号分割的属性值'")
    private String codeName;
    @Column(name="id_goods",columnDefinition = "BIGINT COMMENT '商品id'")
    private Long idGoods;
    @Column(name="price",columnDefinition = "VARCHAR(32) COMMENT '价格'")
    private BigDecimal price;
    @Column(name="marketing_price",columnDefinition = "VARCHAR(32) COMMENT '市场价,原价'")
    private BigDecimal marketingPrice;
    @Column(name="stock",columnDefinition = "INT COMMENT '库存'")
    private Integer stock;

}
