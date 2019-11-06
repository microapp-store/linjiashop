import request from '@/utils/request'

export function getUserInfo() {
    return request({
        url: '/user/getInfo',
        method: 'get'
    })
}


export function updatePwd(params) {
    return request({
        url: '/account/updatePwd',
        method: 'post',
        params
    })
}
