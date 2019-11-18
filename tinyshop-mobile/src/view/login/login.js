import {loginByPassword,loginOrReg } from '@/api/login'
import store from '@/store'
import { Row, Col, Icon, Cell, CellGroup,Field,Button,Toast,
    Tabbar,
    TabbarItem} from 'vant';

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
        [Toast.name]: Toast
    },
    data() {
        return {
            mobile:'',
            smsCode:'',
            password:'',
            activeFooter:3,
            show1:false,
            show2:true,
            redirect:''
        }
    },
    mounted(){
      this.init()
    },
    methods:{
        init(){
            if(this.$route.query.redirect){
                this.redirect = this.$route.query.redirect
                console.log('redirect',this.redirect)
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
        loginOrRegister(){

        },
        loginByPass(){
            loginByPassword(this.mobile,this.password).then( response=> {
                store.dispatch('app/toggleToken',response.data.token)
                store.dispatch('app/toggleUser',response.data.user)
                if(this.redirect){
                    this.$router.push({path: this.redirect})
                }else {
                    this.$router.push({path: '/index'})
                }
            }).then( (err) => {
                // Toast.fail(err)
            })
        }
    }
};
