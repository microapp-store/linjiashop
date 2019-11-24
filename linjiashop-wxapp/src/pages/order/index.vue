<template>
  <div class="user">
    <img class="user-poster" src="/static/img/banner.jpg">
    <van-row class="user-links">
      <van-col span="6">
        <van-icon name="pending-payment" @click="toOrder(1)"/>
        待付款
      </van-col>
      <van-col span="6"user-poster>
        <van-icon name="tosend" @click="toOrder(2)"/>
        待发货
      </van-col>
      <van-col span="6">
        <van-icon name="logistics" @click="toOrder(3)"/>
        已发货
      </van-col>
      <van-col span="6">
        <van-icon name="bag-o" @click="toOrder(4)"/>
        已完成
      </van-col>
    </van-row>
    <van-cell-group class="user-group">
      <van-cell icon="records" title="全部订单" is-link to="order" />
    </van-cell-group>

    <van-cell-group>
      <van-cell icon="location-o" title="收货地址" is-link to="address"/>
      <van-cell icon="points" title="我的积分" is-link />
      <van-cell icon="gold-coin-o" title="我的优惠券" is-link />
      <van-cell icon="gift-o" title="我收到的礼物" is-link />
    </van-cell-group>
  </div>
</template>

<script>
  // Use Vuex
  import store from './store'

  export default {
    computed: {
      count() {
        return store.state.count
      }
    },
    methods: {
      increment() {
        store.commit('increment')
      },
      decrement() {
        store.commit('decrement')
      },
      toOrder(status) {
        console.log('status', status)
      }
    }
  }
</script>


<style>

  .user-poster {
    height: 350rpx;
    display: block;
  }

  .user-group {
    margin-bottom: 15px;
  }

  .user-links {
    padding: 15px 0;
    text-align: center;
    background-color: #fff;
  }
</style>
