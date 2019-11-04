package cn.enilu.flash.bean.entity.shop;

import cn.enilu.flash.bean.entity.ShopBaseEntity;
import lombok.Data;
import org.hibernate.annotations.Table;

import javax.persistence.*;

/**
 * @author ：enilu
 * @date ：Created in 2019/10/29 17:40
 */
@Data
@Table(appliesTo = "t_shop_cart",comment = "购物车")
@Entity(name="t_shop_cart")
public class Cart extends ShopBaseEntity {
    @Column(name="id_user",columnDefinition = "BIGINT COMMENT '用户id'")
    private Long idUser;
    @JoinColumn(name="id_user", insertable = false, updatable = false,foreignKey = @ForeignKey(name="none",value = ConstraintMode.NO_CONSTRAINT))
    @ManyToOne(fetch = FetchType.LAZY)
    private ShopUser user;
    @Column(name="id_goods",columnDefinition = "BIGINT COMMENT '商品id'")
    private Long idGoods;
    @JoinColumn(name="id_goods", insertable = false, updatable = false,foreignKey = @ForeignKey(name="none",value = ConstraintMode.NO_CONSTRAINT))
    @ManyToOne(fetch = FetchType.LAZY)
    private Goods goods;
    @Column(columnDefinition = "VARCHAR(32) COMMENT '产品规格'")
    private String specifications;
}
