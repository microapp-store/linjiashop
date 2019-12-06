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
        address: {},
        id: '',
        showAreaPopup: false
      }
    },
    onShow() {
      this.init()
    },
    computed: {},
    onLoad(options) {
      this.id = options.id
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
      showPopup() {
        console.log('show')
        this.showAreaPopup = true
      },
      onClose(e) {
        console.log(e)
        this.showAreaPopup = false
      },
      edit(item) {
        console.log('edit', item)
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
        console.log('address', this.address)
        console.log(this.addressName)
        this.showAreaPopup = false
      },
      changeName(e) {
        console.log(1)
        console.log(e)
      },
      changeTel(e) {
        console.log(e)
      },
      changeDetail(e) {
        console.log(e)
      },
      save() {
        console.log('save', this.address)
        this.$API.post('/user/address/save', this.address).then(res => {
          console.log('res', res)
        })
      },
      remove() {
        console.log('remove')
      }
    }
  }
</script>

<style>
</style>
