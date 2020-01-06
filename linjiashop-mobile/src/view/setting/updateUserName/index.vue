<template>
    <div class="updateUserName">
        <van-nav-bar
                title="修改姓名"
                left-arrow
                @click-left="onClickLeft"
        />

        <van-cell-group>
            <van-field
                    v-model="userName"
                    required
                    clearable
                    label="用户名"
                    placeholder="请输入"
            />
        </van-cell-group>
            <van-button type="primary"   round block @click="submit">确定</van-button>

    </div>
</template>

<script src="./updateUserName.js"></script>

<style lang="less">
    .updateUserName {
        &-group {
            margin-bottom: 15px;
        }
    }
</style>
