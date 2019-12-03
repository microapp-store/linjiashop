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


  </div>

</template>

<script>
  export default {
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
          console.log('res', res)
          this.addressList = res.data
        })
      },
      onChange(e) {
        console.log(e)
        this.radio = e.mp.detail
      },
      edit(item) {
        console.log('edit', item)
        const url = '/pages/address/edit/main?id=' + item.id
        wx.navigateTo({url})
      }
    }
  }
</script>

<style>
  .van-card {
    margin-top: 10 rpx;
    margin-left: 75 rpx;
  }

  .van-card__thumb {
    width: 25px !important;
  }

  .van-radio__icon {
    left: 10px;
    z-index: 1;
    position: absolute;
    margin-top: 57px;
  }

  .van-card__footer {
    margin-top: -24px;
  }
</style>
