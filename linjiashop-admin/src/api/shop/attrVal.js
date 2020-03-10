import request from '@/utils/request'

export default{
  getAttrVals: function (idAttrKey) {
    return request({
      url: '/shop/attr/val/getAttrVals',
      method: 'get',
      params: {
        idAttrKey: idAttrKey
      }
    })
  },
  save: function (params) {
    return request({
      url: '/shop/attr/val',
      method: 'post',
      params
    })
  },
  remove: function (id) {
    return request({
      url: '/shop/attr/val',
      method: 'delete',
      params: {
        id: id
      }
    })
  },
  updateAttrVal:function(id,attrVal){
    return request({
      url: '/shop/attr/val/updateAttrVal',
      method: 'POST',
      params: {
        id: id,
        attrVal:attrVal
      }
    })
  },

  getAttrBy: function(idCategory) {
    return request({
      url: '/shop/attr/val/getAttrByCategoryAndGoods/'+idCategory,
      method: 'get'
    })
  }
}
