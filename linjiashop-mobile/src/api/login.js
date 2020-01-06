import request from '@/utils/request'

export default {
  loginOrReg:function(mobile, smsCode) {
    return request({
      url: '/loginOrReg',
      method: 'post',
      params: {
        "mobile": mobile,
        "smsCode": smsCode
      }
    })
  },
  loginByPassword:function(mobile, password) {
    return request({
      url: '/loginByPass',
      method: 'post',
      params: {
        "mobile": mobile,
        "password": password
      }
    })
  },
  sendSmsCode:function(mobile) {
    return request({
      url: '/sendSmsCode',
      method: 'post',
      params: {
        "mobile": mobile
      }
    })
  },
  logout:function(){
    return request({
      url: '/logout',
      method: 'post'
    })
  }

}
