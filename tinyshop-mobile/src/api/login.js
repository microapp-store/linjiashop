import request from '@/utils/request'

export function loginOrReg(mobile, smsCode) {
  return request({
    url: '/loginOrReg',
    method: 'post',
    params: {
      "mobile": mobile,
      "smsCode": smsCode
    }
  })
}
export function loginByPassword(mobile, password) {
  return request({
    url: '/loginByPass',
    method: 'post',
    params: {
      "mobile": mobile,
      "password": password
    }
  })
}
