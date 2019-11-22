<template>
  <div>
   商品详情
  </div>
</template>

<script>
  import utils from '@/utils/index.js'

  export default {
    data() {
      return {

      }
    },
    methods: {
      clickNav() {

      },
      formatPrice(price) {
        console.log('price', price)
        return utils.formatPrice(price)
      }
    },

    created() {
    },
    onLoad(options) {
      let id = options.id
      console.log('onload', id)
    }
  }
</script>

<style scoped>
</style>
