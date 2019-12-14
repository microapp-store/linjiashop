import request from '@/utils/request'

export default {
    queryByUser: function () {
        return request({
            url: '/user/cart/queryByUser',
            method: 'get'
        })
    },
    add: function (params) {
        return request({
            url: '/user/cart/add',
            method: 'POST',
            data:params
        })
    },
    update:function(params){
        return request({
            url: '/user/cart/update',
            method: 'POST',
            params
        })
    }
}
