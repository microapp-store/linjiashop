<template>
    <div class="index">
        <van-tabs v-model="activeNav" @click="clickNav">
            <van-tab v-for="nav in navList" :title="nav.name" v-bind:key="nav.id" :name="nav.id">
            </van-tab>
        </van-tabs>
        <div class="subject">
            <van-row>
                <van-col span="12">
                    <van-image height="100"
                               @click="toTopic"
                               src="https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/a56394373382e7d98a395b25c709cfcb.jpg?thumb=1&w=360&h=140"></van-image>
                    <div class="text"> 智能新品好物</div>
                </van-col>
                <van-col span="6">
                    <van-image height="100"
                               @click="toTopic"
                               src="https://i8.mifile.cn/v1/a1/b79cb907-3ae4-2a35-6756-b243b386a95a.webp"></van-image>

                    <div class="text"> 爱生活,爱自己</div>
                </van-col>
                <van-col span="6">
                    <van-image height="100"
                               @click="toTopic"
                               src="https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/efae8106bc194e6a730294c9ee531caf.jpg?thumb=1&w=360&h=360"></van-image>

                    <div class="text"> 给你一个安全的家</div>
                </van-col>
            </van-row>
            <van-row>

                <van-col span="12">
                    <van-image height="100"
                               @click="toTopic"
                               src="https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/f4c4a11536a720ee0f1b516e53e4d868.jpg?thumb=1&w=360&h=140"></van-image>
                    <div class="text"> 新年换新家</div>
                </van-col>
                <van-col span="6">
                    <van-image height="100"
                               @click="toTopic"
                               src="https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/739c1321d2a05d48c0b8b86cd459c4d4.jpg?thumb=1&w=360&h=360"></van-image>

                    <div class="text"> 记录美好时刻</div>
                </van-col>
                <van-col span="6">
                    <van-image height="100"
                               @click="toTopic"
                               src="https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/c33f008a86c151a1500d44d599351256.jpg?thumb=1&w=360&h=360"></van-image>

                    <div class="text"> “小爱同学”</div>
                </van-col>
            </van-row>
        </div>
        <van-panel title="热门推荐">
            <van-card v-for="(goods,index) in hotList" :key="index"
                      :price="formatPrice(goods.price)"
                      :desc="goods.descript"
                      :title="goods.name"
                      :thumb="goods.img"
                      :thumb-link="goods.link"
                      @click="viewGoodsDetail(goods.id)"
            />
        </van-panel>
        <br>
        <van-panel title="新品推荐">
            <van-card v-for="(goods,index) in newList" :key="index"
                      :price="formatPrice(goods.price)"
                      :desc="goods.descript"
                      :title="goods.name"
                      :thumb="goods.img"
                      :thumb-link="goods.link"
                      @click="viewGoodsDetail(goods.id)"
            />
        </van-panel>
        <br><br><br><br>

        <van-tabbar v-model="activeFooter">
            <van-tabbar-item icon="home-o" replace to="/index">首页</van-tabbar-item>
            <van-tabbar-item icon="search" replace to="/search">发现</van-tabbar-item>
            <van-tabbar-item icon="cart-o" replace to="/cart">购物车</van-tabbar-item>
            <van-tabbar-item icon="user-o" replace to="/user">我的</van-tabbar-item>
        </van-tabbar>
    </div>
</template>

<script src="./welcome.js"></script>

<style lang="less">
    .index {
        .subject {
            padding: 0px 0px 5px 0px;
            .text {
                display: none;
                padding: 5px;
                position: absolute;
                z-index: 10;
                margin-top: -32px;
                background-color: #979595;
                color: #ffffff;
                font-size: 12px;
                opacity: .8;
            }
        }


        .van-card {
            background-color: #ffffff;
        }
    }
</style>
