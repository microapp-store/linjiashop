<template>
    <div class="goods" v-if="!offline">
        <van-swipe class="goods-swipe" :autoplay="3000">
            <van-swipe-item v-for="thumb in goods.thumb" :key="thumb">
                <img :src="thumb">
            </van-swipe-item>
        </van-swipe>

        <van-cell-group>
            <van-cell>
                <div class="goods-title">{{ goods.name }}</div>
                <div class="goods-desc">{{ goods.descript }}</div>
                <div class="goods-price">{{ formatPrice(goods.price) }}</div>
            </van-cell>
            <van-cell class="goods-express">
                <van-col span="10">运费：免运费</van-col>
                <van-col span="14">剩余：{{ goods.stock }}</van-col>
            </van-cell>
        </van-cell-group>

        <!--<van-cell-group class="goods-cell-group">-->
        <!--<van-cell value="进入店铺" icon="shop-o" is-link @click="sorry">-->
        <!--<template slot="title">-->
        <!--<span class="van-cell-text">有赞的店</span>-->
        <!--<van-tag class="goods-tag" type="danger">官方</van-tag>-->
        <!--</template>-->
        <!--</van-cell>-->
        <!--s-->
        <!--</van-cell-group>-->

        <van-cell-group class="goods-cell-group">
            <van-cell title="查看商品评论" is-link @click="sorry"/>
        </van-cell-group>
        <van-cell-group class="goods-cell-group">
            <p class="goods-detail" v-html="goods.detail"></p>
        </van-cell-group>
        <van-goods-action>
            <van-goods-action-icon icon="home-o" @click="toHome" text="主页"/>
            <van-goods-action-icon icon="like-o"  @click="like" :color="likeColor" text="喜欢"/>
            <van-goods-action-icon icon="cart-o" @click="goToCart" :info="cartCount" text="购物车"/>
            <van-goods-action-button type="danger" @click="addCart" text="立即购买"/>
        </van-goods-action>
        <van-sku
                v-model="showSku"
                :sku="sku"
                :goods="goods"
                :goods-id="goods.id"
                :hide-stock="sku.hide_stock"
                @buy-clicked="onBuyClicked"
                @add-cart="onAddCartClicked"
        />
    </div>

    <div class="offline" v-else>
        <p style="text-align: center;color:lightgray">
            <van-icon name="warning-o" style="text-align: center" size="3rem"/><br>
            改商品已下架</p>
        <van-button type="primary" block round @click="toHome">去看看其他商品</van-button>
    </div>
</template>

<script src="./goods.js"></script>

<style lang="less">
    img {
        width: 100%;
    }

    .section-detail {
        margin-top: -4px;
    }
    .like-info {
       color:black;
    }
    .like-red {
        color:red !important;
    }
    .goods {
        padding-bottom: 50px;

        &-swipe {
            img {
                width: 100%;
                height: 450px;
                display: block;
            }
        }

        &-desc {
            font-size: 12px;
            color: #999999;
            letter-spacing: 0;
            line-height: 18px;
            margin: 6px 0;
        }

        &-detail {
            padding: 15px;
            font-size: 14px;
        }

        &-title {
            font-size: 16px;
        }

        &-price {
            color: #f44;
        }

        &-express {
            color: #999;
            font-size: 12px;
            padding: 5px 15px;
        }

        &-cell-group {
            margin: 15px 0;

            .van-cell__value {
                color: #999;
            }
        }

        &-tag {
            margin-left: 5px;
        }
    }
    .offline{
        margin-top:60%;
    }
</style>
