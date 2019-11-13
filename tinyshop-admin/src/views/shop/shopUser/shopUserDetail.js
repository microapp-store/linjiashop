import {getUser, save} from '@/api/shop/shopUser'

export default {
  data() {
    return {
      form: {
        mobile: '',
        salt: '',
        password: '',
        nickName: '',
        avatar: '',
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
    this.form.id = this.$route.query.id
    this.init()
  },
  methods: {
    init() {
      this.fetchData()
    },
    fetchData() {

      if (this.form.id) {
        getUser(this.form.id).then(response => {
          this.form = response.data
        })
      }
    },
    save() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          save({
            mobile: this.form.mobile,
            salt: this.form.salt,
            password: this.form.password,
            nickName: this.form.nickName,
            avatar: this.form.avatar,
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
