<template>
  <div class="search">
    <van-field
      left-icon="search"
      :value="value"
      placeholder="搜索商品名称"
      confirm-type="search"
      @blur="onChange"
    >
      <van-button slot="button" size="small" type="primary" @click="onSearch">搜索</van-button>
    </van-field>
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
    config: {
      navigationBarTitleText: '发现'
    },
    data() {
      return {
        value: '',
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
      onChange(e) {
        this.value = e.mp.detail.value
      },
      onSearch() {
        if (this.value) {
          const key = this.value
          this.$API.get('/goods/search?page=1&limit=20&key=' + key).then(res => {
            if (res.data.total > 0) {
              this.isHot = false
              let goodsList = res.data.records
              for (let i = 0; i < goodsList.length; i++) {
                goodsList[i].price = utils.formatPrice(goodsList[i].price)
                goodsList[i].imgUrl = utils.fileMgrUrl + goodsList[i].pic
              }
              this.goodsList = goodsList
            }
          })
        } else {
          if (!this.isHot) {
            this.getHotList()
          } else {
            wx.showToast({title: '请输入搜索关键字', icon: 'none'})
          }
        }
      },
      getHotList() {
        this.$API.get('/goods/searchHot').then(res => {
          this.isHot = true
          let goodsList = res.data.records
          for (let i = 0; i < goodsList.length; i++) {
            goodsList[i].price = utils.formatPrice(goodsList[i].price)
            goodsList[i].imgUrl = utils.fileMgrUrl + goodsList[i].pic
          }
          this.goodsList = goodsList
        })
      },
      viewGoodsDetail(id) {
        const url = '../goods/index?id=' + id
        wx.navigateTo({url})
      }
    }
  }
</script>

<style>
  .van-cell__left-icon-wrap {
    margin-top: 4px;
  }
</style>
