<template>
  <div>
    <van-tabs v-model="activeNav" @click="clickNav">
      <van-tab v-for="nav in navList" :title="nav.name" v-bind:key="nav.id">
      </van-tab>
    </van-tabs>
    <van-card v-for="(goods,index) in goodsList" :key="index"
              :num="goods.num"
              :price="formatPrice(goods.price)"
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
        goodsList: goodsData.data.records,
        activeFooter: 0,
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
      clickNav() {

      },
      formatPrice(price) {
        return (price / 100).toFixed(2)
      }
    },

    created() {
      // let app = getApp()
    }
  }
</script>

<style scoped>
</style>
