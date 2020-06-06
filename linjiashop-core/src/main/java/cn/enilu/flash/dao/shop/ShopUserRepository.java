package cn.enilu.flash.dao.shop;


import cn.enilu.flash.bean.entity.shop.ShopUser;
import cn.enilu.flash.dao.BaseRepository;


public interface ShopUserRepository extends BaseRepository<ShopUser,Long>{

    ShopUser findByMobile(String mobile);

    ShopUser findByWechatOpenId(String openId);
}

