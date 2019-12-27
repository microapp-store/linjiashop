import store from './store'
import utils from '@/utils/index.js'

// const host = 'http://linjiashop-mobile-api.microapp.store/'
const host = 'http://localhost:8081/'
export default ($wx) => {
  let reqHeader = {
    'content-type': 'application/json'
  }
  let handler = {
    get(target, property) {
      const token = store.state.token
      if (token) {
        reqHeader['Authorization'] = token
      }
      target[property] = (url, params = {}) => {
        return new Promise((resolve, reject) => {
          if (!token) {
            if (utils.startWith(url, '/user') || utils.startWith(url, 'user')) {
              const url = '../profile/loginOption/index'
              $wx.navigateTo({url})
              return
            }
          }
          $wx.request({
            header: reqHeader,
            url: host + url,
            method: property.toLocaleUpperCase(),
            data: params,
            success: res => {
              console.log(url, res.data)
              if (Number(res.statusCode) !== 200) {
                $wx.showToast({title: '通讯错误，稍后再试', icon: 'none'})
                return false
              }
              if (res.data.code !== 20000) {
                $wx.showToast({title: res.data.msg, icon: 'none'})
                return false
              }
              resolve(res.data)
            },
            fail: error => {
              reject(error)
            }
          })
        })
      }
      return target[property]
    }
  }

  const API = new Proxy({}, handler)
  return API
}
