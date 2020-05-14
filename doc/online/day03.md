# 03-项目配置
- 数据库配置
- 图片目录配置
- 短信配置

## 数据库配置
- 创建数据库
```sql
   
    CREATE DATABASE IF NOT EXISTS wt1hd DEFAULT CHARSET utf8 COLLATE utf8_general_ci; 
    CREATE USER '数据库账号'@'%' IDENTIFIED BY '数据库密码';
    GRANT ALL privileges ON wt1hd.* TO 'wt1hd '@'localhost'; -- 限制只能本机访问
    flush privileges;
```
- 在本地启动应用的时候自动生成表和插入初始化语句，然后将其通过mysqldump导出后，导入到服务器上的数据库

## 配置图片目录
- 打开后台管理的”系统管理“-》”参数管理“页面，在”系统配置“标签页种更改”系统默认上传文件路径“的参数为：/data/app/wt1hd/runtime/upload

## 短信配置
- 首先确保在腾讯云上申请好短信签名，我申请的短信签名是"外滩一号店”，短信签名也就是后续发送短信的时候最前面的内容,比如：【外滩一号店】校验码2838，请及时输入。。。
- 其次保证创建好短信模板，确认有一个模板编号
- 在腾讯云短信种的应用列表创建一个应用，系统默认回有一个。
- 在“系统管理”->“参数管理”的“消息配置”标签页种正确配置上以下三个参数：
    - 腾讯sms接口appid
    - 腾讯sms接口appkey
    - 腾讯sms接口签名参数
- 在“消息管理”->"消息发送器中新建一个消息发送器，属性值如下：
    - 名称：腾讯短信-注册登录短信验证码
    - 发送类：tencentSmsSender
    - 运营商模板编号：就是该小节第二部申请的那个短信模板的模板编号
- 在”消息管理“->”消息模板“中修改编码为”REGISTER_CODE“的记录的发送器为：上一步新添加的那个消息发送器：腾讯短信-注册登录短信验证码


    
 **如果大家对上线运营笔记相关内容有意见和建议，请在gitee上issue评论区进行讨论：[邻家小铺上线日记评论区](https://gitee.com/microapp/linjiashop/issues/I1H4V3)**
