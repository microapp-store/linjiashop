<template>
  <div class="order">
    <van-tabs :active="activeNav" @change="changeTab">
      <van-tab v-for="nav in navList" :title="nav.name" v-bind:key="nav.id">
      </van-tab>
    </van-tabs>
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
    data() {
      return {
        navList: [
          {name: '全部', id: 0},
          {name: '待付款', id: 1},
          {name: '待发货', id: 2},
          {name: '已发货', id: 3},
          {name: '已完成', id: 4}
        ],
        activeNav: 0
      }
    },
    onLoad(options) {
      const status = options.status
      console.log('status', status)
    },
    methods: {
      changeTab(e) {
        const i = e.mp.detail.index
        const status = this.navList[i].id
        console.log('status', status)
      },
      toOrder(status) {
        console.log('status', status)
      }
    }
  }
</script>


<style>

  .user-poster {
    height: 350 rpx;
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
