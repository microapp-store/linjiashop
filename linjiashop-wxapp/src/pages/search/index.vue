<template>
  <div class="search">
    <van-search  placeholder="搜索商品关键字" @search="onSearch" @cancel="onCancel"/>
  </div>
</template>

<script>
  import {formatTime} from '@/utils/index'
  import card from '@/components/card'

  export default {
    components: {
      card
    },

    data() {
      return {
        listQuery: {
          key: undefined
        }
      }
    },

    created() {
      let logs
      if (mpvuePlatform === 'my') {
        logs = mpvue.getStorageSync({key: 'logs'}).data || []
      } else {
        logs = mpvue.getStorageSync('logs') || []
      }
      this.logs = logs.map(log => formatTime(new Date(log)))
    },
    methods: {
      onSearch(event) {
        console.log(event)
      },
      onCancel(event) {
        console.log(event)
      }
    }
  }
</script>

<style>
  .log-list {
    display: flex;
    flex-direction: column;
    padding: 40 rpx;
  }

  .log-item {
    margin: 10 rpx;
  }
</style>
