package cn.enilu.flash.bean.entity.system;

import cn.enilu.flash.bean.entity.BaseEntity;
import lombok.Data;
import org.hibernate.annotations.Table;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;

/**
 * @author ：enilu
 * @date ：Created in 2020/2/9 10:00
 * 物流公司
 */
@Data
@Table(appliesTo = "t_sys_express",comment = "物流公司")
@Entity(name="t_sys_express")
@EntityListeners(AuditingEntityListener.class)
public class Express extends BaseEntity {
    @Column(columnDefinition = "VARCHAR(32) COMMENT '公司名称'")
    private String name;
    @Column(columnDefinition = "VARCHAR(16) COMMENT '公司编码'")
    private String code;
    @Column(columnDefinition = "TINYINT COMMENT '是否禁用'")
    private Boolean disabled = false;
    @Column(columnDefinition = "INT COMMENT '排序'")
    private Integer sort;

}
