import request from '@/utils/request'


 export default{

    queryGoods:function(params) {
     return request({
       url: '/goods/queryGoods',
       method: 'get',
       params
     })
   },

   /**
    * 根据关键字查询商品
    * @param params
    */
   search:function(params) {
     return request({
       url: '/goods/search',
       method: 'get',
       params
     })
   },

   /**
    * 获取热门推荐
    * @param params
    */
   searchHot:function() {
     return request({
       url: '/goods/searchHot',
       method: 'get'
     })
   },
   /**
    * 获取新品推荐
    */
   searchNew:function() {
     return request({
       url: '/goods/searchNew',
       method: 'get'
     })
   },
   getGoods:function(id) {
     return request({
       url:'/goods/'+id,
       method:'get'
     })
   }

 }
