import request from '@/utils/request'

export default {
    wxPrepare: function (params) {
        return request({
            url: '/pay/wx/prepare',
            method: 'post',
            params
        })
    },
    wxPay: function (userName) {
        return request({
            url: '/user/updateUserName/' + userName,
            method: 'post'
        })
    },
    queryResult:function(orderSn){
        return request({
            url: '/pay/queryResult/' + orderSn,
            method: 'get'
        })
    }
}

