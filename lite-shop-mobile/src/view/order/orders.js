import { queryByUser } from '@/api/cart'
import {getOrders } from '@/api/orders'
import { Checkbox, CheckboxGroup, Card, SubmitBar, Toast, NavBar, Tab,Tabs,Tabbar, TabbarItem,Panel   } from 'vant';

export default {
    components: {
        [Card.name]: Card,
        [Checkbox.name]: Checkbox,
        [SubmitBar.name]: SubmitBar,
        [CheckboxGroup.name]: CheckboxGroup,
        [NavBar.name]: NavBar,
        [Tab.name]:Tab,
        [Tabbar.name]: Tabbar,
        [Tabs.name]: Tabs,
        [TabbarItem.name]: TabbarItem,
        [Panel.name]: Panel

    },

    data() {
        return {
            navList: [
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
            listQuery: {
                page: 1,
                limit: 20,
                status: undefined
            }
        };
    },
    mounted(){
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

            if(this.$router.query){
                let id = this.$router.query.id
                console.log('new Id',id)
            }
            getOrders(this.listQuery).then( response => {
                let orderList = response.data.records
                console.log('orderList',orderList)
                for(var index in  orderList){
                    let orders = orderList[index]
                    orders.title='订单日期:'+orders.createTime
                    orders.descript = '订单编号：'+orders.orderSn


                }
            this.orderList = orderList
            }).catch( (err) => {

            })
          // queryByUser().then( response => {
          //     let goodsList = response.data
          //     let totalPrice = 0
          //     for(var index in goodsList){
          //         goodsList[index].thumb = '/dev-api/file/getImgStream?idFile=' + goodsList[index].goods.pic
          //         totalPrice += goodsList[index].count*parseInt(goodsList[index].goods.price)
          //     }
          //     this.goods = goodsList
          // }).catch((err) => {
          //     Toast(err)
          // })
        },
        onSubmit() {
            Toast('点击结算');
        },
        formatPrice(price) {
            return (price / 100).toFixed(2);
        },
        onClickLeft(){
            this.$router.go(-1)
        },
        clickNav(){

        }
    }
};
