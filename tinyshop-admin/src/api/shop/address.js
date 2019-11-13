import request from '@/utils/request'

export function getList(params) {
    return request({
        url: '/shop/address/list',
        method: 'get',
        params
    })
}


export function save(params) {
    return request({
        url: '/shop/address',
        method: 'post',
        params
    })
}

export function remove(id) {
    return request({
        url: '/shop/address',
        method: 'delete',
        params: {
            id: id
        }
    })
}
