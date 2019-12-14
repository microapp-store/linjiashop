<template>
    <div class="app-container">


        <el-table :data="list" v-loading="listLoading" element-loading-text="Loading" border fit highlight-current-row
                  @current-change="handleCurrentChange">
            <el-table-column label="用户">
                <template slot-scope="scope">
                    {{scope.row.user.mobile}}
                </template>
            </el-table-column>
            <el-table-column label="商品">
                <template slot-scope="scope">
                    {{scope.row.title}}
                </template>
            </el-table-column>
          <el-table-column label="商品价格">
            <template slot-scope="scope">
              {{formatPrice(scope.row.price)}}
            </template>
          </el-table-column>
            <el-table-column label="产品规格">
                <template slot-scope="scope">
                    {{scope.row.specifications}}
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

<script src="./cart.js"></script>


<style rel="stylesheet/scss" lang="scss" scoped>
    @import "src/styles/common.scss";
</style>

