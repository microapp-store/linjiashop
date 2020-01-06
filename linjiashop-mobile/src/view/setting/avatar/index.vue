<template>
    <div class="avatar">
        <van-nav-bar
                title="修改头像"
                left-arrow
                @click-left="onClickLeft"
        />
        <div class="preview">
            <van-image
                    round
                    width="100"
                    height="100"
                    :src="avatarUrl"
            />
        </div>
        <div class="upload">
            <van-uploader :after-read="afterRead"/>
        </div>
    </div>
</template>

<script src="./avatar.js"></script>

<style lang="less">
    .avatar {
        text-align: center;
        .preview{
            margin-top:15px;
        }
        .upload{
            margin-top: 15px;
        }
        &-group {
            margin-bottom: 15px;
        }
    }
</style>
