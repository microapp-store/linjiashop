# 初始化数据

本系统使用mysql数据库

- 在mysql中创建数据库 linjiashop和对应的账户密码
    ```sql
    CREATE DATABASE IF NOT EXISTS linjiashop DEFAULT CHARSET utf8 COLLATE utf8_general_ci; 
    CREATE USER 'linjiashop'@'%' IDENTIFIED BY 'linjiashop191028';
    GRANT ALL privileges ON linjiashop.* TO 'linjiashop'@'%';
    flush privileges;
    ```
- 启动项目的时候会自动创建数据库并初始化系统数据，所以无需自己初始化数据
- 本系统通过Spring Boot的配置：spring.jpa.hibernate.ddl-auto=create 来初始化数据库，建议在生产环境中去掉该配置以避免将已经初始化好的数据库表和数据删除。
- 初始化语句为:linjiashop-admin-api/src/main/resources/import.sql，当配置spring.jpa.hibernate.ddl-auto=create的时候，系统启动会自动执行该语句初始化测试数据。
- 如果你是个传统的人；就是想要数据库文件，用传统的方式初始化数据库。doc/database.sql为你准备好了
