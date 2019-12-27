<template>
  <div class="login">
    <van-image  width="100%" height="150" src="/static/img/banner.jpg"/>
    <van-button type="primary" size="large">微信用户一键登录</van-button>
    <div class="login-account">
    <navigator url="../loginAccount/index">手机号验证注册/登录</navigator>
    </div>
  </div>
</template>

<script>
  export default {
    config:{
      navigationBarTitleText: '登录'
    },
    data() {
      return {
        user: {nickName: '立即登录'}
      }
    },
    methods: {}
  }
</script>


<style>
  .login {
    margin:5px;
    text-align: center;
  }
  .login-account{
    padding-top:15px;
  }
</style>
