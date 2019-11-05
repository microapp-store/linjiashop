import request from '@/utils/request'



export function queryGoods(params) {
  return request({
    url: '/goods/queryGoods',
    method: 'get',
    params
  })
}
export  function getGoods(id) {
  return request({
    url:'/goods/'+id,
    method:'get'
  })
}
