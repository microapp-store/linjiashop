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
    * 获取热门商品
    * @param params
    */
   searchHot:function(params) {
     return request({
       url: '/goods/searchHot',
       method: 'get',
       params
     })
   },
   getGoods:function(id) {
     return request({
       url:'/goods/'+id,
       method:'get'
     })
   }

 }
