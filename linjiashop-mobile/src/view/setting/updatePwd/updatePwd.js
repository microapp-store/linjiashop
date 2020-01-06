import userApi from '@/api/user'
import loginApi from '@/api/login'
import {Button, Cell, CellGroup, Field, NavBar, Toast} from 'vant'
import store from '@/store'

export default {
    components: {
        [Button.name]: Button,
        [Cell.name]: Cell,
        [CellGroup.name]: CellGroup,
        [NavBar.name]: NavBar,
        [Field.name]: Field,
        [Toast.name]: Toast
    },
    data() {
        return {
            oldPwd: '',
            password: '',
            rePassword: ''

        }
    },
    mounted() {
        this.init()
    },
    methods: {
        init() {
            this.userName =store.state.app.user.nickName
        },
        onClickLeft() {
            this.$router.go(-1)
        },
        submit() {
            if(!this.oldPwd || !this.password === '' || !this.rePassword){
                Toast('请输入必填项')
                return
            }
            if(this.password !== this.rePassword){
                Toast('新密码前后不一致')
                return
            }
            userApi.updatePassword(this.oldPwd,this.password,this.rePassword).then( response => {
                loginApi.logout().then(response => {
                    store.dispatch('app/toggleUser', {})
                    store.dispatch('app/toggleToken', '')
                    this.$router.push({path: '/login'})
                }).catch(err => {
                })
            }).catch( err=>{
                console.log('err',err)
                Toast(err)
            })
        }
    }
}
