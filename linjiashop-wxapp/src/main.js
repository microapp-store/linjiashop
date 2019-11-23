import Vue from 'vue'
import App from './App'
import API from './utils/api.js'
Vue.prototype.$API = API(wx)

const app = new Vue(App)
app.$mount()
