import Vue from 'vue';
import Router from 'vue-router';

Vue.use(Router);

const routes = [
  // {
  //   path: '/',
  //   redirect: 'index'
  // },
  {
    name: 'index',
    component: () => import('./view/index'),
    meta: {
      title: '邻家小铺'
    }
  },
  {
    name: 'category',
    component: () => import('./view/category'),
    meta: {
      title: '分类'
    }
  },
  {
    name: 'search',
    component: () => import('./view/search'),
    meta: {
      title: '发现'
    }
  },
  {
    name: 'user',
    component: () => import('./view/user'),
    meta: {
      title: '会员中心'
    }
  },
  {
    name: 'address',
    component: () => import('./view/address'),
    meta: {
      title: '收货地址'
    }
  },
  {
    name: 'address/edit',
    component: () => import('./view/address/edit'),
    meta: {
      title: '新增收货地址'
    }
  },
  {
    name: 'cart',
    component: () => import('./view/cart'),
    meta: {
      title: '购物车'
    }
  },
  {
    name: 'goods',
    component: () => import('./view/goods'),
    meta: {
      title: '商品详情'
    }
  }
];

// add route path
routes.forEach(route => {
  route.path = route.path || '/' + (route.name || '');
});

const router = new Router({ routes });

router.beforeEach((to, from, next) => {
  const title = to.meta && to.meta.title;
  if (title) {
    document.title = title;
  }
  next();
});

export {
  router
};
