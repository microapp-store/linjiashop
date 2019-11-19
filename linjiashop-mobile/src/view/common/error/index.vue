<template>
    <div>
        <van-nav-bar
                title="系统异常"
        />
        <p style="text-align: center;">
            {{message}}
        </p>

        <van-button to="index" type="primary" size="large" style="margin-top:70%">回到首页</van-button>
    </div>
</template>
<script>
    import {Button, NavBar} from 'vant'

    export default {
        components: {
            [NavBar.name]: NavBar,
            [Button.name]: Button
        },
        data() {
            return {
                message: ''
            }
        },
        mounted() {
            this.init()
        },
        methods: {
            init() {
                this.message = this.$route.query.message
                console.log('message', this.message)
            }
        }
    }
</script>
<style>

</style>
