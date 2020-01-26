import request from '@/utils/request'

export function getList(params) {
    return request({
        url: '/shop/favorite/list',
        method: 'get',
        params
    })
}


export function save(params) {
    return request({
        url: '/shop/favorite',
        method: 'post',
        params
    })
}

export function remove(id) {
    return request({
        url: '/shop/favorite',
        method: 'delete',
        params: {
            id: id
        }
    })
}
