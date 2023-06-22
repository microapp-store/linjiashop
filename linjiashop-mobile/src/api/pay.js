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
        const params = {userName:userName}
        return request({
            url: '/user/updateUserName/' ,
            method: 'post',
            params
        })
    },
    queryResult:function(orderSn){
        return request({
            url: '/pay/queryResult/' + orderSn,
            method: 'get'
        })
    }
}

