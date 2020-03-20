<template>
    <div>
        <van-nav-bar
                title="支付结果"
        />
        <div class="result">
        <van-icon v-if="isSuccess" name="success" size="80" color="green"/>

        <van-icon v-if="!isSuccess" name="cross" size="80" color="red"/><br>

       <span>{{result}}</span>


            <br> <br>
            <div v-if="result!=='查询中'">
                <van-button type="primary"  @click="toOrder">查看订单</van-button>
                <van-button type="info"  @click="toIndex">继续购物</van-button>
            </div>
        </div>
    </div>
</template>

<script src="./callback.js"></script>

<style lang="less">
    .result {
        margin-top:10%;
        text-align: center;
    }


</style>
