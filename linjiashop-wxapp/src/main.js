import Vue from 'vue'
import App from './App'
import API from './utils/api.js'
Vue.prototype.$API = API(wx)

Vue.config.errorHandler = function (err) {
  if (console && console.error) {
    console.error(err)
  }
}
const app = new Vue(App)
app.$mount()
