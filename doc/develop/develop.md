# 开发指南

## 系统架构图

![系统架构图](../img/architecture.jpg)

## 项目模块
- linjiashop-admin PC端后台管理的前端网页
- linjiashop-admin-api PC端后台管理的api服务
- linjiashop-mobile 手机商城的前端网页
- linjiashop-mobile-api 移动端商城的api服务（h5,小程序，app都用改api服务作为后台接口）
- linjiasho-wxapp 微信小程序商城
- linjiashop-core 基础模块，包括工具类，dao，service，bean等内容
- linjiashop-generator 代码生成模块,根据实体生成dao,service,后台管理的controller和页面,配合IDEA 代码生成插件[webflash-generator](https://plugins.jetbrains.com/plugin/12648-webflash-generator)使用效果更好


## 后台管理
- 后台管理功能由linjiashop-admin(前端界面)和linjiashop-admin-api(接口服务)配合提供
项目克隆自[web-flash](http://enilu.gitee.io/web-flash),所以后台管理的基础通用模块(非业务模块)请参考web-flash的在线文档，
包括国际化，缓存的使用，定时任务管理，消息管理(短信，邮件模板配置，消息发送),导出excel，权限，日志，字典等等
- linjiashop-admin使用的是[vue-element-admin](https://panjiachen.gitee.io/vue-element-admin-site/zh/),开发过程参考改文档会有事半功倍的效果

## H5商城
- linjiashop-mobile-api 是用户端的api服务，H5商城，小程序，app都使用改服务作为后台数据接口
- linjiashop-mobile 使用了有赞vant作为ui组件，开发过程请参考有赞vant的[官方文档](https://youzan.github.io/vant/#/zh-CN/intro)

## 小程序商城
- linjiashop-wxapp 使用mpvne作为基础框架，使用vant-weapp作为ui框架，开发过程请参考这两个开源组件的官方文档和issue，这两个项目都是很成熟的开源项目，基本上开发过程遇到的问题翻一翻官方文档和issue都能解决。[mpvue](http://mpvue.com),[vant-weapp](https://youzan.github.io/vant-weapp/#/)

## app商城
- app商城由于其特殊性并没有和本项目其他功能放在一个项目目录下，而是放在单独一个仓库，仓库地址：仓库地址[gitee](https://gitee.com/microapp/linjiashop-flutter),[github](https://github.com/microapp-store/linjiashop-flutter)
- app 商城采用flutter开发


## 开发约定
- 本项目主要技术栈为Spring Boot2,Spring Data Jpa,Vue.js，Flutter。所以开发过程中请首选遵循已上技术官方的一些约定
- 本项目开发过程使用了Lombok插件来生成bean的set，get方法。所以请务必为开发工具安装lombok插件，以避免项目中实体由于没有set，get方法无法编译的问题。
- 本项目封装了service和repository的常用操作方法到基类；新建service和repository应继承这两个父类：BaseService,和BaseRepository
- 本项目开发运行设计相关软件版本如下：
    - Tomcat8及其已上
    - MySQL5.6及其已上
    - Jdk1.8及其已上




