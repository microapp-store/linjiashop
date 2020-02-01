import request from '@/utils/request'

export default {
  getList: function (params) {
    return request({
      url: '/promotion/topic/list',
      method: 'get',
      params
    })
  },
  save: function (params) {
    return request({
      url: '/promotion/topic',
      method: 'post',
      params
    })
  },
  remove: function (id) {
    return request({
      url: '/promotion/topic',
      method: 'delete',
      params: {
        id: id
      }
    })
  },
  changeDisabled:function(id,disabled){
    return request({
      url:'/promotion/topic/changeDisabled',
      method:'post',
      params:{
        id:id,
        disabled:disabled
      }
    })
  }

}
