<template>
  <div class="app-container">


    <el-form ref="form"   label-width="120px" label-position="left">
      <el-row>
        <h3>基本信息</h3>
        <el-col :span="12">
          <el-form-item label="手机号">
           <span>{{userInfo.info.mobile}}</span>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="头像">
            <el-avatar   :src="apiUrl+ '/file/getImgStream?idFile=' + userInfo.info.avatar" v-show="userInfo.info.avatar"></el-avatar>
            <el-avatar  v-show="!userInfo.info.avatar"  icon="el-icon-user-solid"></el-avatar>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="昵称">
            <span>{{userInfo.info.nickName}}</span>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="注册时间">
            <span>{{userInfo.info.createTime}}</span>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="最后登陆时间">
           <span>{{userInfo.info.lastLoginTime}}</span>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="订单量">
            <span>{{userInfo.orderCount}}</span>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="购物车数量">
            <span>{{userInfo.cartCount}}</span>
          </el-form-item>
        </el-col>

      </el-row>
      <el-row>
        <h3>收货地址</h3>
        <p v-for="item in addressList" :key="item.id">
          {{item.name}}&nbsp;&nbsp; {{item.tel}}&nbsp;&nbsp;{{item.province}}{{item.city}}{{item.district}}{{item.addressDetail}}
        </p>
      </el-row>

    </el-form>
  </div>
</template>

<script src="./shopUserDetail.js"></script>


<style rel="stylesheet/scss" lang="scss" scoped>
  @import "src/styles/common.scss";
</style>

