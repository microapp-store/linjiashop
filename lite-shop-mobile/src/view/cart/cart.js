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
            checkedGoods: ['1', '2', '3'],
            goods: [{
                id: '1',
                title: '进口香蕉',
                desc: '约250g，2根',
                price: 200,
                num: 1,
                thumb: 'https://img.yzcdn.cn/public_files/2017/10/24/2f9a36046449dafb8608e99990b3c205.jpeg'
            }, {
                id: '2',
                title: '陕西蜜梨',
                desc: '约600g',
                price: 690,
                num: 1,
                thumb: 'https://img.yzcdn.cn/public_files/2017/10/24/f6aabd6ac5521195e01e8e89ee9fc63f.jpeg'
            }, {
                id: '3',
                title: '美国伽力果',
                desc: '约680g/3个',
                price: 2680,
                num: 1,
                thumb: 'https://img.yzcdn.cn/public_files/2017/10/24/320454216bbe9e25c7651e1fa51b31fd.jpeg'
            }]
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
            return this.goods.reduce((total, item) => total + (this.checkedGoods.indexOf(item.id) !== -1 ? item.price : 0), 0);
        }
    },

    methods: {
        init(){
          queryByUser().then( response => {
              console.log(response)
          }).catch((err) => {
              Toast(err)
          })
        },
        formatPrice(price) {
            return (price / 100).toFixed(2);
        },

        onSubmit() {
            Toast('点击结算');
        }
    }
};
