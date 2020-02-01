import {getOrder, sendOut} from '@/api/shop/order'
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
        getOrder(this.form.orderSn).then(response => {
            this.form = response.data
          }
        )
      }
    },
    sendOut() {
      sendOut(this.form.id).then(response => {
          this.fetchData()
          this.$message({
            message: '发货成功,只是将订单状态更改为已发货',
            type: 'info'
          })
        }
      )
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
