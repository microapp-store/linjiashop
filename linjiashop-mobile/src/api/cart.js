import request from '@/utils/request'

export default {
    queryByUser: function () {
        return request({
            url: '/user/cart/queryByUser',
            method: 'get'
        })
    },
    add: function (idGoods, count) {
        return request({
            url: '/user/cart/add',
            method: 'POST',
            params: {
                "idGoods": idGoods,
                "count": count
            }
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
