<template>
    <div>
        <van-tabs v-model="activeNav" @click="onClickNav">
            <!--<van-tab title="推荐" name="0" >-->
            <!--</van-tab>-->
            <van-tab v-for="nav in navList" :title="nav.name" :name="nav.id"  >
            </van-tab>
        </van-tabs>
        <van-swipe :autoplay="3000" style="height: 200px;">
            <van-swipe-item v-for="(image, index) in banners" :key="index" @click="clickSwipe">
                <a :href="image.url"><img v-lazy="image.path" style="width:100%;"/></a>
            </van-swipe-item>
        </van-swipe>
        <van-list
                v-model="loading"
                :finished="finished"
                @load="loadMore"
        >
        </van-list>
        <van-card v-for="(goods,index) in goodsList" :key="index"
                  :num="goods.stock"
                  :price="formatPrice(goods.price)"
                  :desc="goods.descript"
                  :title="goods.name"
                  :thumb="goods.img"
                  :thumb-link="goods.link"
                  @click="viewGoodsDetail(goods.id)"
        />
        <br><br><br><br>

        <van-tabbar v-model="activeFooter">
            <van-tabbar-item icon="home-o"  replace to="/index">首页</van-tabbar-item>
            <van-tabbar-item icon="search"  replace to="/search">发现</van-tabbar-item>
            <van-tabbar-item icon="cart-o"  replace to="/cart">购物车</van-tabbar-item>
            <van-tabbar-item icon="user-o"  replace to="/user">我的</van-tabbar-item>
        </van-tabbar>
    </div>
</template>

<script src="./goodsList.js"></script>

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
