<template>
    <div>
        <van-nav-bar
                :title="title"
                left-arrow
                @click-left="onClickLeft"
        />
        <div class="expressInfo">
            <van-steps direction="vertical" :active="0" class="traces">
                <van-step v-for="(item, index) in expressInfo.traces"
                          :key="index">
                    <h3 class="station">{{item.acceptStation}}</h3>
                    <p class="time">{{item.acceptTime}}</p>
                </van-step>
            </van-steps>
            <br><br><br>
        </div>


        <van-tabbar v-model="activeFooter">
            <van-tabbar-item icon="home-o" replace to="/index">首页</van-tabbar-item>
            <van-tabbar-item icon="search" replace to="/search">发现</van-tabbar-item>
            <van-tabbar-item icon="cart-o" replace to="/cart">购物车</van-tabbar-item>
            <van-tabbar-item icon="user-o" replace to="/user">我的</van-tabbar-item>
        </van-tabbar>
    </div>
</template>

<script src="./expressInfo.js"></script>

<style lang="less">
    .van-step--vertical{
        padding:0px 10px 2px 0px;
    }
    .expressInfo {
        .traces{
           .station{
               font-size:.8em;
           }
            .time{
                font-size:.9em;
            }
        }
        .van-panel {
            margin-top: 5px;
        }

        .van-card {
            background-color: #fff;
        }

        .total {
            text-align: right;
            padding: 10px;
        }

        .footer_btn {
            text-align: right;

            .van-button {
                margin-left: 10px;
            }
        }
    }
</style>
