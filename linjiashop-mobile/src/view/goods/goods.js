import goods from '@/api/goods'
import cart from '@/api/cart'
const baseApi = process.env.VUE_APP_BASE_API
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
                name: '',
                price: 0,
                express: '免运费',
                remain: 0,
                thumb: []
            }
        };
    },
    created(){
        this.init()
    },
    computed:{
        cartCount(){
            return 2
        }
    },
    methods: {
        init(){
            let id = this.$route.params.id
            goods.getGoods(id).then( response => {
                let goods = response.data
                goods.thumb = new Array()
                const gallery = response.data.gallery.split(',')
                for(var index in gallery){
                     goods.thumb.push(baseApi+'/file/getImgStream?idFile=' + gallery[index])
                }
                this.goods = goods
            }).catch((err) =>{
                Toast(err)
            })
        },
        toHome(){
            this.$router.push('/index')
        },
        formatPrice() {
            return '¥' + (this.goods.price / 100).toFixed(2)
        },

        goToCart() {
            this.$router.push('/cart');
        },
        addCart() {
            cart.add(this.goods.id,1).then( response => {
                Toast('已经加入到购物车')
            }).catch((err) => {
                Toast.fail(err)
            })

        },
        buy() {
            cart.add(this.goods.id,1).then( response => {
                this.$router.push('/cart');
            }).catch( (err) => {
                Toast.fail(err)
            })
        },
        sorry(){
            Toast('敬请期待...')
        }
    }
};
