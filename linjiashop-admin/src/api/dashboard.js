import request from '@/utils/request'
export  default{
  get:function(){
    return request({
      url: '/dashboard',
      method: 'get',
    })
  }
}
