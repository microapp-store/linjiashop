<template>
  <div class="cart">
    <van-checkbox-group class="goods" v-model="checkedGoods">
      <div v-for="item in cartList"
           :key="item.id" class="cart-item">
        <van-checkbox
          :name="item.id"
        ></van-checkbox>
        <van-card
          style="margin-left:15px;"
          :title="item.goods.name"
          :desc="item.goods.descript"
          :num="item.count"
          :price="item.goods.price"
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
      <van-checkbox v-model="checkedAll" @click="checkAll" style="padding: 0 10px;">全选</van-checkbox>
    </van-submit-bar>
  </div>
</template>

<script>
  // Use Vuex
  import store from './store'
  import cartData from './cart.json'
  import utils from '@/utils/index.js'

  export default {
    data() {
      return {
        checkedGoods: [],
        cartList: cartData.data
      }
    },
    computed: {
      count() {
        return store.state.count
      },
      submitBarText() {
        const count = this.checkedGoods.length
        return '结算' + (count ? `(${count})` : '')
      },
      totalPrice() {
        return this.cartList.reduce((total, item) => total + (this.checkedGoods.indexOf(item.id) !== -1 ? (parseFloat(item.goods.price) * item.count) : 0), 0)
      }
    },
    onLoad() {
      for (let index = 0; index < this.cartList.length; index++) {
        this.cartList[index].goods.price = utils.formatPrice(this.cartList[index].goods.price)
        this.cartList[index].thumb = utils.fileMgrUrl + this.cartList[index].goods.pic
        this.checkedGoods.push(this.cartList[index].id + '')
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

  .cart {
    background-color: #fff;
  }

  .cart > .goods > .cart-item {
    position: relative;
    background-color: #fafafa;
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
