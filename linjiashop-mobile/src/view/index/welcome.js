import categoryApi from '@/api/category'
import goodsApi from '@/api/goods'
import topicApi from '@/api/topic'
import store from '@/store'
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
          // Toast('敬请期待')
        }

    }
};
