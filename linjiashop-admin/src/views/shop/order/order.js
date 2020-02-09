import orderApi from '@/api/shop/order'
import expressApi from '@/api/system/express'
import { getApiUrl } from '@/utils/utils'
export default {
  data() {
    return {
      listQuery: {
        page: 1,
        limit: 20,
        mobile: undefined,
        orderSn: undefined
      },
      total: 0,
      list: null,
      listLoading: true,
      selRow: {},
      logVisible: false,
      logs: [],
      expressList:[],
      shipping:{
        show:false,
        id:'',
        idExpress:'',
        shippingSn:''
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
      orderApi.getList(this.listQuery).then(response => {
        this.list = response.data.records
        this.listLoading = false
        this.total = response.data.total
      })
    },
    search() {
      this.fetchData()
    },
    reset() {
      this.listQuery.mobile = ''
      this.listQuery.orderSn = ''
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
    formatPrice(price) {
      return (price / 100).toFixed(2);
    },
    viewShippingInfo(data){
      this.shipping.idExpress = data.idExpress
      this.shipping.shippingSn = data.shippingSn
      this.openSendOutForm(data.id)
    },
    openSendOutForm(id){
      if(this.expressList.length==0){
        expressApi.queryAll().then( response=> {
          this.expressList = response.data
        })
      }
      this.shipping.id = id
      this.shipping.show = true
    },
    sendOut() {
      this.$confirm(this.$t('common.optionConfirm'), this.$t('common.tooltip'), {
        confirmButtonText: this.$t('button.submit'),
        cancelButtonText: this.$t('button.cancel'),
        type: 'warning'
      }).then(() => {
        orderApi.sendOut(this.shipping.id,this.shipping.idExpress,this.shipping.shippingSn).then(response => {
          this.fetchData()
          this.shipping.show = false
          this.$message({
            message: '发货成功',
            type: 'success'
          })
        })
      }).catch(() => {
      })
    },
    viewLog(id) {
      this.logs = []
      this.logVisible = true
      orderApi.getLogs(id).then(response => {
        this.logs = response.data
      })
    },
    addComment(id) {
      console.log('idOrder', id)
      this.$prompt('请输入备注信息', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消'
      }).then(({value}) => {
        orderApi.addComment(id, value)
      }).catch(() => {
      })
    },
    exportXls() {
      orderApi.exportXls(this.listQuery).then(response => {
        window.location.href= getApiUrl() + '/file/download?idFile='+response.data.id
      })

    }
  }
}
