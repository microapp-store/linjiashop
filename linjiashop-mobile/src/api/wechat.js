import request from '@/utils/request'

export default {

    getWxSign:function(params){
        return request({
            url: '/wechat/getWxSign',
            method: 'post',
            params
        })
    },
    getWxOpenId:function(params){
        return request({
            url: '/wechat/getWxOpenId',
            method: 'post',
            params
        })
    }
}

