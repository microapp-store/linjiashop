package cn.enilu.flash.bean.vo;

import cn.enilu.flash.utils.StringUtil;
import lombok.Data;

import java.util.Date;

/**
 * @author ：enilu
 * @date ：Created in 11/6/2019 11:29 AM
 */
@Data
public class UserInfo {
    private String mobile;
    private String avatar;
    private String nickName;
    private Date lastLoginTime;
    private String gender;
    private String wechatNickName;
    private String wechatHeadImgUrl;
    private Boolean refreshWechatInfo = true;

    public String getNickName() {
        return StringUtil.isEmpty(nickName)?wechatNickName:nickName;
    }
}
