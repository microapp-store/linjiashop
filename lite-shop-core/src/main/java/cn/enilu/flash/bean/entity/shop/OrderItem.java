package cn.enilu.flash.bean.entity.shop;

import cn.enilu.flash.bean.entity.ShopBaseEntity;
import lombok.Data;
import org.hibernate.annotations.Table;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @author ：enilu
 * @date ：Created in 2019/10/29 17:40
 */
@Data
@Table(appliesTo = "t_shop_order_item",comment = "订单明细")
@Entity(name="t_shop_order_item")
public class OrderItem extends ShopBaseEntity {
    @Column(columnDefinition = "BIGINT COMMENT '所属订单id'")
    private Long idOrder;
    @Column(name="id_goods",columnDefinition = "BIGINT COMMENT '商品id'")
    private Long idGoods;
    @JoinColumn(name="id_goods", referencedColumnName = "id",  columnDefinition = "BIGINT COMMENT '商品id'", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Goods goods;
    @Column(columnDefinition = "VARCHAR(16) COMMENT '单价'")
    private BigDecimal price;
    @Column(columnDefinition = "VARCHAR(16) COMMENT '数量'")
    private BigDecimal amount;
    @Column(columnDefinition = "VARCHAR(16) COMMENT '合计'")
    private BigDecimal totalPrice;
}
