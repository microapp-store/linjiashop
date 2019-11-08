import Vue from 'vue';
import Router from 'vue-router';
import store from '@/store'
import { getToken } from '@/utils/auth'
Vue.use(Router);

const routes = [
  {
    path: '*',
    redirect: '/index'
  },
  {
    name: 'error',
    component: () => import('./view/common/error'),
    meta: {
      requireAuth:true,
      title: '系统异常'
    }
  },
  {
    name: 'login',
    component: () => import('./view/login'),
    meta: {
      title: '登录'
    }
  },
  {
    name: 'index',
    component: () => import('./view/index'),
    meta: {
      title: '邻家小铺'
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
      requireAuth:true,
      title: '会员中心'
    }
  },
  {
    name: 'order',
    component: () => import('./view/order'),
    meta: {
      requireAuth:true,
      title: '我的订单'
    }
  },
  {
    name: 'checkout',
    component: () => import('./view/order/checkout'),
    meta: {
      requireAuth:true,
      title: '提交订单'
    }
  },
  {
    name: 'payment',
    component: () => import('./view/order/payment'),
    meta: {
      requireAuth:true,
      title: '收银台'
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
      requireAuth:true,
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
  if (to.meta.requireAuth) {  // 判断该路由是否需要登录权限
    if (getToken()) {
      next();
    }
    else {
      next({
        path: '/login',
        query: {redirect: to.fullPath}  // 将跳转的路由path作为参数，登录成功后跳转到该路由
      })
    }
  }
  else {
    next();
  }
  //next();
});



export {
  router
};
