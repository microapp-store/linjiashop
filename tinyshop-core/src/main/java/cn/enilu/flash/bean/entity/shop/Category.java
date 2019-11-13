package cn.enilu.flash.bean.entity.shop;

import cn.enilu.flash.bean.entity.BaseEntity;
import cn.enilu.flash.bean.entity.ShopBaseEntity;
import lombok.Data;
import org.hibernate.annotations.Table;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * @author ：enilu
 * @date ：Created in 2019/10/29 17:40
 */
@Data
@Table(appliesTo = "t_shop_category",comment = "商品类别")
@Entity(name="t_shop_category")
public class Category extends ShopBaseEntity {
    @Column(columnDefinition = "VARCHAR(16) COMMENT '名称'")
    private String name;
    @Column(columnDefinition = "VARCHAR(32) COMMENT '链接地址'")
    private String url;
    @Column(columnDefinition = "VARCHAR(32) COMMENT '图标'")
    private String icon;

}
