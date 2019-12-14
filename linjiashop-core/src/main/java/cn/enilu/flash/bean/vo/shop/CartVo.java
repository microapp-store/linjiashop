package cn.enilu.flash.bean.vo.shop;

import lombok.Data;

/**
 * @author ：enilu
 * @date ：Created in 12/13/2019 2:50 PM
 */
@Data
public class CartVo {
    private Long idGoods;
    private Integer count;
    private Long idSku;
    private Long idUser;

}
