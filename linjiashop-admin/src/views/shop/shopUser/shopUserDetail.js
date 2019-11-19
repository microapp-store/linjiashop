import {getUserInfo, save} from '@/api/shop/shopUser'
import {getApiUrl} from '@/utils/utils'
export default {
  data() {
    return {
     userInfo:{},
      idUser:'',
      apiUrl:getApiUrl()
    }
  },
  created() {
    this.idUser  = this.$route.query.id
    this.init()
  },
  methods: {
    init() {
      this.fetchData()
    },
    fetchData() {

      if (this.idUser ) {
        getUserInfo(this.idUser).then(response => {
          this.userInfo = response.data
        })
      }
    }

  }
}
