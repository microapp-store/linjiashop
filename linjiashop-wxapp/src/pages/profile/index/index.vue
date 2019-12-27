<template>
  <div class="user">
    <navigator url="../loginOption/index">
      <van-row class="user-poster">
        <van-col span="6">

          <van-image
            round
            width="60"
            height="60"
            src="/static/img/logo.jpg"
          />
        </van-col>
        <van-col span="15">
          <div style="padding-top:25px;">

            {{user.nickName}}

          </div>
        </van-col>
        <van-col span="3">

          <van-icon name="arrow" style="padding-top:25px;"/>

        </van-col>
      </van-row>
    </navigator>
    <!--<img class="user-poster" src="/static/img/banner.jpg">-->
    <div class="user-links">
      <van-row>
        <van-col span="6">
          <van-icon name="pending-payment" @click="toOrder(1)"/>
          待付款
        </van-col>
        <van-col span="6" user-poster>
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
    </div>
    <van-row>
      <van-col span="24">
        <van-cell-group class="user-group">
          <van-cell icon="records" title="全部订单" is-link @click="toOrder(0)"/>
        </van-cell-group>
      </van-col>
    </van-row>

    <van-cell-group>
      <van-cell icon="location-o" title="收货地址" is-link @click="toAddress()"/>
      <van-cell icon="points" title="我的积分" is-link/>
      <van-cell icon="gold-coin-o" title="我的优惠券" is-link/>
      <van-cell icon="gift-o" title="我收到的礼物" is-link/>
      <van-cell icon="warning-o" title="关于本项目" is-link @click="toAbout()"/>
    </van-cell-group>
  </div>
</template>

<script>
  // Use Vuex
  import store from '@/utils/store.js'

  export default {
    config:{
      navigationBarTitleText: '个人中心'
    },
    data() {
      return {}
    },
    computed: {
      user() {
        return store.state.user
      }
    },
    methods: {
      toOrder(status) {
        const url = '../../order/index?status=' + status
        wx.navigateTo({url})
      },
      toAddress() {
        const url = '/pages/address/index'
        wx.navigateTo({url})
      },
      toAbout() {
        const url = '/pages/profile/about/index'
        wx.navigateTo({url})
      }

    }
  }
</script>


<style>
  .user-poster > .van-row {
    padding: 8px;
    background-color: lightblue;
  }

  .user-links {
    padding: 15px;
    text-align: center;
    background-color: #fff;
  }
</style>
