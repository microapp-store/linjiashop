import orderApi from '@/api/shop/order'
import expressApi from '@/api/system/express'
import {getApiUrl} from '@/utils/utils'

export default {
  data() {
    return {
      form: {user: {id: '', name: ''}, address: {name: '', tel: '', addressDetail: ''}},
      apiUrl: getApiUrl(),
      expressList:[],
      shipping:{
        show:false,
        id:'',
        idExpress:'',
        shippingSn:''
      }
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
    openSendOutForm(){
      if(this.expressList.length==0){
        expressApi.queryAll().then( response=> {
          this.expressList = response.data
        })
      }
      this.shipping.show = true
    },
    sendOut() {
      this.$confirm(this.$t('common.optionConfirm'), this.$t('common.tooltip'), {
        confirmButtonText: this.$t('button.submit'),
        cancelButtonText: this.$t('button.cancel'),
        type: 'warning'
      }).then(() => {
        orderApi.sendOut(this.form.id,this.shipping.idExpress,this.shipping.shippingSn).then(response => {
          this.fetchData()
          this.shipping.show = false
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
