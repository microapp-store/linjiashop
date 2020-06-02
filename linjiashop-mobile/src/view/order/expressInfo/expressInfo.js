import order  from '@/api/orders'
import { Cell, CellGroup,Row, Col,Step, Steps, NavBar, Tab,Tabs,Tabbar, TabbarItem,Button    } from 'vant';
const baseApi = process.env.VUE_APP_BASE_API

export default {
    components: {
        [Cell.name]: Cell,
        [CellGroup.name]: CellGroup,
        [Row.name]: Row,
        [Col.name]: Col,
        [Step.name]: Step,
        [Steps.name]: Steps,
        [NavBar.name]: NavBar,
        [Tab.name]:Tab,
        [Tabbar.name]: Tabbar,
        [Tabs.name]: Tabs,
        [TabbarItem.name]: TabbarItem,
        [Button.name]:Button

    },

    data() {
        return {
            activeFooter: 3,
            title:'',
            order:{orderSn:'',address:{name:''}},
            expressInfo:{}
        };
    },
    mounted(){
        this.init()
    },
    methods: {
        init(){
            this.order.orderSn = this.$route.params.orderSn
            this.getData()

        },
        getData(){
            order.getExpressInfo(this.order.orderSn).then( response => {
                this.order = response.data.order
                this.expressInfo = response.data.expressInfo
                this.title = this.order.orderSn+'('+this.order.statusName+')'
            })
        },
        onClickLeft(){
            this.$router.go(-1)
        },
    }
}
