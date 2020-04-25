<template>
  <div class="app-container">
    <div class="block">
      <el-row  :gutter="20">
        <el-col :span="6">
            <el-select    size="mini" v-model="listQuery.disabled" filterable placeholder="请选择是否禁用">
              <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>

        </el-col>
        <el-col :span="10">
          <el-date-picker
            v-model="rangeDate"
            size="mini"
            type="datetimerange"
            range-separator="至"
            start-placeholder="发送起始日期"
            end-placeholder="发送截至日期"
            value-format="yyyyMMddHHmmss"
            align="right">
          </el-date-picker>
        </el-col>
        <el-col :span="6">
          <el-button type="success" size="mini" icon="el-icon-search" @click.native="search">{{ $t('button.search') }}</el-button>
          <el-button type="primary" size="mini" icon="el-icon-refresh" @click.native="reset">{{ $t('button.reset') }}</el-button>
        </el-col>
      </el-row>
      <br>
      <el-row>
        <el-col :span="24">
          <el-button type="success" size="mini" icon="el-icon-plus" @click.native="add" v-permission="['/topic/edit']">{{ $t('button.add') }}
          </el-button>
          <el-button type="danger" size="mini" icon="el-icon-delete" @click.native="remove" v-permission="['/topic/delete']">{{ $t('button.delete') }}
          </el-button>
        </el-col>
      </el-row>
    </div>


    <el-table :data="list" v-loading="listLoading" element-loading-text="Loading" border fit highlight-current-row
              @current-change="handleCurrentChange">
      <el-table-column label="标题">
        <template slot-scope="scope">
          {{scope.row.title}}
        </template>
      </el-table-column>
      <el-table-column label="专题详情">
        <template slot-scope="scope">
          <img :src="apiUrl+ '/file/getImgStream?idFile=' +scope.row.article.img"
               style="width:50px !important;cursor:pointer;" @click="viewArticle(scope.row.article)" title="查看专题详情">
        </template>
      </el-table-column>
      <el-table-column label="阅读量">
        <template slot-scope="scope">
          {{scope.row.pv}}
        </template>
      </el-table-column>
      <el-table-column label="是否禁用">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.disabled"
            @change="changeDisabled(scope.row)"
          >
          </el-switch>
        </template>
      </el-table-column>
      <el-table-column label="创建日期">
        <template slot-scope="scope">
          {{scope.row.createTime}}
        </template>
      </el-table-column>
      <el-table-column label="最近维护日期">
        <template slot-scope="scope">
          {{scope.row.modifyTime}}
        </template>
      </el-table-column>
      <el-table-column>
        <template slot-scope="scope">
          <el-button type="primary" size="mini" icon="el-icon-view" @click.native="view(scope.row.id)" circle></el-button>
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
      :title="article.title"
      :visible.sync="showArticle"
      width="80%">
      <p v-html="article.content"></p>
    </el-dialog>
    <el-dialog
      :title="formTitle"
      :visible.sync="formVisible"
      width="70%">
      <el-form ref="form" :model="form" :rules="rules" label-width="150px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="标题">
              <el-input v-model="form.title" minlength=4 placeholder="请输入标题"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="专题文章">
              <el-select
                v-model="form.idArticle"
                filterable
                remote
                no-data-text="无数据,可在CMS管理-文章管理中新建专题文章"
                placeholder="请输入文章关键字搜索2"
                :remote-method="searchArticle"
                :loading="searchLoading">
                <el-option
                  v-for="item in articleList"
                  :key="item.id"
                  :label="item.title"
                  :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="商品列表">
              <el-select
                v-model="form.idGoodsList"
                multiple
                filterable
                remote
                placeholder="请输入商品关键字搜索"
                :remote-method="searchGoods"
                :loading="searchLoading"
                @change="changeGoods">
                <el-option
                  v-for="item in goodsList"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id">
                </el-option>
              </el-select>
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
      title="专题预览"
      :visible.sync="showTopic"
      width="450px">
      <p v-html="topicDetail"></p>


    </el-dialog>
  </div>
</template>

<script src="./topic.js"></script>


<style rel="stylesheet/scss" lang="scss" scoped>
  @import "src/styles/common.scss";

</style>

