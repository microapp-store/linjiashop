import goods from '@/api/goods'
import favoriteApi from '@/api/favorite'
const baseApi = process.env.VUE_APP_BASE_API
import {
    Checkbox,
    SubmitBar,
    CheckboxGroup,
    NavBar,
    Divider,
    List,
    Toast,
    Cell,
    CellGroup,
    Card,
    Col,
    Icon,
    Lazyload,
    Row,
    Tab,
    Tabbar,
    TabbarItem,
    Tabs,
    Search
} from 'vant';

export default {
    components: {
        [Checkbox.name]: Checkbox,
        [SubmitBar.name]: SubmitBar,
        [CheckboxGroup.name]: CheckboxGroup,
        [NavBar.name]: NavBar,
        [Divider.name]: Divider,
        [List.name]: List,
        [Toast.name]:Toast,
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
        [Search.name]:Search,
        Lazyload


    },
    data() {
        return {
            showEdit:false,
            rightText:'管理',
            checkedFavorites:[],
            checkedAllFavorites:[],
            checkedAll: false,
            loading:false,
            loadingGoods:false,
            showHot:false,
            finished:false,
            favoriteList:[],
            goodsList: [],
            activeFooter: 1,
            listQuery: {
                page: 1,
                limit: 20,
                key:''
            },
        }
    },
    mounted(){
        this.init()
    },
    methods: {
        init(){
            this.searchFavorites()
        },
        searchFavorites(){
            favoriteApi.list().then( response =>{
                let list = response.data
                this.checkedAllFavorites = []
                this.checkedFavorites = []
                for(const index in  list){
                    const item = list[index]
                    item.goods.img = baseApi+'/file/getImgStream?idFile=' + item.goods.pic
                    this.checkedAllFavorites.push(item.id + '')
                }
                this.favoriteList = list

            }).catch( (err) => {
                Toast.fail(err)
            })
        },
        onClickLeft(){
            this.$router.go(-1)
        },
        onClickRight(){
            this.showEdit = !this.showEdit
            this.rightText = this.rightText==='管理'?'完成':'管理'
        },
        checkAll(){
            if (this.checkedFavorites.length === this.favoriteList.length) {
                this.checkedAllFavorites = this.checkedFavorites
                this.checkedFavorites = []
            } else {
                this.checkedFavorites = this.checkedAllFavorites
            }
        },
        submit(){
            favoriteApi.dislikeBatch( this.checkedFavorites).then( response =>{
                this.searchFavorites()
            })
        },
        viewGoodsDetail(id){
            this.$router.push({path:'/goods/'+id})
        },
        formatPrice(price) {
            return (price / 100).toFixed(2)
        }
    }
}
