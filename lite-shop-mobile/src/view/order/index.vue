<template>
  <div>
    <van-nav-bar
            title="我的订单"
            left-arrow
            @click-left="onClickLeft"
    />
    <van-tabs v-model="activeNav" @click="clickNav">
      <van-tab v-for="nav in navList" :title="nav.name" v-bind:key="nav.id">
      </van-tab>
    </van-tabs>



    <van-panel :title="item.title" :desc="item.descript" :status="item.statusName"   v-for="(item,index) in orderList" :key="index">
      <div>{{item.orderSn}}</div>
    </van-panel>



    <van-tabbar v-model="activeFooter">
      <van-tabbar-item icon="home-o"  replace to="/index">首页</van-tabbar-item>
      <van-tabbar-item icon="search"  replace to="/search">发现</van-tabbar-item>
      <van-tabbar-item icon="cart-o"  replace to="/cart">购物车</van-tabbar-item>
      <van-tabbar-item icon="user-o"  replace to="/user">我的</van-tabbar-item>
    </van-tabbar>
  </div>
</template>

<script src="./orders.js"></script>

<style lang="less">
  .van-submit-bar{
   bottom:48px;
  }
.card-goods {
  background-color: #fff;

  &__item {
    position: relative;
    background-color: #fafafa;

    .van-checkbox__label {
      width: 100%;
      height: auto; // temp
      padding: 0 10px 0 15px;
      box-sizing: border-box;
    }

    .van-checkbox__icon {
      top: 50%;
      left: 10px;
      z-index: 1;
      position: absolute;
      margin-top: -10px;
    }

    .van-card__price {
      color: #f44;
    }
  }
}
</style>
