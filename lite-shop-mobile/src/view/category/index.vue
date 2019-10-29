<template>
    <div>
        <van-tabs v-model="activeNav">
            <van-tab v-for="nav in navList" :title="nav.text" :url="nav.url">
            </van-tab>
        </van-tabs>
        <van-swipe :autoplay="3000" style="height: 200px;">
            <van-swipe-item v-for="(image, index) in images" :key="index" @click="clickSwipe">
                <a :href="image.link"><img v-lazy="image.path"/></a>
            </van-swipe-item>
        </van-swipe>
        <van-card v-for="(goods,index) in goodsList" :key="index"
                  :num="goods.num"
                  :price="goods.price"
                  :desc="goods.desc"
                  :title="goods.title"
                  :thumb="goods.img"
                  :thumb-link="goods.link"
                  @click="viewGoodsDetail(goods.id)"
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
        Swipe,
        SwipeItem,
        Tab,
        Tabbar,
        TabbarItem,
        Tabs
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
            [Swipe.name]: Swipe,
            [SwipeItem.name]: SwipeItem,
            [Tab.name]: Tab,
            [Tabs.name]: Tabs,
            [Card.name]: Card,
            Lazyload


        },
        data() {
            return {
                navList: [
                    {text: '日用杂品',url: '/#/category?id=1'},
                    {text: '水果生鲜', url: '/#/category?id=2'},
                    {text: '干果零食', url: '/#/category?id=3'},
                    {text: '家用电器', url: '/#/category?id=4'},

                ],
                images: [{
                    link: '/#/user', path:
                        'https://img.yzcdn.cn/public_files/2017/10/23/8690bb321356070e0b8c4404d087f8fd.png'
                },
                    {
                        link: 'http://www.baidu.com', path:
                            'https://img.yzcdn.cn/public_files/2017/10/23/8690bb321356070e0b8c4404d087f8fd.png'
                    }
                ],
                goodsList: [{
                    id:1,
                    num: 21,
                    price: "2.00",
                    desc: "描述信息",
                    title: "商品标题",
                    img: "https://img.yzcdn.cn/vant/t-thirt.jpg",
                    link:'/#/goods'
                },
                    {
                        id:2,
                        num: 200,
                        price: "2.00",
                        desc: "描述信息",
                        title: "商品标题",
                        img: "https://img.yzcdn.cn/vant/t-thirt.jpg",
                        link:'/#/goods'
                    },
                    {
                        id:3,
                        num: 200,
                        price: "2.00",
                        desc: "描述信息",
                        title: "商品标题",
                        img: "https://img.yzcdn.cn/vant/t-thirt.jpg",
                        link:'/#/goods'
                    },
                    {
                        id:4,
                        num: 200,
                        price: "2.00",
                        desc: "描述信息",
                        title: "商品标题",
                        img: "https://img.yzcdn.cn/vant/t-thirt.jpg",
                        link:'/#/goods'
                    }
                ],
                activeFooter: 0,
                activeNav: 0
            }
        },
        created(){
          this.init()
        },
        methods: {
            init(){
                let index = this.$route.query.index
              let title = this.$route.query.title
                console.log(index)
                console.log(title)
                this.activeNav=index
            },
            clickSwipe(index, p2) {
                console.log(index)
                console.log(p2)
            },
            viewGoodsDetail(id){
                console.log(id)
                this.$router.push({path:'/goods',query:{id:id}})
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
