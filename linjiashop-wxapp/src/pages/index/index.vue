<template>
  <div>
    <van-tabs :active="activeNav" @change="changeTab">
      <van-tab v-for="nav in navList" :title="nav.name" v-bind:key="nav.id">
      </van-tab>
    </van-tabs>
    <swiper class="swiper" indicator-dots="true" autoplay="true" interval="5000" duration="1000">
      <block v-for="(item, index) in banners" :index="index" :key="key">
        <swiper-item>
          <navigator :url="item.url">
            <image :src="item.imgUrl" class="slide-image" mode="aspectFill"/>
          </navigator>
        </swiper-item>
      </block>
    </swiper>
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
  import card from '@/components/card'
  import utils from '@/utils/index.js'

  export default {
    config: {
      navigationBarTitleText: '邻家小铺'
    },
    data() {
      return {
        banners: [],
        navList: [],
        goodsList: [],
        activeNav: 0,
        listQuery: {
          page: 1,
          limit: 20,
          idCategory: undefined
        }
      }
    },
    components: {
      card
    },
    methods: {
      changeTab(event) {
        const index = event.mp.detail.index
        const idCategory = this.navList[index].id
        this.listQuery.idCategory = idCategory
        this.banners = this.processBanners(this.navList[index].bannerList)
        this.getGoodsList()
      },
      getGoodsList() {
        this.$API.get('/goods/queryGoods?page=' + this.listQuery.page + '&limit=' + this.listQuery.limit + '&idCategory=' + this.listQuery.idCategory, this.listQuery).then(res => {
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
      },
      processBanners(banners) {
        for (let i = 0; i < banners.length; i++) {
          let url = ''
          let page = banners[i].page
          let param = banners[i].param

          if (!utils.startWith(page, 'http')) {
            url = '../' + page + '/index'
          }
          if (param !== '') {
            const paramJson = JSON.parse(param)
            let i = 0
            for (const key in paramJson) {
              if (i === 0) {
                url += '?' + key + '=' + paramJson[key]
              } else {
                url += '&' + key + '=' + paramJson[key]
              }
              i++
            }
          }
          banners[i].url = url
          banners[i].imgUrl = utils.fileMgrUrl + banners[i].idFile
        }
        return banners
      }
    },

    created() {
      // let app = getApp()
    },

    onLoad() {
      this.$API.get('/category/list').then(res => {
        this.navList = res.data
        this.banners = this.processBanners(res.data[0].bannerList)
        this.listQuery.idCategory = res.data[0].id
        this.getGoodsList()
      }).catch(e => {
        console.log('e', e)
      })
    }
  }
</script>

<style>
  swiper {
    height: 220px;
  }

  swiper-item > navigator > image {
    width: 100%;
  }
</style>
