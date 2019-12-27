<template>
  <div class="checkout">
    <van-cell-group>
      <van-cell :title="addrText" is-link @click="chooseAddress"/>
    </van-cell-group>
    <div v-for="item in cartList"
         :key="item.id" class="card-goods__item">
      <van-card
        :title="item.title"
        :desc="item.goods.descript"
        :num="item.count"
        :price="item.price"
        :thumb="item.thumb"
      >
      </van-card>
    </div>
    <van-submit-bar
      :price="totalPrice"
      :disabled="!checkedGoods.length"
      :button-text="submitBarText"
      @submit="submit"
    >
    </van-submit-bar>
  </div>

</template>

<script>
  import utils from '@/utils/index.js'
  import store from '@/utils/store.js'

  export default {
    config: {
      navigationBarTitleText: '提交订单'
    },
    data() {
      return {
        activeFooter: 2,
        checkedGoods: [],
        checkeAllCarts: [],
        cartList: [],
        checkedAll: true,
        addr: undefined,
        message: ''
      }
    },
    onShow() {
      this.init()
    },
    computed: {
      addrText() {
        const address = store.state.addr
        if (address.name) {
          this.addr = address
        }
        if (this.addr) {
          return this.addr.name + '(' + this.addr.tel + ')'
        } else {
          return ''
        }
      },
      submitBarText() {
        const count = this.checkedGoods.length
        return '提交订单' + (count ? `(${count})` : '')
      },
      totalPrice() {
        return this.cartList.reduce((total, item) => total + (this.checkedGoods.indexOf(item.id) !== -1 ? (parseFloat(item.goods.price) * item.count) : 0), 0)
      }
    },

    methods: {
      init() {
        this.$API.get('/user/order/prepareCheckout').then(res => {
          let cartList = res.data.list
          this.addr = res.data.addr
          for (const index in cartList) {
            cartList[index].price = utils.formatPrice(cartList[index].count * cartList[index].price)
            cartList[index].thumb = utils.fileMgrUrl + cartList[index].goods.pic
            this.checkedGoods.push(cartList[index].id + '')
          }
          this.cartList = cartList
        })
      },
      submit() {
        this.$API.post('user/order/save?idAddress=' + this.addr.id + '&message=' + this.message).then(res => {
          const url = '/pages/payment/index?orderSn=' + res.data.orderSn + '&totalPrice=' + res.data.totalPrice
          wx.navigateTo({url})
        })
      },
      formatPrice(price) {
        return (price / 100).toFixed(2)
      },
      checkAll() {
        if (this.checkedGoods.length === this.cartList.length) {
          this.checkeAllCarts = this.checkedGoods
          this.checkedGoods = []
        } else {
          this.checkedGoods = this.checkeAllCarts
        }
      },
      chooseAddress() {
        const url = '/pages/address/index?choose=true'
        wx.navigateTo({url})
      }
    }
  }
</script>

<style>
  .van-submit-bar {
    bottom: 48px;
  }

  .card-goods__item {
    margin-bottom: 80px;
  }

  /*.card-goods {*/
  /*background-color: #fff;*/

  /*&__item {*/
  /*position: relative;*/
  /*background-color: #fafafa;*/

  /*.van-checkbox__label {*/
  /*width: 100%;*/
  /*height: auto; // temp*/
  /*padding: 0 10px 0 15px;*/
  /*box-sizing: border-box;*/
  /*}*/

  /*.van-checkbox__icon {*/
  /*top: 50%;*/
  /*left: 10px;*/
  /*z-index: 1;*/
  /*position: absolute;*/
  /*margin-top: -10px;*/
  /*}*/

  /*.van-card__price {*/
  /*color: #f44;*/
  /*}*/
  /*}*/
  /*}*/
</style>
