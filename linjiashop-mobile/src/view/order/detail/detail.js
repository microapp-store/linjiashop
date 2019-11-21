import order  from '@/api/orders'
import { Cell, CellGroup,Row, Col,Checkbox, CheckboxGroup, Card, SubmitBar, Toast, NavBar, Tab,Tabs,Tabbar, TabbarItem,Panel,List,Button    } from 'vant';
const baseApi = process.env.VUE_APP_BASE_API

export default {
    components: {
        [Cell.name]: Cell,
        [CellGroup.name]: CellGroup,
        [Row.name]: Row,
        [Col.name]: Col,
        [Card.name]: Card,
        [Checkbox.name]: Checkbox,
        [SubmitBar.name]: SubmitBar,
        [CheckboxGroup.name]: CheckboxGroup,
        [NavBar.name]: NavBar,
        [Tab.name]:Tab,
        [Tabbar.name]: Tabbar,
        [Tabs.name]: Tabs,
        [TabbarItem.name]: TabbarItem,
        [Panel.name]: Panel,
        [List.name]:List,
        [Button.name]:Button

    },

    data() {
        return {
            activeFooter: 3,
            title:'',
            imgUrl:baseApi+'/file/getImgStream?idFile=',
            order:{orderSn:'',address:{name:''}}
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
            order.get(this.order.orderSn).then( response => {
                this.order = response.data
            })
        },
        formatPrice(price) {
            return (price / 100).toFixed(2);
        },
        toGoods(id){
            this.$router.push({path: '/goods/'+id})
        },
        confirmReceive(){
            Toast('敬请期待')
            order.confirm(this.order.orderSn).then( response => {
                this.order = response.data
            })
        },
        payment(){
            this.$router.push({path:'/payment',query:{orderSn:this.order.orderSn,totalPrice:this.order.totalPrice}})
        },
        contact(){
          Toast('敬请期待')
        },
        onClickLeft(){
            this.$router.go(-1)
        },
    }
}
