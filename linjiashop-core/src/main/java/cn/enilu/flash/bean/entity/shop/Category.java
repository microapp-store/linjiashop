package cn.enilu.flash.bean.entity.shop;

import cn.enilu.flash.bean.entity.BaseEntity;
import cn.enilu.flash.bean.entity.cms.Banner;
import lombok.Data;
import org.hibernate.annotations.Table;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Transient;
import java.util.List;

/**
 * @author ：enilu
 * @date ：Created in 2019/10/29 17:40
 */
@Data
@Table(appliesTo = "t_shop_category",comment = "商品类别")
@Entity(name="t_shop_category")
@EntityListeners(AuditingEntityListener.class)
public class Category extends BaseEntity {
    @Column(columnDefinition = "VARCHAR(16) COMMENT '名称'")
    private String name;
    @Column(columnDefinition = "VARCHAR(32) COMMENT '链接地址'")
    private String url;
    @Column(columnDefinition = "VARCHAR(64) COMMENT '图标'")
    private String icon;
    @Column(columnDefinition = "BIGINT COMMENT '父类别'")
    private Long pid;
    @Column(columnDefinition = "VARCHAR(64) COMMENT '备注'")
    private String descript;
    @Column(columnDefinition = "TINYINT COMMENT '是否删除'")
    private Boolean isDelete= false;
    /**
     * 改字段只对一级菜单生效，也即：只有一级菜单可以显示在首页
     */
    @Column(columnDefinition = "TINYINT COMMENT '是否显示在首页'")
    private Boolean showIndex= true;
    @Column(columnDefinition = "INT COMMENT '顺序'")
    private Integer sort;
    @Transient
    private List<Banner> bannerList;

}
