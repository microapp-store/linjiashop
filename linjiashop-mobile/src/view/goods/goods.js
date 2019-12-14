import goods from '@/api/goods'
import cart from '@/api/cart'
import {
    Cell,
    CellGroup,
    Col,
    GoodsAction,
    GoodsActionButton,
    GoodsActionIcon,
    Icon,
    Sku,
    Swipe,
    SwipeItem,
    Tabbar,
    TabbarItem,
    Tag,
    Toast
} from 'vant';

const baseApi = process.env.VUE_APP_BASE_API

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
        [TabbarItem.name]: TabbarItem,
        [Sku.name]: Sku
    },

    data() {
        return {
            showSku:false,
            sku: {
                // 所有sku规格类目与其值的从属关系，比如商品有颜色和尺码两大类规格，颜色下面又有红色和蓝色两个规格值。
                // 可以理解为一个商品可以有多个规格类目，一个规格类目下可以有多个规格值。
                tree: [
                    {
                        k: '颜色', // skuKeyName：规格类目名称
                        v: [
                            {
                                id: '30349', // skuValueId：规格值 id
                                name: '红色' // skuValueName：规格值名称
                            },
                            {
                                id: '1215',
                                name: '蓝色'
                            }
                        ],
                        k_s: 's1' // skuKeyStr：sku 组合列表（下方 list）中当前类目对应的 key 值，value 值会是从属于当前类目的一个规格值 id
                    }
                ],
                // 所有 sku 的组合列表，比如红色、M 码为一个 sku 组合，红色、S 码为另一个组合
                list: [
                    {
                        id: 2259, // skuId，下单时后端需要
                        price: 100, // 价格（单位分）
                        s1: '1215', // 规格类目 k_s 为 s1 的对应规格值 id
                        s2: '1193', // 规格类目 k_s 为 s2 的对应规格值 id
                        s3: '0', // 最多包含3个规格值，为0表示不存在该规格
                        stock_num: 110 // 当前 sku 组合对应的库存
                    }
                ],
                price: '1.00', // 默认价格（单位元）
                stock_num: 227, // 商品总库存
                collection_id: 2261, // 无规格商品 skuId 取 collection_id，否则取所选 sku 组合对应的 id
                none_sku: false, // 是否无规格商品
                hide_stock: false // 是否隐藏剩余库存
            },
            goods: {
                name: '',
                price: 0,
                express: '免运费',
                remain: 0,
                thumb: []
            }
        };
    },
    created() {
        this.init()
    },
    computed: {
        cartCount() {
            return 2
        }
    },
    methods: {
        init() {
            let id = this.$route.params.id
            goods.getGoods(id).then(response => {
                let goods = response.data.goods
                let sku = response.data.sku
                sku.price = (sku.price / 100).toFixed(2)
                this.sku = sku
                goods.thumb = new Array()
                goods.picture = baseApi + '/file/getImgStream?idFile=' +goods.pic
                const gallery = goods.gallery.split(',')
                for (var index in gallery) {
                    goods.thumb.push(baseApi + '/file/getImgStream?idFile=' + gallery[index])
                }
                this.goods = goods
            }).catch((err) => {
                console.log('err',err)
                Toast(err)
            })
        },
        toHome() {
            this.$router.push('/index')
        },
        formatPrice() {
            return '¥' + (this.goods.price / 100).toFixed(2)
        },

        goToCart() {
            this.$router.push('/cart');
        },
        addCart() {
            this.showSku = true
        },
        buy() {
            this.showSku = true
        },
        sorry() {
            Toast('敬请期待...')
        },
        onBuyClicked(skuData) {
            let cartData = {idGoods:skuData.goodsId,idSku:this.sku.none_sku?'':skuData.selectedSkuComb.id,count:skuData.selectedNum}
            cart.add(cartData).then( response => {
                this.$router.push('/cart');
                this.showSku = false
            }).catch( (err) => {
                Toast.fail(err)
            })
        },
        onAddCartClicked(skuData) {
            let cartData = {idGoods:skuData.goodsId,idSku:this.sku.none_sku?'':skuData.selectedSkuComb.id,count:skuData.selectedNum}
            cart.add(cartData).then( response => {
                Toast.success('已加入到购物车')
                this.showSku = false
                // this.$router.push('/cart');
            }).catch( (err) => {
                Toast.fail(err)
            })

        }
    }
};
