# 接口设计规范
本项目采用前后端分离的架构方式，即前端采用vuejs开发界面，后端采用spring boot提供api服务。
本节说明开发过程中接口的基本设计规范，开发人员可以根据实际情况做调整，但是如果你打算为本项目提交代码，请尽量遵守下面规范
## 综述
- 前端部分包括手机h5(linjiashop-mobile),微信小程序(linjiashop-wxapp),app([linjiashop-flutter](https://gitee.com/microapp/linjiashop-flutter),[github](https://github.com/microapp-store/linjiashop-flutter))
- 已上前端项目公用一个api服务：linjiashop-mobile-api
# 后端
- 所有接口必须指定method，
    - 例如下面代码必须指定method为DELETE而，不能不指定method
    ```java
    @RequestMapping(value = "{id}",method = RequestMethod.DELETE)
        public Object remove(@PathVariable("id") Long id){
            Long idUser = getIdUser();
            addressService.delete(idUser,id);
            return Rets.success();
        }
    ```
- 所有接口必须通过method明显显示其操作意思，比如获取数据：GET，删除数据：DELETE,编辑数据：POST(本项目不使用PUT)
- GET方法尽量使用@PathVariable注解来获取参数，除非参数过于复杂则采用@RequestParam来接收，相应的前端也使用对应的方式提交参数
- POST方法 ，如果参数过少可以采用@PathVariable或者@Requestaram来接收，如果参数较多则采用@RequestBody来接收参数
- DELETE方法尽量根据id去删除数据，接收参数方式尽量使用@PathVariable来接收
   
# 前端
- 针对上述后端接收参数的方式，前端要使用对应的方式提交参数

