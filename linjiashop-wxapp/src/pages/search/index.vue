<template>
  <div class="search">
    <van-search placeholder="搜索商品关键字" @search="onSearch" @cancel="onCancel"/>
    <van-divider contentPosition="center" v-show="isHot">热门商品</van-divider>
    <van-card v-for="(goods,index) in goodsList" :key="index"
              :num="goods.num"
              :price="goods.price"
              :desc="goods.descript"
              :title="goods.name"
              :thumb="goods.imgUrl"
              @click="viewGoodsDetail(goods.id)"
    />
  </div>
</template>

<script>
  import {formatTime} from '@/utils/index'
  import utils from '@/utils/index.js'

  export default {
    data() {
      return {
        listQuery: {
          key: undefined
        },
        isHot: true,
        goodsList: []
      }
    },

    created() {
      let logs
      if (mpvuePlatform === 'my') {
        logs = mpvue.getStorageSync({key: 'logs'}).data || []
      } else {
        logs = mpvue.getStorageSync('logs') || []
      }
      this.logs = logs.map(log => formatTime(new Date(log)))
    },
    onLoad() {
      this.getHotList()
    },
    methods: {
      onSearch(event) {
        this.isHot = true
        console.log(event)
      },
      onCancel(event) {
        console.log(event)
      },
      getHotList() {
        this.$API.get('/goods/searchHot', this.listQuery).then(res => {
          let goodsList = res.data.records
          for (let i = 0; i < goodsList.length; i++) {
            goodsList[i].price = utils.formatPrice(goodsList[i].price)
            goodsList[i].imgUrl = utils.fileMgrUrl + goodsList[i].pic
          }
          this.goodsList = goodsList
        })
      },
      viewGoodsDetail(id) {
        const url = '../goods/main?id=' + id
        wx.navigateTo({url})
      }
    }
  }
</script>

<style>
  .log-list {
    display: flex;
    flex-direction: column;
    padding: 40 rpx;
  }

  .log-item {
    margin: 10 rpx;
  }
</style>
