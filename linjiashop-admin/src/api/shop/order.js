import request from '@/utils/request'

export function getList(params) {
    return request({
        url: '/shop/order/list',
        method: 'get',
        params
    })
}


export function sendOut(id) {
    return request({
        url: '/shop/order/sendOut/'+id,
        method: 'post'
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

export function getOrder(orderSn) {
  return request({
    url: '/shop/order/'+orderSn,
    method: 'get'
  })
}
