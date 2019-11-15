import request from '@/utils/request'

export default {
  getAllCategories:function(){
    return request({
      url: '/category/list',
      method: 'get'
    })
  }
}
