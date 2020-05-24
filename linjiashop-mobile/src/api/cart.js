import request from '@/utils/request'

export default {
    count: function () {
        return request({
            url: '/user/cart/count',
            method: 'get'
        })
    },
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
    update:function(id,count){
        return request({
            url: '/user/cart/update/'+id+'/'+count,
            method: 'POST',
        })
    },
    remove:function(params){
        return request({
            url: '/user/cart',
            method: 'DELETE',
            data:params
        })
    }
}
