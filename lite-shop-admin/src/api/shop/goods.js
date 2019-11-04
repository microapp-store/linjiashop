import request from '@/utils/request'

export function getList(params) {
    return request({
        url: '/shop/goods/list',
        method: 'get',
        params
    })
}


export function save(params) {
    return request({
        url: '/shop/goods',
        method: 'post',
        params
    })
}

export function remove(id) {
    return request({
        url: '/shop/goods',
        method: 'delete',
        params: {
            id: id
        }
    })
}
