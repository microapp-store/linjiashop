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
    remove: function (id) {
        return request({
            url: '/user/order/cancel/' + id,
            method: 'post'
        })
    },
    getOrders: function (params) {
        return request({
            url: '/user/order/getOrders',
            method: 'get',
            params
        })
    },

    prepareCheckout: function (params) {
        return request({
            url: '/user/order/prepareCheckout',
            params,
            method: 'get'
        })
    }
}
