import { remove, getList, save } from '@/api/cms/banner'
import categoryApi from '@/api/shop/category'
import { getToken } from '@/utils/auth'
import { Loading } from 'element-ui'
import { getApiUrl } from '@/utils/utils'
import permission from '@/directive/permission/index.js'
export default {
  directives: { permission },
  data() {
    return {
      uploadUrl: '',
      uploadFileId: '',
      uploadHeaders: {
        'Authorization': ''
      },
      loadingInstance: {},
      formVisible: false,
      formTitle: '添加banner',
      deptList: [],
      isAdd: true,
      options: [
        { label: '首页', value: 'index' },
        { label: '新闻', value: 'news' },
        { label: '产品', value: 'product' },
        { label: '解决方案', value: 'solution' },
        { label: '案例', value: 'case' }
      ],
      form: {
        id: '',
        title: '',
        page: '',
        param: '',
        img: '',
        idFile: '',
        type: 'index'
      },
      listQuery: {
        title: undefined
      },
      list: null,
      listLoading: true,
      selRow: {},
      shopCategory:{
        id:undefined,
        show:false,
        disabled:true
      }
    }
  },
  computed: {
    rules() {
      return {
        title: [
          { required: true, message: '标题不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  created() {
    this.shopCategory.id = this.$route.query.idCategory
    this.init()
  },
  methods: {
    init() {
      this.uploadUrl = getApiUrl() + '/file'
      this.uploadHeaders['Authorization'] = getToken()
      this.fetchData()
      if(this.shopCategory.id){
        this.shopCategory.show = true
      }
    },
    fetchData() {
      this.listLoading = true
      getList(this.listQuery).then(response => {
        this.list = response.data
        for (var index in this.list) {
          let item = this.list[index]
          item.img = getApiUrl() + '/file/getImgStream?idFile=' + item.idFile
        }

        this.listLoading = false
      })
    },
    search() {
      this.listQuery.page = 1
      this.fetchData()
    },
    reset() {
      this.listQuery.title = ''
      this.listQuery.page = 1
      this.fetchData()
    },
    handleFilter() {
      this.getList()
    },
    handleCurrentChange(currentRow, oldCurrentRow) {
      this.selRow = currentRow
    },
    resetForm() {
      this.form = {
        id: '',
        title: '',
        page: '',
        param: '',
        idFile: this.uploadFileId,
        type: ''
      }
    },
    add() {
      this.resetForm()
      this.formTitle = '添加banner'
      this.formVisible = true
      this.isAdd = true
    },
    save() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          save({
            id: this.form.id,
            title: this.form.title,
            page: this.form.page,
            param: this.form.param,
            idFile: this.uploadFileId,
            type: this.form.type
          }).then(response => {
            this.$message({
              message: this.$t('common.optionSuccess'),
              type: 'success'
            })
            this.fetchData()
            this.formVisible = false
          })
        } else {
          return false
        }
      })
    },
    checkSel() {
      if (this.selRow && this.selRow.id) {
        return true
      }
      this.$message({
        message: this.$t('common.mustSelectOne'),
        type: 'warning'
      })
      return false
    },
    clickRow(row){
      this.shopCategory.disabled=false
      this.shopCategory.idBanner = row.id
    },
    setBanner(){
      if( this.shopCategory.idBanner) {
        categoryApi.setCategoryBanner(this.shopCategory.id, this.shopCategory.idBanner).then(response => {
          this.$message({
            message: '设置成功',
            type: 'success'
          })
          this.shopCategory.disabled = true
        })
      }else{
        this.$message({
          message: '请先选中要设置的banner',
          type: 'warning'
        })
      }
    },
    remove() {
      if (this.checkSel()) {
        var id = this.selRow.id
        this.$confirm(this.$t('common.deleteConfirm'), this.$t('common.tooltip'), {
          confirmButtonText: this.$t('button.submit'),
          cancelButtonText: this.$t('button.cancel'),
          type: 'warning'
        }).then(() => {
          remove(id).then(response => {
            this.$message({
              message: this.$t('common.optionSuccess'),
              type: 'success'
            })
            this.fetchData()
          })
        }).catch(() => {
        })
      }
    },
    handleBeforeUpload() {
      if (this.uploadFileId !== '') {
        this.$message({
          message: this.$t('common.mustSelectOne'),
          type: 'warning'
        })
        return false
      }
      this.loadingInstance = Loading.service({
        lock: true,
        text: this.$t('common.uploading'),
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      })
    },
    handleUploadSuccess(response, raw) {
      this.loadingInstance.close()
      if (response.code === 20000) {
        this.uploadFileId = response.data.realFileName
        this.form.fileName = response.data.originalFileName
      } else {
        this.$message({
          message: this.$t('common.uploadError'),
          type: 'error'
        })
      }
    }
  }
}
