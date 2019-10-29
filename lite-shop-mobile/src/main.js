import Vue from 'vue';
import App from './App';
import { router } from './router';
import VueLazyload from 'vue-lazyload'
Vue.use(VueLazyload, {
  preLoad: 1.3,
  error: 'dist/error.png',
  loading: 'dist/loading.gif',
  attempt: 1
})
new Vue({
  router,
  el: '#app',
  render: h => h(App)
});
