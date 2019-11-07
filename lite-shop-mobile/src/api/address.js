import request from '@/utils/request'

export default {
    queryByUser: function () {
        return request({
            url: '/user/address/queryByUser',
            method: 'get'
        })
    },
    add: function (params) {
        return request({
            url: '/user/address/add',
            method: 'POST',
            params
        })
    },
    update:function(params){
        return request({
            url: '/user/address/update',
            method: 'POST',
            params
        })
    }
}
