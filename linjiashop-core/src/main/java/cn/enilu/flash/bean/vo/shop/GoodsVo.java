package cn.enilu.flash.bean.vo.shop;

import cn.enilu.flash.bean.entity.shop.Goods;
import cn.enilu.flash.bean.entity.shop.GoodsSku;
import lombok.Data;

import java.util.List;

/**
 * @author ：enilu
 * @date ：Created in 12/10/2019 7:57 PM
 */
@Data
public class GoodsVo {
    private Goods goods;
    private List<GoodsSku> skuList;

}
