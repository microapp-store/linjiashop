<template>
  <div class="cart">
    <van-checkbox-group class="card-goods" :value="checkedGoods" @change="onChange">
      <div v-for="item in cartList"
           :key="item.id" class="card-goods__item">
        <van-checkbox
          :name="item.id"
        ></van-checkbox>
        <van-card
          :title="item.title"
          :desc="item.goods.descript"
          :num="item.count"
          :price="item.priceFormat"
          :thumb="item.thumb"
        >
          <div slot="footer">
            <van-stepper v-model="item.count" @change="stepperEvent(item,arguments)" disableInput/>
          </div>
        </van-card>
      </div>

    </van-checkbox-group>
    <van-submit-bar
      :price="totalPrice"
      :disabled="!checkedGoods.length"
      :button-text="submitBarText"
      @submit="submit"
    >
      <van-checkbox :value="checkedAll" @click="checkAll" style="padding: 0 10px;">全选</van-checkbox>
    </van-submit-bar>
  </div>
</template>

<script>
  import store from '@/utils/store.js'
  import utils from '@/utils/index.js'

  export default {
    data() {
      return {
        checkeAllCarts: [],
        checkedGoods: [],
        cartList: []
      }
    },
    computed: {
      submitBarText() {
        const count = this.checkedGoods.length
        console.log('count', count)
        return '结算' + (count ? `(${count})` : '')
      },
      totalPrice() {
        return this.cartList.reduce((total, item) => total + (this.checkedGoods.indexOf(item.id) !== -1 ? (parseFloat(item.goods.price) * item.count) : 0), 0)
      },
      checkedAll() {
        return this.checkedGoods.length > 0
      }
    },
    onShow() {
      this.checkedGoods = []
      this.cartList = []
      this.checkeAllCarts = []
      this.getCartData()
    },
    methods: {
      getCartData() {
        this.$API.get('/user/cart/queryByUser').then(res => {
          const cartList = res.data
          for (let index = 0; index < cartList.length; index++) {
            cartList[index].priceFormat = utils.formatPrice(cartList[index].price)
            cartList[index].thumb = utils.fileMgrUrl + cartList[index].goods.pic
            this.checkedGoods.push(cartList[index].id + '')
          }
          console.log('checked', this.checkedGoods)
          this.cartList = cartList
        })
      },
      onChange(e) {
        console.log(e)
        this.checkedGoods = e.mp.detail
      },
      checkAll() {
        if (this.checkedGoods.length === this.cartList.length) {
          this.checkeAllCarts = this.checkedGoods
          this.checkedGoods = []
        } else {
          this.checkedGoods = this.checkeAllCarts
        }
      },
      submit() {
        console.log('提交订单')
        const url = '/pages/checkout/index'
        wx.navigateTo({url})
      },
      stepperEvent(item, arg) {
        this.$API.post('user/cart/update/' + item.id + '/' + arg[0].mp.detail).then(res => {
        })
      },
      decrement() {
        store.commit('decrement')
      },
      toOrder(status) {
        console.log('status', status)
      },
      formatPrice(price) {
        return utils.formatPrice(price)
      }
    }
  }
</script>


<style>
  .van-submit-bar {
    bottom: 48px;
  }

  .card-goods {
    background-color: #fff;
  }

  .card-goods__item {
    position: relative;
    background-color: #fafafa;
  }

  .van-card {
    margin-left: 20px;
  }

  .van-checkbox__label {
    width: 100%;
    height: auto;
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

</style>
