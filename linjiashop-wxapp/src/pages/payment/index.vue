<template>
  <div class="payment">
    <van-cell-group>
      <van-cell title="订单编号" :value="order.orderSn"/>
      <van-cell title="应付金额">
        <span class="red strong">￥{{order.totalPrice}}</span>
      </van-cell>
    </van-cell-group>

    <van-radio-group :value="payType" @change="onChange">
      <van-cell-group>
        <van-cell clickable data-name="wxpay" @click="onClick">
          <img

            style="width:45%;height:25px;margin-right: 130px;"
            slot="icon"
            slot-scope="props"
            src="/static/img/wxpay.png"
          >
          <van-radio slot="right-icon" name="wxpay"/>
        </van-cell>
        <van-cell clickable data-name="alipay" @click="onClick">
          <img
            style="width:45%;height:25px;margin-right: 130px;"
            slot="icon"
            slot-scope="props"
            src="/static/img/alipay.png"
          >
          <van-radio slot="right-icon" name="alipay"/>
        </van-cell>
      </van-cell-group>
    </van-radio-group>
    <van-button class="footer" type="primary" size="large" @click="pay">立即支付</van-button>
  </div>
</template>

<script>
  import store from '@/utils/store'
  import utils from '@/utils/index'

  export default {
    computed: {
      count() {
        return store.state.count
      }
    },
    config:{
      navigationBarTitleText: '收银台'
    },
    data() {
      return {
        order: {},
        payType: 'wxpay'
      }
    },
    onLoad(options) {
      let orderSn = options.orderSn
      let totalPrice = utils.formatPrice(options.totalPrice)
      this.order = {orderSn: orderSn, totalPrice: totalPrice}
    },
    methods: {
      pay() {
        let payTypeName = this.payType === 'wxpay' ? '微信支付' : '支付宝'
        wx.showToast({title: '准备使用' + payTypeName + '支付', icon: 'none'})
      },
      onChange(event) {
        console.log('event', event)
        this.payType = event.detail
      },
      onClick(event) {
        console.log('event', event)
        this.payType = event.mp.currentTarget.dataset.name
      }
    }
  }
</script>

<style>
  .red {
    color: red;
  }

  .strong {
    font-weight: bold;
  }

  .van-cell-group {
    margin-top: 10px;
  }

  .footer {
    width: 100%;
    position: fixed;
    bottom: 0px;
  }

  .van-cell__value {
    margin-left: -10px;
  }
</style>
