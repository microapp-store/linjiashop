<template>
    <div class="index">
        <van-tabs v-model="activeNav" @click="clickNav">
            <van-tab v-for="nav in navList" :title="nav.name" v-bind:key="nav.id" :name="nav.id">
            </van-tab>
        </van-tabs>
        <div class="subject">
            <van-row>
                    <van-col  v-for="(topic,index) in topicList" :key="index" :span="(index+3)%3===0?12:6"  >
                        <van-image height="100"
                                   @click="toTopic(topic.id)"
                                   :src="topic.img"></van-image>
                        <div class="text"> {{topic.title}}</div>
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
                width:100%;
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
