import request from '@/utils/request'

export default {
    get:function(id){
        return request({
            url:'/user/address/'+id,
            method:'get'
        })
    },
    changeDefault(id,isDefault){
      return request({
          url:'/user/address/'+id+'/'+isDefault,
          method:'post'
      })
    },
    queryByUser: function () {
        return request({
            url: '/user/address/queryByUser',
            method: 'get'
        })
    },
    save: function (params) {
        return request({
            url: '/user/address/save',
            method: 'post',
            data:params
        })
    },

    remove:function(id){
        return request({
            url:'/user/address/'+id,
            method:'delete'
        })
    }
}
