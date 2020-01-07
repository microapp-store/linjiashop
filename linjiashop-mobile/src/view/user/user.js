import userApi from '@/api/user'
import {Cell, CellGroup, Col, Icon, Row, Tabbar, TabbarItem, Toast} from 'vant';

export default {
    components: {
        [Row.name]: Row,
        [Col.name]: Col,
        [Icon.name]: Icon,
        [Cell.name]: Cell,
        [CellGroup.name]: CellGroup,
        [Tabbar.name]: Tabbar,
        [TabbarItem.name]: TabbarItem,
        [Toast.name]: Toast
    },
    data() {
        return {
            activeFooter: 3
        }
    },
    mounted() {
        this.init()
    },
    methods: {
        init() {
            userApi.getUserInfo().then(response => {

            }).catch((err) => {
                this.$router.replace({path:'login',query:{redirect:'user'}})
            })
        },
        sorry(){
          Toast('敬请期待')
        },
        toOrder(status){
            this.$router.push({path:'order',query:{status:status}})
        }
    }
}
