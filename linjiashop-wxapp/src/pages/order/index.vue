<template>
  <div class="order">
    <van-tabs :active="activeNav" @change="changeTab">
      <van-tab v-for="nav in navList" :title="nav.name" v-bind:key="nav.id">
      </van-tab>
    </van-tabs>
    <div class="order_list">

      <van-panel v-for="(item, index) in orderList"
                 :key="index"
                 :title="'订单编号: ' + item.orderSn"
                 :status="item.statusName"
                 @click="toOrderDetail(item.orderSn)"
      >
        <navigator url="../profile/index/main">
          <van-card v-for="(orderItem, index2) in item.items"
                    :key="index2"
                    :title="orderItem.goods.name"
                    :desc="orderItem.goods.descript"
                    :price="orderItem.price"
                    :num="orderItem.count"
                    :thumb=" orderItem.goods.imgUrl">
          </van-card>
        </navigator>
        <div class="total">合计: {{ item.totalPrice }}</div>
        <div slot="footer" class="footer_btn">
          <van-button size="small" @click.stop="cancelOrder(item)" v-show="item.statusName === '待付款'" type="default">
            取消订单
          </van-button>
          <van-button size="small" @click.stop="handleOrder(item)" type="danger">
            {{getHandlerText(item.statusName)}}
          </van-button>

        </div>

      </van-panel>
    </div>

  </div>
</template>

<script>
  // Use Vuex
  import store from '@/utils/store'
  import utils from '@/utils/index'

  export default {
    computed: {
      count() {
        return store.state.count
      }
    },
    data() {
      return {
        navList: [
          {name: '全部', id: 0},
          {name: '待付款', id: 1},
          {name: '待发货', id: 2},
          {name: '已发货', id: 3},
          {name: '已完成', id: 4}
        ],
        activeNav: 0,
        listQuery: {
          page: 1,
          limit: 20,
          status: 0
        },
        orderList: [],

        loading: false,
        finished: false
      }
    },
    onLoad(options) {
      const token = store.state.token
      if (!token) {
        const url = '../profile/loginOption/main'
        wx.navigateTo({url})
      } else {
        const status = options.status
        console.log('status', status)
        this.listQuery.status = status
        this.getData()
      }
    },
    methods: {
      changeTab(e) {
        console.log('e', e)
        const i = e.mp.detail.index
        const status = this.navList[i].id
        console.log('status', status)
        this.listQuery.status = status
        this.getOrders()
      },
      getData() {
        let url = 'user/order/getOrders?page=' + this.listQuery.page + '&limit=' + this.listQuery.limit + '&status=' + this.listQuery.status
        this.$API.get(url).then(res => {
          let orderList = res.data.records
          for (let i = 0; i < orderList.length; i++) {
            let order = orderList[i]
            order.totalPrice = utils.formatPrice(order.totalPrice)
            for (let j = 0; j < order.items.length; j++) {
              order.items[i].price = utils.formatPrice(order.items[i].price)
              let goods = order.items[i].goods
              goods.imgUrl = utils.fileMgrUrl + goods.pic
            }
          }
          this.orderList = orderList
        })
      },
      toOrderDetail(id) {
        console.log('orderdetail', id)
      },
      toGoods(id) {
        console.log('goods', id)
      }
    }
  }
</script>

<style>

  .order_list > .van-panel {
    margin-top: 5px;
  }

  .order_list > .van-panel > .van-card {
    background-color: #fff;
  }

  .total {
    text-align: right;
    padding: 10px;
  }
</style>
