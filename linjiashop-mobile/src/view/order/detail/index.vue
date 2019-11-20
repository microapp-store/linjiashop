<template>
    <div>
        <van-nav-bar
                title="订单详情"
                left-arrow
                @click-left="onClickLeft"
        />
        <div class="order_detail">
            <van-panel :title="order.address.name +' '+  order.address.tel" :status="order.statusName">
                <div class="address_detail">
                    {{order.address.province}}{{order.address.city}}{{order.address.district}}{{order.address.addressDetail}}
                    <br>
                </div>
                <div slot="footer"
                     class="footer_btn">
                    <van-button size="small" type="default" @click="contact">联系客服</van-button>
                    <van-button size="small" type="danger" @click="payment" v-show="order.statusName=='待付款'">立即付款</van-button>
                    <van-button size="small" type="danger" @click="confirmReceive" v-show="order.statusName=='已发货'">确认收货</van-button>
                </div>
            </van-panel>
            <van-panel :title="'订单编号: ' + order.orderSn">
                <van-card v-for="(orderItem, index) in order.items"
                          :key="index"
                          :title="orderItem.goods.name"
                          :desc="orderItem.goods.descript"
                          :price="formatPrice(orderItem.price)"
                          :num="orderItem.count"
                          @click="toGoods(orderItem.goods.id)"
                          :thumb="imgUrl+orderItem.goods.pic">

                </van-card>
                <div class="total">合计: {{formatPrice(order.totalPrice)}}</div>

            </van-panel>
            <van-panel title="订单信息" >
                <van-cell-group>
                    <van-cell title="订单编号" :value="order.orderSn" />
                    <van-cell title="备注" :value="order.message"   />
                    <van-cell title="创建时间" :value="order.createTime"/>
                </van-cell-group>
            </van-panel>
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

<script src="./detail.js"></script>

<style lang="less">
    .order_detail {
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
        .address_detail{
            padding: 5px;
        }
    }
</style>
