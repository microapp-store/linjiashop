import loginApi from '@/api/login'
import store from '@/store'
import storage  from '@/utils/storage'
import { Row, Col, Icon, Cell, CellGroup,Field,Button,Toast,
    Tabbar,
    TabbarItem,Dialog} from 'vant';

export default {
    components: {
        [Row.name]: Row,
        [Col.name]: Col,
        [Icon.name]: Icon,
        [Cell.name]: Cell,
        [CellGroup.name]: CellGroup,
        [Tabbar.name]: Tabbar,
        [TabbarItem.name]: TabbarItem,
        [Field.name]: Field,
        [Button.name]: Button,
        [Toast.name]: Toast,
        [Dialog.name]: Dialog
    },
    data() {
        return {
            mobile:'',
            smsCode:'',
            password:'',
            activeFooter:3,
            show1:false,
            show2:true,
            redirect:'',
            hasSendSms: false,
            second: 60
        }
    },
    mounted(){
      this.init()
    },
    methods:{
        init(){
            if(store.state.app.user.nickName){
                this.$router.push({path: '/index'})
                return
            }
            if(this.$route.query.redirect){
                this.redirect = this.$route.query.redirect
            }
        },
        toLoginByPassword(){
            this.show1 = false;
            this.show2 = true;
        },
        toRegister(){
            this.show2 = false;
            this.show1 = true;
        },
        resetForLogin(){
            //登录成功后初始化数据状态
            //清空缓存中的地址信息
            storage.set('chosenAddressId','')
        },
        loginOrRegister(){
            loginApi.loginOrReg(this.mobile,this.smsCode).then( response=> {
                store.dispatch('app/toggleToken',response.data.token)
                store.dispatch('app/toggleUser',response.data.user)
                if(response.data.initPassword){
                    Toast({duration:8000,message:'欢迎新用户，请谨慎保管您的初始密码：'+response.data.initPassword})
                }
                this.resetForLogin()
                if(this.redirect){
                    this.$router.push({path: this.redirect})
                }else {
                    this.$router.push({path: '/index'})
                }
            }).then( (err) => {
                // Toast.fail(err)
            })
        },
        loginByPass(){
            loginApi.loginByPassword(this.mobile,this.password).then( response=> {
                store.dispatch('app/toggleToken',response.data.token)
                store.dispatch('app/toggleUser',response.data.user)
                this.resetForLogin()
                if(this.redirect){
                   this.$router.push({path: this.redirect})
                }else {
                   this.$router.push({path: '/index'})
                }
            }).catch( err=>{
                Toast(err)
            })
        },
        sendSms(){
            this.hasSendSms = true
            loginApi.sendSmsCode(this.mobile).then( response => {
                this.setTimeOut()
            })
        },
        setTimeOut () {
            let timer = setTimeout(() => {
                this.setTimeOut()
                if(this.second > 0) {
                    this.second--
                }
            }, 1000)
            if(this.second <= 0) {
                this.hasSendSms = false
                this.second = 60
                clearTimeout(timer)
            }
        }
    }
};
