import request from '@/utils/request'


 export default{
    queryAll:function() {
     return request({
       url: '/topic/list',
       method: 'get'
     })
   },
   get:function(id) {
     return request({
       url: '/topic/'+id,
       method: 'get'
     })
   }
 }
