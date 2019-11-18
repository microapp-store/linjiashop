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



export function removeBanner(id,idBanner) {
  return request({
    url: '/shop/category/removeBanner/'+id+'/'+idBanner,
    method: 'delete'
  })
}

export function setCategoryBanner(id,idBanner) {
  return request({
    url: '/shop/category/setBanner/'+id+'/'+idBanner,
    method: 'post'
  })
}
