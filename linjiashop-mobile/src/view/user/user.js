import userApi from '@/api/user'
import {Cell, CellGroup, Col, Icon, Row, Tabbar, TabbarItem, Toast} from 'vant';

export default {
    components: {
        [Row.name]: Row,
        [Col.name]: Col,
        [Icon.name]: Icon,
        [Cell.name]: Cell,
        [CellGroup.name]: CellGroup,
        [Tabbar.name]: Tabbar,
        [TabbarItem.name]: TabbarItem,
        [Toast.name]: Toast
    },
    data() {
        return {
            activeFooter: 3
        }
    },
    mounted() {
        this.init()
    },
    methods: {
        init() {
            userApi.getUserInfo().then(response => {
                const url = window.location.href
                if(url.indexOf('localhost')>-1 || url.indexOf('127.0.0.1')>-1) {
                    console.log('开发环境不获取openid')
                }else{
                    //todo 如果需要获取用户的微信openid，恢复下面代码
                    //const userAgent = window.navigator.userAgent.toLowerCase()
                    //使用微信访问本系统的时候获取微信openid，否则不获取
                    //if(userAgent.indexOf('micromessenger'>-1)) {
                    //    this.processOpenid();
                    //}
                }
            }).catch((err) => {
                this.$router.replace({path:'login',query:{redirect:'user'}})
            })
        },
        sorry(){
          Toast('敬请期待')
        },
        toOrder(status){
            this.$router.push({path:'order',query:{status:status}})
        },
        processOpenid(){
            let url  = window.location.href;
            if(url.indexOf('code')>-1){
                try {
                    const code = url.split('code=')[1].split("&")[0];
                    userApi.getWxOpenId({
                        "code" : code
                    }).then( res => {

                    }).catch( err => {
                        console.log(err.data.msg)
                    })
                }catch (e) {
                    console.log('获取code异常',e);
                }
            }else{
                this.redirectForCode();
            }
        },
        redirectForCode(){
            userApi.getWxSign({
                "url" : window.location.href
            }).then(res => {
                const rr = res.data;
                const redirectUrl = window.location.href;
                let param = 'appid='+ rr.appId
                param += '&response_type=code'
                param += '&scope=snsapi_base'
                param += '&redirect_uri=' + encodeURIComponent(redirectUrl)
                param += '&state=linjiashop#wechat_redirect'
                console.log('url:','https://open.weixin.qq.com/connect/oauth2/authorize?' + param)
                window.location.href = 'https://open.weixin.qq.com/connect/oauth2/authorize?' + param
            }).catch(err => {

            })
        }
    }
}
