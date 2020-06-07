import categoryApi from '@/api/category'
import goodsApi from '@/api/goods'
import topicApi from '@/api/topic'
import wechatApi from '@/api/wechat'
import store from '@/store'
import storage  from '@/utils/storage'
const baseApi = process.env.VUE_APP_BASE_API
import {
    Card,
    Cell,
    CellGroup,
    Col,
    Icon,
    Lazyload,
    Row,
    Tab,
    Tabbar,
    TabbarItem,
    Tabs,
    Toast,
    Divider,
    Panel,
    Image,
    List
} from 'vant';

export default {
    components: {
        [Row.name]: Row,
        [Col.name]: Col,
        [Icon.name]: Icon,
        [Cell.name]: Cell,
        [CellGroup.name]: CellGroup,
        [Tabbar.name]: Tabbar,
        [TabbarItem.name]: TabbarItem,
        [Tab.name]: Tab,
        [Tabs.name]: Tabs,
        [Card.name]: Card,
        [Toast.name]: Toast,
        [Divider.name]: Divider,
        [List.name]: List,
        [Panel.name]: Panel,
        [Image.name]: Image,
        Lazyload


    },
    data() {
        return {
            loading: false,
            finished: false,
            navList: [],
            banners: [],
            hotList: [],
            newList: [],
            activeFooter: 0,
            activeNav: 0,
            total:0,
            listQuery: {
                page: 1,
                limit: 50,
                idCategory: undefined
            },
            count: 0,
            isLoading: false,
            topicList: []
        }
    },
    mounted() {
        this.init()

    },
    methods: {
        init() {
            //首页暂不自动获取微信信息
            // const user = storage.getUser()
            // this.isLogin = user.nickName
            // if(!this.isLogin) {
            //     const url = window.location.href
            //     if (url.indexOf('localhost') > -1 || url.indexOf('127.0.0.1') > -1) {
            //         console.log('开发环境不获取openid')
            //     } else {
            //         const userAgent = window.navigator.userAgent.toLowerCase()
            //         //使用微信访问本系统的时候获取微信openid，否则不获取
            //         if (userAgent.indexOf('micromessenger') > -1) {
            //             this.processOpenid()
            //         }
            //     }
            // }
            let categoryData = store.state.app.category
            if (!categoryData || categoryData.length == 0) {
                let platform = navigator.platform
                store.dispatch('app/toggleDevice', platform)
                categoryApi.getAllCategories().then(response => {
                    let navList = response.data
                    navList.splice(0,0,{name:'推荐',id:'0'})
                    this.navList = navList
                    store.dispatch('app/toggleCategory', navList)

                }).catch((err) => {
                    Toast.fail(err);
                })
            } else {
                this.navList = categoryData
            }
            this.queryGoods()
            this.queryTopic()

        },
        queryGoods() {
            goodsApi.searchHot().then(response => {
                let list = response.data
                for (const index in  list) {
                    const item = list[index]
                    item.img = baseApi+'/file/getImgStream?idFile=' + item.pic
                }
                this.hotList = list

            }).catch((err) => {
                Toast(err)
            })
            goodsApi.searchNew().then(response => {
                let list = response.data
                for (const index in  list) {
                    const item = list[index]
                    item.img = baseApi+'/file/getImgStream?idFile=' + item.pic
                }
                this.newList = list

            }).catch((err) => {
                Toast(err)
            })
        },
        queryTopic(){
            topicApi.queryAll().then(response => {
                let list = response.data
                for (const index in  list) {
                    const item = list[index]
                    item.img = baseApi+'/file/getImgStream?idFile=' + item.article.img
                }
                this.topicList = list
                console.log('topicList',this.topicList)
            })
        },
        clickNav(index, title) {
            this.activeNav = index;
            this.$router.push({path: '/list?itemId='+index})

        },
        clickSwipe(index, p2) {
            console.log(index)
            console.log(p2)
        },
        viewGoodsDetail(id) {
            this.$router.push({path: '/goods/'+id})
        },
        formatPrice(price) {
            return (price / 100).toFixed(2)
        },
        toTopic(id) {
            this.$router.push({path: '/topic/'+id})
        },
        processOpenid() {
            let url = window.location.href;
            if (url.indexOf('code') > -1) {
                const code = url.split('code=')[1].split("&")[0];
                wechatApi.getWxOpenId({
                    "code": code
                }).then(res => {
                    store.dispatch('app/toggleToken',res.data.token)
                    store.dispatch('app/toggleUser',res.data.user)
                    storage.set('chosenAddressId','')
                })

            } else {
                this.redirectForCode();
            }
        },
        redirectForCode() {
            wechatApi.getWxSign({
                "url": window.location.href
            }).then(res => {
                const rr = res.data;
                const redirectUrl = window.location.href;
                let param = 'appid=' + rr.appId
                param += '&response_type=code'
                param += '&scope=snsapi_base'
                param += '&redirect_uri=' + encodeURIComponent(redirectUrl)
                param += '&state=linjiashop#wechat_redirect'
                console.log('url:', 'https://open.weixin.qq.com/connect/oauth2/authorize?' + param)
                window.location.href = 'https://open.weixin.qq.com/connect/oauth2/authorize?' + param
            }).catch(err => {

            })
        }

    }
};
