import orderApi from '@/api/shop/order'
import {getApiUrl} from '@/utils/utils'

export default {
  data() {
    return {
      form: {user: {id: '', name: ''}, address: {name: '', tel: '', addressDetail: ''}},
      apiUrl: getApiUrl()
    }
  },
  created() {
    this.form.orderSn = this.$route.query.orderSn
    this.init()
  },
  methods: {
    init() {
      this.fetchData()
    },
    fetchData() {
      if (this.form.orderSn) {
        orderApi.getOrder(this.form.orderSn).then(response => {
            this.form = response.data
          }
        )
      }
    },
    sendOut() {
      //todo 发货只是更改订单状态为已发货，正常发货需要填写物流信息
      //this.$t('common.optionConfirm')
      this.$confirm('发货只是更改订单状态为已发货，正常发货需要填写物流信息,功能完善中，确认发货?', this.$t('common.tooltip'), {
        confirmButtonText: this.$t('button.submit'),
        cancelButtonText: this.$t('button.cancel'),
        type: 'warning'
      }).then(() => {
        this.fetchData()
        orderApi.sendOut(id).then(response => {
          this.$message({
            message: '发货成功',
            type: 'success'
          })
        })
      }).catch(() => {
      })
    },
    formatPrice(price) {
      if (price) {
        return '￥' + (price / 100).toFixed(2)
      }
      return ''
    },
    printOrder() {
      this.$print(this.$refs.print)
    }

  }
}
