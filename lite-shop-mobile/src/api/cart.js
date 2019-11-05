import request from '@/utils/request'

export  function queryByUser( ) {
  return request({
    url:'/user/cart/queryByUser',
    method:'get'
  })
}
