<template>
    <div class="app-container">

        <el-table :data="list" v-loading="listLoading" element-loading-text="Loading" border fit highlight-current-row
                  @current-change="handleCurrentChange">
            <el-table-column label="用户">
                <template slot-scope="scope">
                  <router-link :to="{path:'shopUserDetail?id='+scope.row.user.id}">
                    {{scope.row.user.mobile}}
                  </router-link>
                </template>
            </el-table-column>
            <el-table-column label="商品">
                <template slot-scope="scope">
                  <router-link :to="{path:'goodsEdit?id='+scope.row.goods.id}">
                    {{scope.row.goods.name}}
                  </router-link>

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

<script src="./favorite.js"></script>


<style rel="stylesheet/scss" lang="scss" scoped>
    @import "src/styles/common.scss";
</style>

