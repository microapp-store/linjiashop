import {getUserInfo, save} from '@/api/shop/shopUser'
import {getList} from '@/api/shop/address'
import {getApiUrl} from '@/utils/utils'

export default {
  data() {
    return {
      userInfo: {info:{}},
      idUser: '',
      apiUrl: getApiUrl(),
      addressList:[],
      avatarUrl:''
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
          if(this.userInfo.info.avatar!=='') {
            this.avatarUrl = this.apiUrl + '/file/getImgStream?idFile=' + this.userInfo.info.avatar
          }else{
            this.avatarUrl = this.userInfo.info.wechatHeadImgUrl
          }
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
