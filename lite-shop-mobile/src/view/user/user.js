import {getUserInfo} from '@/api/user'
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
            getUserInfo().then(response => {

            }).catch((err) => {
                console.log('err',err.response)
                this.$router.replace({path:'login',query:{redirect:'user'}})
            })
        }
    }
}
