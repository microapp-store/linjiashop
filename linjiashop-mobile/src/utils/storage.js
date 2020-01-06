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
  removeToken:function(){
    localStorage.removeItem(tokenKey)
  },
  set:function(key,value){
    localStorage.setItem(key,value)
  },
  get:function(key){
    return localStorage.getItem(key)
  }

}
// export function getToken() {
//   // return Cookies.get(tokenKey)
//   return localStorage.getItem(tokenKey)
// }
//
// export function setToken(token) {
//   // return Cookies.set(tokenKey, token)
//   localStorage.setItem(tokenKey,token)
// }
//
// export function removeToken() {
//   return Cookies.remove(tokenKey)
//   localStorage.removeItem(tokenKey)
// }
// export function set(key,value){
//   // return Cookies.set(key,value)
//   localStorage.setItem(key,value)
// }
// export  function get(key){
//   // return Cookies.get(key)
//   return localStorage.getItem(key)
// }
