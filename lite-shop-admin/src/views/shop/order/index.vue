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
            <el-table-column label="用户id">
                <template slot-scope="scope">
                    {{scope.row.idUser}}
                </template>
            </el-table-column>
            <el-table-column label="订单号">
                <template slot-scope="scope">
                    {{scope.row.orderSn}}
                </template>
            </el-table-column>
            <el-table-column label="状态">
                <template slot-scope="scope">
                    {{scope.row.status}}
                </template>
            </el-table-column>
            <el-table-column label="收货信息">
                <template slot-scope="scope">
                    {{scope.row.idAddress}}
                </template>
            </el-table-column>
            <el-table-column label="订单备注">
                <template slot-scope="scope">
                    {{scope.row.message}}
                </template>
            </el-table-column>
            <el-table-column label="总金额">
                <template slot-scope="scope">
                    {{scope.row.totalPrice}}
                </template>
            </el-table-column>
            <el-table-column label="优惠券抵扣金额">
                <template slot-scope="scope">
                    {{scope.row.couponPrice}}
                </template>
            </el-table-column>
            <el-table-column label="实付金额">
                <template slot-scope="scope">
                    {{scope.row.realPrice}}
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
                        <el-form-item label="用户id"  >
                            <el-input v-model="form.idUser" minlength=1></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="订单号"  >
                            <el-input v-model="form.orderSn" minlength=1></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="状态"  >
                            <el-input v-model="form.status" minlength=1></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="收货信息"  >
                            <el-input v-model="form.idAddress" minlength=1></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="订单备注"  >
                            <el-input v-model="form.message" minlength=1></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="总金额"  >
                            <el-input v-model="form.totalPrice" minlength=1></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="优惠券抵扣金额"  >
                            <el-input v-model="form.couponPrice" minlength=1></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="实付金额"  >
                            <el-input v-model="form.realPrice" minlength=1></el-input>
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

<script src="./order.js"></script>


<style rel="stylesheet/scss" lang="scss" scoped>
    @import "src/styles/common.scss";
</style>

