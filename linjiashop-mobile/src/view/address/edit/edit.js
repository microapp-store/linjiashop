import areaData from './area.js'
import address from '@/api/address'
import {AddressEdit , NavBar, Toast} from 'vant'

export default {
    components: {
        [AddressEdit .name]: AddressEdit ,
        [Toast.name]: Toast,
        [NavBar.name]: NavBar
    },
    data() {
        return {
            showDelete:false,
            areaList:areaData,
            columnsPlaceholder: ['请选择', '请选择', '请选择'],
            searchResult: [],
            addressInfo:{}
        }
    },
    created(){
        this.init()
    },
    methods: {
        init() {
            const id = this.$route.query.id
            if(id){
                this.showDelete = true
                address.get(id).then( response => {
                    this.addressInfo = response.data
                }).catch( (err) => {
                    Toast.fail(err)
                })
            }
        },
        onSave(info) {
            let addressEntity = {
                province:info.province,
                city:info.city,
                district:info.county,
                addressDetail:info.addressDetail,
                areaCode:info.areaCode,
                tel:info.tel,
                name:info.name,
                postCode:info.postalCode,
                isDefault : info.isDefault
            }
            if(info.id){
                addressEntity.id = info.id
            }
            address.save(addressEntity).then( response => {
                Toast('保存成功')
                this.$router.push('address')
            }).catch( (err) => {
                Toast.fail(err)
            })
        },

        onChangeDetail(val) {
            if (val) {
                //todo 这里可以根据gps定位和关键字模糊搜索详细地址列表
                this.searchResult = []
                // this.searchResult = [{
                //     name: '黄龙万科中心',
                //     address: '杭州市西湖区'
                // }];
            } else {
                this.searchResult = []
            }
        },
        onClickLeft() {
            this.$router.go(-1)
        },
        changeDefault(isDefault){
             if(this.addressInfo.id) {
                 address.changeDefault(this.addressInfo.id, isDefault).then(response => {
                     Toast('修改成功')
                 }).catch((err) => {
                     Toast.fail(err)
                 })
             }

        },
        onDelete() {
            address.remove(this.addressInfo.id).then( response => {
                Toast('删除成功')
                this.$router.push('address')
            }).catch( (err) => {
                Toast.fail(err)
            })
        }
    }
}
