<template>
  <div class="app-container">


    <el-form ref="form" :model="form" :rules="rules" label-width="150px">
      <el-row>
        <el-col :span="24">
          <el-form-item label="手机号">
           <span>{{userInfo.info.mobile}}</span>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="头像">
            <img :src="apiUrl+ '/file/getImgStream?idFile=' + userInfo.info.avatar" style="width:100px;">
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="昵称">
            <span>{{userInfo.info.nickName}}</span>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="最后登陆时间">
           <span>{{userInfo.info.lastLoginTime}}</span>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="订单量">
            <span>{{userInfo.orderCount}}</span>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="购物车数量">
            <span>{{userInfo.cartCount}}</span>
          </el-form-item>
        </el-col>


      </el-row>

    </el-form>
  </div>
</template>

<script src="./shopUserDetail.js"></script>


<style rel="stylesheet/scss" lang="scss" scoped>
  @import "src/styles/common.scss";
</style>

