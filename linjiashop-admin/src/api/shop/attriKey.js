import request from '@/utils/request'

export function getList(params) {
  return request({
    url: '/shop/goods/attr/list',
    method: 'get',
    params
  })
}

export function save(params) {
  return request({
    url: '/shop/goods/attr',
    method: 'post',
    params
  })
}


export function update(params) {
  return request({
    url: '/shop/goods/attr',
    method: 'put',
    params
  })
}

export function remove(id) {
  return request({
    url: '/shop/goods/attr',
    method: 'delete',
    params: {
      id:id
    }
  })
}
