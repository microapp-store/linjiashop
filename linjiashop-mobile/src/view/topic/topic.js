import topicApi from '@/api/topic'
import {Cell, CellGroup, Row, Col, Icon, Tabbar, TabbarItem, Tag, Toast, Image} from 'vant';

const baseApi = process.env.VUE_APP_BASE_API

export default {
    components: {
        [Tag.name]: Tag,
        [Row.name]: Row,
        [Col.name]: Col,
        [Icon.name]: Icon,
        [Cell.name]: Cell,
        [CellGroup.name]: CellGroup,
        [Tabbar.name]: Tabbar,
        [TabbarItem.name]: TabbarItem,
        [Image.name]: Image
    },

    data() {
        return {
            activeFooter: 0,
            topic: {title:'',article:{content:''}},
            goodsList:[]
        };
    },
    created() {
        console.log('init')
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
            topicApi.get(id).then(response => {
                this.topic = response.data
                let goodsList = response.data.goodsList
                for(const i in goodsList){
                    goodsList[i].img = baseApi + '/file/getImgStream?idFile=' +goodsList[i].pic
                }
                this.goodsList = goodsList
            }).catch((err) => {
                console.log('err', err)
                Toast(err)
            })
        },
        toGoods(id){
            this.$router.push({path: '/goods/'+id})
        },
        sorry() {
            Toast('敬请期待...')
        }
    }
};
