# 02-环境准备
- 短信服务
- 服务器环境准备
- 前后端部署
- nginx配置

## 短信服务
果然短信签名有问题，因为我的腾讯云账号是我个人认证的，但是我要使用的这个短信签名的公众号是是企业认证的，
主题不一致，需要公众号主题授权给我个人。

太麻烦了，有没有简单的方法呢，细细看了下，还真有。腾讯云账号有种登录方式叫做使用微信公众号登录。那么我可以使用公众号账号来关联登录

登录进去后就已经自动用公众号主体（是个企业）做了实名认证

接下来申请签名就块多了，基本上十分钟内就可以批准

## 服务器环境准备
- 因为之前的服务器上已经安装了相关软件，下面列出相关软件版本：
- nginx：1.12.2
- mysql:5.6.47
- jdk:1.8.0_201
- tomcat:9

## 前后端部署
- admin-api和mobile-api分别打war包为：wt1hd-admin.war,wt1hd-mobile.war 将war包放在/opt/tomcat/webapps/目录下
- 前端项目部署：
    - admin存放在：/opt/wt1hd/admin/
    - mobile放在：/opt/wt1hd/mobile/

## nginx 配置
- 前端项目和前端api配置wt1hd-mobile.config，配置内容如下：

```
server {
    listen	80;
    server_name  wt1hd.microapp.store;

    access_log  /data/app/runtime/log/nginx/wt1hd/mobile.access.log  main;
    
    location /{
		alias /opt/wt1hd/mobile/;
		index index.html index.htm;
    }
    //api服务配置
     location /prod-api/{
                proxy_ignore_client_abort on;
                proxy_set_header   Host             $host;
                proxy_set_header   X-Real-IP        $remote_addr;
                proxy_set_header   X-Forwarded-For $proxy_add_x_forwarded_for;
                proxy_read_timeout 600s;
                proxy_pass      http://localhost:8080/wt1hd-mobile-api/;
    }

    error_page   500 502 503 504  /50x.html;
    location = /50x.html {
        root   /usr/share/nginx/html;
    }

}

```    
- 后端项目和后端api配置：wt1hd-admin.config，配置内容如下：

```

server {
    listen       80;
    server_name  wt1hd-admin.microapp.store;

    access_log  /data/app/runtime/log/nginx/wt1hd/admin.access.log  main;
    
    location /{
		alias /opt/wt1hd/admin/;
		index index.html index.htm;
    }

     location /prod-api/{
                proxy_ignore_client_abort on;
                proxy_set_header   Host             $host;
                proxy_set_header   X-Real-IP        $remote_addr;
                proxy_set_header   X-Forwarded-For $proxy_add_x_forwarded_for;
                proxy_read_timeout 600s;
                proxy_pass      http://localhost:8080/wt1hd-admin-api/;
    }

    error_page   500 502 503 504  /50x.html;
    location = /50x.html {
        root   /usr/share/nginx/html;
    }

}
```

- 注意我使用上面的配置的时候，前端的两个项目admin和mobile没有更改任何配置，分别运行npm run build:prod 和npm run build打包后直接用了。很多朋友打包部署这里遇到各种各样的问题，根本原因有两点，一是没有搞懂vue.config.js种的配置，二是对nginx的配置不明白不会用。


 **如果大家对上线运营笔记相关内容有意见和建议，请在gitee上issue评论区进行讨论：[邻家小铺上线日记评论区](https://gitee.com/microapp/linjiashop/issues/I1H4V3)**
