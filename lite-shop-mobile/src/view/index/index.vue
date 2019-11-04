<template>
    <div>
        <van-tabs v-model="activeNav" @click="clickNav">
            <van-tab v-for="nav in navList" :title="nav.text">
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

<script src="./index.js"></script>

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
