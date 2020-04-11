package cn.enilu.flash.security;

import cn.enilu.flash.bean.core.AuthorizationUser;
import cn.enilu.flash.bean.entity.shop.ShopUser;
import cn.enilu.flash.bean.entity.system.Role;
import cn.enilu.flash.bean.entity.system.User;
import cn.enilu.flash.bean.vo.JwtUser;
import cn.enilu.flash.bean.vo.SpringContextHolder;
import cn.enilu.flash.cache.CacheDao;
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

import java.util.*;

/**
 * 用户认证服务类,后台管理系统和前端用户系统都用该服务类做登录验证相关逻辑
 */
@Service
@DependsOn("springContextHolder")
public class UserService {
    private Logger logger = LoggerFactory.getLogger(UserService.class);
     @Value("${jwt.user.type}")
     private Integer userType;
    @Value("${jwt.token.expire.time}")
    private Long tokenExpireTime ;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private TokenCache tokenCache;
    @Autowired
    private CacheDao cacheDao;
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
            userBean.setId(user.getId());
            userBean.setAccount(user.getAccount());
            userBean.setDeptId(user.getDeptid());
            userBean.setDeptName(ConstantFactory.me().getDeptName(user.getDeptid()));
            userBean.setName(user.getName());
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

    /**
     * 获取新的token
     * @return
     */
    public String  refreshToken(){
        //获取用户信息
        String oldToken = HttpUtil.getToken();
        AuthorizationUser userBean = tokenCache.getUser(oldToken);

        //验证refreshToken是否有效
        if(refreshTokenIsValid(oldToken)) {
            //生成新token 返回界面
            JwtUser jwtUser = new JwtUser(userBean);
            String newToken = loginForToken(jwtUser);
            return newToken;
        }
        return null;
    }

    public boolean refreshTokenIsValid(String token){
        String  refreshTokenTime = (String) cacheDao.hget(CacheDao.SESSION,token);
        if(refreshTokenTime == null){
            return false;
        }
        return System.currentTimeMillis()<=Long.valueOf(refreshTokenTime);

    }

    public String loginForToken(JwtUser user){
        //获取用户token值
        String token = JwtUtil.sign(user,tokenExpireTime*60000);
        //将token作为RefreshToken Key 存到缓存中，缓存时间为token有效期的两倍
        String   refreshTokenCacheKey = token;
        Date expireDate = new Date(System.currentTimeMillis()+tokenExpireTime*120000);
        cacheDao.hset(CacheDao.SESSION,refreshTokenCacheKey,String.valueOf(expireDate.getTime()));
        return token;
    }


}
