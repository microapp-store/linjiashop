import request from '@/utils/request'

export function getList(params) {
    return request({
        url: '/promotion/topic/list',
        method: 'get',
        params
    })
}


export function save(params) {
    return request({
        url: '/promotion/topic',
        method: 'post',
        params
    })
}

export function remove(id) {
    return request({
        url: '/promotion/topic',
        method: 'delete',
        params: {
            id: id
        }
    })
}
