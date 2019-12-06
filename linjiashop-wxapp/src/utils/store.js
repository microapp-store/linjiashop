// https://vuex.vuejs.org/zh-cn/intro.html
// make sure to call Vue.use(Vuex) if using a module system
import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

const store = new Vuex.Store({
  state: {
    count: 0,
    token: undefined,
    user: {nickName: '立即登录'},
    addr: {}
  },
  mutations: {
    increment: (state) => {
      const obj = state
      obj.count += 1
    },
    decrement: (state) => {
      const obj = state
      obj.count -= 1
    },
    setToken: (state, token) => {
      console.log('token', token)
      const obj = state
      obj.token = token
    },
    setUser: (state, user) => {
      console.log('user', user)
      const obj = state
      obj.user = user
    },
    setAddr: (state, addr) => {
      const obj = state
      obj.addr = addr
    }

  }
})

export default store
