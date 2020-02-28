import request from '@/utils/request'

export default {
  getList: function (params) {
    return request({
      url: '/shop/goods/attr/key/list',
      method: 'get',
      params
    })
  },
  save: function (params) {
    return request({
      url: '/shop/goods/attr/key',
      method: 'post',
      params
    })
  },
  remove: function (id) {
    return request({
      url: '/shop/goods/attr/key',
      method: 'delete',
      params: {
        id: id
      }
    })
  },
  updateAttrName:function(id,attrName){
    return request({
      url: '/shop/goods/attr/key/updateAttrName',
      method: 'POST',
      params: {
        id: id,
        attrName:attrName
      }
    })
  }
}
