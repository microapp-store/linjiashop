<template>
    <div>

        <van-search placeholder="请输入搜索关键词" v-model="searchKey" />

        <van-card v-for="(goods,index) in goodsList" :key="index"
                  :num="goods.num"
                  :price="goods.price"
                  :desc="goods.desc"
                  :title="goods.title"
                  :thumb="goods.img"
        />


        <van-tabbar v-model="activeFooter">
            <van-tabbar-item icon="home-o"  replace to="/index">首页</van-tabbar-item>
            <van-tabbar-item icon="search"  replace to="/search">发现</van-tabbar-item>
            <van-tabbar-item icon="cart-o"  replace to="/cart">订单</van-tabbar-item>
            <van-tabbar-item icon="user-o"  replace to="/user">我的</van-tabbar-item>
        </van-tabbar>
    </div>
</template>

<script>
    import {
        Card,
        Cell,
        CellGroup,
        Col,
        Icon,
        Lazyload,
        Row,
        Tab,
        Tabbar,
        TabbarItem,
        Tabs,
        Search
    } from 'vant';

    export default {
        components: {
            [Row.name]: Row,
            [Col.name]: Col,
            [Icon.name]: Icon,
            [Cell.name]: Cell,
            [CellGroup.name]: CellGroup,
            [Tabbar.name]: Tabbar,
            [TabbarItem.name]: TabbarItem,
            [Tab.name]: Tab,
            [Tabs.name]: Tabs,
            [Card.name]: Card,
            [Search.name]:Search,
            Lazyload


        },
        data() {
            return {
                searchKey:'',
                navList: [
                    {text: '日用杂品'},
                    {text: '水果生鲜', url: '/#/goods'},
                    {text: '干果零食', url: '/#/user'},
                    {text: '家用电器', url: '/#/cart'},

                ],

                goodsList: [{
                    num: 21,
                    price: "2.00",
                    desc: "描述信息",
                    title: "商品标题",
                    img: "https://img.yzcdn.cn/vant/t-thirt.jpg"
                },
                    {
                        num: 200,
                        price: "2.00",
                        desc: "描述信息",
                        title: "商品标题",
                        img: "https://img.yzcdn.cn/vant/t-thirt.jpg"
                    },
                    {
                        num: 200,
                        price: "2.00",
                        desc: "描述信息",
                        title: "商品标题",
                        img: "https://img.yzcdn.cn/vant/t-thirt.jpg"
                    },
                    {
                        num: 200,
                        price: "2.00",
                        desc: "描述信息",
                        title: "商品标题",
                        img: "https://img.yzcdn.cn/vant/t-thirt.jpg"
                    }
                ],
                activeFooter: 1,
                activeNav: 0
            }
        },
        methods: {
            clickSwipe(index, p2) {
                console.log(index)
                console.log(p2)
            }
        }
    };
</script>

<style lang="less">
    .user {
        &-poster {
            width: 100%;
            height: 53vw;
            display: block;
        }

        &-group {
            margin-bottom: 15px;
        }

        &-links {
            padding: 15px 0;
            font-size: 12px;
            text-align: center;
            background-color: #fff;

            .van-icon {
                display: block;
                font-size: 24px;
            }
        }
    }
</style>
