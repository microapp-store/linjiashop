import orderApi from '@/api/shop/order'

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
      logs:[]
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
    sendOut(id) {
      //todo 发货只是更改订单状态为已发货，正常发货需要填写物流信息
      this.$confirm(this.$t('common.optionConfirm'), this.$t('common.tooltip'), {
        confirmButtonText: this.$t('button.submit'),
        cancelButtonText: this.$t('button.cancel'),
        type: 'warning'
      }).then(() => {
        this.fetchData()
        orderApi.sendOut(id).then(response => {
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
      orderApi.getLogs(id).then( response=>{
        this.logs = response.data
      })
    },
    addComment(id){
      console.log('idOrder', id)
      this.$prompt('请输入备注信息', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消'
      }).then(({ value }) => {
        orderApi.addComment(id,value)
      }).catch(() => {
      })
    }
  }
}
