<template>
  <div class="address-detail">
    <van-cell-group>
      <van-field
        :value="address.name"
        @blur="changeName"
        required
        clearable
        label="姓名"
        placeholder="收货人姓名"

      />
      <van-field
        :value="address.tel"
        @blur="changeTel"
        required
        clearable
        label="电话"
        placeholder="收货人电话"
      />
      <van-field
        :value="address.province + address.city + address.district"
        required
        clearable
        label="地区"
        placeholder="选择地区"
        @focus="showPopup"
      />
      <van-field
        :value="address.addressDetail"
        @blur="changeDetail"
        required
        clearable
        label="详细地址"
        placeholder="详细地址"
      />
    </van-cell-group>
    <van-popup :show="showAreaPopup" :close="onClose"
               position="bottom">
      <van-area
        :area-list="areaList"
        :columns-placeholder="placeholder"
        title="标题"
        @cancel="onCancel"
        @confirm="onConfirm"
      />
    </van-popup>

    <van-button type="primary" size="large" @click="save">提交</van-button>
    <van-button type="danger" size="large" @click="remove">删除</van-button>

  </div>
</template>

<script>
  import areaData from './area.js'

  export default {
    data() {
      return {
        addressName: '张三',
        areaList: areaData,
        placeholder: ['请选择', '请选择', '请选择'],
        address: {province: '', city: '', district: ''},
        id: '',
        showAreaPopup: false
      }
    },
    onShow() {
      console.log('onShow')
    },
    computed: {
      getAddress() {
        if (this.address.province) {
          return this.address.province + this.address.city + this.address.district
        }
        return ''
      }
    },
    onLoad(options) {
      this.id = options.id
      if (this.id) {
        this.init()
      } else {
        this.reset()
      }
    },
    methods: {
      init() {
        if (this.id) {
          this.$API.get('/user/address/' + this.id).then(res => {
            console.log('res', res)
            this.address = res.data
          })
        }
      },
      reset() {
        this.address = {province: '', city: '', district: ''}
      },
      showPopup() {
        console.log('show')
        this.showAreaPopup = true
      },
      onClose(e) {
        this.showAreaPopup = false
      },
      onCancel() {
        this.showAreaPopup = false
      },
      onConfirm(e) {
        console.log(e)
        const params = e.mp.detail.values
        const province = params[0].name
        const city = params[1].name
        const district = params[2].name
        const areaCode = params[2] ? params[2].code : undefined
        if (areaCode === '') {
          wx.showToast({title: '请选择完整地址', icon: 'warn'})
        }
        this.address.province = province
        this.address.city = city
        this.address.district = district
        this.address.areaCode = areaCode
        this.showAreaPopup = false
      },
      changeName(e) {
        this.address.name = e.mp.detail.value
      },
      changeTel(e) {
        this.address.tel = e.mp.detail.value
      },
      changeDetail(e) {
        this.address.addressDetail = e.mp.detail.value
      },
      save() {
        console.log('save', this.address)
        this.$API.post('/user/address/save', this.address).then(res => {
          wx.showToast({title: '提交成功', icon: 'success'})
          wx.navigateBack({
            delta: 1
          })
        })
      },
      remove() {
        this.$API.delete('/user/address/' + this.address.id).then(res => {
          wx.showToast({title: '删除成功', icon: 'success'})
          wx.navigateBack({
            delta: 1
          })
        })
        console.log('remove')
      }
    }
  }
</script>

<style>
</style>
