<template>
  <div class="app-container">
    <div class="block">
      <el-row>
        <el-col :span="24">
          <el-button type="success" size="mini" icon="el-icon-edit" @click.native="openSendOutForm"
                     v-show="form.statusName =='待发货'">发货
          </el-button>
          <el-button type="default" size="mini" @click="printOrder">打 印</el-button>
        </el-col>
      </el-row>
    </div>

    <el-form ref="print"   label-width="150px" >
      <el-row>
        <h3>基本信息</h3>
        <el-col :span="12">
          <el-form-item label="订单号">
            <span> {{ form.orderSn}}</span>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="订单用户">
            <router-link :to="{path:'shopUserDetail?id='+form.user.id}">
              {{ form.user.mobile}}({{form.user.nickName}})
            </router-link>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="状态">
            <span> <strong>{{ form.statusName }} </strong></span>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="订单备注">
            <span>{{ form.message }} </span>
          </el-form-item>
        </el-col>
        <h3>收货信息</h3>
        <el-col :span="12">
          <el-form-item label="收货人">
            <span>{{ form.address.name}}</span>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="联系电话">
            <span>{{ form.address.tel}}</span>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="收货地址">
            <span>{{ form.address.addressDetail}}</span>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <h3>订单明细</h3>
        </el-col>
        <el-table :data="form.items" border fit  >
          <el-table-column label="商品">
            <template slot-scope="scope">
              <router-link :to="{path:'goodsEdit?id='+scope.row.goods.id}">
                {{scope.row.title}}
              </router-link>
            </template>
          </el-table-column>
          <el-table-column label="图片">
            <template slot-scope="scope">
            <img :src="apiUrl+ '/file/getImgStream?idFile=' + scope.row.goods.pic" style="width:50px;">
            </template>
          </el-table-column>
          <el-table-column label="价格">
            <template slot-scope="scope">
              {{formatPrice(scope.row.price)}}
            </template>
          </el-table-column>
          <el-table-column label="数量">
            <template slot-scope="scope">
              {{scope.row.count}}
            </template>
          </el-table-column>
          <el-table-column label="合计">
            <template slot-scope="scope">
              {{ formatPrice(scope.row.totalPrice)}}
            </template>
          </el-table-column>
          <el-table-column label="" >
            <!--todo 打印的时候总是无法打印表格最后一列，此列用于占位-->
          </el-table-column>

        </el-table>
        <el-col :span="8">
          <el-form-item label="总金额">
            <span>{{formatPrice(form.totalPrice)}}</span>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="优惠券抵扣金额">
            <span> {{formatPrice(form.couponPrice)}}</span>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="实付金额">
            <span>{{formatPrice(form.realPrice)}}</span>
          </el-form-item>
        </el-col>
      </el-row>

    </el-form>
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

<script src="./orderDetail.js"></script>


<style rel="stylesheet/scss" lang="scss" scoped>
  @import "src/styles/common.scss";
</style>

