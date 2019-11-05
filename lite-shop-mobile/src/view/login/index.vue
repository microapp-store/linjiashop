<template>
    <div>
        <img class="user-poster" src="@/assets/img/banner.jpg" >


            <van-cell-group>
            <van-field
                    v-model="mobile"
                    required
                    clearable
                    label="手机号"
                    placeholder="请输入手机号"
            />
            </van-cell-group>
            <van-cell-group v-show="show1">
                <van-field
                        v-model="sms"
                        center
                        clearable
                        label="短信验证码"
                        placeholder="请输入短信验证码"
                >
                    <van-button slot="button" size="small" type="primary">发送验证码</van-button>
                </van-field>
            </van-cell-group>
            <van-cell-group  v-show="show2">
            <van-field
                    v-model="password"
                    type="password"
                    label="密码"
                    placeholder="请输入密码"
                    required
            />

        </van-cell-group>
            <div class="button-group">
            <van-button type="warning" size="large"   v-show="show1" @click="loginOrRegister">立即登录/注册</van-button>

            <van-button type="info" size="large"   v-show="show1" @click="toLoginByPassword">用户名密码登录</van-button>

            <van-button type="warning" size="large"  v-show="show2"  @click="loginByPassword">登录</van-button>


        <van-button type="info" size="large"  v-show="show2" @click="toRegister">手机短信登录/注册</van-button>
            </div>
        <van-tabbar v-model="activeFooter">
            <van-tabbar-item icon="home-o"  replace to="/index">首页</van-tabbar-item>
            <van-tabbar-item icon="search"  replace to="/search">发现</van-tabbar-item>
            <van-tabbar-item icon="cart-o"  replace to="/cart">订单</van-tabbar-item>
            <van-tabbar-item icon="user-o"  replace to="/user">我的</van-tabbar-item>
        </van-tabbar>
    </div>
</template>

<script>
    import { Row, Col, Icon, Cell, CellGroup,Field,Button,
        Tabbar,
        TabbarItem} from 'vant';

    export default {
        components: {
            [Row.name]: Row,
            [Col.name]: Col,
            [Icon.name]: Icon,
            [Cell.name]: Cell,
            [CellGroup.name]: CellGroup,
            [Tabbar.name]: Tabbar,
            [TabbarItem.name]: TabbarItem,
            [Field.name]: Field,
            [Button.name]: Button
    },
        data() {
            return {
                mobile:'',
                password:'',
                activeFooter:3,
                show1:true,
                show2:false,
            }
        },
        methods:{
            toLoginByPassword(){
                this.show1 = false;
                this.show2 = true;
            },
            toRegister(){
                this.show2 = false;
                this.show1 = true;
            },
            loginOrRegister(){

            },
            loginByPassword(){

            }
        }
    };
</script>

<style lang="less">
    .button-group{
     margin: 15px;
        .van-button{
            margin-top: 10px;
        }
    }
    .user {
        &-poster {
            width: 100%;
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
