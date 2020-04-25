import categoryApi from '@/api/shop/category'
import attrKeyApi from '@/api/shop/attrKey'
import attrValApi from '@/api/shop/attrVal'
import {getApiUrl} from '@/utils/utils'
import permission from '@/directive/permission/index.js'
export default {
  directives: { permission },
  data() {
    return {
      formVisible: false,
      formTitle: '添加商品类别',
      isAdd: true,
      form: {
        name: '',
        url: '',
        icon: '',
        id: ''
      },
      listQuery: {
        page: 1,
        limit: 20,
        id: undefined
      },
      total: 0,
      list: null,
      listLoading: true,
      selRow: {},
      apiUrl:getApiUrl(),
      banner: {
        idCategory:'',
        activeName:'first',
        visible: false,
        list:[]
      },
      attrKey: {
        idCategory:'',
        visible:false,
        list:[]
      },
      attrVal:{
        idAttrKey:'',
        visible:false,
        list:[]
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
      this.fetchData()
    },
    fetchData() {
      this.listLoading = true
      categoryApi.getList(this.listQuery).then(response => {
        this.list = response.data.records
        this.listLoading = false
        this.total = response.data.total
      })
    },
    search() {
      this.listQuery.page = 1
      this.fetchData()
    },
    reset() {
      this.listQuery.id = ''
      this.listQuery.page = 1
      this.fetchData()
    },
    handleFilter() {
      this.listQuery.page = 1
      this.getList()
    },
    handleClose() {

    },
    fetchNext() {
      this.listQuery.page = this.listQuery.page + 1
      this.fetchData()
    },
    fetchPrev() {
      this.listQuery.page = this.listQuery.page - 1
      this.fetchData()
    },
    fetchPage(page) {
      this.listQuery.page = page
      this.fetchData()
    },
    changeSize(limit) {
      this.listQuery.limit = limit
      this.fetchData()
    },
    handleCurrentChange(currentRow, oldCurrentRow) {
      this.selRow = currentRow
    },
    resetForm() {
      this.form = {
        name: '',
        url: '',
        icon: '',
        id: ''
      }
    },
    add() {
      this.resetForm()
      this.formTitle = '添加商品类别',
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
            id: this.form.id
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
    edit() {
      if (this.checkSel()) {
        this.isAdd = false
        this.form = this.selRow
        this.formTitle = '编辑商品类别'
        this.formVisible = true
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
      }
    },
    bannerMgr(idCategory) {
      this.banner.visible = true
      this.banner.idCategory = idCategory
      categoryApi.getBanners(idCategory).then( response=>{
        this.banner.list = response.data
        console.log('banner',this.banner)
      })
    },
    bannerRemove(id) {
      categoryApi.removeBanner(this.banner.idCategory,id).then( response =>{
        this.$message({
          message: this.$t('common.optionSuccess'),
          type: 'success'
        })
        this.bannerMgr(this.banner.idCategory)
      })

    },
    bannerEdit() {
    },
    addBanner(){
      this.$router.push({path:'banner',query:{idCategory:this.banner.idCategory}})
    },
    attrKeyMgr(idCategory){
      this.attrKey.visible=true
      this.attrKey.idCategory = idCategory
      categoryApi.getAttrKeys(idCategory).then(response => {
        this.attrKey.list = response.data
      })
    },
    attrKeyAdd(){
      this.$prompt('请输入属性名', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
      }).then(({ value }) => {
        if(!value || value === ''){
          this.$message({
            type: 'warning',
            message: '属性名不能为空'
          })
        }
        attrKeyApi.save({attrName:value,idCategory:this.attrKey.idCategory}).then(response => {
          this.$message({
            type: 'success',
            message: '新增属性成功'
          })
          categoryApi.getAttrKeys(this.attrKey.idCategory).then( response2=>{
            this.attrKey.list = response2.data
          })

        })

      })
    },
    attrKeyEdit(item){
      console.log('item',item)
      this.$prompt('请输入属性名', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        inputValue:item.attrName,
      }).then(({ value }) => {
        if(!value || value === ''){
          this.$message({
            type: 'warning',
            message: '属性名不能为空'
          })
        }
          attrKeyApi.updateAttrName(item.id,value).then(response => {
            item.attrName = value
            this.$message({
              type: 'success',
              message: '编辑成功'
            })
          })
          return

      })

    },
    attrKeyRemove(id){
      console.log('id',id)
      attrKeyApi.remove(id).then( response => {
        this.$message({
          type: 'success',
          message: '删除成功'
        })
        categoryApi.getAttrKeys(this.attrKey.idCategory).then( response2=>{
          this.attrKey.list = response2.data
        })
      })

    },
    openAttrValDialog(item){
      this.attrVal.idAttrKey = item.id
      this.attrVal.visible=true
      console.log('item',item)
      attrValApi.getAttrVals(this.attrVal.idAttrKey).then( response2=>{
        this.attrVal.list = response2.data
      })
    },
    attrValAdd(){
      this.$prompt('请输入属性名', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
      }).then(({ value }) => {
        if(!value || value === ''){
          this.$message({
            type: 'warning',
            message: '属性名不能为空'
          })
        }
        attrValApi.save({attrVal:value,idAttrKey:this.attrVal.idAttrKey}).then(response => {
          this.$message({
            type: 'success',
            message: '新增属性成功'
          })
          attrValApi.getAttrVals(this.attrVal.idAttrKey).then( response2=>{
            this.attrVal.list = response2.data
          })

        })

      })
    },
    attrValEdit(item){
      console.log('item',item)
      this.$prompt('请输入属性名', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        inputValue:item.attrVal,
      }).then(({ value }) => {
        if(!value || value === ''){
          this.$message({
            type: 'warning',
            message: '属性名不能为空'
          })
        }
        attrValApi.updateAttrVal(item.id,value).then(response => {
          item.attrName = value
          this.$message({
            type: 'success',
            message: '编辑成功'
          })
          attrValApi.getAttrVals(this.attrVal.idAttrKey).then( response2=>{
            this.attrVal.list = response2.data
          })
        })
        return

      })

    },
    attrValRemove(id){
      console.log('id',id)
      attrValApi.remove(id).then( response => {
        this.$message({
          type: 'success',
          message: '删除成功'
        })
        attrValApi.getAttrVals(this.attrVal.idAttrKey).then( response2=>{
          this.attrVal.list = response2.data
        })
      })

    }

  }
}
