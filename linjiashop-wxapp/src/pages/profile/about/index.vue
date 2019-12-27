<template>
  <div class="about">

      <van-row>
        <van-col span="24" style="text-align: center;">
          <van-image
            round
            width="120"
            height="120"
            src="/static/img/logo.jpg"
          />
        </van-col>
      </van-row>
    <van-row>
      <van-col span="24">
        <p>
          linjiashop:邻家小铺，轻量，简洁的商城系统(h5,小程序,app)

        </p>
        <p>http://microapp.gitee.io/linjiashop</p>

      </van-col>
    </van-row>
  </div>
</template>

<script>
  export default {
    config:{
      navigationBarTitleText: '关于本项目'
    }
  }
</script>


<style>
  p{
    padding: 15px
  }
</style>
