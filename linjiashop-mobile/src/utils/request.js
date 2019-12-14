import axios from 'axios'
import { router } from '@/router';
import { getToken } from '@/utils/auth'
// create an axios instance
const service = axios.create({
  baseURL: process.env.VUE_APP_BASE_API, // url = base url + request url
  withCredentials: true, // send cookies when cross-domain requests
  timeout: 15000 // request timeout
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
    console.log('response',response)
    const res = response.data
    if (res.code !== 20000) {
      console.log('error',res)
      return Promise.reject(res.msg || 'error')
    } else {
      console.log('res',res)
      return res
    }
  },
  error => {

    if (error.response) {
      switch (error.response.status) {
        case 401:
          router.replace({
            path: '/login',
            query:{redirect:router.currentRoute.path}
          })
          return
          break;
        case 500:
          if(error.response.data.message && error.response.data.message.indexOf('relogin')>-1){
            router.replace({
              path: '/login',
              query:{redirect:router.currentRoute.path}
            })
            return Promise.reject(error.response.data.message)
          }else{
            return Promise.reject(error.response.data.message)
          }
          break;
        default:
          console.log('aaaaaaaaa')
          return Promise.reject(error.response.data.message)
      }
    }
  }
)

export default service
