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
        orderSn: undefined,
        status:undefined,
        date:undefined,
        startDate:undefined,
        endDate:undefined
      },
      orderDate:undefined,
      total: 0,
      list: null,
      listLoading: true,
      selRow: {},
      logVisible: false,
      logs: [],
      expressList:[],
      //发货表单
      shipping:{
        show:false,
        id:'',
        idExpress:'',
        shippingSn:''
      },
      //物流信息
      shippingInfo:{
        show:false,
        form: {
          id: '',
          idExpress: '',
          shippingSn: '',
          traces: []
        }
      },
      query:{
        button:{
          lastStatus:'all',
          lastDate:'all',
          showCustomer:false,
          css:{
            status: {
              all: 'primary',
              unPay: 'default',
              unSend: 'default',
              sended: 'default',
              finished: 'default',
              cancel: 'default',
              refundIng: 'default',
              refund: 'default'
            },
            date:{
              all: 'primary',
              today: 'default',
              yesterday: 'default',
              seven: 'default',
              thirty:'default',
              month: 'default',
              year: 'default',
              customer: 'default'
            }
          },
          tag:{
            unPay:0,
            unSend:0,
            sended:0,
            finished:0,
            cancel:0,
            refundIng:0,
            refund:0
          }
        }
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
      orderApi.getOrderStatistic().then(response => {
        const data  = response.data
        for(const key in data){
          this.query.button.tag[key] = data[key]
        }
      })
      expressApi.queryAll().then( response=> {
        this.expressList = response.data
      })
    },
    fetchData() {
      this.listLoading = true
      if(this.orderDate){
        this.listQuery['startDate'] = this.orderDate[0]
        this.listQuery['endDate'] = this.orderDate[1]
      }

      orderApi.getList(this.listQuery).then(response => {
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
      this.listQuery.mobile = ''
      this.listQuery.orderSn = ''
      this.listQuery.page = 1
      this.fetchData()
    },
    queryByState(status){
      const lastStatus = this.query.button.lastStatus
      this.query.button.css.status[lastStatus]='default'
      this.query.button.css.status[status] = 'primary'
      this.query.button.lastStatus = status
      if(status !== 'all') {
        this.listQuery.status = status
      }else{
        this.listQuery.status = ''
      }
      this.fetchData()
    },
    queryByDate(date){
      const lastDate = this.query.button.lastDate
      this.query.button.css.date[lastDate]='default'
      this.query.button.css.date[date] = 'primary'
      this.query.button.lastDate = date
      if(date !== 'all' && date !=='customer') {
        this.listQuery.date = date
      }else{
        this.listQuery.date = ''
      }
      if(date === 'customer'){
        this.query.button.showCustomer=true
        return
      }else{
        this.query.button.showCustomer=false
      }
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
      this.shippingInfo.form = data
      const expressList = this.expressList
      let shipperCode =''
      for(const index in expressList){
        const express = expressList[index]
        if(express.id === data.idExpress){
          shipperCode = express.code
        }
      }
      orderApi.getShippingInfo(data.shippingSn,shipperCode).then( response => {
        this.shippingInfo.form['traces'] = response.data.traces
        this.shippingInfo.show = true
      })

    },
    openSendOutForm(id){
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
        window.location.href= getApiUrl() + '/file/download?fileName='+response.data.realFileName
      })
    }
  }
}
