package cn.enilu.flash.service.system;

import cn.enilu.flash.cache.TokenCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * AccountService
 *
 * @author enilu
 * @version 2018/9/12 0012
 */
@Service
public class AccountService {
    @Autowired
    private TokenCache tokenCache;
    public void logout(String token) {
        tokenCache.remove(token);
    }

}
