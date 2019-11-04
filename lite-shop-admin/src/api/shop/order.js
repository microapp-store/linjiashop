import request from '@/utils/request'

export function getList(params) {
    return request({
        url: '/shop/order/list',
        method: 'get',
        params
    })
}


export function save(params) {
    return request({
        url: '/shop/order',
        method: 'post',
        params
    })
}

export function remove(id) {
    return request({
        url: '/shop/order',
        method: 'delete',
        params: {
            id: id
        }
    })
}
