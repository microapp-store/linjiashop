# Change log

## v0.6
[gitee](https://gitee.com/microapp/linjiashop/releases/v0.6) [github](https://github.com/microapp-store/linjiashop/releases/tag/v0.6)
### Issues
- echarts随着窗口缩放自适应
- 用户个人中心增加收藏商品管理功能
- 对接快递鸟物流查询服务
- 编辑sku使用逻辑删除代替物理删除
- 购物车管理功能，可以删除购物车中的商品
- banner编辑时针对参数进行必须为json字符串的验证
- 后台管理中显示用户微信头像
### Fixes
- 分页查询bug
- 升级webpack导致的组件无法加载的问题
- 定日日志任务异常日志没有正常显示
- 更新缓存的时候连带更新常量工具类中使用的本地(TimeCacheMap)缓存
- 编辑商品规格信息的时候允许规格重复的问题
- 商品类别中维护商品规格属性名的时候可以重复的问题

## v0.5
[gitee](https://gitee.com/microapp/linjiashop/releases/v0.5) [github](https://github.com/microapp-store/linjiashop/releases/tag/v0.5)
### Issues
- 对接微信-获取微信用户信息
- 重构日志记录方式（直接使用实体类的comment注释代替自定义AbstractDictMap子类的方式来描述更改的字段信息）
- H5端完成微信支付的对接
- 用户下单的时候判断库存避免超卖
- mobile端针对后台的500错误使用toast提示统一显示处理
### Fixes
- 【后台管理】商品编辑界面上传的图片无法显示
- 【后台管理】新建立的角色配置给用户后无法登录
- 后台管理(admin-api)和用户端(mobile-api)参数由于缓存无法同步的问题 
- 【后台管理】业务日志和登录日志创建日期不显示的问题
- 【后台管理】不配置菜单权限时登录管理员无法获取并生成左侧菜单树
- BaseEntity使用hibernate的@CreationTimestamp和@UpdateTimestamp代替spring的@CreatedDate和@LastModifiedDate
- 【后台管理】商品更新的时候从多规格更改位单规格的时候逻辑错误
- 对下单的时候库存做事务管理和加锁处理，以避免超卖问题
- 定时任务执行异常日志没有正常显示
- 静态界面段配置问题导致无法跨域


## v0.4
[gitee](https://gitee.com/microapp/linjiashop/releases/v0.4) [github](https://github.com/microapp-store/linjiashop/releases/tag/v0.4)

### Issues
- 【后台管理】管理员给订单添加备注信息
- 【后台管理】维护物流公司
- 【后台管理】增加订单发货功能
- 【后台管理】增加导出订单excel报表功能
- 针对订单全流程生命周期添加操作日志
- 基础框架封装支持关联查询；封装根据原生sql语句查询返回List Map代替List Object[]
- 【后台管理】商品类别中维护该类商品规格名信息
- 【后台管理】订单管理增加高级检索功能
- 【后台管理】参数管理增加分组功能，方便分组维护参数
- 增加定时任务获取微信token


### Fixes
- 【H5】用户提交订单的时候收货地址缓存问题
- 【H5】用户跳转到其他页面再跳转回来会话信息丢失的问题
- 【后台管理】banner上传后界面无法回显的问题
- 【H5】跳转到其网站再跳转回来会话信息丢失的问题[将会话信息存储在localStorage]
- 【H5】提交订单时收货地址缓存了上一个登录用户的收货地址的问题
- 【H5】使用手机号验证码登陆后后没有正常返回登录用户信息
- 【H5] 用户登录的时候没有记录上用户最后登录时间


## v0.3
 [gitee](https://gitee.com/microapp/linjiashop/releases/v0.3)
### Issues 
- 【后台管理】压缩svg图标
- 【H5】新增未登录状态和购物车为空状态下的购物车界面
- 【H5】商品下架后商品详情页显示该商品已下架
- 【H5】新增未登录状态和购物车为空状态下的购物车界面
- 【后台管理】后台新增定时任务定时更新微信token
- 【H5】在商品详情页商品添加到购物车后购物车图标显示商品数量
- 【H5】新增商品收藏功能
- 优化图片资源请求地址：图片资源标识使用uuid代替自增的id
- 完善后台管理的专题管理功能

### Fixes
- 【后台管理】修复并完善权限配置功能
- 【后台管理】商品编辑菜单数据初始化错误
- 【后台管理】修改管理员密码后缓存处理错误导致重新登录失败
- 【后台管理】 banner上传问题

## v0.2
 [gitee](https://gitee.com/microapp/linjiashop/releases/v0.2) [github](https://github.com/microapp-store/linjiashop/releases/tag/v0.2)
### Issues
- 【API】 Token过期自动刷新
- 【后台管理】 用户列表增加用户头像，注册时间列，添加过滤功能，增加用户详情页内容
- 【H5】 添加用户使用手机号自主注册账号功能
- 【后台管理】 优化dashboard页，从后台获取展示数据
- 【H5】 新增设置中心，添加用户自维护功能,包括上传头像，修改昵称，性别，密码，更换手机等
- 【后台管理】 新增专题管理功能，对专题信息进行维护
- 【H5】 专题推荐
- 【后台管理】 热门商品和新品推荐商品的维护
- 【H5】 新增个性化首页，首页内容包括：专题推荐，热门商品推荐，新品推荐
### Fixes
- 【H5，后台管理】用户户没有权限操作的时候浏览器自动弹出验证框的问题


## v0.1
[gitee](https://gitee.com/microapp/linjiashop/releases/v0.1)[github](https://github.com/microapp-store/linjiashop/releases/tag/v0.1)
### Issues
- 【后台管理】  基础的管理功能：用户，权限，菜单，角色，定时任务，消息发送，日志，监控
- 【后台管理】  业务功能：商品类别维护，商品维护，banner维护，订单管理，购物车列表，用户列表
- 【H5,小程序】用户登录，商品列表，banner列表，搜索，商品详情，购物车，下单，历史订单
- 【APP商城】  另外发布了商城的app版本（基于Flutter):linjiashop-flutter
