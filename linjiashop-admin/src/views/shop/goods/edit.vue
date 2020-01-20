<template>
  <div class="app-container">
    <div class="block">
      <el-row>
        <el-col :span="24">
          <el-button type="primary" @click="save" size="mini">{{active==3?"提交":"下一步"}}</el-button>
          <el-button @click="prev" v-show="active>0" size="mini">上一步</el-button>
        </el-col>
      </el-row>
    </div>
    <br>
    <div class="block">
      <el-steps :active="active" finish-status="success">
        <el-step title="基本信息"></el-step>
        <el-step title="商品相册"></el-step>
        <el-step title="商品详情"></el-step>
        <el-step title="上架信息"></el-step>
      </el-steps>
    </div>


    <el-form  :model="form"   label-width="150px" v-show="active==0">
      <el-row>
        <el-col :span="24">
          <el-form-item label="名称">
            <el-input v-model="form.name" minlength=1></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="产品简介">
            <el-input v-model="form.descript" minlength=1></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="类别">
            <el-select v-model="form.idCategory" filterable placeholder="请选择">
              <el-option
                v-for="item in categories"
                :key="item.id"
                :label="item.name"
                :value="item.id"

              >
              </el-option>
            </el-select>

          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="是否新品">
            <el-radio-group v-model="form.isNew">
              <el-radio :label="true">是</el-radio>
              <el-radio :label="false">否</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="是否热卖">
            <el-radio-group v-model="form.isHot">
              <el-radio :label="true">是</el-radio>
              <el-radio :label="false">否</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>


      </el-row>
    </el-form>

    <el-form  label-width="150px" v-show="active==1">
      <el-row>

        <el-col :span="24">
          <el-form-item label="商品缩略图">
            <el-upload
              :headers="uploadHeaders"
              :action="uploadUrl"
              :show-file-list="false"
              :on-success="handleUploadPicSuccess"
              class="avatar-uploader"
              accept=".jpg,.jpeg,.png,.gif">
              <img v-if="form.pic" :src="apiUrl+ '/file/getImgStream?idFile=' +form.pic" class="avatar">
              <i v-else class="el-icon-plus avatar-uploader-icon"/>
            </el-upload>

          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="商品相册">
              <el-upload
                list-type="picture-card"
                :action="uploadUrl"
                :headers="uploadHeaders"
                :file-list="galleryList"
                :on-remove="handleRemove"
                :on-success="handleUploadGallerySuccess">
                <i class="el-icon-plus"></i>
              </el-upload>

          </el-form-item>
        </el-col>


      </el-row>
    </el-form>

    <div :class="{fullscreen:fullscreen}" class="tinymce-container editor-container" v-show="active==2">
      <textarea id="tinymceId" class="tinymce-textarea"/>
      <div class="editor-custom-btn-container">
        <editorImage color="#1890ff" class="editor-upload-btn" @successCBK="imageSuccessCBK"/>
      </div>
    </div>
    <el-form  label-width="150px" v-show="active==3">
      <el-row>



        <el-col :span="24">
          <el-form-item label="商品规格">
            <el-radio class="radio" v-model="spec" label="one">单规格</el-radio>
            <el-radio class="radio" v-model="spec" label="more" v-show="attrKeyList.length>0">多规格</el-radio>
          </el-form-item>
        </el-col>
        <el-col :span="24" v-if="spec == 'one'">
          <el-form-item label="库存">
            <el-input-number v-model="form.stock" :min="0" :max="100000"></el-input-number>
          </el-form-item>
          <el-form-item label="市场价(分)">
            <el-input-number v-model="form.marketingPrice" :min="0" :max="10000000"></el-input-number>
          </el-form-item>
          <el-form-item label="价格(分)">
            <el-input-number v-model="form.price" :min="0" :max="10000000"></el-input-number>
          </el-form-item>
        </el-col>
        <el-col  :offset="3" :span="18" v-else style="overflow: auto; text-align: center;">
          <el-button type="primary" @click="openAddSkuForm" style="margin-bottom: 10px;">添加规格</el-button>
          <el-table
            :data="skuList"
            style="margin-bottom: 20px;"
            :row-class-name="tableRowClassName">
            <el-table-column
              prop="codeName"
              label="规格">
            </el-table-column>
            <el-table-column
              prop="marketingPrice"
              label="市场价">
            </el-table-column>

            <el-table-column
              prop="price"
              label="价格(分)">
            </el-table-column>

            <el-table-column
              prop="stock"
              label="库存">
            </el-table-column>

            <el-table-column label="操作">
              <template slot-scope="scope">
                <el-button
                  size="small"
                  type="danger"
                  @click="removeSku(scope.$index)">删除
                </el-button>
              </template>
            </el-table-column>

          </el-table>
        </el-col>
      </el-row>
    </el-form>
    <el-dialog title="SKU配置" :visible.sync="specDialogFormVisible">
      <el-form :model="skuForm">

            <el-form-item label="已选规格" label-width="100px" prop="specs">
            <el-tag
              v-for="tag in tags"
              :key="tag.id"
              closable
              :type="tag.id">
              {{tag.attrVal}}
            </el-tag>
            </el-form-item>

        <el-row>
          <el-col :span="8">
            <el-form-item label="属性名" label-width="100px"  >
              <el-select
                v-model="attrKeySel"
                filterable
                allow-create
                default-first-option
                @change="changeAttrKey"
                placeholder="属性名">
                <el-option
                  v-for="item in attrKeyList"
                  :key="item.id"
                  :label="item.attrName"
                  :value="item.id">
                </el-option>
              </el-select>

            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="属性值" label-width="100px"  >
              <el-select
                v-model="attrValSel"
                filterable
                allow-create
                default-first-option
                placeholder="属性值">
                <el-option
                  v-for="item in attrValListSel"
                  :key="item.id"
                  :label="item.attrVal"
                  :value="item.id">
                </el-option>
              </el-select>

            </el-form-item>
          </el-col>
          <el-col :offset="2" :span="6">
            <el-button type="success" @click="addToTag">添加规格</el-button>
          </el-col>
        </el-row>

        <el-form-item label="市场价(分)" label-width="100px">
          <el-input-number v-model="skuForm.marketingPrice" :min="0" :max="100000"></el-input-number>
        </el-form-item>
        <el-form-item label="价格(分)" label-width="100px">
          <el-input-number v-model="skuForm.price" :min="0" :max="1000000"></el-input-number>
        </el-form-item>
        <el-form-item label="库存" label-width="100px">
          <el-input-number v-model="skuForm.stock" :min="0" :max="100000"></el-input-number>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="default" @click="closeAddSkuForm">取 消</el-button>
        <el-button type="primary" @click="addSku">确 定</el-button>
      </div>
    </el-dialog>
    <br>
    <div class="block">
      <el-row>
        <el-col :span="24">
          <el-button type="primary" @click="save" size="mini">{{active==3?"提交":"下一步"}}</el-button>
          <el-button @click="prev" v-show="active>0" size="mini">上一步</el-button>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script src="./goodsEdit.js"></script>


<style scoped>

  .tinymce-container {
    position: relative;
    line-height: normal;
  }

  .tinymce-container >>> .mce-fullscreen {
    z-index: 10000;
  }

  .tinymce-textarea {
    visibility: hidden;
    z-index: -1;
  }

  .editor-custom-btn-container {
    position: absolute;
    right: 4px;
    top: 4px;
    /*z-index: 2005;*/
  }

  .fullscreen .editor-custom-btn-container {
    z-index: 10000;
    position: fixed;
  }

  .editor-upload-btn {
    display: inline-block;
  }

  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #20a0ff;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 120px;
    height: 120px;
    line-height: 120px;
    text-align: center;
  }
  .avatar {
    width: 145px;
    height: 145px;
    display: block;
  }

  .add_attr_key_row {
    height: 0;
    overflow: hidden;
    transition: all 400ms;
  }

  .showEdit {
    height: 185px;
  }

  .add_attr_key_button {
    text-align: center;
    line-height: 40px;
    transition: all 400ms;

  }
</style>

