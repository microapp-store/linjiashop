<template>
    <div class="updateMobile">
        <van-nav-bar
                title="更换手机"
                left-arrow
                @click-left="onClickLeft"
        />

        <van-cell-group>
            <van-field
                    v-model="oldMobile"
                    required
                    clearable
                    label="原手机号"
                    placeholder="请输入"
            />
        </van-cell-group>
            <van-button type="primary"   round block @click="submit">确定</van-button>

    </div>
</template>

<script src="./updateMobile.js"></script>

<style lang="less">
    .updateMobile {
        &-group {
            margin-bottom: 15px;
        }
    }
</style>
