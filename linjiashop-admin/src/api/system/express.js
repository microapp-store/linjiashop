import request from '@/utils/request'

export default {
  getList: function (params) {
    return request({
      url: '/sys/express/list',
      method: 'get',
      params
    })
  },


  save: function (params) {
    return request({
      url: '/sys/express',
      method: 'post',
      params
    })
  },

  remove: function (id) {
    return request({
      url: '/sys/express',
      method: 'delete',
      params: {
        id: id
      }
    })
  },
  changeDisabled: function (id, disabled) {
    return request({
      url: '/sys/express/changeDisabled',
      method: 'post',
      params: {
        id: id,
        disabled: disabled
      }
    })
  }
}
