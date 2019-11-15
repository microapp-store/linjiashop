import request from '@/utils/request'

export function getList(params) {
    return request({
        url: '/shop/category/list',
        method: 'get',
        params
    })
}

export function getCategories() {
  return request({
    url: '/shop/category/getAll',
    method: 'get'
  })
}

export function getBanners(id) {
  return request({
    url: '/shop/category/getBanners/'+id,
    method: 'get'
  })
}
export function save(params) {
    return request({
        url: '/shop/category',
        method: 'post',
        params
    })
}

export function remove(id) {
    return request({
        url: '/shop/category',
        method: 'delete',
        params: {
            id: id
        }
    })
}
