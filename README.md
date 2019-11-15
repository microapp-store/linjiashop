 # 前言
[![Spring Boot](https://img.shields.io/badge/spring--boot-2.1.1.1.RELEASE-brightgreen)](https://github.com/spring-projects/spring-boot)
[![vue](https://img.shields.io/badge/vue-2.6.10-brightgreen.svg)](https://github.com/vuejs/vue)
[![element-ui](https://img.shields.io/badge/element--ui-2.11.0-brightgreen.svg)](https://github.com/ElemeFE/element)
[![license](https://img.shields.io/github/license/mashape/apistatus.svg)](https://github.com/enilu/tinyshop/blob/master/LICENSE)
 


- tinyshop是一个基于[Spring Boot](https://spring.io/projects/spring-boot/)和[Vue.js](https://cn.vuejs.org)的web系统，包含了基于[element](https://element.eleme.cn/#/zh-CN)搭建的但商户的商城系统。
- tinyshop具备后台管理类系统的通用的基础功能和商城业务功能，而且提供了基于idea intellij的的代码生成插件，可以一键生成前后端页面。
- tinyshop提供了手机端的商城系统
 

## 功能模块
tinyshop包含了后台管理功能和手机端商城业务功能
- 基础模块
    - 部门管理
    - 用户管理
    - 角色管理
    - 菜单管理
    - 权限分配
    - 参数管理
    - 数据字典管理
    - 定时任务管理
    - 操作日志
    - 登录日志
    - cms内容管理
    - 消息管理：配置消息模板，发送短信，邮件消息
    - 基于idea插件的代码生成
- 商城功能
    - 店铺管理
    - 类别管理
    - 商品管理    
    - 订单管理
    - 购物车列表
- 手机端
    -完整的商城购物功能        

## 技术选型
- 核心框架：Spring Boot
- 数据库层：Spring data jpa
- 数据库连接池：Druid
- 缓存：Ehcache
- 前端：后台管理基于[element](http://element-cn.eleme.io)，手机端界面基于[vant](https://youzan.github.io/vant/#/zh-CN/intro)


## 目录说明
- tinyshop-admin PC端后台管理的前端网页
- tinyshop-admin-api PC端后台管理的api服务
- tinyshop-mobile 手机商城的前端网页
- tinyshop-mobile-api 手机端商城的api服务
- tinyshop-core 基础模块，包括工具类，dao，service，bean等内容
- tinyshop-generator 代码生成模块,配合IDEA 代码生成插件[webflash-generator](https://plugins.jetbrains.com/plugin/12648-webflash-generator)使用效果更好

## 演示
 暂无



## 快速开始
- 克隆本项目
- 导入idea或者eclipse
- 创建数据库：tinyshop
     ```sql
    CREATE DATABASE IF NOT EXISTS tinyshop DEFAULT CHARSET utf8 COLLATE utf8_general_ci; 
    CREATE USER 'tinyshop'@'%' IDENTIFIED BY 'tinyshop191028';
    GRANT ALL privileges ON tinyshop.* TO 'tinyshop'@'%';
    flush privileges;
    ``` 
- 导入测试语句:tinyshop-admin-api/src/main/resources/tinyshop.sql    
- 确保开发工具下载了lombok插件
- 启动后台管理
    -启动后台管理api服务:tinyshop-admin-api
        - 修改tinyshop-admin-api中数据库连接配置
        - 启动tinyshop-admin-api主类：cn.enilu.flash.api.AdminApiApplication，访问http://localhost:8082/swagger-ui.html ， 保证api服务启动成功
    - 启动后台管理的前端界面:tinyshop-admin
        - 运行 npm install --registry=https://registry.npm.taobao.org
        - 运行npm run dev
        - 启动成功后访问 http://localhost:9528 ,登录，用户名密码:admin/admin 
- 启动手机端商城
    - 启动手商城的api服务:tinyshop-mobile-api   
        - 修改tinyshop-mobile-api中数据库连接欸配置
        - 启动tinyshop-mobile-api主类：cn.enilu.flash.MobileApiApplication,访问http://localhost:8081/swagger-ui.html ， 保证api服务启动成功
    - 启动手机商城的前端:tinyshop-mobile
        - 运行 npm install --registry=https://registry.npm.taobao.org
        - 运行npm run dev
        - 启动成功后访问 http://localhost:8080/#/index
 

## 在线文档
- 该项目克隆并扩展自[web-flash](https://github.com/enilu/web-flash),所以开发的时候多看看web-flash的[在线文档](http://enilu.gitee.io/web-flash) 
- 该项目的业务功能参考[litemall](https://github.com/linlinjava/litemall)，感谢[linlinjava](https://github.com/linlinjava)
 
