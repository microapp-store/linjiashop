<template>
    <div class="updateMobile">
        <van-nav-bar
                title="更换手机"
                left-arrow
                @click-left="onClickLeft"
        />
        <van-row>
            <van-col span="24">
                <strong>为确认身份，我们仍需验证您的安全手机</strong>
                <p>点击发送短信按钮，将会发送一条有验证码的短信至手机<span style="color: #FF0000;">{{oldMobile}}</span></p>
            </van-col>
        </van-row>

        <van-cell-group v-show="showOldMobileSmsCode">
            <van-field
                    v-model="oldMobileSmsCode"
                    center
                    clearable
                    label="短信验证码"
                    placeholder="请输入短信验证码"
            >
                <van-button slot="button" size="small" type="primary" @click="sendSmsCode" v-show="!hasSendSms">发送验证码</van-button>
                <van-button slot="button" size="small" disabled type="primary" v-show="hasSendSms">{{second}}秒后重新获取</van-button>

            </van-field>
        </van-cell-group>
            <van-button type="warning"  v-show="showSendBtn"   block @click="sendSmsCode">发送短信</van-button>
        <van-button type="primary"     v-show="showConfirmBtn" block @click="confirmOldMobileSmsCode">确定</van-button>

    </div>
</template>

<script src="./updateMobile.js"></script>

<style lang="less">
    .updateMobile {
        .van-row{
            padding:16px;
        }
        .van-col{
            background-color: #f8f8f8;
        }
    }
</style>
