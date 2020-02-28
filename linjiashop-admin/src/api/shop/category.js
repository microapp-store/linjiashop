import request from '@/utils/request'

export default {

  getList: function (params) {
    return request({
      url: '/shop/category/list',
      method: 'get',
      params
    })
  },

  getCategories: function () {
    return request({
      url: '/shop/category/getAll',
      method: 'get'
    })
  },

  getBanners: function (id) {
    return request({
      url: '/shop/category/getBanners/' + id,
      method: 'get'
    })
  },
  getAttrKeys: function (id) {
    return request({
      url: '/shop/category/getAttrKeys/' + id,
      method: 'get'
    })
  },
  save: function (params) {
    return request({
      url: '/shop/category',
      method: 'post',
      params
    })
  },
  remove: function (id) {
    return request({
      url: '/shop/category',
      method: 'delete',
      params: {
        id: id
      }
    })
  },
  removeBanner: function (id, idBanner) {
    return request({
      url: '/shop/category/removeBanner/' + id + '/' + idBanner,
      method: 'delete'
    })
  },
  setCategoryBanner: function (id, idBanner) {
    return request({
      url: '/shop/category/setBanner/' + id + '/' + idBanner,
      method: 'post'
    })
  }
}
