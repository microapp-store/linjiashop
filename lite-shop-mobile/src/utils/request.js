import axios from 'axios'
// import router from '@/router'
import router from '../router'
import { getToken } from '@/utils/auth'

// create an axios instance
const service = axios.create({
  baseURL: process.env.VUE_APP_BASE_API, // url = base url + request url
  withCredentials: true, // send cookies when cross-domain requests
  timeout: 5000 // request timeout
})

// request interceptor
service.interceptors.request.use(
  config => {

    if (getToken()) {
      // 让每个请求携带自定义token 请根据实际情况自行修改
      config.headers['Authorization'] = getToken()
    }
    return config
  },
  error => {
    // do something with request error
    console.log(error) // for debug
    return Promise.reject(error)
  }
)

// response interceptor
service.interceptors.response.use(
  /**
   * If you want to get http information such as headers or status
   * Please return  response => response
  */

  /**
   * Determine the request status by custom code
   * Here is just an example
   * You can also judge the status by HTTP Status Code
   */
  response => {
    const res = response.data
    console.log('response',response)
    // if the custom code is not 20000, it is judged as an error.
    if (res.code !== 20000) {
      Message({
        message: res.message || 'error',
        type: 'error',
        duration: 5 * 1000
      })
      return Promise.reject(res.message || 'error')
    } else {
      return res
    }
  },
  error => {
    let me = this
    //todo 未完成
    console.log('失败，准备登录' ,error) // for debug
    if (error.response) {
      console.log('error',error.response)
      switch (error.response.status) {
        case 401:
           router.replace({
            path: 'login'
          })
          break;
        case 500:
          console.log('准备跳转到登录',500)

            router.replace('login')
          // router.replace({
          //   path: 'login'
          // })
          break;
      }
    }
    //todo 未完成
    // Message({
    //   message: error.message,
    //   type: 'error',
    //   duration: 5 * 1000
    // })
    return Promise.reject(error)
  }
)

export default service
