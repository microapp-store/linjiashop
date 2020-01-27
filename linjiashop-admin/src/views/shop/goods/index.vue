<template>
  <div class="app-container">
    <div class="block">
        <el-row  :gutter="20">
          <el-col :span="6">
            <el-input v-model="listQuery.name" size="mini" placeholder="商品名称"></el-input>
          </el-col>

          <el-col :span="6">
            <el-button type="success" size="mini" icon="el-icon-search" @click.native="search">{{ $t('button.search') }}</el-button>
            <el-button type="primary" size="mini" icon="el-icon-refresh" @click.native="reset">{{ $t('button.reset') }}</el-button>
          </el-col>
        </el-row> <br>
      <el-row>
        <el-col :span="24">
          <el-button type="success" size="mini" icon="el-icon-plus" @click.native="add" v-permission="['/goodsEdit']">{{ $t('button.add') }}
          </el-button>
        </el-col>
      </el-row>
    </div>


    <el-table :data="list" v-loading="listLoading" element-loading-text="Loading" border fit highlight-current-row
              @current-change="handleCurrentChange">
      <el-table-column label="名称">
        <template slot-scope="scope">
          {{scope.row.name}}
        </template>
      </el-table-column>
      <el-table-column label="小图">
        <template slot-scope="scope">
          <img :src="apiUrl+ '/file/getImgStream?idFile=' +scope.row.pic" style="width:50px;">

        </template>
      </el-table-column>

      <el-table-column label="类别">
        <template slot-scope="scope">
          {{scope.row.category.name}}
        </template>
      </el-table-column>
      <el-table-column label="产品简介">
        <template slot-scope="scope">
          {{scope.row.descript}}
        </template>
      </el-table-column>
      <el-table-column label="上架">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.isOnSale"
            @change="changeIsOnSale(scope.row)"
          >
          </el-switch>
        </template>
      </el-table-column>
      <el-table-column label="是否新品推荐">
        <template slot-scope="scope">
          <el-tag type="success" v-show="scope.row.isNew">是</el-tag>
          <el-tag type="warning"  v-show="!scope.row.isNew">否</el-tag>

        </template>
      </el-table-column>
      <el-table-column label="是否人气商品">
        <template slot-scope="scope">
          <el-tag type="success" v-show="scope.row.isHot">是</el-tag>
          <el-tag type="warning"  v-show="!scope.row.isHot">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" icon="el-icon-edit" @click.native="edit(scope.row.id)" circle>
          </el-button>
        </template>
      </el-table-column>

    </el-table>

    <el-pagination
      background
      layout="total, sizes, prev, pager, next, jumper"
      :page-sizes="[10, 20, 50, 100,500]"
      :page-size="listQuery.limit"
      :total="total"
      @size-change="changeSize"
      @current-change="fetchPage"
      @prev-click="fetchPrev"
      @next-click="fetchNext">
    </el-pagination>

  </div>
</template>

<script src="./goods.js"></script>


<style rel="stylesheet/scss" lang="scss" scoped>
  @import "src/styles/common.scss";
</style>

