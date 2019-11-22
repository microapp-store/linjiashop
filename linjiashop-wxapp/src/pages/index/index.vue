<template>
  <div>
    <van-tabs :active="activeNav" bind:change="onChangeTab">
      <van-tab v-for="nav in navList" :title="nav.name" v-bind:key="nav.id">
      </van-tab>
    </van-tabs>
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
  import goodsData from './goodslist.json'
  import utils from '@/utils/index.js'

  export default {
    data() {
      return {
        navList: [
          {
            'name': '手机',
            'id': 1
          },
          {
            'name': '电视',
            'id': 2
          },
          {
            'name': '笔记本',
            'id': 3
          },
          {
            'name': '家电',
            'id': 4
          }
        ],
        banners: [],
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
      onChangeTab(event) {
        let id = event.detail.index + 1
        console.log('id', id)
      },
      formatPrice(price) {
        return utils.formatPrice(price)
      },
      viewGoodsDetail(id) {
        const url = '../goods/main?id=' + id
        wx.navigateTo({url})
      }
    },

    created() {
      let goodsList = goodsData.data.records
      for (let i = 0; i < goodsList.length; i++) {
        goodsList[i].price = utils.formatPrice(goodsList[i].price)
      }
      this.goodsList = goodsList
      console.log('goodsList', this.goodsList)
      // let app = getApp()
    },
    onLoad() {
      let activeNav = this.$root.$mp.query.activeNav

      console.log('onload', activeNav)
    }
  }
</script>

<style scoped>
</style>
