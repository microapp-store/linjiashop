import { getAllCategories } from '@/api/login'
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
            navList: [
                {text: '日用杂品'},
                {text: '水果生鲜'},
                {text: '干果零食'},
                {text: '家用电器'},

            ],
            images: [{
                link: '/#/user', path:
                    './img/banner.jpg'
            },
                {
                    link: 'http://www.baidu.com', path:
                        './img/banner.jpg'
                }
            ],
            goodsList: [{
                id:1,
                num: 21,
                price: "2.00",
                desc: "描述信息",
                title: "商品标题",
                img: "https://img.yzcdn.cn/vant/t-thirt.jpg",
                link:'/#/goods'
            },
                {
                    id:2,
                    num: 200,
                    price: "2.00",
                    desc: "描述信息",
                    title: "商品标题",
                    img: "https://img.yzcdn.cn/vant/t-thirt.jpg",
                    link:'/#/goods'
                },
                {
                    id:3,
                    num: 200,
                    price: "2.00",
                    desc: "描述信息",
                    title: "商品标题",
                    img: "https://img.yzcdn.cn/vant/t-thirt.jpg",
                    link:'/#/goods'
                },
                {
                    id:4,
                    num: 200,
                    price: "2.00",
                    desc: "描述信息",
                    title: "商品标题",
                    img: "https://img.yzcdn.cn/vant/t-thirt.jpg",
                    link:'/#/goods'
                }
            ],
            activeFooter: 0,
            activeNav: 0
        }
    },
    mounted() {
        this.init()
    },
    methods: {
        init() {
            getAllCategories().then(response => {
                console.log(response)


            }).catch((err) => {
                Toast(err);
            })
        },
        clickNav(index,title){
            this.$router.push({path:'category',query:{index:index,title:title}})
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
