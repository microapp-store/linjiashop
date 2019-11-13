<template>
  <div class="app-container">


    <el-form ref="form" :model="form" :rules="rules" label-width="150px">
      <el-row>
        <el-col :span="12">
          <el-form-item label="手机号">
            <el-input v-model="form.mobile" minlength=1></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="密码盐">
            <el-input v-model="form.salt" minlength=1></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="密码">
            <el-input v-model="form.password" minlength=1></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="昵称">
            <el-input v-model="form.nickName" minlength=1></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="头像">
            <el-input v-model="form.avatar" minlength=1></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-form-item>
        <el-button type="primary" @click="save">{{ $t('button.submit') }}</el-button>
        <el-button @click.native="formVisible = false">{{ $t('button.cancel') }}</el-button>
      </el-form-item>

    </el-form>
  </div>
</template>

<script src="./shopUserDetail.js"></script>


<style rel="stylesheet/scss" lang="scss" scoped>
  @import "src/styles/common.scss";
</style>

