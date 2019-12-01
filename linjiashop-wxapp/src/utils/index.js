function formatNumber(n) {
  const str = n.toString()
  return str[1] ? str : `0${str}`
}

export function formatTime(date) {
  const year = date.getFullYear()
  const month = date.getMonth() + 1
  const day = date.getDate()

  const hour = date.getHours()
  const minute = date.getMinutes()
  const second = date.getSeconds()

  const t1 = [year, month, day].map(formatNumber).join('/')
  const t2 = [hour, minute, second].map(formatNumber).join(':')

  return `${t1} ${t2}`
}

export function formatPrice(price) {
  return (price / 100).toFixed(2)
}

export function startWith(src, str) {
  const reg = new RegExp('^' + str)
  return reg.test(src)
}

export function endWith(src, str) {
  const reg = new RegExp(str + '$')
  return reg.test(src)
}

export const host = 'http://linjiashop-mobile-api.microapp.store/'
export const fileMgrUrl = host + 'file/getImgStream?idFile='

export default {
  formatNumber,
  formatTime,
  formatPrice,
  startWith,
  endWith,
  host,
  fileMgrUrl
}
