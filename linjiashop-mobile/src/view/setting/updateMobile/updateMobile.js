import userApi from '@/api/user'
import {Button, Cell, CellGroup, Col, Field, NavBar, Row, Toast} from 'vant'
import store from '@/store'
import util from '@/utils/util'
import storage from '@/utils/storage'
export default {
    components: {
        [Row.name]: Row,
        [Col.name]: Col,
        [Button.name]: Button,
        [Cell.name]: Cell,
        [CellGroup.name]: CellGroup,
        [NavBar.name]: NavBar,
        [Field.name]: Field,
        [Toast.name]: Toast
    },
    data() {
        return {
            oldMobile: '',
            oldMobileSmsCode: '',
            showOldMobileSmsCode: false,
            hasSendSms: false,
            second: 60,
            showSendBtn: true,
            showConfirmBtn: false
        }
    },
    mounted() {
        this.init()
    },
    methods: {
        init() {
            const user = storage.getUser()
            this.oldMobile = util.formatMobile(user.mobile)
        },
        onClickLeft() {
            this.$router.go(-1)
        },
        sendSmsCode() {
            this.showSendBtn = false
            this.showConfirmBtn = true
            this.hasSendSms = true
            this.showOldMobileSmsCode = true
            userApi.sendSmsCode(this.oldMobile).then( response => {
                this.setTimeOut()
                const smsCode = response.data
                Toast('提示：测试阶段不发送短信验证码：'+smsCode)
            })

        },
        setTimeOut() {
            let timer = setTimeout(() => {
                this.setTimeOut()
                if (this.second > 0) {
                    this.second--
                }
            }, 1000)
            if (this.second <= 0) {
                this.hasSendSms = false
                this.second = 60
                clearTimeout(timer)
            }
        },
        confirmOldMobileSmsCode() {
            Toast('敬请期待')
        },
        submit() {
            Toast('敬请期待')
            return
            if (!this.userName || this.userName === '') {
                Toast('请输入姓名')
                return
            }
            userApi.updateUserName(this.userName).then(response => {
                store.dispatch('app/toggleUser', response.data)
                this.$router.go(-1)
            })
        }
    }
}
