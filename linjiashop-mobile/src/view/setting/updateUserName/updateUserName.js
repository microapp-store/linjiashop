import userApi from '@/api/user'
import {Button, Cell, CellGroup, Field, NavBar, Toast} from 'vant'
import store from '@/store'
import storage from '@/utils/storage'
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
            userName: ''
        }
    },
    mounted() {
        this.init()
    },
    methods: {
        init() {
            this.userName = storage.getUser().nickName
        },
        onClickLeft() {
            this.$router.go(-1)
        },
        submit() {
            if(!this.userName || this.userName === ''){
                Toast('请输入姓名')
                return
            }
            userApi.updateUserName(this.userName).then( response => {
                store.dispatch('app/toggleUser', response.data)
                this.$router.go(-1)
            })
        }
    }
}
