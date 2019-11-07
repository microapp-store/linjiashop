 # 前言
[![Spring Boot](https://img.shields.io/badge/spring--boot-2.1.1.1.RELEASE-brightgreen)](https://github.com/spring-projects/spring-boot)
[![vue](https://img.shields.io/badge/vue-2.6.10-brightgreen.svg)](https://github.com/vuejs/vue)
[![element-ui](https://img.shields.io/badge/element--ui-2.11.0-brightgreen.svg)](https://github.com/ElemeFE/element)
[![license](https://img.shields.io/github/license/mashape/apistatus.svg)](https://github.com/enilu/lite-shop/blob/master/LICENSE)
 


- lite-shop是一个基于[Spring Boot](https://spring.io/projects/spring-boot/)和[Vue.js](https://cn.vuejs.org)的web系统，包含了基于[element](https://element.eleme.cn/#/zh-CN)搭建的但商户的商城系统。
- lite-shop具备后台管理类系统的通用的基础功能和商城业务功能，而且提供了基于idea intellij的的代码生成插件，可以一键生成前后端页面。
- lite-shop提供了手机端的商城系统
 

## 目录说明

- lite-shop-api PC端后台管理的api服务
- lite-shop-admin PC端后台管理的前端网站
- lite-h5-api 手机端商城api服务
- lite-shop-mobile 手机商城的前端
- lite-shop-core 基础模块，包括工具类，dao，service，bean等内容
- lite-shop-generator 代码生成模块,配合IDEA 代码生成插件[webflash-generator](https://plugins.jetbrains.com/plugin/12648-webflash-generator)使用效果更好

## 演示
 暂无

## 技术选型
- 核心框架：Spring Boot
- 数据库层：Spring data jpa
- 数据库连接池：Druid
- 缓存：Ehcache
- 前端：后台管理基于[element](http://element-cn.eleme.io)，手机端界面基于[vant](https://youzan.github.io/vant/#/zh-CN/intro)


## 包含的功能
lite-shop包含了后台管理功能和手机端商城业务功能
- 基础模块
    - 部门管理
    - 用户管理
    - 角色管理
    - 菜单管理：配置菜单功能
    - 权限分配：为指定的角色配置特定的功能菜单
    - 参数管理：维护系统参数，并缓存系统参数提供高效的读取
    - 数据字典管理：配置维护数据字典
    - 定时任务管理：编写、配置、执行定时任务
    - 业务日志：通过注解的方式记录用户操作日志，并提供日志查询功能
    - 登录日志：查看用户登录登出日志
    - cms内容管理，配合flash-vue-h5提供了手机端内容展示系统
    - 消息管理：配置消息模板，发送短信，邮件消息
    - 基于idea插件的代码生成
- 商城功能
    - 店铺管理
    - 商品管理
    - 类别管理
    - 订单管理
- 手机端
    -完整的商城购物功能        


## 使用
- 克隆本项目
- 导入idea或者eclipse
- 创建数据库：liteshop
     ```sql
    CREATE DATABASE IF NOT EXISTS liteshop DEFAULT CHARSET utf8 COLLATE utf8_general_ci; 
    CREATE USER 'liteshop'@'%' IDENTIFIED BY 'liteshop191028';
    GRANT ALL privileges ON liteshop.* TO 'liteshop'@'%';
    flush privileges;
    ``` 
- 导入测试语句:lite-shop-api/src/main/resources/liteshop.sql    
- 确保开发工具下载了lombok插件
- 启动后台管理
    -启动后台管理api服务:lite-shop-api
        - 修改lite-shop-api中数据库连接配置
        - 启动lite-shop-api主类：cn.enilu.flash.api.ApiApplication，访问http://localhost:8082/swagger-ui.html ， 保证api服务启动成功
    - 启动后台管理的前端界面:lite-shop-admin
        - 运行 npm install --registry=https://registry.npm.taobao.org
        - 运行npm run dev
        - 启动成功后访问 http://localhost:9528 ,登录，用户名密码:admin/admin 
- 启动手机端商城
    - 启动手商城的api服务:lite-h5-api   
        - 修改lite-h5-api中数据库连接欸配置
        - 启动lite-h5-api主类：cn.enilu.flash.H5ApiApplication,访问http://localhost:8081/swagger-ui.html ， 保证api服务启动成功
    - 启动手机商城的前端:lite-shop-mobile
        - 运行 npm install --registry=https://registry.npm.taobao.org
        - 运行npm run dev
        - 启动成功后访问 http://localhost:8080/#/index
 

## 在线文档
- 该项目克隆并该自[web-flash](https://github.com/enilu/web-flash),所以开发的时候多看看web-flash的[在线文档](http://enilu.gitee.io/web-flash) 
- 该项目的业务功能参考[litemall](https://gitee.com/linlinjava/litemall)
 
