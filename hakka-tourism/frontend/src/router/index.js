import { createRouter, createWebHistory } from 'vue-router'
import Login from '../views/Login.vue'
import Layout from '../views/Layout.vue'
import ResourceList from '../views/ResourceList.vue'
import ResourceDetail from '../views/ResourceDetail.vue'
import RouteCustom from '../views/RouteCustom.vue'
import RouteList from '../views/RouteList.vue'
import OrderList from '../views/OrderList.vue'
import Statistics from '../views/Statistics.vue'
import ResourceManage from '../views/ResourceManage.vue'

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path: '/',
    component: Layout,
    redirect: '/resources',
    children: [
      {
        path: 'resources',
        name: 'ResourceList',
        component: ResourceList
      },
      {
        path: 'resources/:id',
        name: 'ResourceDetail',
        component: ResourceDetail
      },
      {
        path: 'route/custom',
        name: 'RouteCustom',
        component: RouteCustom
      },
      {
        path: 'route/list',
        name: 'RouteList',
        component: RouteList
      },
      {
        path: 'orders',
        name: 'OrderList',
        component: OrderList
      },
      {
        path: 'statistics',
        name: 'Statistics',
        component: Statistics
      },
      {
        path: 'manage',
        name: 'ResourceManage',
        component: ResourceManage
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token')
  if (to.path !== '/login' && !token) {
    next('/login')
  } else {
    next()
  }
})

export default router
