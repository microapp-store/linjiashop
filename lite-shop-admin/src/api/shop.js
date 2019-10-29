import request from '@/utils/request'

export function getList(params) {
    return request({
        url: '/shop/user/list',
        method: 'get',
        params
    })
}


export function save(params) {
    return request({
        url: '/shop/user',
        method: 'post',
        params
    })
}

export function remove(id) {
    return request({
        url: '/shop/user',
        method: 'delete',
        params: {
            id: id
        }
    })
}
