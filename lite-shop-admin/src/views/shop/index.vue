<template>
    <div class="app-container">
        <div class="block">
            <el-row>
                <el-col :span="24">
                    <el-button type="success" icon="el-icon-plus" @click.native="add">{{ $t('button.add') }}</el-button>
                    <el-button type="primary" icon="el-icon-edit" @click.native="edit">{{ $t('button.edit') }}</el-button>
                    <el-button type="danger" icon="el-icon-delete" @click.native="remove">{{ $t('button.delete') }}</el-button>
                </el-col>
            </el-row>
        </div>


        <el-table :data="list" v-loading="listLoading" element-loading-text="Loading" border fit highlight-current-row
                  @current-change="handleCurrentChange">
            <el-table-column label="手机号">
                <template slot-scope="scope">
                    {{scope.row.mobile}}
                </template>
            </el-table-column>
            <el-table-column label="密码盐">
                <template slot-scope="scope">
                    {{scope.row.salt}}
                </template>
            </el-table-column>
            <el-table-column label="密码">
                <template slot-scope="scope">
                    {{scope.row.password}}
                </template>
            </el-table-column>
            <el-table-column label="昵称">
                <template slot-scope="scope">
                    {{scope.row.nickName}}
                </template>
            </el-table-column>
            <el-table-column label="头像">
                <template slot-scope="scope">
                    {{scope.row.avatar}}
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
                        <el-form-item label="手机号"  >
                            <el-input v-model="form.mobile" minlength=1></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="密码盐"  >
                            <el-input v-model="form.salt" minlength=1></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="密码"  >
                            <el-input v-model="form.password" minlength=1></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="昵称"  >
                            <el-input v-model="form.nickName" minlength=1></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="头像"  >
                            <el-input v-model="form.avatar" minlength=1></el-input>
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

<script src="./t_shop_user.js"></script>


<style rel="stylesheet/scss" lang="scss" scoped>
    @import "src/styles/common.scss";
</style>

