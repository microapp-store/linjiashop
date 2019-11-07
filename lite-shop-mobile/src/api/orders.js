import request from '@/utils/request'

export default {
     getOrders:function(params) {
        return request({
            url: '/user/order/getOrders',
            method: 'get',
            params
        })
    },

    prepareCheckout:function(){
        return request({
            url: '/user/order/prepareCheckout',
            method: 'get'
        })
    }
}
