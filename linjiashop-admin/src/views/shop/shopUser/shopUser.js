import {getList, remove, save} from '@/api/shop/shopUser'

import { getApiUrl } from '@/utils/utils'
export default {
  data() {
    return {
      formVisible: false,
      formTitle: '添加用户',
      isAdd: true,
      form: {
        mobile: '',
        salt: '',
        password: '',
        nickName: '',
        avatar: '',
        id: ''
      },
      regDate: undefined,
      lastLoginTime:undefined,
      listQuery: {
        page: 1,
        limit: 20,
        mobile: undefined,
        nickName: undefined,
        startRegDate:undefined,
        endRegDate:undefined,
        startLastLoginTime:undefined,
        endLastLoginTime:undefined
      },
      total: 0,
      list: null,
      listLoading: true,
      selRow: {}
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
      if(this.regDate){
        this.listQuery['startRegDate'] = this.regDate[0]
        this.listQuery['endRegDate'] = this.regDate[1]
      }
      if(this.lastLoginTime){

        this.listQuery['startLastLoginTime'] = this.lastLoginTime[0]
        this.listQuery['endLastLoginTime'] = this.lastLoginTime[1]
      }
      getList(this.listQuery).then(response => {
        this.list = response.data.records
        for (const index in this.list) {
          let item = this.list[index]
          console.log(item)
          item.img = getApiUrl() + '/file/getImgStream?idFile=' + item.avatar
        }
        this.listLoading = false
        this.total = response.data.total
      })
    },
    search() {
      this.fetchData()
    },
    reset() {
      this.listQuery.mobile = ''
      this.listQuery.nickName = ''
      this.listQuery.startRegDate = ''
      this.listQuery.endRegDate = ''
      this.listQuery.startLastLoginTime = ''
      this.listQuery.endLastLoginTime = ''
      this.lastLoginTime = ''
      this.regDate = ''
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
    }

  }
}
