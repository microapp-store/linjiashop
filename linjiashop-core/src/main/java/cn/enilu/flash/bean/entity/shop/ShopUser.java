package cn.enilu.flash.bean.entity.shop;

import lombok.Data;
import org.hibernate.annotations.Table;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * @author ：enilu
 * @date ：Created in 2019/10/29 17:41
 */
@Data
@Table(appliesTo = "t_shop_user",comment = "用户")
@Entity(name="t_shop_user")
public class ShopUser  implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    @CreatedDate
    @Column(columnDefinition="DATETIME COMMENT '注册时间'")
    private Date createTime;
    @Column(columnDefinition = "VARCHAR(16) COMMENT '手机号'")
    private String mobile;
    @Column(columnDefinition = "VARCHAR(32) COMMENT '密码盐'")
    private String salt;
    @Column(columnDefinition = "VARCHAR(32) COMMENT '密码'")
    private String password;
    @Column(columnDefinition = "VARCHAR(32) COMMENT '昵称'")
    private String nickName;
    @Column(columnDefinition = "VARCHAR(64) COMMENT '头像'")
    private String avatar;
    @Column(columnDefinition = "VARCHAR(18) COMMENT '性别:male;female'")
    private String gender;
    @Column(columnDefinition = "DATETIME COMMENT '最后登陆时间'")
    private Date lastLoginTime;

}
