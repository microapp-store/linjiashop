import { getAllCategories } from '@/api/category'
import { queryGoods } from '@/api/goods'
import store from '@/store'
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
            images: [{
                link: '/#/user', path:
                    './img/banner.jpg'
            },
                {
                    link: 'http://www.baidu.com', path:
                        './img/banner.jpg'
                }
            ],
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
            let category = store.state.app.category
            console.log('category',category)
            if(!category ||category.length==0 ){
                let platform = navigator.platform
                store.dispatch('app/toggleDevice',platform)
                getAllCategories().then(response => {
                    this.navList = response.data
                    store.dispatch('app/toggleCategory',response.data)
                    this.getGoods(response.data[0].id)
                }).catch((err) => {
                    Toast(err);
                })
            }else{
                this.navList = category
                this.getGoods(category[0].id)
            }
        },
        getGoods(idCategory) {
          this.listQuery['idCategory'] = idCategory
          queryGoods(this.listQuery).then(response => {
              console.log('goods',response.data)
              let list = response.data.records

              for(var index in  list){
                  const item = list[index]
                  item.img = '/dev-api/file/getImgStream?idFile=' + item.pic
              }
              this.goodsList = list

          }).catch((err) => {
              Toast(err)
          })
        },
        clickNav(index,title){
             this.$router.replace({path:'login'})
            return
            this.activeNav = index;
            let idCategory = this.navList[index].id
            this.getGoods(idCategory)
        },
        clickSwipe(index, p2) {
            console.log(index)
            console.log(p2)
        },
        viewGoodsDetail(id){
            console.log(id)
            this.$router.push({path:'/goods',query:{id:id}})
        }
    }
};
