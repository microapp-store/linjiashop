package cn.enilu.flash.bean.entity.shop;

import cn.enilu.flash.bean.entity.ShopBaseEntity;
import lombok.Data;
import org.hibernate.annotations.Table;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * @author ：enilu
 * @date ：Created in 2019/10/29 17:39
 */
@Data
@Table(appliesTo = "t_shop_address",comment = "收货地址")
@Entity(name="t_shop_address")
public class Address extends ShopBaseEntity {
    @Column(columnDefinition = "BIGINT COMMENT '用户id'")
    private Long idUser;
    @Column(columnDefinition = "VARCHAR(32) COMMENT '收件人'")
    private String name;
    @Column(columnDefinition = "VARCHAR(16) COMMENT '联系电话'")
    private String tel;
    @Column(columnDefinition = "VARCHAR(16) COMMENT '省'")
    private String province;
    @Column(columnDefinition = "VARCHAR(16) COMMENT '市'")
    private String city;
    @Column(columnDefinition = "VARCHAR(16) COMMENT '区县'")
    private String district;
    @Column(columnDefinition = "VARCHAR(16) COMMENT '地区编码'")
    private String areaCode;

    @Column(columnDefinition = "VARCHAR(64) COMMENT '详细地址'")
    private String addressDetail;
    @Column(columnDefinition = "VARCHAR(6) COMMENT '邮政编码'")
    private String postCode;
    @Column(columnDefinition = "tinyint COMMENT '是否默认'")
    private Boolean isDefault=false;
    @Column(columnDefinition = "tinyint COMMENT '是否删除'")
    private Boolean isDelete= false;

}
