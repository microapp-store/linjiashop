import { getGoods } from '@/api/goods'
import {
    Tag,
    Col,
    Icon,
    Cell,
    CellGroup,
    Swipe,
    Toast,
    SwipeItem,
    GoodsAction,
    GoodsActionIcon,
    GoodsActionButton,
    Tabbar,
    TabbarItem
} from 'vant';

export default {
    components: {
        [Tag.name]: Tag,
        [Col.name]: Col,
        [Icon.name]: Icon,
        [Cell.name]: Cell,
        [CellGroup.name]: CellGroup,
        [Swipe.name]: Swipe,
        [SwipeItem.name]: SwipeItem,
        [GoodsAction.name]: GoodsAction,
        [GoodsActionIcon.name]: GoodsActionIcon,
        [GoodsActionButton.name]: GoodsActionButton,
        [Tabbar.name]: Tabbar,
        [TabbarItem.name]: TabbarItem
    },

    data() {
        return {
            goods: {
                name: '美国伽力果（约680g/3个）',
                price: 2680,
                express: '免运费',
                remain: 19,
                thumb: [
                    // 'https://img.yzcdn.cn/public_files/2017/10/24/e5a5a02309a41f9f5def56684808d9ae.jpeg',
                    // 'https://img.yzcdn.cn/public_files/2017/10/24/1791ba14088f9c2be8c610d0a6cc0f93.jpeg'
                ]
            }
        };
    },
    created(){
        this.init()
    },
    methods: {
        init(){
            let id = this.$route.query.id
            getGoods(id).then( response => {
                let goods = response.data
                goods.thumb = new Array()
                const gallery = response.data.gallery.split(',')
                for(var index in gallery){
                     goods.thumb.push('/dev-api/file/getImgStream?idFile=' + gallery[index])
                }
                this.goods = goods
            }).catch((err) =>{
                Toast(err)
            })
        },
        toHome(){
            this.$router.push('index')
        },
        formatPrice() {
            return '¥' + (this.goods.price / 100).toFixed(2)
        },

        goToCart() {
            this.$router.push('cart');
        },
        addCart() {
            this.$router.push({path:'cart',query:{type:'add',id:this.goods.id}});
        },
        sorry() {
            Toast('开发中~');
        }
    }
};
