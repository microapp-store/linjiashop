import request from '@/utils/request'

export function login(mobile, password) {
  return request({
    url: '/user/loginOrReg',
    method: 'post',
    params: {
      "mobile": mobile,
      "smsCode": smsCode
    }
  })
}


export function logout() {
  console.log('logout')
  return request({
    url: '/account/logout',
    method: 'post'
  })
}

export function updatePwd(params) {
  return request({
    url: '/account/updatePwd',
    method: 'post',
    params
  })
}
