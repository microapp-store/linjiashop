
import { isvalidUsername } from '@/utils/validate'
import LangSelect from '@/components/LangSelect'
import AesUtil  from "@/utils/aes.js"

export default {
  name: 'login',
  components: { LangSelect },
  data() {
    const validateUsername = (rule, value, callback) => {
      if (!isvalidUsername(value)) {
        callback(new Error(this.$t('login.errorAccount') ))
      } else {
        callback()
      }
    }
    const validatePassword = (rule, value, callback) => {
      if (value.length < 5) {
        callback(new Error(this.$t('login.errorPassword')))
      } else {
        callback()
      }
    }
    return {
      loginForm: {
        username: '',
        password: ''
      },
      loginRules: {
        username: [{ required: true, trigger: 'blur', validator: validateUsername }],
        password: [{ required: true, trigger: 'blur', validator: validatePassword }]
      },
      loading: false,
      pwdType: 'password',
      redirect:'/'
    }
  },
  mounted(){
    this.init()
  },
  methods: {
    init(){
      let redirect = this.$route.query.redirect
      if(redirect){
        this.redirect = redirect
      }
    },
    showPwd() {
      if (this.pwdType === 'password') {
        this.pwdType = ''
      } else {
        this.pwdType = 'password'
      }
    },
    handleLogin() {
      const loginForm = this.loginForm
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loading = true

          let loginBody = {username: loginForm.username, password: AesUtil.encrypt(loginForm.password)}
          this.$store.dispatch('user/login', loginBody).then(() => {
            this.loading = false
            this.$router.push(this.redirect)

          }).catch((err) => {

            this.loading = false
          })
        } else {
          return false
        }
      })
    }
  }
}
