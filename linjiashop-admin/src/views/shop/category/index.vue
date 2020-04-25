<template>
    <div class="app-container">
        <div class="block">
            <el-row>
                <el-col :span="24">
                    <el-button type="success" size="mini"  icon="el-icon-plus" @click.native="add" v-permission="['/category/edit']">{{ $t('button.add') }}</el-button>
                    <el-button type="primary" size="mini"  icon="el-icon-edit" @click.native="edit" v-permission="['/category/edit']">{{ $t('button.edit') }}</el-button>
                    <el-button type="danger" size="mini"  icon="el-icon-delete" @click.native="remove" v-permission="['/category/edit']">{{ $t('button.delete') }}</el-button>
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
              <el-button type="primary" size="mini"  icon="el-icon-picture" @click.native="bannerMgr(scope.row.id)">管理</el-button>
            </template>
          </el-table-column>
          <el-table-column label="属性管理">
            <template slot-scope="scope">
              <el-button type="primary" size="mini"  icon="el-icon-s-operation" @click.native="attrKeyMgr(scope.row.id)">管理</el-button>
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
      <el-dialog
        title="属性管理"
        :visible.sync="attrKey.visible"
        width="70%">
        <el-row>
          <el-col :span="24">
            <el-button type="success" size="mini"  icon="el-icon-plus" @click.native="attrKeyAdd" v-permission="['/category/edit']">{{ $t('button.add') }}</el-button>
          </el-col>
        </el-row>
        <el-table :data="attrKey.list" v-loading="listLoading" element-loading-text="Loading" border fit highlight-current-row>
          <el-table-column label="属性名">
            <template slot-scope="scope">
              {{scope.row.attrName}}
            </template>
          </el-table-column>
          <el-table-column label="操作">
            <template slot-scope="scope">
              <el-button type="primary" size="mini"  icon="el-icon-edit" @click.native="attrKeyEdit(scope.row)">编辑</el-button>
              <el-button type="primary" size="mini"  icon="el-icon-edit" @click.native="openAttrValDialog(scope.row)">编辑属性值</el-button>
              <el-button type="danger" size="mini"  icon="el-icon-delete" @click.native="attrKeyRemove(scope.row.id)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>

      </el-dialog>
      <el-dialog
        title="属性值管理"
        :visible.sync="attrVal.visible"
        width="50%">
        <el-row>
          <el-row>
            <el-col :span="24">
              <el-button type="success" size="mini"  icon="el-icon-plus" @click.native="attrValAdd" v-permission="['/category/edit']">{{ $t('button.add') }}</el-button>
            </el-col>
          </el-row>
          <el-table :data="attrVal.list" v-loading="listLoading" element-loading-text="Loading" border fit highlight-current-row>
            <el-table-column label="属性值">
              <template slot-scope="scope">
                {{scope.row.attrVal}}
              </template>
            </el-table-column>
            <el-table-column label="操作">
              <template slot-scope="scope">
                <el-button type="primary" size="mini"  icon="el-icon-edit" @click.native="attrValEdit(scope.row)">编辑</el-button>
                <el-button type="danger" size="mini"  icon="el-icon-delete" @click.native="attrValRemove(scope.row.id)">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-row>
      </el-dialog>
    </div>
</template>

<script src="./category.js"></script>


<style rel="stylesheet/scss" lang="scss" scoped>
    @import "src/styles/common.scss";
</style>

