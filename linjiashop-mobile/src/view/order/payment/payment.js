import payApi from '@/api/pay'
import {Button, Cell, CellGroup, NavBar, Radio, RadioGroup, Toast} from 'vant'
import wx from 'weixin-js-sdk'

export default {
    components: {
        [NavBar.name]: NavBar,
        [Cell.name]: Cell,
        [CellGroup.name]: CellGroup,
        [RadioGroup.name]: RadioGroup,
        [Radio.name]: Radio,
        [Button.name]: Button,
        [Toast.name]: Toast
    },

    data() {
        return {
            order: {},
            payType: 'wxpay'
        };
    },
    mounted() {
        this.init()
    },
    computed: {},
    methods: {
        init() {
            let orderSn = this.$route.query.orderSn
            let totalPrice = this.$route.query.totalPrice
            this.order ={orderSn:orderSn,totalPrice:totalPrice}
        },
        pay() {
            if(this.payType === 'wxpay'){
                this.wxPrepare()
            }else{
                Toast('支付宝支付对接中...')
            }
        },
        formatPrice(price) {
            return (price / 100).toFixed(2);
        },
        wxPrepare () {
            payApi.wxPrepare({orderSn:this.order.orderSn}).then(res => {
                // 存储微信支付数据data
                let data = res.data
                //函数为分装过得  (就是调用微信支付)
                this.wxPay(
                    {
                        appId: data.appId,
                        nonceStr: data.nonceStr,
                        package: data.packageValue,
                        paySign: data.paySign,
                        signType: data.signType,
                        timeStamp: data.timeStamp
                    }

                )
            }).catch(err =>{
                Toast(err)
            })

        },
        wxPay: function (data ) {
            //获取后台传入的数据
            let appId = data.appId
            let timestamp = data.timeStamp
            let nonceStr = data.nonceStr
            let signature = data.signature
            let packageValue = data.package
            let paySign = data.paySign
            let signType = data.signType
            const that = this
            //下面要发起微信支付了
            wx.config({
                debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
                appId: appId, // 必填，公众号的唯一标识
                timestamp: timestamp, // 必填，生成签名的时间戳
                nonceStr: nonceStr, // 必填，生成签名的随机串
                signature: signature, // 必填，签名，见附录1
                jsApiList: ['chooseWXPay'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
            })
            wx.ready(function () {
                wx.chooseWXPay({
                    timestamp: timestamp, // 支付签名时间戳，注意微信jssdk中的所有使用timestamp字段均为小写。但最新版的支付后台生成签名使用的timeStamp字段名需大写其中的S字符
                    nonceStr: nonceStr, // 支付签名随机串，不长于 32 位
                    package: packageValue, // 统一支付接口返回的prepay_id参数值，提交格式如：prepay_id=***）
                    signType: signType, // 签名方式，默认为'SHA1'，使用新版支付需传入'MD5'
                    paySign: paySign, // 支付签名
                    success: function (res) {
                        // 支付成功后的回调函数
                        that.$router.push('/payment/callback/'+that.order.orderSn)
                    },
                    fail: function (res) {
                        //失败回调函数
                        Toast('支付失败')
                    }
                })
            })
            wx.error(function (res) {
                console.log('微信支付失败',res)
                Toast('支付失败')
                // config信息验证失败会执行error函数，如签名过期导致验证失败，具体错误信息可以打开config的debug模式查看，也可以在返回的res参数中查看，对于SPA可以在这里更新签名。
                /*alert("config信息验证失败")*/
            })
        }

    }
};
