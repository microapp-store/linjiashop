# 前言
 一个轻量，简单的商城系统
 
 
 ```sql
 CREATE DATABASE IF NOT EXISTS liteshop DEFAULT CHARSET utf8 COLLATE utf8_general_ci; 
    CREATE USER 'liteshop'@'%' IDENTIFIED BY 'liteshop191028';
    GRANT ALL privileges ON liteshop.* TO 'liteshop'@'%';
    flush privileges;
```
