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
        FINISHED(4, "已完成"),
        CANCEL(5,"已取消"),
        REFUND_ING(6,"退款中"),
        REFUND(7,"已退款");


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

    public static String getStatusStr(Integer status){
        String statusStr = null;
        switch (status){
            case 1:
                statusStr = "unPay";
                break;
            case 2:
                statusStr = "unSend";
                break;
            case 3:
                statusStr = "sended";
                break;
            case 4:
                statusStr = "finished";
                break;
            case 5:
                statusStr = "cancel";
                break;
            case 6:
                statusStr = "refundIng";
                break;
            case 7:
                statusStr = "refund";
                break;
                default:
                    break;


        }
        return statusStr;

    }
    public static  Integer getStatusByStr(String statusStr){
        Integer state = null;
        switch (statusStr){
            case "unPay":
                state = 1;
                break;
            case "unSend":
                state =2;
                break;
            case "sended":
                state = 3;
                break;
            case "finished":
                state = 4;
                break;
            case "cancel":
                state = 5;
                break;
            case "refundIng":
                state = 6;
                break;
            case "refund":
                state = 7;
                break;
                default:
                    state = null;
                    break;

        }
        return state;
    }

    public static  OrderStatusEnum get(Integer status){
        for(OrderStatusEnum se : OrderStatusEnum.values()){
            if(se.getId().intValue() == status.intValue()){
                return se;
            }
        }
        return null;
    }

    public enum  PayTypeEnum {
        UN_PAY("alipay", "支付宝"),
        UN_SEND("wxpay", "微信支付");


        private String value;
        private String key;

        PayTypeEnum(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public String getKey() {
            return key;
        }
    }

    public static PayTypeEnum get(String key){
        for(PayTypeEnum payTypeEnum : PayTypeEnum.values()){
            if(payTypeEnum.getKey().equals(key)){
                return payTypeEnum;
            }
        }
        return null;
    }


    public enum  PayStatusEnum {
        UN_PAY(1, "未付款"),
        UN_SEND(2, "已付款"),
        PAYING(3, "支付中");


        private String value;
        private Integer status;

        PayStatusEnum(Integer status, String value) {
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

    public static PayStatusEnum getPayStatus(Integer status){
        for(PayStatusEnum se : PayStatusEnum.values()){
            if(se.getId().intValue() == status.intValue()){
                return se;
            }
        }
        return null;
    }


}
