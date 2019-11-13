package cn.enilu.flash.bean.vo;

import cn.enilu.flash.bean.entity.shop.ShopUser;
import cn.enilu.flash.bean.entity.system.User;
import lombok.Data;

/**
 * @author ：enilu
 * @date ：Created in 11/4/2019 8:16 PM
 */
@Data
public class JwtUser {
    public static  int MANAGER= 0;
    public static  int FRONT_USER= 1;
    private String userName;
    private String password;
    private Long id;

    public JwtUser(ShopUser user){
        this.userName = user.getMobile();
        this.id = user.getId();
        this.password = user.getPassword();
    }
    public JwtUser(User user){
        this.userName = user.getAccount();
        this.id = user.getId();
        this.password = user.getPassword();
    }
}
