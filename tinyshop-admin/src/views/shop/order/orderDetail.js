import {getOrder, save} from '@/api/shop/order'

export default {
  data() {
    return {
      form: {
        idUser: '',
        orderSn: '',
        status: '',
        idAddress: '',
        message: '',
        totalPrice: '',
        couponPrice: '',
        realPrice: '',
        id: ''
      }
    }
  },
  computed: {

    //表单验证
    rules() {
      return {
        // cfgName: [
        //   { required: true, message: this.$t('config.name') + this.$t('common.isRequired'), trigger: 'blur' },
        //   { min: 3, max: 2000, message: this.$t('config.name') + this.$t('config.lengthValidation'), trigger: 'blur' }
        // ]
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
        getOrder(this.form.orderSn).then(response => {
          this.form = response.data
        })
      }
    },
    save() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          save({
            idUser: this.form.idUser,
            orderSn: this.form.orderSn,
            status: this.form.status,
            idAddress: this.form.idAddress,
            message: this.form.message,
            totalPrice: this.form.totalPrice,
            couponPrice: this.form.couponPrice,
            realPrice: this.form.realPrice,
            id: this.form.id
          }).then(response => {
            this.$message({
              message: this.$t('common.optionSuccess'),
              type: 'success'
            })
            this.fetchData()
            this.formVisible = false
          })
        } else {
          return false
        }
      })
    }

  }
}
