package cn.enilu.flash.bean.entity.shop;

import cn.enilu.flash.bean.entity.ShopBaseEntity;
import cn.enilu.flash.bean.enumeration.shop.OrderEnum;
import lombok.Data;
import org.hibernate.annotations.Table;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author ：enilu
 * @date ：Created in 2019/10/29 17:40
 */
@Data
@Table(appliesTo = "t_shop_order",comment = "订单")
@Entity(name="t_shop_order")
public class Order extends ShopBaseEntity {
    @Column(name="id_user",columnDefinition = "BIGINT COMMENT '用户id'")
    private Long idUser;
    @JoinColumn(name="id_user", insertable = false, updatable = false,foreignKey = @ForeignKey(name="none",value = ConstraintMode.NO_CONSTRAINT))
    @ManyToOne(fetch = FetchType.LAZY)
    private ShopUser user;
    @Column(columnDefinition = "VARCHAR(32) COMMENT '订单号'")
    private String orderSn;
    @Column(columnDefinition = "INT COMMENT '状态'")
    private Integer status;
    @Column(name="id_address",columnDefinition = "BIGINT COMMENT '收货信息'")
    private Long idAddress;
    @JoinColumn(name="id_address",  insertable = false, updatable = false,foreignKey = @ForeignKey(name="none",value = ConstraintMode.NO_CONSTRAINT))
    @ManyToOne(fetch = FetchType.LAZY)
    private Address address;
    @OneToMany(cascade = {CascadeType.ALL})
    @JoinColumn(name = "id_order")
    private List<OrderItem> items;
    @Column(columnDefinition = "VARCHAR(64) COMMENT '订单备注'")
    private String message;
    @Column(columnDefinition = "VARCHAR(32) COMMENT '总金额'")
    private BigDecimal totalPrice;
    @Column(columnDefinition = "VARCHAR(32) COMMENT '优惠券抵扣金额'")
    private BigDecimal couponPrice;
    @Column(columnDefinition = "VARCHAR(32) COMMENT '实付金额'")
    private BigDecimal realPrice;


    public String getStatusName(){
        return OrderEnum.get(status).getValue();
    }

}
