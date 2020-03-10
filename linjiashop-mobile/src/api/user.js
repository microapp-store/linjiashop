import request from '@/utils/request'

export default {
    getUserInfo: function () {
        return request({
            url: '/user/getInfo',
            method: 'get'
        })
    },
    updateUserName: function (userName) {
        return request({
            url: '/user/updateUserName/' + userName,
            method: 'post'
        })
    },
    updatePwd: function (params) {
        return request({
            url: '/account/updatePwd',
            method: 'post',
            params
        })
    },
    updateGender:function(gender) {
        return request({
            url: '/user/updateGender/' + gender,
            method: 'post'
        })
    },
    updatePassword:function(oldPwd,password,rePassword) {
        return request({
            url: '/user/updatePassword/' + oldPwd+'/'+password+'/'+rePassword,
            method: 'post'
        })
    },
    upload:function(file){
        console.log('file',file)
        return request({
            url: '/file/upload/base64',
            method: 'post',
            data:file
        })
    },
    sendSmsCode:function(mobile) {
        return request({
            url: '/user/sendSmsCode',
            method: 'post',
            params: {
                "mobile": mobile
            }
        })
    },
    getWxSign:function(params){
        return request({
            url: '/user/getWxSign',
            method: 'post',
            params
        })
    },
    getWxOpenId:function(params){
        return request({
            url: '/user/getWxOpenId',
            method: 'post',
            params
        })
    }
}

