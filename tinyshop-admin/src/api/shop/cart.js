import request from '@/utils/request'

export function getList(params) {
    return request({
        url: '/shop/cart/list',
        method: 'get',
        params
    })
}


export function save(params) {
    return request({
        url: '/shop/cart',
        method: 'post',
        params
    })
}

export function remove(id) {
    return request({
        url: '/shop/cart',
        method: 'delete',
        params: {
            id: id
        }
    })
}
