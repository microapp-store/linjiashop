---
pageClass: getting-started
---

 # 前言
[![Spring Boot](https://img.shields.io/badge/spring--boot-2.1.1.1.RELEASE-brightgreen)](https://github.com/spring-projects/spring-boot)
[![vue](https://img.shields.io/badge/vue-2.6.10-brightgreen.svg)](https://github.com/vuejs/vue)
[![element-ui](https://img.shields.io/badge/element--ui-2.11.0-brightgreen.svg)](https://github.com/ElemeFE/element)
[![Vant](https://img.shields.io/badge/vant-2.2.0-brightgreen.svg)](https://youzan.github.io/vant/#/zh-CN/intro)
[![Vant-weapp](https://img.shields.io/badge/vant--weapp-1.0.1-brightgreen)](https://youzan.github.io/vant-weapp/#/intro)
[![Flutter](https://img.shields.io/badge/Flutter-1.12.13-brightgreen)](https://flutter.dev/)
[![license](https://img.shields.io/github/license/mashape/apistatus.svg)](https://github.com/enilu/linjiashop/blob/master/LICENSE)
[![Gitee stars](https://gitee.com/microapp/linjiashop/badge/star.svg?theme=social)](https://gitee.com/microapp/linjiashop)
[![GitHub stars](https://img.shields.io/github/stars/microapp-store/linjiashop.svg?style=social&label=Stars)](https://github.com/microapp-store/linjiashop)
 

- linjiashop 是一个基于[Spring Boot](https://spring.io/projects/spring-boot/)和[Vue.js](https://cn.vuejs.org)的web商城系统
- linjiashop 包含了商城的后台管理系统,手机h5，小程序版本
- linjiashop 包含了app版本（基于flutter) 仓库地址[gitee](https://gitee.com/microapp/linjiashop-flutter),[github](https://github.com/microapp-store/linjiashop-flutter)
- linjiashop 采用[web-flash](http://enilu.gitee.io/web-flash/)作为底层基础框架搭建，开发过程遇到问题请多阅读该项目文档，本文档重点对业务功能做介绍。 

## 演示
- 手机版本：[http://linjiashop.microapp.store](http://linjiashop.microapp.store) 
- 后台管理：[http://linjiashop-admin.microapp.store](http://linjiashop-admin.microapp.store)
- 公众号演示：关注公众号“外滩一号店"进行体验，或者扫描下面二维码关注

    ![外滩一号店](https://gitee.com/microapp/linjiashop/raw/master/doc/img/online/wt1hd.jpg)

- [开发教程视频](../other/video.html)
## 功能模块
linjiashop包含了后台管理功能和手机端商城业务功能
- 基础模块
    - 部门管理
    - 用户管理
    - 角色管理
    - 菜单管理
    - 权限分配
    - 参数管理
    - 数据字典管理
    - 定时任务管理
    - 操作日志shi
    - 登录日志
    - cms内容管理
    - 消息管理：配置消息模板，发送短信，邮件消息
    - 基于idea插件的代码生成
- 商城功能
    - 会员管理
    - 商品类别
    - 商品管理
    - 订单管理
    - 购物车
    - banner管理
    - 收藏列表
- 手机端
    -完整的商城购物功能        

## 运行效果图
### 手机端
![手机端](../img/mobile.gif)

### IOS
<table>
<tr>
<td>
<img src="../img/app/home.png" width="300"/></td>
<td>
<img src="../img/app/find.png" width="300"/></td>
</tr>
<tr>
<td>
<img src="../img/app/guige.jpg" width="300"/></td>
<td>
<img src="../img/app/login.png" width="300"/></td>
</tr>
<tr>
<td>
<img src="../img/app/mine.png" width="300"/></td>
<td>
<img src="../img/app/cart.png" width="300"/></td>
</tr>
</table>

### Android
![效果](../img/app/mobile.gif)
### 后台管理
![后台管理](../img/admin.gif)

## 技术选型
- 核心框架：Spring Boot
- 数据库层：Spring data jpa
- 数据库连接池：Druid
- 缓存：Ehcache
- 前端：后台管理基于[element](http://element-cn.eleme.io)，手机端界面基于[vant](https://youzan.github.io/vant/#/zh-CN/intro)


## 目录说明
- linjiashop-admin PC端后台管理的前端网页
- linjiashop-admin-api PC端后台管理的api服务
- linjiashop-mobile 手机商城的前端网页
- linjiashop-mobile-api 手机端商城的api服务
- linjiashop-core 基础模块，包括工具类，dao，service，bean等内容
- linjiashop-generator 代码生成模块,主要生成后台管理的前后端代码,配合IDEA 代码生成插件[webflash-generator](https://plugins.jetbrains.com/plugin/12648-webflash-generator)使用效果更好

## 快速开始
- 克隆本项目
- 导入idea或者eclipse
- 确保开发工具下载并安装了lombok插件，另外由于lombok有版本差异，如果出现问题，可以更新maven以来中lombok的版本来解决
- 创建数据库：linjiashop
     ```sql
    CREATE DATABASE IF NOT EXISTS linjiashop DEFAULT CHARSET utf8 COLLATE utf8_general_ci; 
    CREATE USER 'linjiashop'@'%' IDENTIFIED BY 'linjiashop191028';
    GRANT ALL privileges ON linjiashop.* TO 'linjiashop'@'%';
    flush privileges;
    ```     
- 下载项目测试数据的图片：链接：[https://pan.baidu.com/s/1V1QTSLspuDRuwI7H9TjcOA](https://pan.baidu.com/s/1V1QTSLspuDRuwI7H9TjcOA) 提取码：dyj6 ，将图片存放到t_sys_cfg表中system.file.upload.path配置的目录下(注意该目录未绝对路径，该目录可以通过“系统管理”-“参数管理”进行配置)
- 启动后台管理
    - 启动后台管理api服务:linjiashop-admin-api
        - 修改linjiashop-admin-api中数据库连接配置
            ```properties
            ## 首次启动需要设置下列配置项设置为create，以便系统可以自动创建表并导入./import.sql测试数据文件，
            ## 如果下面配置无法自动建表并导入测试数据文件；则可以手动初始化数据库，手动使用的初始化文件文件位于：doc/database.sql
            spring.jpa.hibernate.ddl-auto=create
            ```                    
        - 启动linjiashop-admin-api主类：cn.enilu.flash.api.AdminApiApplication，访问http://localhost:8082/swagger-ui.html ， 保证api服务启动成功
    - 启动后台管理的前端界面:linjiashop-admin
        - 运行 npm install --registry=https://registry.npm.taobao.org
        - 运行npm run dev
        - 启动成功后访问 http://localhost:9528 ,登录，用户名密码:admin/admin 
- 启动手机端商城
    - 启动手商城的api服务:linjiashop-mobile-api   
        - 修改linjiashop-mobile-api中数据库连接欸配置
        - 启动linjiashop-mobile-api主类：cn.enilu.flash.MobileApiApplication,访问http://localhost:8081/swagger-ui.html ， 保证api服务启动成功
    - 启动手机商城的前端:linjiashop-mobile
        - 运行 npm install --registry=https://registry.npm.taobao.org
        - 运行npm run dev
        - 启动成功后访问 http://localhost:8080/#/index
- 微信小程序开发
    - 首先启动后台api服务，和h5公用一个后台服务，即：linjiashop-mobile-api
    - 进入linjiashop-wxapp目录
        - 运行  npm install --registry=https://registry.npm.taobao.org
        - 运行npm run dev
        - 启动微信开发工具导入linjiashop-wxapp/dist/wx 目录即可预览小程序
 
 
## 技术交流
- QQ群：254059156
- [商业支持](http://microapp.gitee.io/linjiashop/other/xiaomiquan.html)
- 微信：myenilu,添加请备注：邻家小铺
