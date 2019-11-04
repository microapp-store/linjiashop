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
            <el-table-column label="所属订单id">
                <template slot-scope="scope">
                    {{scope.row.idOrder}}
                </template>
            </el-table-column>
            <el-table-column label="商品id">
                <template slot-scope="scope">
                    {{scope.row.idGoods}}
                </template>
            </el-table-column>
            <el-table-column label="单价">
                <template slot-scope="scope">
                    {{scope.row.price}}
                </template>
            </el-table-column>
            <el-table-column label="数量">
                <template slot-scope="scope">
                    {{scope.row.amount}}
                </template>
            </el-table-column>
            <el-table-column label="合计">
                <template slot-scope="scope">
                    {{scope.row.totalPrice}}
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
            <el-form ref="form" :model="form" :rules="rules" label-width="150px">
                <el-row>
                    <el-col :span="12">
                        <el-form-item label="所属订单id"  >
                            <el-input v-model="form.idOrder" minlength=1></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="商品id"  >
                            <el-input v-model="form.idGoods" minlength=1></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="单价"  >
                            <el-input v-model="form.price" minlength=1></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="数量"  >
                            <el-input v-model="form.amount" minlength=1></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="合计"  >
                            <el-input v-model="form.totalPrice" minlength=1></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-form-item>
                    <el-button type="primary" @click="save">{{ $t('button.submit') }}</el-button>
                    <el-button @click.native="formVisible = false">{{ $t('button.cancel') }}</el-button>
                </el-form-item>

            </el-form>
        </el-dialog>
    </div>
</template>

<script src="./orderItem.js"></script>


<style rel="stylesheet/scss" lang="scss" scoped>
    @import "src/styles/common.scss";
</style>

