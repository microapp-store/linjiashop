<template>
  <div class="login">
    <img class="user-poster" src="/static/img/banner.jpg">


    <van-cell-group>
      <van-field
        v-model="mobile"
        required
        clearable
        label="账号"
        placeholder="测试账号:15011112222"
      />
    </van-cell-group>
    <van-cell-group v-if="show1">
      <van-field
        v-model="smsCode"
        center
        clearable
        label="短信验证码"
        placeholder="请输入短信验证码"
      >
        <van-button slot="button" size="small" type="primary" @click="sendSms">发送验证码</van-button>
      </van-field>
    </van-cell-group>
    <van-cell-group v-if="show2">
      <van-field
        v-model="password"
        type="password"
        label="密码"
        placeholder="测试密码：admin"
        required
      />

    </van-cell-group>
    <div class="button-group">
      <van-button type="warning" size="large" v-if="show1" @click="loginOrRegister">立即登录/注册</van-button>
      <van-button type="info" size="large" v-if="show1" @click="toLoginByPassword">用户名密码登录</van-button>
      <van-button type="warning" size="large" v-if="show2" @click="loginByPass">登录</van-button>
      <van-button type="info" size="large" v-if="show2" @click="toRegister">手机短信登录/注册</van-button>
    </div>
  </div>
</template>

<script>
  import store from '@/utils/store.js'

  export default {
    data() {
      return {
        mobile: '15011112222',
        smsCode: '',
        password: 'admin',
        activeFooter: 3,
        show1: false,
        show2: true,
        redirect: ''
      }
    },
    methods: {
      toLoginByPassword() {
        this.show1 = false
        this.show2 = true
      },
      toRegister() {
        this.show2 = false
        this.show1 = true
      },
      loginOrRegister() {
        this.$API.get('/loginOrReg', {mobile: this.mobile, smsCode: this.smsCode}).then(res => {
          // store.dispatch('app/toggleToken',response.data.token)
          // store.dispatch('app/toggleUser',response.data.user)
          if (this.redirect) {
            this.$router.push({path: this.redirect})
          } else {
            this.$router.push({path: '/index'})
          }
        })
      },
      loginByPass() {
        console.log(this.mobile)
        this.$API.post('/loginByPass?mobile=' + this.mobile + '&password=' + this.password).then(res => {
          store.commit('setToken', res.data.token)
          store.commit('setUser', res.data.user)
          console.log('准备跳转')
          wx.navigateBack({delta: 2})
          // if (this.redirect) {
          //  this.$router.push({path: this.redirect})
          // } else {
          // this.$router.push({path: '/index'})
          // }
        })
      },
      sendSms() {
        this.$API.post('/sendSmsCode', {mobile: this.mobile}).then(res => {
          const smsCode = res.data
          console.log(smsCode)
          // Toast('提示：测试阶段不是加发送短信验证码：'+smsCode)
        })
      }
    }
  }
</script>


<style>
  .user-poster {
    height: 340 rpx;
  }

  .user-links {
    padding-bottom: 15px;
    text-align: center;
    background-color: #fff;
  }
</style>
