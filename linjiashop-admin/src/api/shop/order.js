import request from '@/utils/request'

export  default {
  getList: function (params) {
    return request({
      url: '/shop/order/list',
      method: 'get',
      params
    })
  },
  getOrderStatistic:function(){
    return request({
      url: '/shop/order/getOrderStatistic',
      method: 'get'
    })
  },
  exportXls: function (params) {
    return request({
      url: '/shop/order/export',
      method: 'get',
      params
    })
  },


  sendOut: function (id,idExpress,shippingSn) {
    return request({
      url: '/shop/order/sendOut/' + id,
      method: 'post',
      params:{
        idExpress:idExpress,
        shippingSn:shippingSn
      }
    })
  },

  remove: function (id) {
    return request({
      url: '/shop/order',
      method: 'delete',
      params: {
        id: id
      }
    })
  },
  getOrder: function (orderSn) {
    return request({
      url: '/shop/order/' + orderSn,
      method: 'get'
    })
  },
  getLogs:function(idOrder){
    return request({
      url: '/shop/order/log/queryByIdOrder/' + idOrder,
      method: 'get'
    })
  },
  addComment:function(idOrder,message){
    return request({
      url: '/shop/order/comment/' + idOrder,
      method: 'post',
      params: {
        message: message
      }
    })
  },
  getShippingInfo:function(shippingSn,shipperCode){
    return request({
      url:'/shop/order/getShippingInfo/'+shippingSn+'/'+shipperCode,
        method:'get'
    })
  }
}
