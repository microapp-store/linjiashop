import {getUserInfo, save} from '@/api/shop/shopUser'
import {getList} from '@/api/shop/address'
import {getApiUrl} from '@/utils/utils'

export default {
  data() {
    return {
      userInfo: {info:{}},
      idUser: '',
      apiUrl: getApiUrl(),
      addressList:[]
    }
  },
  created() {
    this.idUser = this.$route.query.id
    this.init()
  },
  methods: {
    init() {
      this.fetchData()
    },
    fetchData() {

      if (this.idUser) {
        getUserInfo(this.idUser).then(response => {
          this.userInfo = response.data
        })
        getList({
          page: 1,
          limit: 20, idUser: this.idUser
        }).then(response => {
          this.addressList = response.data.records
        })
      }
    }

  }
}
