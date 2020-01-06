<template>
    <div class="updateUserName">
        <van-nav-bar
                title="修改密码"
                left-arrow
                @click-left="onClickLeft"
        />

        <van-cell-group>
            <van-field
                    v-model="oldPwd"
                    required
                    clearable
                    label="旧密码"
                    placeholder="请输入"
            />
            <van-field
                    v-model="password"
                    required
                    clearable
                    label="新密码"
                    placeholder="请输入"
            />
            <van-field
                    v-model="rePassword"
                    required
                    clearable
                    label="确认密码"
                    placeholder="请输入"
            />
        </van-cell-group>
            <van-button type="primary"   round block @click="submit">确定</van-button>

    </div>
</template>

<script src="./updatePwd.js"></script>

<style lang="less">
    .updateUserName {
        &-group {
            margin-bottom: 15px;
        }
    }
</style>
