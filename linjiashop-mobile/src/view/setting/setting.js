import loginApi from '@/api/login'
import userApi from '@/api/user'
import store from '@/store'
import storage  from '@/utils/storage'

import {Button, Cell, CellGroup, Col, Icon, Image, NavBar, Popup, Radio, RadioGroup, Row, Toast} from 'vant';

const baseApi = process.env.VUE_APP_BASE_API
export default {
    components: {
        [Row.name]: Row,
        [Button.name]: Button,
        [Col.name]: Col,
        [Icon.name]: Icon,
        [Cell.name]: Cell,
        [CellGroup.name]: CellGroup,
        [NavBar.name]: NavBar,
        [Toast.name]: Toast,
        [Image.name]: Image,
        [Popup.name]: Popup,
        [RadioGroup.name]: RadioGroup,
        [Radio.name]: Radio
    },
    store,
    data() {
        return {
            avatarUrl: 'http://microapp.gitee.io/linjiashop/logo.jpg',
            user: {},
            showGender: false
        }
    },
    mounted() {
        this.init()
    },
    methods: {
        init() {
            this.user =storage.getUser()
            if (this.user.avatar) {
                this.avatarUrl = baseApi + '/file/getImgStream?idFile=' + this.user.avatar
            }else if(this.user.wechatHeadImgUrl){
                this.avatarUrl = this.user.wechatHeadImgUrl
            }
        },
        onClickLeft() {
            this.$router.go(-1)
        },
        onChangeGender(name) {
            this.user.gender = name
            this.user.genderStr = (name === 'male' ? '男' : '女')
            userApi.updateGender(name).then(response => {
                store.dispatch('app/toggleUser', this.user)
                this.showGender = false
            })
        },
        onLogout() {
            loginApi.logout().then(response => {
                store.dispatch('app/toggleUser', {})
                store.dispatch('app/toggleToken', '')

                this.$router.push({path: '/index'})
            }).catch(err => {
            })
        }
    }
}
