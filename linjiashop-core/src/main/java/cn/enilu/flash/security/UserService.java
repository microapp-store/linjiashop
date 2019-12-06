package cn.enilu.flash.security;

import cn.enilu.flash.bean.core.AuthorizationUser;
import cn.enilu.flash.bean.entity.shop.ShopUser;
import cn.enilu.flash.bean.entity.system.Role;
import cn.enilu.flash.bean.entity.system.User;
import cn.enilu.flash.bean.vo.JwtUser;
import cn.enilu.flash.bean.vo.SpringContextHolder;
import cn.enilu.flash.cache.TokenCache;
import cn.enilu.flash.dao.shop.ShopUserRepository;
import cn.enilu.flash.dao.system.MenuRepository;
import cn.enilu.flash.dao.system.RoleRepository;
import cn.enilu.flash.dao.system.UserRepository;
import cn.enilu.flash.service.system.impl.ConstantFactory;
import cn.enilu.flash.utils.Convert;
import cn.enilu.flash.utils.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 用户认证服务类
 */
@Service
@DependsOn("springContextHolder")
@Transactional(readOnly = true)
public class UserService {
    private Logger logger = LoggerFactory.getLogger(UserService.class);
     @Value("${jwt.user.type}")
     private Integer userType;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private TokenCache tokenCache;
    @Autowired
    private ShopUserRepository shopUserRepository;

    public static UserService me() {
        return SpringContextHolder.getBean(UserService.class);
    }


    public AuthorizationUser getAuthorizationInfo(String account) {
        String token = HttpUtil.getToken();
        AuthorizationUser userBean = tokenCache.getUser(token);

        if(userBean!=null){
            return userBean;
        }
        userBean = new AuthorizationUser();
        //构建后台用户认证信息
        if(userType == JwtUser.MANAGER) {
            User user = userRepository.findByAccount(account);
            userBean.setId(user.getId());            // 账号id
            userBean.setAccount(user.getAccount());// 账号
            userBean.setDeptId(user.getDeptid());    // 部门id
            userBean.setDeptName(ConstantFactory.me().getDeptName(user.getDeptid()));// 部门名称
            userBean.setName(user.getName());        // 用户名称
            userBean.setPassword(user.getPassword());
            Long[] roleArray = Convert.toLongArray(",", user.getRoleid());
            List<Long> roleList = new ArrayList<Long>();
            List<String> roleNameList = new ArrayList<String>();
            List<String> roleCodeList = new ArrayList<String>();
            Set<String> permissions = new HashSet<String>();
            Set<String> resUrls = new HashSet<>();
            for (Long roleId : roleArray) {
                roleList.add(roleId);
                Role role = roleRepository.getOne(roleId);
                roleNameList.add(role.getName());
                roleCodeList.add(role.getTips());
                permissions.addAll(menuRepository.getResCodesByRoleId(roleId));
                resUrls.addAll(menuRepository.getResUrlsByRoleId(roleId));

            }
            userBean.setRoleList(roleList);
            userBean.setRoleNames(roleNameList);
            userBean.setRoleCodes(roleCodeList);
            userBean.setPermissions(permissions);
            userBean.setUrls(resUrls);
        }
        //构建前台用户认证信息
        if(userType == JwtUser.FRONT_USER){
            ShopUser user = shopUserRepository.findByMobile(account);
            userBean.setId(user.getId());
            userBean.setAccount(user.getMobile());
            userBean.setPassword(user.getPassword());
        }
        tokenCache.setUser(token,userBean);
        return userBean;
    }


}
