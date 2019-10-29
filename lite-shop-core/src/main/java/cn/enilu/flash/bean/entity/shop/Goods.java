package cn.enilu.flash.bean.entity.shop;

import cn.enilu.flash.bean.entity.BaseEntity;
import lombok.Data;
import org.hibernate.annotations.Table;

import javax.persistence.*;

/**
 * @author ：enilu
 * @date ：Created in 2019/10/29 17:39
 */
@Data
@Table(appliesTo = "t_shop_goods",comment = "商品")
@Entity(name="t_shop_goods")
public class Goods extends BaseEntity {
    @Column(columnDefinition = "VARCHAR(32) COMMENT '名称'")
    private String name;
    @Column(columnDefinition = "VARCHAR(16) COMMENT '小图'")
    private String pic;
    @Column(columnDefinition = "VARCHAR(32) COMMENT '大图相册列表,以逗号分隔'")
    private String  gallery;
    @Column(name="id_category",columnDefinition = "BIGINT COMMENT '类别id'")
    private Long idCategory;
    @JoinColumn(name="id_category", referencedColumnName = "id",  columnDefinition = "BIGINT COMMENT '类别id'", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;
    @Column(columnDefinition = "TEXT COMMENT '产品详情'")
    private String detail;
    @Column(columnDefinition = "TEXT COMMENT '产品规格'")
    private String specifications;

}
