import request from '@/utils/request'

export function getList(params) {
    return request({
        url: '/shop/goods/list',
        method: 'get',
        params
    })
}


export function saveBaseInfo(params) {
  return request({
    url: '/shop/goods/saveBaseInfo',
    method: 'post',
    data : params
  })
}
export function save(params) {
    return request({
        url: '/shop/goods',
        method: 'post',
        data : params
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
export function get(id) {
  return request({
    url: '/shop/goods',
    method: 'get',
    params: {
      id: id
    }
  })
}
export function changeIsOnSale(id,isOnSale){
  return request({
    url:'/shop/goods/changeIsOnSale',
    method:'post',
    params:{
      id:id,
      isOnSale:isOnSale
    }
  })
}

