<template>
  <div class="app-container">
    <div class="block">
      <el-row  :gutter="20">
        <el-col :span="4">
          <el-input v-model="listQuery.mobile" size="mini" placeholder="手机号"></el-input>
        </el-col>
        <el-col :span="4">
          <el-input v-model="listQuery.nickName" size="mini"  placeholder="昵称"></el-input>
        </el-col>
        <el-col :span="8">
          <el-date-picker
            v-model="regDate"
            size="mini"
            type="daterange"
            range-separator="至"
            value-format="yyyy-MM-dd"
            start-placeholder="注册开始日期"
            end-placeholder="注册截至日期">
          </el-date-picker>
        </el-col>
        <el-col :span="8">
          <el-date-picker
            v-model="lastLoginTime"
            size="mini"
            type="daterange"
            range-separator="至"
            value-format="yyyy-MM-dd"
            start-placeholder="最近登录开始日期"
            end-placeholder="最近登录截至日期">
          </el-date-picker>
        </el-col>
      </el-row>
      <el-row  :gutter="20">
        <el-col :span="6">
          <el-button type="success" size="mini" icon="el-icon-search" @click.native="search">{{ $t('button.search') }}</el-button>
          <el-button type="primary" size="mini" icon="el-icon-refresh" @click.native="reset">{{ $t('button.reset') }}</el-button>
        </el-col>
      </el-row>

    </div>
    <el-table :data="list" v-loading="listLoading" element-loading-text="Loading" border fit highlight-current-row
              @current-change="handleCurrentChange">
      <el-table-column label="ID">
        <template slot-scope="scope">
          {{scope.row.id}}
        </template>
      </el-table-column>
      <el-table-column label="头像">
        <template slot-scope="scope">
          <el-avatar   :src="scope.row.img"></el-avatar>
        </template>
      </el-table-column>
      <el-table-column label="昵称">
        <template slot-scope="scope">
          {{scope.row.nickName}}
        </template>
      </el-table-column>
      <el-table-column label="手机号">
        <template slot-scope="scope">
          <router-link :to="{path:'shopUserDetail?id='+scope.row.id}">
            {{scope.row.mobile}}
          </router-link>

        </template>
      </el-table-column>
      <el-table-column label="性别">
        <template slot-scope="scope">
          {{scope.row.gender}}
        </template>
      </el-table-column>
      <el-table-column label="注册时间">
        <template slot-scope="scope">
          {{scope.row.createTime}}
        </template>
      </el-table-column>
      <el-table-column label="最后登陆时间">
        <template slot-scope="scope">
          {{scope.row.lastLoginTime}}
        </template>
      </el-table-column>
    </el-table>

    <el-pagination
      background
      layout="total, sizes, prev, pager, next, jumper"
      :page-sizes="[10, 20, 50, 100,500]"
      :page-size="listQuery.limit"
      :total="total"
      :current-page.sync="listQuery.page"
      @size-change="changeSize"
      @current-change="fetchPage"
      @prev-click="fetchPrev"
      @next-click="fetchNext">
    </el-pagination>

  </div>
</template>

<script src="./shopUser.js"></script>


<style rel="stylesheet/scss" lang="scss" scoped>
  @import "src/styles/common.scss";
</style>

