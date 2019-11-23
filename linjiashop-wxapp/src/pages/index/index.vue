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
              :thumb="'http://linjiashop.microapp.store/prod-api/file/getImgStream?idFile='+goods.pic"
              @click="viewGoodsDetail(goods.id)"
    />
  </div>
</template>

<script>
  import card from '@/components/card'
  import utils from '@/utils/index.js'

  export default {
    data() {
      return {
        banners: [
          {
            'url': '../goods/main?id=1',
            'imgUrl': 'http://linjiashop.microapp.store/prod-api/file/getImgStream?idFile=143'
          },
          {
            'url': '../goods/main?id=2',
            'imgUrl': 'http://linjiashop.microapp.store/prod-api/file/getImgStream?idFile=145'
          }
        ],
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
          }
          this.goodsList = goodsList
        })
      },
      viewGoodsDetail(id) {
        const url = '../goods/main?id=' + id
        wx.navigateTo({url})
      },
      processBanners(banners) {
        for (let i = 0; i < banners.length; i++) {
          banners[i].imgUrl = 'http://linjiashop.microapp.store/prod-api/file/getImgStream?idFile=' + banners[i].idFile
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

<style scoped>
</style>
