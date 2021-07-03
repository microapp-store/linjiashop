import categoryApi from '@/api/shop/category'
import attrKeyApi from '@/api/shop/attrKey'
import attrValApi from '@/api/shop/attrVal'
import {getApiUrl} from '@/utils/utils'
import { getToken } from '@/utils/auth'
import permission from '@/directive/permission/index.js'
import { Loading } from 'element-ui'
export default {
  name:'category',
  directives: {permission},
  data() {
    return {
      uploadUrl: '',
      uploadFileId: '',
      uploadHeaders: {
        'Authorization': ''
      },
      rootList: [],
      formVisible: false,
      formTitle: '添加商品类别',
      isAdd: true,
      form: {
        name: '',
        icon: '',
        id: ''
      },
      total: 0,
      list: null,
      listLoading: true,
      selRow: {},
      apiUrl: getApiUrl(),
      banner: {
        idCategory: '',
        activeName: 'first',
        visible: false,
        list: []
      },
      attrKey: {
        idCategory: '',
        visible: false,
        list: []
      },
      attrVal: {
        idAttrKey: '',
        visible: false,
        list: []
      }
    }
  },
  filters: {
    statusFilter(status) {
      const statusMap = {
        published: 'success',
        draft: 'gray',
        deleted: 'danger'
      }
      return statusMap[status]
    }
  },
  created() {
    this.init()
  },
  methods: {
    init() {
      this.uploadUrl = getApiUrl() + '/file'
      this.uploadHeaders['Authorization'] = getToken()
      this.fetchData()
    },
    fetchData() {
      this.listLoading = true
      categoryApi.getList(this.listQuery).then(response => {
        this.list = response.data
        this.rootList = Object.assign({},response.data)
        this.listLoading = false
      })
    },
    handleCurrentChange(currentRow, oldCurrentRow) {
      this.selRow = currentRow
    },
    resetForm() {
      this.form = {
        name: '',
        icon: '',
        id: ''
      }
    },
    add() {
      this.resetForm()
      this.formTitle = '添加商品类别'
      this.formVisible = true
      this.isAdd = true
    },
    save() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          categoryApi.save({
            name: this.form.name,
            url: this.form.url,
            icon: this.form.icon,
            id: this.form.id,
            sort:this.form.sort,
            descript:this.form.descript,
            pid:this.form.pid
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
    edit(data) {
      this.isAdd = false
      this.form = data
      this.formTitle = '编辑商品类别'
      this.formVisible = true

    },
    changeShowIndex(data) {
      if (data.pid) {
        this.$message({
          message: '二级菜单不支持该操作',
          type: 'warning'
        })
        data.showIndex = false
        return
      }
      categoryApi.changeShowIndex(data.id, data.showIndex).then(response => {
        this.$message({
          message: this.$t('common.optionSuccess'),
          type: 'success'
        })
      })
    },
    remove(data) {

      const id = data.id
      this.$confirm(this.$t('common.deleteConfirm'), this.$t('common.tooltip'), {
        confirmButtonText: this.$t('button.submit'),
        cancelButtonText: this.$t('button.cancel'),
        type: 'warning'
      }).then(() => {
        categoryApi.remove(id).then(response => {
          this.$message({
            message: this.$t('common.optionSuccess'),
            type: 'success'
          })
          this.fetchData()
        }).catch(err => {

        })
      }).catch(() => {
      })

    },
    bannerMgr(data) {
      if (data.pid) {
        this.$message({
          message: '二级菜单不支持该操作',
          type: 'warning'
        })
        data.showIndex = false
        return
      }
      this.banner.visible = true
      this.banner.idCategory = data.id
      categoryApi.getBanners(data.id).then(response => {
        this.banner.list = response.data
        console.log('banner', this.banner)
      })
    },
    bannerRemove(id) {

      categoryApi.removeBanner(this.banner.idCategory, id).then(response => {
        this.$message({
          message: this.$t('common.optionSuccess'),
          type: 'success'
        })
        this.bannerMgr(this.banner.idCategory)
      })

    },
    bannerEdit() {
    },
    addBanner() {
      this.$router.push({path: 'banner', query: {idCategory: this.banner.idCategory}})
    },
    attrKeyMgr(data) {
      this.attrKey.visible = true
      this.attrKey.idCategory = data.id
      categoryApi.getAttrKeys(data.id).then(response => {
        this.attrKey.list = response.data
      })
    },
    attrKeyAdd() {
      this.$prompt('请输入属性名', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
      }).then(({value}) => {
        if (!value || value === '') {
          this.$message({
            type: 'warning',
            message: '属性名不能为空'
          })
        }
        attrKeyApi.save({attrName: value, idCategory: this.attrKey.idCategory}).then(response => {
          this.$message({
            type: 'success',
            message: '新增属性成功'
          })
          categoryApi.getAttrKeys(this.attrKey.idCategory).then(response2 => {
            this.attrKey.list = response2.data
          })

        })

      })
    },
    attrKeyEdit(item) {
      console.log('item', item)
      this.$prompt('请输入属性名', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        inputValue: item.attrName,
      }).then(({value}) => {
        if (!value || value === '') {
          this.$message({
            type: 'warning',
            message: '属性名不能为空'
          })
        }
        attrKeyApi.updateAttrName(item.id, value).then(response => {
          item.attrName = value
          this.$message({
            type: 'success',
            message: '编辑成功'
          })
        })
        return

      })

    },
    attrKeyRemove(id) {
      console.log('id', id)
      attrKeyApi.remove(id).then(response => {
        this.$message({
          type: 'success',
          message: '删除成功'
        })
        categoryApi.getAttrKeys(this.attrKey.idCategory).then(response2 => {
          this.attrKey.list = response2.data
        })
      })

    },
    openAttrValDialog(item) {
      this.attrVal.idAttrKey = item.id
      this.attrVal.visible = true
      console.log('item', item)
      attrValApi.getAttrVals(this.attrVal.idAttrKey).then(response2 => {
        this.attrVal.list = response2.data
      })
    },
    attrValAdd() {
      this.$prompt('请输入属性名', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
      }).then(({value}) => {
        if (!value || value === '') {
          this.$message({
            type: 'warning',
            message: '属性名不能为空'
          })
        }
        attrValApi.save({attrVal: value, idAttrKey: this.attrVal.idAttrKey}).then(response => {
          this.$message({
            type: 'success',
            message: '新增属性成功'
          })
          attrValApi.getAttrVals(this.attrVal.idAttrKey).then(response2 => {
            this.attrVal.list = response2.data
          })

        })

      })
    },
    attrValEdit(item) {
      console.log('item', item)
      this.$prompt('请输入属性名', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        inputValue: item.attrVal,
      }).then(({value}) => {
        if (!value || value === '') {
          this.$message({
            type: 'warning',
            message: '属性名不能为空'
          })
        }
        attrValApi.updateAttrVal(item.id, value).then(response => {
          item.attrName = value
          this.$message({
            type: 'success',
            message: '编辑成功'
          })
          attrValApi.getAttrVals(this.attrVal.idAttrKey).then(response2 => {
            this.attrVal.list = response2.data
          })
        })
        return

      })

    },
    attrValRemove(id) {
      console.log('id', id)
      attrValApi.remove(id).then(response => {
        this.$message({
          type: 'success',
          message: '删除成功'
        })
        attrValApi.getAttrVals(this.attrVal.idAttrKey).then(response2 => {
          this.attrVal.list = response2.data
        })
      })

    },
    handleBeforeUpload() {
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
        console.log('aa',response)
        this.form.icon = response.data.realFileName
      } else {
        this.$message({
          message: this.$t('common.uploadError'),
          type: 'error'
        })
      }
    }

  }
}
