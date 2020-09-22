# 基本包结构

本节详细说明本项目的基本目录结构

## 邻家小铺项目模块

- linjiashop-admin PC端后台管理的前端网页
- linjiashop-admin-api PC端后台管理的api服务
- linjiashop-mobile 手机商城的前端网页
- linjiashop-mobile-api 移动端商城的api服务（h5,小程序，app都用改api服务作为后台接口）
- linjiasho-wxapp 微信小程序商城
- linjiashop-core 基础模块，包括工具类，dao，service，bean等内容
- linjiashop-generator 代码生成模块,根据实体生成dao,service,后台管理的controller和页面,配合IDEA 代码生成插件[webflash-generator](https://plugins.jetbrains.com/plugin/12648-webflash-generator)使用效果更好，使用前请仔细阅读该[文档](http://enilu.gitee.io/web-flash/ecosystem/code-generator.html)
- linjiashop-flutter 商城app版本；项目地址：(https://gitee.com/microapp/linjiashop-flutter)[https://gitee.com/microapp/linjiashop-flutter]
