<template>
    <div class="app-container">
        <div class="block">
            <el-row>
                <el-col :span="24">
                    <el-button type="success" size="mini"  icon="el-icon-plus" @click.native="add">{{ $t('button.add') }}</el-button>
                    <el-button type="primary" size="mini"  icon="el-icon-edit" @click.native="edit">{{ $t('button.edit') }}</el-button>
                    <el-button type="danger" size="mini"  icon="el-icon-delete" @click.native="remove">{{ $t('button.delete') }}</el-button>
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
          <el-table-column label="Banner管理">
            <template slot-scope="scope">
              <el-button type="primary" size="mini"  icon="el-icon-edit" @click.native="bannerMgr(scope.row.id)">管理</el-button>
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

        <el-dialog
                :title="formTitle"
                :visible.sync="formVisible"
                width="70%">
            <el-form ref="form" :model="form"   label-width="150px">
                <el-row>
                    <el-col :span="12">
                        <el-form-item label="名称"  >
                            <el-input v-model="form.name" minlength=1></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-form-item>
                    <el-button type="primary" @click="save">{{ $t('button.submit') }}</el-button>
                    <el-button @click.native="formVisible = false">{{ $t('button.cancel') }}</el-button>
                </el-form-item>

            </el-form>
        </el-dialog>
      <el-dialog
        title="Banner管理"
        :visible.sync="banner.visible"
        width="70%">
        <el-tabs v-model="banner.activeName">
          <el-tab-pane label="管理" name="first">
            <el-button type="primary" size="mini"  icon="el-icon-plus" @click.native="addBanner">添加</el-button>
            <el-table :data="banner.list" v-loading="listLoading" element-loading-text="Loading" border fit highlight-current-row>
              <el-table-column label="标题">
                <template slot-scope="scope">
                  {{scope.row.title}}
                </template>
              </el-table-column>
              <el-table-column label="url">
                <template slot-scope="scope">
                  {{scope.row.url}}
                </template>
              </el-table-column>
              <el-table-column label="图片">
                <template slot-scope="scope">
                  <img :src="apiUrl+ '/file/getImgStream?idFile=' +scope.row.idFile" style="width:100px;">
                </template>
              </el-table-column>
              <el-table-column label="操作">
                <template slot-scope="scope">
                  <el-button type="danger" size="mini"  icon="el-icon-delete" @click.native="bannerRemove(scope.row.id)">删除</el-button>
                </template>
              </el-table-column>
            </el-table>
          </el-tab-pane>
          <el-tab-pane label="预览" name="second">
            <el-carousel indicator-position="outside">
              <el-carousel-item v-for="item in banner.list" :key="item">
                <img :src="apiUrl+ '/file/getImgStream?idFile=' +item.idFile" style="width:100%;">
              </el-carousel-item>
            </el-carousel>
          </el-tab-pane>

        </el-tabs>
      </el-dialog>
    </div>
</template>

<script src="./category.js"></script>


<style rel="stylesheet/scss" lang="scss" scoped>
    @import "src/styles/common.scss";
</style>

