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
    <van-row>
      <van-col span="24">
        <wxParse :content="goods.detail"></wxParse>
      </van-col>
    </van-row>

    <van-goods-action>
      <van-goods-action-icon
        icon="home-o"
        text="主页"
        @click="toHome"
      />
      <van-goods-action-icon
        icon="like-o"
        text="喜欢"
        @click="sorry"
      />
      <van-goods-action-icon
        icon="cart-o"
        text="购物车"
        @click="goToCart"
      />
      <van-goods-action-button
        text="立即购买"
        @click="openAddDialog"
      />
    </van-goods-action>
    <van-popup
      :show="showDialog"
      position="bottom"
      custom-style="height: 50%;min-height:25rem;"
      closeable
      round
      @close="closeDialog"
    >
      <div class="sel-goods" style="padding:1.1rem;">
        <van-row>
          <van-col :span="8">
            <van-image
              width="80"
              height="80"
              :src="goods.imgUrl"
            />
          </van-col>
          <van-col :span="16">
            <div>价格：{{skuData.price}}</div>
            <div>剩余：{{skuData.stockNum}}</div>
            <div> {{attrText}}</div>
          </van-col>
        </van-row>
        <block v-for="(treeNode, index) in sku.tree" :index="index" :key="key">
          <div style="margin:.2rem 0 .5rem 0;">{{treeNode.k}}</div>
          <van-tag v-for="(treeV,index2) in treeNode.v"
                   :index="index2"
                   :key="key2"
                   color="#f2826a"
                   size="large"
                   :plain="treeV.plain"
                   style="margin-right:2px;"
                   @click="chooseAttr(treeV,treeNode)">
            {{treeV.name}}
          </van-tag>
        </block>
        <div class="buy-count">
          <div style="margin-bottom:.5rem;">
            购买数量
          </div>
          <van-stepper :value="skuData.selectedNum"/>
        </div>
      </div>
      <van-goods-action>
        <van-goods-action-button
          text="加入购物车"
          type="warning"
          @click="addCart"
        />
        <van-goods-action-button
          text="立即购买"
          @click="buy"
        />
      </van-goods-action>


    </van-popup>
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
        temp: 1,
        skuSel: {},
        goods: {thumb: []},
        sku: [],
        selSku: {},
        imgheights: [],
        showDialog: false,
        chooseAttrText: {},
        plainTest: false,
        skuData: {
          idGoods: '',
          stockNum: '',
          selectedNum: 1,
          price: '',
          idSku: '',
          selAttrKey: {},
          selAttrKeyList: [],
          selectedSkuComb: {
            attrKeyList: []
          }
        }
      }
    },
    computed: {
      attrText() {
        if (!this.sku.none_sku) {
          let ret = '选择：'
          for (let key in this.chooseAttrText) {
            ret += this.chooseAttrText[key] + ';'
          }
          return ret
        }
        return ''
      }
    },
    methods: {
      getGoods(id) {
        this.$API.get('/goods/' + id).then(res => {
          let goods = res.data.goods
          this.sku = res.data.sku
          let chooseAttrText = {}
          for (let i = 0; i < this.sku.tree.length; i++) {
            chooseAttrText[this.sku.tree[i]['k_s']] = this.sku.tree[i].k
          }
          this.chooseAttrText = chooseAttrText
          goods.thumb = []
          const gallery = goods.gallery.split(',')
          for (const index in gallery) {
            goods.thumb.push({imgUrl: utils.fileMgrUrl + gallery[index]})
          }
          goods.imgUrl = utils.fileMgrUrl + goods.pic
          this.goods = goods
        })
      },
      formatPrice(price) {
        return utils.formatPrice(price)
      },
      toHome() {
        const url = '/pages/index/index'
        wx.switchTab({url})
      },
      sorry() {
        wx.showToast({title: '敬请期待...', icon: 'info'})
      },
      goToCart() {
        wx.switchTab({url: '/pages/cart/index'})
      },
      openAddDialog() {
        this.showDialog = true
      },
      prepareSkuData() {
        let selAttrKeyLen = 0
        for (let key in this.skuData.selAttrKey) {
          console.log(key)
          selAttrKeyLen++
        }
        if (selAttrKeyLen !== this.sku.tree.length && !this.sku.none_sku) {
          wx.showToast({title: '请先选择商品规格', icon: 'none'})
          return false
        }
        if (!this.sku.none_sku) {
          // 根据规格类别id和规格值id获取sku id
          for (let i = 0; i < this.sku.list.length; i++) {
            let notSelSku = true
            for (let key in this.skuData.selAttrKey) {
              if (this.sku.list[i][key] !== this.skuData.selAttrKey[key]) {
                notSelSku = false
                break
              }
            }
            if (notSelSku) {
              this.skuData.idGoods = this.goods.id
              this.skuData.idSku = this.sku.list[i]['id']
              this.skuData.price = this.sku.list[i]['price']
              break
            }
          }
        }
        return true;

      },
      addCart() {
        if(!this.prepareSkuData()) {
          return
        }
        let skuData = this.skuData
        console.log('skuData', this.skuData)
        this.$API.post('user/cart/add', {
          idGoods: this.goods.id,
          idSku: this.sku.none_sku ? '' : skuData.idSku,
          count: skuData.selectedNum
        }).then(res => {
          wx.showToast({title: '已经加入到购物车', icon: 'success'})
          this.showDialog = false
        })
      },
      closeDialog(e) {
        this.showDialog = false
      },
      buy() {
        this.prepareSkuData()
        let skuData = this.skuData
        this.$API.post('/user/cart/add', {
          idGoods: this.goods.id,
          idSku: this.sku.none_sku ? '' : skuData.idSku,
          count: skuData.selectedNum
        }).then(res => {
          wx.switchTab({url: '/pages/cart/index'})
        })
      },

      chooseAttr(treeV, treeNode) {
        this.plainTest = !this.plainTest
        let sku = this.sku
        this.skuData.selAttrKey[treeNode.k_s] = treeV.id
        for (let i = 0; i < sku.tree.length; i++) {
          if (sku.tree[i]['k_s'] === treeNode['k_s']) {
            let skuSel = this.skuSel[treeNode['k_s']]
            if (!skuSel) {
              this.skuSel[treeNode['k_s']] = {}
            }
            // 第一个for循环将之前选中的取消掉
            for (let j = 0; j < sku.tree[i].v.length; j++) {
              if (skuSel) {
                let skuSelIdKeyIndex = skuSel['idKeyIndex']
                let skuSelIdValIndex = skuSel['idValIndex']
                if (skuSelIdKeyIndex > -1 && skuSelIdValIndex > -1) {
                  if (skuSelIdKeyIndex === i && skuSelIdValIndex === j) {
                    sku.tree[skuSelIdKeyIndex].v[skuSelIdValIndex].plain = !sku.tree[skuSelIdKeyIndex].v[skuSelIdValIndex].plain
                  }
                }
              }
            }
            // 第二个循环获取当前选中的标签标记选中
            for (let j = 0; j < sku.tree[i].v.length; j++) {
              if (sku.tree[i].v[j].id === treeV.id) {
                this.chooseAttrText[sku.tree[i]['k_s']] = sku.tree[i].v[j].name
                sku.tree[i].v[j].plain = !sku.tree[i].v[j].plain
                this.skuSel[treeNode['k_s']]['idKeyIndex'] = i
                this.skuSel[treeNode['k_s']]['idValIndex'] = j
              }
            }
          }
        }
        // 必须使用以下方式更新数组，使用数组的方法或者=赋值不会触发视图更新
        this.sku.tree = [...sku.tree]
      }
    },
    onLoad(options) {
      let id = options.id
      console.log('id:', id)
      this.skuSel = {}
      this.sku = {}
      this.showDialog = false
      this.skuData.selAttrKey = []
      this.getGoods(id)
    }
  }
</script>

<style scoped>
  .swiper image {
    width: 100%;
    height: 400px;
  }

  .goods {
    padding: 10px;
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

  .buy-count {
    margin-top: 15px;
  }
</style>

