package cn.enilu.flash.bean.entity;

import cn.enilu.flash.bean.entity.shop.ShopUser;
import cn.enilu.flash.bean.entity.system.User;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 前端业务实体基类
 * @author ：enilu
 * @date ：Created in 2019/10/29 19:09
 */

@MappedSuperclass
@Data
public abstract class ShopBaseEntity implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    @CreatedDate
    @Column(name = "create_time",columnDefinition="DATETIME COMMENT '创建时间/注册时间'")
    private Date createTime;
    @Column(name = "create_by",columnDefinition="bigint COMMENT '创建人'")
    @CreatedBy
    private Long createBy;
    @JoinColumn(name="create_by", referencedColumnName = "id",  columnDefinition = "BIGINT COMMENT '借据ID'", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    @JSONField(serialzeFeatures = SerializerFeature.WriteMapNullValue)
    private ShopUser creator;
    @LastModifiedDate
    @Column(name = "modify_time",columnDefinition="DATETIME COMMENT '最后更新时间'")
    private Date modifyTime;
    @LastModifiedBy
    @Column(name = "modify_by",columnDefinition="bigint COMMENT '最后更新人'")
    private Long modifyBy;
}