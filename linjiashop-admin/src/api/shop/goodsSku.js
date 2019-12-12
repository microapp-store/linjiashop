import request from '@/utils/request'


export default {
  save:function(params){
    return request({
      url: '/shop/goods/sku',
      method: 'post',
      data : params
    })
  },
  remove:function(id){
    return request({
      url: '/shop/goods/sku',
      method: 'delete',
      params: {
        id: id
      }
    })
  }
}
