<template>
    <div>
        <van-nav-bar
                title="编辑收货地址"
                left-text="返回"
                left-arrow
                @click-left="onClickLeft"
        />
        <van-address-edit
                :area-list="areaList"
                show-postal
                :show-delete="showDelete"
                show-set-default
                show-search-result
                :search-result="searchResult"
                area-columns-placeholder="['请选择', '请选择', '请选择']"
                @save="onSave"
                @delete="onDelete"
                @change-detail="onChangeDetail"
        />

    </div>
</template>

<script>
    import {AddressEdit , Col, Icon, NavBar, Row, Toast} from 'vant';

    export default {
        components: {
            [AddressEdit .name]: AddressEdit ,
            [Toast.name]: Toast,
            [Row.name]: Row,
            [Col.name]: Col,
            [Icon.name]: Icon,

            [NavBar.name]: NavBar
        },
        data() {
            return {
                showDelete:false,
                areaList,
                searchResult: []
            }
        },
        created(){
          this.init()
        },
        methods: {
            init() {
                let id = this.$route.query.id
                if(id){
                    this.showDelete = true
                    console.log(id)
                    Toast('修改地址:' + id);
                }else{
                    Toast('新增地址' );
                }
            },
            onSave() {
                Toast('save');
            },
            onDelete() {
                Toast('delete');
            },
            onChangeDetail(val) {
                if (val) {
                    this.searchResult = [{
                        name: '黄龙万科中心',
                        address: '杭州市西湖区'
                    }];
                } else {
                    this.searchResult = [];
                }
            },
            onClickLeft() {
                this.$router.go(-1)
            }
        }
    };
</script>

<style lang="less">
    .user {
        &-poster {
            width: 100%;
            height: 53vw;
            display: block;
        }

        &-group {
            margin-bottom: 15px;
        }

        &-links {
            padding: 15px 0;
            font-size: 12px;
            text-align: center;
            background-color: #fff;

            .van-icon {
                display: block;
                font-size: 24px;
            }
        }
    }
</style>
