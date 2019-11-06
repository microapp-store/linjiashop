import { queryByUser } from '@/api/cart'
import { Checkbox, CheckboxGroup, Card, SubmitBar, Toast, NavBar, Tabbar, TabbarItem } from 'vant';

export default {
    components: {
        [Card.name]: Card,
        [Checkbox.name]: Checkbox,
        [SubmitBar.name]: SubmitBar,
        [CheckboxGroup.name]: CheckboxGroup,
        [NavBar.name]: NavBar,
        [Tabbar.name]: Tabbar,
        [TabbarItem.name]: TabbarItem
    },

    data() {
        return {
            activeFooter: 2,
            checkedGoods: ['1'],
            goods: [ ]
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
        totalPrice() {

                return this.goods.reduce((total, item) => total + (this.checkedGoods.indexOf(item.id) !== -1 ? (parseFloat(item.goods.price)*item.count) : 0), 0)


        }
    },

    methods: {
        init(){

            if(this.$router.query){
                let id = this.$router.query.id
                console.log('new Id',id)
            }
          queryByUser().then( response => {
              let goodsList = response.data
              let totalPrice = 0
              for(var index in goodsList){
                  goodsList[index].thumb = '/dev-api/file/getImgStream?idFile=' + goodsList[index].goods.pic
                  totalPrice += goodsList[index].count*parseInt(goodsList[index].goods.price)
              }
              this.goods = goodsList
          }).catch((err) => {
              Toast(err)
          })
        },
        onSubmit() {
            Toast('点击结算');
        },
        formatPrice(price) {
            return (price / 100).toFixed(2);
        }
    }
};
