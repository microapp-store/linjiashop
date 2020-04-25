import topicApi from '@/api/promotion/topic'
import goodsApi from '@/api/shop/goods'
import articleApi from '@/api/cms/article'
import {getApiUrl} from '@/utils/utils'
import permission from '@/directive/permission/index.js'

export default {
  directives: {permission},
  data() {
    return {
      formVisible: false,
      formTitle: '添加专题',
      isAdd: true,
      form: {
        title: '',
        idArticle: '',
        idGoodsList: '',
        pv: '',
        id: ''
      },
      listQuery: {
        page: 1,
        limit: 20,
        id: undefined,
        disabled: ''
      },
      options: [
        {label: '是', value: 1},
        {label: '否', value: 0}
      ],
      rangeDate: '',
      total: 0,
      list: null,
      listLoading: true,
      selRow: {},
      apiUrl: getApiUrl(),
      showArticle: false,
      article: {title: '', content: ''},
      goodsList:[],
      selectedGoods:[],
      articleList:[],
      searchLoading:false,
      showTopic:false,
      topicUrl:'',
      topicDetail:''
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
  computed: {

    //表单验证
    rules() {
      return {
        // cfgName: [
        //   { required: true, message: this.$t('config.name') + this.$t('common.isRequired'), trigger: 'blur' },
        //   { min: 3, max: 2000, message: this.$t('config.name') + this.$t('config.lengthValidation'), trigger: 'blur' }
        // ]
      }
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
      let queryData = this.listQuery
      if (this.rangeDate) {
        queryData['startDate'] = this.rangeDate[0]
        queryData['endDate'] = this.rangeDate[1]

      }
      topicApi.getList(this.listQuery).then(response => {
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
      this.listQuery.disabled = ''
      this.rangeDate = ''
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
        title: '',
        idArticle: '',
        idGoodsList: '',
        pv: '',
        id: ''
      }
    },
    add() {
      this.resetForm()
      this.formTitle = '添加专题',
        this.formVisible = true
      this.isAdd = true
    },
    save() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          topicApi.save({
            title: this.form.title,
            idArticle: this.form.idArticle,
            idGoodsList: this.form.idGoodsList.join(','),
            pv: this.form.pv,
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
        this.formTitle = '编辑专题'
        this.formVisible = true
      }
    },
    remove() {
      if (this.checkSel()) {
        const id = this.selRow.id
        this.$confirm(this.$t('common.deleteConfirm'), this.$t('common.tooltip'), {
          confirmButtonText: this.$t('button.submit'),
          cancelButtonText: this.$t('button.cancel'),
          type: 'warning'
        }).then(() => {
          topicApi.remove(id).then(response => {
            this.$message({
              message: this.$t('common.optionSuccess'),
              type: 'success'
            })
            this.fetchData()
          }).catch(err => {
            this.$notify.error({
              title: '错误',
              message: err
            })
          })
        }).catch(() => {
        })
      }
    },
    viewArticle(article) {
      this.article = article
      this.showArticle = true
    },
    changeDisabled(data) {
      console.log('data', data)
      topicApi.changeDisabled(data.id,data.disabled).then(response => {
        this.$message({
          message: data.disabled?'禁用成功':'启用成功',
          type: 'success'
        })
      })
    },
    searchGoods(query) {
      console.log('query',query)
      if (query !== '') {
        this.searchLoading = true
        goodsApi.getList({ page: 1,
          limit: 20,
          name:query
        }).then( response => {
          let goodsList = response.data.records
          if(this.selectedGoods.length>0){
            //将已选择的商品列表和新的搜索结果列表合并为新的下拉列表【和changeGoods中的处理方式呼应】
            this.selectedGoods.forEach(function(item,index,arr){
              let hasInArr = false
              goodsList.forEach(function(item2,index2,arr2){
                if(item2.id === item.id ){
                  hasInArr = true
                }
              })
              if(!hasInArr){
                goodsList.push(item)
              }

            })
          }
          this.searchLoading = false
          this.goodsList = goodsList
        })
      } else {
        this.goodsList = [];
      }
    },
    changeGoods(val){
      //选中一个值的时候，将该值存放在以选择列表中，以便下一次模糊搜索的时候将该列表和搜索结果列表合并为新的下拉列表【临时处理多次搜索的时候导致前面已选中结果label丢失的情况，暂时没有更好的解决方法，先这么处理了】
      const goodsList = this.goodsList
      let selectedGoods = new Array()
      val.forEach(function(item,index,arr){
        goodsList.forEach(function(item2,index2,arr2){
          if(item2.id === item){
            selectedGoods.push(item2)
          }
        })
      })
      this.selectedGoods = selectedGoods
      console.log('val',val)
    },
    searchArticle(query) {
      console.log('query',query)
      if (query !== '') {
        this.searchLoading = true
        articleApi.getList({ page: 1,
          limit: 20,
          title:query
        }).then( response => {
          this.articleList = response.data.records
          console.log('articleList',this.articleList)
          this.searchLoading = false
        })
      } else {
        this.goodsList = [];
      }
    },
    view(id){
      //todo 预览使用了手机h5站点的地址,后期可以将h5 站点地址配置在系统参数中
      this.topicDetail = ' <iframe src="https://linjiashop.microapp.store/#/topic/'+id+'" width="100%" height="667px" frameborder="0" scrolling="auto"></iframe>'
      this.showTopic = true
    }

  }
}
