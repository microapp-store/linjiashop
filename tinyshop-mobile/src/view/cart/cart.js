import cart from '@/api/cart'
import { Checkbox, CheckboxGroup, Card, SubmitBar, Toast, NavBar, Tabbar, TabbarItem,Stepper } from 'vant';
const baseApi = process.env.VUE_APP_BASE_API
export default {
    components: {
        [Card.name]: Card,
        [Checkbox.name]: Checkbox,
        [SubmitBar.name]: SubmitBar,
        [CheckboxGroup.name]: CheckboxGroup,
        [NavBar.name]: NavBar,
        [Tabbar.name]: Tabbar,
        [TabbarItem.name]: TabbarItem,
        [Stepper.name]: Stepper
    },

    data() {
        return {
            activeFooter: 2,
            checkedGoods: [],
            checkeAllCarts:[],
            cartList: [],
            checkedAll: true
        }
    },
    mounted(){
      this.init()
    },
    computed: {
        submitBarText() {
            const count = this.checkedGoods.length;
            return '结算' + (count ? `(${count})` : '');
        },
        totalPrice() {
                return this.cartList.reduce((total, item) => total + (this.checkedGoods.indexOf(item.id) !== -1 ? (parseFloat(item.goods.price)*item.count) : 0), 0)
        }
    },

    methods: {
        init(){
            cart.queryByUser().then( response => {
              let cartList = response.data
              for(var index in cartList){
                  cartList[index].thumb = baseApi+ '/file/getImgStream?idFile=' + cartList[index].goods.pic
                  this.checkedGoods.push(cartList[index].id+'')
              }
              this.cartList = cartList
          }).catch((err) => {

          })
        },
        submit() {
            this.$router.push('checkout')
        },
        formatPrice(price) {
            return (price / 100).toFixed(2);
        },
        stepperEvent(item, arg) {
            let count = arg[0];
            cart.update({id:item.id,count:count})
        },
        checkAll( ) {
            if (this.checkedGoods.length === this.cartList.length) {
                this.checkeAllCarts = this.checkedGoods
                this.checkedGoods = []
            } else {
                this.checkedGoods = this.checkeAllCarts
            }
        },
    }
};
