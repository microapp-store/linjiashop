import category from '@/api/category'
import goods from '@/api/goods'
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
    Swipe,
    SwipeItem,
    Tab,
    Tabbar,
    TabbarItem,
    Tabs,
    Toast
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
        [Swipe.name]: Swipe,
        [SwipeItem.name]: SwipeItem,
        [Tab.name]: Tab,
        [Tabs.name]: Tabs,
        [Card.name]: Card,
        [Toast.name]: Toast,
        Lazyload


    },
    data() {
        return {
            navList: [],
            banners: [],
            goodsList: [],
            activeFooter: 0,
            activeNav: 0,
            listQuery: {
                page: 1,
                limit: 20,
                idCategory: undefined
            }
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
                category.getAllCategories().then(response => {
                    this.navList = response.data
                    this.getBanners(0)

                    store.dispatch('app/toggleCategory', response.data)
                    this.getGoods(response.data[0].id)
                }).catch((err) => {
                    Toast.fail(err);
                })
            } else {
                this.navList = categoryData
                this.getBanners(0)
                this.getGoods(categoryData[0].id)
            }

        },
        getBanners(categoryIndex){
            let bannerList = this.navList[categoryIndex].bannerList
            let imgList = new Array()
            for(let i=0;i<bannerList.length;i++){
                let url = ''
                let page = bannerList[i].page
                if(page!=''){
                    if(page.indexOf('http') === 0){
                        url = page
                    }else {
                        url = '#/' + bannerList[i].page
                    }
                }
                if(bannerList[i].param !=''){
                    const param = JSON.parse(bannerList[i].param)
                    for(const key in param){
                        url +='/'+param[key]
                    }
                }
                imgList.push({
                    url:url,
                    path:baseApi+'/file/getImgStream?idFile=' + bannerList[i].idFile
                })
            }
            this.banners = imgList
        },
        getGoods(idCategory) {
            this.listQuery['idCategory'] = idCategory
            goods.queryGoods(this.listQuery).then(response => {
                let list = response.data.records
                for (var index in  list) {
                    const item = list[index]
                    item.img = baseApi+'/file/getImgStream?idFile=' + item.pic
                }
                this.goodsList = list

            }).catch((err) => {
                Toast(err)
            })
        },
        clickNav(index, title) {
            this.activeNav = index;
            let idCategory = this.navList[index].id
            this.getBanners(index)
            this.getGoods(idCategory)

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

    }
};
