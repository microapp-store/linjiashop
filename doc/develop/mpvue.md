# vant weapp和mpvue入坑指南

**如果你能静下心来把vant-weapp和mpvue的官方文档读一遍，那么你是不需要看本文档的**

**如果你遇到问题先去vant-weapp和mpvue的官方issue里翻一番，那么你是不需要看本文档的**

**如果你看到这里；恭喜你，你距离伟大的程序猿又远了一步**

本节主要介绍再试用vant weapp和mpvue开发过程中遇到的各种坑。当然这个也不能怪谁，毕竟微信综合各方面考虑（性能，安全等）裁剪掉了原生H5的很多原生功能。
而mpvue本质上是把原生代码（vue）转换成小程序代码。实际项目中，总有一些情况mpvue是考虑不到的。但是没问题，俗话说条条大道通罗马，方法A不行，总能找到方法B；俗话说，你遇到的问题，总有人比你先遇到，多按照本章开头的方法去找找解决方案，总能找到你想要的答案。


关于vant weapp和mpvue配合使用的坑，请先阅读下官方的说明：

[mpvue官方避坑指南](http://mpvue.com/mpvue/#%E6%A1%86%E6%9E%B6%E5%8E%9F%E7%90%86)
[vant weapp和mpvue入坑大全](https://github.com/youzan/vant-weapp/issues?utf8=%E2%9C%93&q=mpvue)


## 本项目中的避坑经历(待完善)

### 商品详情页添加到购物车的时候，选择商品规格时，无法通过更新sku.tree数组中的数据来更新规格视图未选中状态
在这里使用数组的方法，如slice,push，等都没有用，必须使用如下方法可以
```
this.sku.tree = [...this.sku.tree]
```
