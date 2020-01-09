<template>
    <div class="topic">
        <h3 class="title">{{topic.article.title}}</h3>
        <van-cell-group>
            <p class="detail" v-html="topic.article.content"></p>
        </van-cell-group>
        <div class="goods">
            <van-row>
                <van-col  v-for="(goods,index) in goodsList" :key="index" :span="12"  style="padding-left:5px;" >
                    <van-image height="150"
                               @click="toGoods(goods.id)"
                               :src="goods.img"></van-image>
                    <p class="text"  >
                            {{goods.name}}
                    </p>
                </van-col>

            </van-row>

        </div>
        <br>
        <van-tabbar v-model="activeFooter">
            <van-tabbar-item icon="home-o" replace to="/index">首页</van-tabbar-item>
            <van-tabbar-item icon="search" replace to="/search">发现</van-tabbar-item>
            <van-tabbar-item icon="cart-o" replace to="/cart">购物车</van-tabbar-item>
            <van-tabbar-item icon="user-o" replace to="/user">我的</van-tabbar-item>
        </van-tabbar>
    </div>

</template>

<script src="./topic.js"></script>

<style lang="less">
    img {
        width: 100%;
    }
    .topic {
        padding-bottom: 50px;
        .goods {
            padding: 0px 0px 5px 0px;
            .text {
                height:25px;
                background-color: #979595;
                color: #ffffff;
                font-size: 12px;
                opacity: .8;
                margin-top:-4px;
                text-align: center;
            }
        }
        .title{
            padding:5px;
        }
        .detail {
            padding: 0px;
            font-size: 14px;
        }

    }
</style>
