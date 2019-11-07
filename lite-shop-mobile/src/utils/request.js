import axios from 'axios'
import { router } from '@/router';
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
    console.log('error111'.error) // for debug
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
    // console.log('response',response)
    // if the custom code is not 20000, it is judged as an error.
    if (res.code !== 20000) {
      console.log('error',res)
      return Promise.reject(res.message || 'error')
    } else {
      return res
    }
  },
  error => {

    console.log('1error',error.data)
    //todo 未完成
    if (error.response) {
      switch (error.response.status) {
        case 401:
          console.log(401)
          router.replace({
            path: 'login',
            redirect:router.currentRoute.path
          })
          return
          break;
        case 500:
          console.log(500)
          break;
      }
      return Promise.reject(error)
    }
  }
)

export default service
