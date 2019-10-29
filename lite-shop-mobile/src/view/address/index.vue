<template>
    <div>
        <van-nav-bar
                title="收货地址"
                left-text="返回"
                left-arrow
                @click-left="onClickLeft"
        />
        <van-address-list
                v-model="chosenAddressId"
                :list="list"
                disabled-text="以下地址超出配送范围"
                @add="onAdd"
                @edit="onEdit"
        />

    </div>
</template>

<script>
    import {AddressList, Col, Icon, NavBar, Row, Toast} from 'vant';

    export default {
        components: {
            [AddressList.name]: AddressList,
            [Toast.name]: Toast,
            [Row.name]: Row,
            [Col.name]: Col,
            [Icon.name]: Icon,

            [NavBar.name]: NavBar
        },
        data() {
            return {
                activeFooter: 3,
                chosenAddressId: '1',
                list: [
                    {
                        id: '1',
                        name: '张三',
                        tel: '13000000000',
                        address: '浙江省杭州市西湖区文三路 138 号东方通信大厦 7 楼 501 室'
                    },
                    {
                        id: '2',
                        name: '李四',
                        tel: '1310000000',
                        address: '浙江省杭州市拱墅区莫干山路 50 号'
                    }
                ]
            }
        },
        methods: {
            onAdd() {
                Toast('新增地址');
                this.$router.push('address/edit')
            },

            onEdit(item, index) {
                Toast('编辑地址:' + index);
                console.log(item)
                this.$router.push({path:'address/edit',query:{id:item.id}})
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
