import cartApi from '@/api/cart'
import { Checkbox, CheckboxGroup, Card, SubmitBar, Toast, NavBar, Tabbar, TabbarItem,Stepper, Button, Icon  } from 'vant';
const baseApi = process.env.VUE_APP_BASE_API
import storage from '@/utils/storage'
export default {
    components: {
        [Card.name]: Card,
        [Checkbox.name]: Checkbox,
        [SubmitBar.name]: SubmitBar,
        [CheckboxGroup.name]: CheckboxGroup,
        [NavBar.name]: NavBar,
        [Tabbar.name]: Tabbar,
        [TabbarItem.name]: TabbarItem,
        [Stepper.name]: Stepper,
        [Button.name]: Button,
        [Icon.name]: Icon
    },

    data() {
        return {
            isLogin:false,
            activeFooter: 2,
            checkedCartItem: [],//当前选中的购物车项目id
            allCartItem:[], // 用户所有的购物车项目id列表
            cartList: [],
            checkedAll: true,
            showEdit: false,
            rightText:'编辑'
        }
    },
    mounted(){
      this.init()
    },
    computed: {
        submitBarText() {
            const count = this.checkedCartItem.length;
            return '结算' + (count ? `(${count})` : '');
        },
        totalPrice() {
                return this.cartList.reduce((total, item) => total + (this.checkedCartItem.indexOf(item.id) !== -1 ? (parseFloat(item.price)*item.count) : 0), 0)
        }
    },

    methods: {
        init(){
            const user = storage.getUser()
            this.isLogin = user.nickName
            if(this.isLogin) {
                cartApi.queryByUser().then(response => {
                    let cartList = response.data
                    for (const index in cartList) {
                        let cart = cartList[index]
                        cart.thumb = baseApi + '/file/getImgStream?idFile=' + cart.goods.pic
                        this.checkedCartItem.push(cartList[index].id + '')
                    }
                    this.allCartItem = this.checkedCartItem
                    this.cartList = cartList
                }).catch((err) => {

                })
            }
        },
        submit() {
            this.$router.push({path:'checkout',query: {ids: this.checkedCartItem }})
        },
        formatPrice(price) {
            return (price / 100).toFixed(2);
        },
        stepperEvent(item, arg) {
            const count = arg[0];
            cartApi.update(item.id,count)
        },
        toHome() {
            this.$router.push('/')
        },
        toLogin() {
            this.$router.push({path:'login', query: {redirect:'cart'}})
        },
        checkAll( ) {
            if(this.checkedAll === true){
                this.checkedCartItem = this.allCartItem
            }else{
                this.checkedCartItem = []
            }
            return
            if (this.checkedCartItem.length === this.cartList.length) {
                this.allCartItem = this.checkedCartItem
                this.checkedCartItem = []
            } else {
                this.checkedCartItem = this.allCartItem
            }
        },
        onClickRight(){
            console.log('aa')
            if(this.showEdit === true){
                this.showEdit = false
                this.rightText = '编辑'
            }else {
                this.showEdit = true
                this.rightText = '完成'
            }
        },
        remove(){
            cartApi.remove(this.checkedCartItem).then(response =>{
                this.init()
            })
        }
    }
};
