import request from '@/utils/request'

export default {
    save: function (params) {
        return request({
            url: '/user/order/save',
            method: 'post',
            params
        })
    },
    get: function (orderSn) {
        return request({
            url: '/user/order/' + orderSn,
            method: 'get'
        })
    },
    /**
     * 获取物流信息
     * @param orderSn
     */
    getExpressInfo: function (orderSn) {
        return request({
            url: '/user/order/getExpressInfo/' + orderSn,
            method: 'get'
        })
    },
    /**
     * 取消订单
     * @param orderSn
     */
    remove: function (orderSn) {
        return request({
            url: '/user/order/cancel/' + orderSn,
            method: 'post'
        })
    },
    /**
     * 获取订单列表
     * @param params
     */
    getOrders: function (params) {
        return request({
            url: '/user/order/getOrders',
            method: 'get',
            params
        })
    },
    /**
     * 准备付款
     * @param params
     */
    prepareCheckout: function (params) {
        return request({
            url: '/user/order/prepareCheckout',
            params,
            method: 'get'
        })
    },
    /**
     * 确认收货
     * @param params
     */
    confirm(orderSn){
        return request({
            url: '/user/order/confirm/'+orderSn,
            method: 'post'
        })
    }

}
