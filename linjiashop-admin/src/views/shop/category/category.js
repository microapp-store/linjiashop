import {getList, remove, save, getBanners,removeBanner} from '@/api/shop/category'
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
      getList(this.listQuery).then(response => {
        this.list = response.data.records
        this.listLoading = false
        this.total = response.data.total
      })
    },
    search() {
      this.fetchData()
    },
    reset() {
      this.listQuery.id = ''
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
          save({
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
          remove(id).then(response => {
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
      getBanners(idCategory).then( response=>{
        this.banner.list = response.data
        console.log('banner',this.banner)
      })
    },
    bannerRemove(id) {
      console.log('item',id)
      removeBanner(this.banner.idCategory,id).then( response =>{
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
    }


  }
}
