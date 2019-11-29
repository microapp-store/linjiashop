# 配置项目

你已经下载项目，并且初始化好了数据库，那么接下来只需要更改相应的配置就可以运行该项目了

- 更改admin-api和mobile-api下的数据库配置：src/resources/application-dev.properties配置：

```properties
## 开发环境配置，根据实际情况调整数据库连接信息
spring.datasource.url=jdbc:mysql://localhost:3306/linjiashop?useUnicode=true&characterEncoding=UTF8
spring.datasource.username=linjiashop
spring.datasource.password=linjiashop191028

```
