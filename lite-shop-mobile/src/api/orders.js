import request from '@/utils/request'

export function getOrders(params) {
    return request({
        url: '/user/order/getOrders',
        method: 'get',
        params
    })
}
