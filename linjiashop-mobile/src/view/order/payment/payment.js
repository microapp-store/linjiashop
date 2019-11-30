import {Button, Cell, CellGroup, NavBar, Radio, RadioGroup, Toast} from 'vant';

export default {
    components: {
        [NavBar.name]: NavBar,
        [Cell.name]: Cell,
        [CellGroup.name]: CellGroup,
        [RadioGroup.name]: RadioGroup,
        [Radio.name]: Radio,
        [Button.name]: Button,
        [Toast.name]: Toast
    },

    data() {
        return {
            order: {},
            payType: 'wxpay'
        };
    },
    mounted() {
        this.init()
    },
    computed: {},

    methods: {
        init() {
            let orderSn = this.$route.query.orderSn
            let totalPrice = this.$route.query.totalPrice
            this.order ={orderSn:orderSn,totalPrice:totalPrice}
        },
        pay() {
            let payTypeName = this.payType == 'wxpay' ? '微信支付' : '支付宝'
            Toast('准备使用' + payTypeName + '支付')
            this.$router.push('/')
        },
        formatPrice(price) {
            return (price / 100).toFixed(2);
        }

    }
};
