package cn.enilu.flash.service.shop;


import cn.enilu.flash.bean.entity.shop.Cart;
import cn.enilu.flash.bean.entity.shop.Goods;
import cn.enilu.flash.bean.entity.shop.GoodsSku;
import cn.enilu.flash.bean.vo.query.SearchFilter;
import cn.enilu.flash.bean.vo.shop.CartVo;
import cn.enilu.flash.dao.shop.CartRepository;
import cn.enilu.flash.dao.shop.GoodsRepository;
import cn.enilu.flash.dao.shop.GoodsSkuRepository;
import cn.enilu.flash.service.BaseService;
import cn.enilu.flash.utils.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CartService extends BaseService<Cart,Long,CartRepository>  {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private GoodsSkuRepository goodsSkuRepository;
    @Autowired
    private GoodsRepository goodsRepository;

    /**
     * 添加到购物车<br>
     * 1,这里需要考虑库存管控的问题
     * 2，实际项目中如果需要部署服务集群，那么下面synchronized关键字一点用是没有的，可以考虑使用分布式锁（zookeeper，redis等）来处理
     * @param cartVo
     * @return，添加新商品，返回1，添加购物车已经存在的商品，返回0
     */
    @Transactional
    public synchronized  Integer add(CartVo cartVo) {
        Integer count = cartVo.getCount();

        Long idSku = cartVo.getIdSku();

        List<SearchFilter> searchFilters = Lists.newArrayList(
                SearchFilter.build("idUser",cartVo.getIdUser()),
                SearchFilter.build("idGoods",cartVo.getIdGoods())
        );

        if(idSku!=null){
            searchFilters.add(SearchFilter.build("idSku",idSku));

        }
        Cart old  = get(searchFilters);
        Integer result = 0;
        if(old!=null){
            //判断之前是否添加到购物车，如果已添加，则在原有基础上增加购买数量即可
            old.setCount(old.getCount().add(new BigDecimal(count)));
            update(old);

        }else {
            //购物车新增商品
            Cart cart = new Cart();
            cart.setIdGoods(cartVo.getIdGoods());
            cart.setCount(new BigDecimal(count));
            cart.setIdUser(cartVo.getIdUser());
            cart.setIdSku(idSku);
            insert(cart);
            result = 1;
        }

        //减库存
        if(idSku!=null){
            GoodsSku goodsSku = goodsSkuRepository.getOne(idSku);
            if(goodsSku.getStock()<count){
                throw  new RuntimeException("库存不足");
            }
            goodsSku.setStock(goodsSku.getStock()-count);
        }
        Goods goods = goodsRepository.getOne(cartVo.getIdGoods());
        if(goods.getStock()<count){
            throw  new RuntimeException("库存不足");
        }
        goods.setStock(goods.getStock()-count);
        goodsRepository.save(goods);
        return result;
    }
}

