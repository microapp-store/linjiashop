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
     * 添加到购物车
     * @param cartVo
     * @return，添加新商品，返回1，添加购物车已经存在的商品，返回0
     */
    public Integer add(CartVo cartVo) {
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
            old.setCount(old.getCount().add(new BigDecimal(count)));
            update(old);

        }else {
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
            goodsSku.setStock(goodsSku.getStock()-count);
        }
        Goods goods = goodsRepository.getOne(cartVo.getIdGoods());
        goods.setStock(goods.getStock()-count);
        goodsRepository.save(goods);
        return result;
    }
}

