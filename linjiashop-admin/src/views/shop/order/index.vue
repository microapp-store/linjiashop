<template>
  <div class="app-container">
    <div class="block">
      <el-row  :gutter="20">
        <el-col :span="4">
          <el-input v-model="listQuery.mobile" size="mini" placeholder="手机号"></el-input>
        </el-col>
        <el-col :span="4">
          <el-input v-model="listQuery.orderSn" size="mini" placeholder="订单号"></el-input>
        </el-col>
        <el-col :span="6">
          <el-button type="success" size="mini" icon="el-icon-search" @click.native="search">{{ $t('button.search') }}</el-button>
          <el-button type="primary" size="mini" icon="el-icon-document" @click.native="exportXls">{{ $t('button.export') }}</el-button>
          <el-button type="primary" size="mini" icon="el-icon-refresh" @click.native="reset">{{ $t('button.refresh') }}</el-button>
        </el-col>
      </el-row>
    </div>

    <el-table :data="list" v-loading="listLoading" element-loading-text="Loading" border fit highlight-current-row
              @current-change="handleCurrentChange">
      <el-table-column label="用户">
        <template slot-scope="scope">
          <router-link :to="{path:'shopUserDetail?id='+scope.row.user.id}">
            {{scope.row.user.mobile}}
          </router-link>
        </template>
      </el-table-column>
      <el-table-column label="订单号">
        <template slot-scope="scope">
          <router-link :to="{path:'orderDetail?orderSn='+scope.row.orderSn}">
            {{scope.row.orderSn}}
          </router-link>
        </template>
      </el-table-column>
      <el-table-column label="创建日期">
        <template slot-scope="scope">
          {{scope.row.createTime}}
        </template>
      </el-table-column>
      <el-table-column label="订单状态">
        <template slot-scope="scope">
          {{scope.row.statusName}}
        </template>
      </el-table-column>
      <el-table-column label="支付状态">
        <template slot-scope="scope">
          {{scope.row.payStatusName}}
        </template>
      </el-table-column>
      <el-table-column label="支付方式">
        <template slot-scope="scope">
          {{scope.row.payTypeName}}
        </template>
      </el-table-column>
      <el-table-column label="订单备注">
        <template slot-scope="scope">
          {{scope.row.message}}
        </template>
      </el-table-column>
      <el-table-column label="总金额">
        <template slot-scope="scope">
          {{formatPrice(scope.row.totalPrice)}}
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-dropdown  size="small"   split-button type="primary">

              操作
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item v-if="scope.row.statusName === '待付款'">修改订单</el-dropdown-item>
              <el-dropdown-item @click.native="addComment(scope.row.id)">订单备注</el-dropdown-item>
              <el-dropdown-item v-if="scope.row.statusName==='已发货'" @click.native="viewShippingInfo(scope.row)">物流信息</el-dropdown-item>
              <el-dropdown-item v-if="scope.row.statusName === '待发货'" @click.native="openSendOutForm(scope.row.id)">立即发货</el-dropdown-item>
              <el-dropdown-item v-if="scope.row.payStatusName === '已付款' && scope.row.statusName !== '已退款'&& scope.row.statusName !== '退款中'">立即退款</el-dropdown-item>
              <el-dropdown-item @click.native="viewLog(scope.row.id)">操作日志</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
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
      title="订单日志"
      :visible.sync="logVisible"
      width="40%">
      <el-table
        :data="logs"
        stripe
        border
        style="width: 100%">
        <el-table-column
          prop="descript"
          label="操作记录">
        </el-table-column>
        <el-table-column
          prop="createTime"
          label="操作时间">
        </el-table-column>
      </el-table>
    </el-dialog>
    <el-dialog
      title="发货"
      :visible.sync="shipping.show"
      width="40%">
      <el-form ref="form" :model="shipping"  label-width="200px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="快递公司"  >
              <el-select v-model="shipping.idExpress" placeholder="请选择">
                <el-option
                  v-for="item in expressList"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="快递单号"  >
              <el-input v-model="shipping.shippingSn" minlength=1></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item>
          <el-button type="primary" @click="sendOut">{{ $t('button.submit') }}</el-button>
          <el-button @click.native="shipping.show = false">{{ $t('button.cancel') }}</el-button>
        </el-form-item>

      </el-form>
    </el-dialog>

  </div>
</template>

<script src="./order.js"></script>


<style rel="stylesheet/scss" lang="scss" scoped>
  @import "src/styles/common.scss";
</style>

