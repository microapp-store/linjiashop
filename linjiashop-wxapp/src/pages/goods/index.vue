<template>
  <div class="goods">
    <swiper class="swiper" indicator-dots="true" autoplay="true" interval="5000" duration="1000" style="height:350px;">
      <block v-for="(item, index) in goods.thumb" :index="index" :key="key">
        <swiper-item>
          <image :src="item.imgUrl" class="slide-image" mode="aspectFill" bindload="imageLoad"/>
        </swiper-item>
      </block>
    </swiper>
    <van-row>
      <van-col span="24">
        <div class="goods-title">{{ goods.name }}</div>
      </van-col>
      <van-col span="24">
        <div class="goods-desc">{{ goods.descript }}</div>
      </van-col>
      <van-col span="24">
        <div class="goods-price">{{ formatPrice(goods.price) }}</div>
      </van-col>
    </van-row>
    <van-row class="goods-express">
      <van-col span="10">运费：免运费</van-col>
      <van-col span="14">剩余：{{ goods.num }}</van-col>
    </van-row>
    <van-cell-group class="goods-cell-group">
      <van-cell is-link
                link-type="navigateTo"
                url="/pages/index"
                title="查看商品评论"/>
    </van-cell-group>
    <van-row>
      <van-col span="24">
        <wxParse :content="goods.detail"></wxParse>
      </van-col>
    </van-row>
  </div>
</template>

<script>
  import utils from '@/utils/index.js'
  import wxParse from 'mpvue-wxparse'
  export default {
    components: {
      wxParse
    },
    data() {
      return {
        goods: {thumb: []},
        imgheights: []
      }
    },
    methods: {
      clickNav() {

      },
      getGoods(id) {
        this.$API.get('/goods/' + id).then(res => {
          let goods = res.data
          goods.thumb = []
          const gallery = res.data.gallery.split(',')
          for (var index in gallery) {
            goods.thumb.push({imgUrl: utils.fileMgrUrl + gallery[index]})
          }
          this.goods = goods
        })
      },
      formatPrice(price) {
        console.log('price', price)
        return utils.formatPrice(price)
      }
    },

    created() {
    },
    onLoad(options) {
      let id = options.id
      this.getGoods(id)
      console.log('onload', id)
    }
  }
</script>

<style scoped>
  .swiper image {
    width: 100%;
    height: 400px;
  }

  .goods-desc {
    font-size: 12px;
    color: #999999;
    letter-spacing: 0;
    line-height: 18px;
    margin: 6px 0;
  }

  .goods-detail {
    width: 100%;
    padding: 15px;
    font-size: 14px;
  }

  .goods-title {
    font-size: 16px;
  }

  .goods-price {
    color: #f44;
  }

  .goods-express {
    color: #999;
    font-size: 12px;
    padding: 5px 15px;
  }

  .goods-tag {
    margin-left: 5px;
  }

  .goods-cell-group {
    margin: 15px 0;
  }
</style>

