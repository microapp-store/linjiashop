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
                 use-footer-slot
      >
        <navigator
          v-for="(orderItem, index2) in item.items"
          :key="index2"
          :url="'/pages/goods/index?id='+orderItem.goods.id">
          <van-card
            :title="orderItem.title"
            :desc="orderItem.goods.descript"
            :price="orderItem.price"
            :num="orderItem.count"
            :thumb=" orderItem.goods.imgUrl">
          </van-card>
        </navigator>

        <div class="total">合计: {{ item.totalPrice }}</div>
        <view slot="footer" class="footer">
          <van-button size="small" @click="viewOrder(item)">查看详情</van-button>
          <van-button size="small" v-if="item.statusName === '待付款'" @click="cancelOrder(item)">取消订单</van-button>
          <van-button size="small" type="danger" @click="handleOrder(item)" v-if="item.btnText !== '查看详情'">
            {{item.btnText}}
          </van-button>
        </view>


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
    config:{
      navigationBarTitleText: '订单'
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
        const url = '../profile/loginOption/index'
        wx.navigateTo({url})
      } else {
        const status = options.status
        this.listQuery.status = status
        this.getData()
      }
    },
    methods: {
      changeTab(e) {
        const i = e.mp.detail.index
        const status = this.navList[i].id
        this.listQuery.status = status
        this.getData()
      },
      getData() {
        let url = 'user/order/getOrders?page=' + this.listQuery.page + '&limit=' + this.listQuery.limit + '&status=' + this.listQuery.status
        this.$API.get(url).then(res => {
          let orderList = res.data.records
          for (let i = 0; i < orderList.length; i++) {
            let order = orderList[i]
            order.btnText = this.getHandlerText(order.statusName)
            order.totalPrice = utils.formatPrice(order.totalPrice)
            for (let j = 0; j < order.items.length; j++) {
              order.items[j].price = utils.formatPrice(order.items[j].price)
              let goods = order.items[j].goods
              goods.imgUrl = utils.fileMgrUrl + goods.pic
            }
          }
          this.orderList = orderList
        })
      },
      viewOrder(order) {
        console.log('view', order)
      },
      cancelOrder(order) {
        console.log('cancel', order)
        this.$API.post('user/order/cancel/' + order.orderSn).then(res => {
          console.log('res', res)
          wx.showToast({title: '取消成功', icon: 'none'})
          this.getData()
        })
      },
      getHandlerText(statusName) {
        if (statusName === '已发货') {
          return '确认收货'
        }
        if (statusName === '待付款') {
          return '立即付款'
        }
        return '查看详情'
      },
      handleOrder(order) {
        if (order.statusName === '待付款') {
          console.log('立即付款')
          const url = '../payment/index?orderSn=' + order.orderSn + ' &totalPrice=' + order.totalPrice
          wx.navigateTo({url})
        }
        if (order.statusName === '已发货') {
          this.$API.post('user/order/confirm/' + order.orderSn).then(res => {
            console.log('res', res)
            wx.showToast({title: '成功收货', icon: 'none'})
            this.getData()
          })
        }
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

  .footer {
    text-align: right;
  }

  .footer > van-button {
    margin-left: 5px;
  }
</style>
