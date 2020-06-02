 import order  from '@/api/orders'
import { Row, Col,Checkbox, CheckboxGroup, Card, SubmitBar, Toast, NavBar, Tab,Tabs,Tabbar, TabbarItem,Panel,List,Button    } from 'vant';
 const baseApi = process.env.VUE_APP_BASE_API

export default {
    components: {
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
            navList: [
                {name:'全部',id:0},
                {name:'待付款',id:1},
                {name:'待发货',id:2},
                {name:'已发货',id:3},
                {name:'已完成',id:4},
            ],
            orderList:[],
            activeNav: 0,
            activeFooter: 3,
            checkedGoods: ['1'],
            goods: [ ],
            imgUrl:baseApi+'/file/getImgStream?idFile=',
            listQuery: {
                page: 1,
                limit: 20,
                status: 0
            },
            loading: false,
            finished: false
        };
    },
    mounted(){
        if(this.$route.query){
            let status = this.$route.query.status
            //使用状态减一作为导航栏的序号，如果状态值改变，则不能使用该方法
            this.activeNav = parseInt(status)-1
            this.listQuery.status = status
        }
      this.init()
    },
    computed: {
        submitBarText() {
            const count = this.checkedGoods.length;
            return '结算' + (count ? `(${count})` : '');
        },
    },

    methods: {
        init(){
            this.getData()

        },
        getData(){
            order.getOrders(this.listQuery).then( response => {
                let orderList = response.data.records
                for(let index in  orderList){
                    let orders = orderList[index]
                    orders.title=''+orders.createTime
                    orders.descript = ''+orders.orderSn
                }
                this.orderList = orderList
                this.loading = false
                this.finished= (response.data.total === 0 || (response.data.current === response.data.pages))
            }).catch( (err) => {

            })
        },
        formatPrice(price) {
            return (price / 100).toFixed(2);
        },
        onClickLeft(){
            this.$router.go(-1)
        },
        clickNav(index,title){
            this.activeNav = index;
            this.listQuery.status = this.navList[index].id
            this.getData()
        },
        toOrderDetail(orderSn){
            this.$router.push('/order/detail/'+orderSn)
        },
        toGoods(id){
            this.$router.push({path: '/goods/'+id})
        },
        cancelOrder(orderInfo){
            order.remove(orderInfo.orderSn).then( response => {
                this.getData()
            })
        },
        viewExpressInfo(orderSn){
            this.$router.push('/order/expressInfo/'+orderSn)
        },
        getHandlerText(statusName){
            if(statusName==='已发货'){
                return '确认收货'
            }
            if(statusName==='待付款') {
                return '立即付款'
            }
            return statusName
        },
        handleOrder(orderInfo){
            if(orderInfo.statusName==='待付款'){
                this.$router.push({path:'payment',query:{orderSn:orderInfo.orderSn,totalPrice:orderInfo.totalPrice}})
            }
            if(orderInfo.statusName==='已发货'){
                order.confirm(orderInfo.orderSn).then( response => {
                    Toast('成功收货')
                    this.getData()
                })
            }
        }
    }
}
