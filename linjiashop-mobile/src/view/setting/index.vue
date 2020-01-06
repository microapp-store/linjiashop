<template>
    <div class="setting">
        <van-nav-bar
                title="设置"
                left-arrow
                @click-left="onClickLeft"
        />
        <van-cell-group>
            <van-cell icon="flower-o" title="头像" is-link to="avatar">
                <van-image
                        round
                        width="30"
                        height="30"
                        :src="avatarUrl"
                />
            </van-cell>
            <van-cell icon="user-o" title="姓名" :value="user.nickName"is-link to="updateUserName"/>
            <van-cell icon="share" title="性别" is-link :value="user.genderStr" @click="showGender=true"/>
            <van-cell icon="closed-eye" title="修改密码" is-link to="updatePwd"/>
            <van-cell icon="phone-o" title="更换手机" is-link to="updateMobile"/>
            <van-cell icon="warning-o" title="关于本项目" is-link url="http://microapp.gitee.io/linjiashop/"/>
        </van-cell-group>
        <div class="logout">
        <van-button type="danger" plain round block @click="onLogout">退出登录</van-button>
        </div>
        <van-popup v-model="showGender"  :style="{ padding:'20px',width:'50px', height: '50px' }">
            <van-radio-group v-model="user.gender" @change="onChangeGender">
                <van-radio name="male">男</van-radio>
                <van-radio name="female">女</van-radio>
            </van-radio-group>
        </van-popup>
    </div>
</template>

<script src="./setting.js"></script>

<style lang="less">
    .setting {
        &-group {
            margin-bottom: 15px;
        }

        &-links {
            padding: 15px 0;
            font-size: 12px;
            text-align: center;
            background-color: #fff;

            .van-icon {
                display: block;
                font-size: 24px;
            }
        }
        .logout{
            position:fixed;
            bottom:0px;
            width:100%;
        }
    }
</style>
