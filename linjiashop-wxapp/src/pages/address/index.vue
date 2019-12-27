<template>
  <div class="address">
    <van-radio-group :value="radio" @change="onChange">
      <div v-for="(item, index) in addressList" :index="index" :key="key" style="margin-top:5px;">
        <van-radio :name="item.id" v-if="cho"></van-radio>
        <van-card
          tag="公司"
          :desc="item.province + item.city + item.district"
          :title="item.name + ' '+ item.tel"
        >
          <view slot="footer">
            <van-icon name="edit" @click="edit(item)"/>

          </view>
        </van-card>
      </div>
    </van-radio-group>

    <van-button type="primary" size="large" @click="add">新增地址</van-button>
  </div>

</template>

<script>
  import store from '@/utils/store.js'
  export default {
    config:{
      navigationBarTitleText: '地址管理'
    },
    data() {
      return {
        radio: '1',
        addressList: [],
        cho: false
      }
    },
    onShow() {
      this.init()
    },
    computed: {},
    onLoad(options) {
      this.cho = options.choose
    },
    methods: {
      init() {
        this.$API.get('/user/address/queryByUser').then(res => {
          this.addressList = res.data
        })
      },
      onChange(e) {
        this.radio = e.mp.detail
        for (let i = 0; i < this.addressList.length; i++) {
          if (this.addressList[i].id === this.radio) {
            store.commit('setAddr', this.addressList[i])
            wx.navigateBack({
              delta: 1
            })
            break
          }
        }
      },
      edit(item) {
        const url = '/pages/address/edit/index?id=' + item.id
        wx.navigateTo({url})
      },
      add() {
        const url = '/pages/address/edit/index'
        wx.navigateTo({url})
      }
    }
  }
</script>

<style>
  .van-card__header {
    margin-top: 5rpx;
    margin-left: 75rpx;
  }

  .van-card__thumb {
    width: 25px !important;
  }

  .van-radio__icon {
    left: 10px;
    z-index: 1;
    position: absolute;
    margin-top: 57rpx;
  }

  .van-card__footer {
    margin-top: -100rpx;
  }
</style>
