import Vue from 'vue'
import Router from 'vue-router'
import store from '@/store'
import storage from '@/utils/storage'
Vue.use(Router)

const routes = [
    {
        path: '*',
        redirect: '/index'
    },
    {
        name: 'error',
        component: () => import('./view/common/error'),
        meta: {
            requireAuth: true,
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
        path: '/topic/:id',
        name: 'topic',
        component: () => import('./view/topic'),
        meta: {
            title: '专题推荐'
        }
    },
    {
        name: 'list',
        component: () => import('./view/goods/list'),
        meta: {
            title: '邻家小铺'
        }
    },
    {
        path: '/goods/:id',
        name: 'goods',
        component: () => import('./view/goods'),
        meta: {
            title: '商品详情'
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
            requireAuth: true,
            title: '会员中心'
        }
    },
    {
        name: 'setting',
        component: () => import('./view/setting'),
        meta: {
            requireAuth: true,
            title: '设置'
        }
    },
    {
        name: 'updateUserName',
        component: () => import('./view/setting/updateUserName'),
        meta: {
            requireAuth: true,
            title: '修改姓名'
        }
    },
    {
        name: 'updateMobile',
        component: () => import('./view/setting/updateMobile'),
        meta: {
            requireAuth: true,
            title: '修改手机号'
        }
    },
    {
        name: 'updatePwd',
        component: () => import('./view/setting/updatePwd'),
        meta: {
            requireAuth: true,
            title: '修改密码'
        }
    },
    {
        name: 'avatar',
        component: () => import('./view/setting/avatar'),
        meta: {
            requireAuth: true,
            title: '修改头像'
        }
    },
    {
        name: 'order',
        component: () => import('./view/order'),
        meta: {
            requireAuth: true,
            title: '我的订单'
        }
    },
    {
        name: 'order/detail/:orderSn',
        component: () => import('./view/order/detail'),
        meta: {
            requireAuth: true,
            title: '我的订单'
        }
    },
    {
        name: 'order/expressInfo/:orderSn',
        component: () => import('./view/order/expressInfo'),
        meta: {
            requireAuth: true,
            title: '物流信息'
        }
    },
    {
        name: 'favorite',
        component: () => import('./view/favorite'),
        meta: {
            requireAuth: true,
            title: '喜欢的商品'
        }
    },
    {
        name: 'payment/callback/:orderSn',
        component: () => import('./view/order/payment/callback'),
        meta: {
            requireAuth: true,
            title: '支付结果'
        }
    },
    {
        name: 'checkout',
        component: () => import('./view/order/checkout'),
        meta: {
            requireAuth: true,
            title: '提交订单'
        }
    },
    {
        name: 'payment',
        component: () => import('./view/order/payment'),
        meta: {
            requireAuth: true,
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
            // requireAuth: true,
            title: '购物车'
        }
    }
]

routes.forEach(route => {
    route.path = route.path || '/' + (route.name || '');
})

const router = new Router({routes})

router.beforeEach((to, from, next) => {
    const title = to.meta && to.meta.title
    if (title) {
        document.title = title;
    }
    // 判断该路由是否需要登录权限
    if (to.meta.requireAuth) {
        if(storage.getToken()){
            next();
        } else {
            next({
                path: '/login',
                query: {redirect: to.fullPath}  // 将跳转的路由path作为参数，登录成功后跳转到该路由
            })
        }
    } else {
        next()
    }
})


export {
    router
}
