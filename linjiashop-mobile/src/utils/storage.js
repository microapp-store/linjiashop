// import Cookies from 'js-cookie'

const COOKIE_KEY_PRE="lite-shop-mobile-"
const tokenKey = COOKIE_KEY_PRE+"token"
export default {
  getToken:function(){
    return localStorage.getItem(tokenKey)
  },
  setToken:function(token){
    localStorage.setItem(tokenKey,token)
  },
  setUser:function(user){
    this.set('user',JSON.stringify(user))

  },
  getUser:function(){
    const user  = JSON.parse(this.get('user'))
    return user
  },
  removeToken:function(){
    localStorage.removeItem(tokenKey)
  },
  set:function(key,value){
    localStorage.setItem(COOKIE_KEY_PRE+key,value)
  },
  get:function(key){
    return localStorage.getItem(COOKIE_KEY_PRE+key)
  }

}
