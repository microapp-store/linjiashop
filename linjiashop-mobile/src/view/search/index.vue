<template>
    <div>

        <van-search placeholder="搜商品名称" v-model="listQuery.key"    @search="searchGoods" />
        <van-divider dashed>热门商品</van-divider>


        <van-card v-for="(goods,index) in goodsList" :key="index"
                  :price="formatPrice(goods.price)"
                  :desc="goods.descript"
                  :title="goods.name"
                  :thumb="goods.img"
                  @click="viewGoodsDetail(goods.id)"
        />
        <br><br><br>
        <van-tabbar v-model="activeFooter">
            <van-tabbar-item icon="home-o"  replace to="/index">首页</van-tabbar-item>
            <van-tabbar-item icon="search"  replace to="/search">发现</van-tabbar-item>
            <van-tabbar-item icon="cart-o"  replace to="/cart">购物车</van-tabbar-item>
            <van-tabbar-item icon="user-o"  replace to="/user">我的</van-tabbar-item>
        </van-tabbar>
    </div>
</template>

<script>
    import goods from '@/api/goods'
    const baseApi = process.env.VUE_APP_BASE_API
    import {
        Divider,
        List,
        Toast,
        Cell,
        CellGroup,
        Card,
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
            [Divider.name]: Divider,
            [List.name]: List,
            [Toast.name]:Toast,
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
                loading:false,
                finished:false,
                goodsList: [],
                activeFooter: 1,
                listQuery: {
                    page: 1,
                    limit: 20,
                    key:''
                },
            }
        },
        mounted(){
            this.init()
        },
        methods: {
            init(){
                this.searchHot()
            },
            searchGoods(){
                goods.search(this.listQuery).then( response => {
                    let list = response.data.records
                    for(var index in  list){
                        const item = list[index]
                        item.img = baseApi+'/file/getImgStream?idFile=' + item.pic
                    }
                    this.goodsList = list
                    this.loading=false

                }).catch( (err) => {
                    Toast.fail(err)
                    this.loading=false
                })

            },
            searchHot(){
                goods.searchHot().then( response =>{
                    let list = response.data
                    for(var index in  list){
                        const item = list[index]
                        item.img = baseApi+'/file/getImgStream?idFile=' + item.pic
                    }
                    this.goodsList = list
                    this.loading=false
                }).catch( (err) => {
                    Toast.fail(err)
                    this.loading=false
                })
            },
            viewGoodsDetail(id){
                this.$router.push({path:'/goods/'+id})
            },
            formatPrice(price) {
                return (price / 100).toFixed(2)
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
