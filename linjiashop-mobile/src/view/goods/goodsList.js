import category from '@/api/category'
import goods from '@/api/goods'
import store from '@/store'
import {
    Card,
    Cell,
    CellGroup,
    Col,
    Divider,
    Icon,
    Lazyload,
    List,
    Row,
    Swipe,
    SwipeItem,
    Tab,
    Tabbar,
    TabbarItem,
    Tabs,
    Toast
} from 'vant';

const baseApi = process.env.VUE_APP_BASE_API

export default {
    components: {
        [Row.name]: Row,
        [Col.name]: Col,
        [Icon.name]: Icon,
        [Cell.name]: Cell,
        [CellGroup.name]: CellGroup,
        [Tabbar.name]: Tabbar,
        [TabbarItem.name]: TabbarItem,
        [Swipe.name]: Swipe,
        [SwipeItem.name]: SwipeItem,
        [Tab.name]: Tab,
        [Tabs.name]: Tabs,
        [Card.name]: Card,
        [Toast.name]: Toast,
        [Divider.name]: Divider,
        [List.name]: List,
        Lazyload


    },
    data() {
        return {
            loading: false,
            finished: false,
            navList: [],
            banners: [],
            goodsList: [],
            activeFooter: 0,
            activeNav: undefined,
            total: 0,
            listQuery: {
                page: 1,
                limit: 50,
                idCategory: undefined
            },
            count: 0,
            isLoading: false
        }
    },
    mounted() {
        this.init()
    },
    methods: {
        init() {
            this.activeNav = this.$route.query.itemId
            let categoryData = store.state.app.category
            if (!categoryData || categoryData.length == 0) {
                let platform = navigator.platform
                store.dispatch('app/toggleDevice', platform)
                category.getAllCategories().then(response => {
                    let navList = response.data
                    navList.splice(0, 0, {name: '推荐', id: '0'})
                    this.navList = navList
                    store.dispatch('app/toggleCategory', navList)
                    this.getBanners(this.activeNav)
                }).catch((err) => {
                    console.log('err', err)
                    Toast.fail(err);
                })
            } else {
                this.navList = categoryData
                this.getBanners(this.activeNav)
            }
            this.getGoods(this.activeNav)
        },
        getBanners(categoryIndex) {
            let bannerList = null
            for (const i in this.navList) {
                if (this.navList[i].id == categoryIndex) {
                    bannerList = this.navList[i].bannerList
                }
            }

            let imgList = new Array()
            for (let i = 0; i < bannerList.length; i++) {
                let url = ''
                let page = bannerList[i].page
                if (page != '') {
                    if (page.indexOf('http') === 0) {
                        url = page
                    } else {
                        url = '#/' + bannerList[i].page
                    }
                }
                if (bannerList[i].param != '') {
                    const param = JSON.parse(bannerList[i].param)
                    for (const key in param) {
                        url += '/' + param[key]
                    }
                }
                imgList.push({
                    url: url,
                    path: baseApi + '/file/getImgStream?idFile=' + bannerList[i].idFile
                })
            }
            this.banners = imgList
        },
        getGoods(idCategory) {
            this.listQuery['idCategory'] = idCategory
            goods.queryGoods(this.listQuery).then(response => {
                let list = response.data.records
                this.total = response.data.total
                for (var index in  list) {
                    const item = list[index]
                    item.img = baseApi + '/file/getImgStream?idFile=' + item.pic
                }
                this.goodsList = list

            }).catch((err) => {
                Toast(err)
            })
        },
        loadMore() {
            this.loading = false
            this.finished = true
        },
        onClickNav(index, title) {
            if (index === '0') {
                this.$router.push({path: '/index'})
                return
            }
            this.activeNav = index;
            this.getBanners(index)
            this.getGoods(index)

        },
        clickSwipe(index, p2) {
            console.log(index)
            console.log(p2)
        },
        viewGoodsDetail(id) {

            this.$router.push({path: '/goods/' + id})
        },
        formatPrice(price) {
            return (price / 100).toFixed(2)
        }

    }
};
