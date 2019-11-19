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


      </el-row>
    </el-form>

    <el-form  label-width="150px" v-show="active==1">
      <el-row>

        <el-col :span="24">
          <el-form-item label="商品相册">
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
          <el-form-item label="价格(分)">
            <el-input v-model="form.price" minlength=1 type="number" placeholder="精确到分"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="数量">
            <el-input v-model="form.num" minlength=1 type="number"></el-input>
          </el-form-item>
        </el-col>


      </el-row>
    </el-form>
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
</style>

