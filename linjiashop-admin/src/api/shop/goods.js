import request from '@/utils/request'

export default {
  getList: function (params) {
    return request({
      url: '/shop/goods/list',
      method: 'get',
      params
    })
  },


  saveBaseInfo: function (params) {
    return request({
      url: '/shop/goods/saveBaseInfo',
      method: 'post',
      data: params
    })
  },
  save: function (params) {
    return request({
      url: '/shop/goods',
      method: 'post',
      data: params
    })
  },
  remove: function (id) {
    return request({
      url: '/shop/goods',
      method: 'delete',
      params: {
        id: id
      }
    })
  },
  get: function (id) {
    return request({
      url: '/shop/goods',
      method: 'get',
      params: {
        id: id
      }
    })
  },
  changeIsOnSale: function (id, isOnSale) {
    return request({
      url: '/shop/goods/changeIsOnSale',
      method: 'post',
      params: {
        id: id,
        isOnSale: isOnSale
      }
    })
  }
}
