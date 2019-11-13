package cn.enilu.flash.bean.enumeration.shop;

/**
 * 订单状态
 * @author ：enilu
 * @date ：Created in 11/6/2019 3:15 PM
 */
public class OrderEnum {
    public enum  OrderStatusEnum {
        UN_PAY(1, "待付款"),
        UN_SEND(2, "待发货"),
        SENDED(3, "已发货"),
        FINISHED(4, "已完成");


        private String value;
        private Integer status;

        OrderStatusEnum(Integer status, String value) {
            this.status = status;
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public Integer getId() {
            return status;
        }
    }

    public static  OrderStatusEnum get(Integer status){
        for(OrderStatusEnum se : OrderStatusEnum.values()){
            if(se.getId().intValue() == status.intValue()){
                return se;
            }
        }
        return null;
    }
}
