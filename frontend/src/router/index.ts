import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue';
import { useUserStore } from '@/stores/user';

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/', // 根路径
    name: 'home',
    component: HomeView,
    meta: { requiresAuth: true }
    },
    {
      path:'/login',
      name: 'login',
      component: () => import('../views/LoginView.vue'),
    },
    {
      path:'/register',
      name: 'register',
      component: () => import('../views/RegisterView.vue'),
    },
  ],
})

router.beforeEach((to, from, next) => {
  // 在守卫中获取 store 实例
  const userStore = useUserStore();
  const isAuthenticated = !!userStore.token; // 判断是否已登录

  // 判断目标路由是否需要认证
  if (to.meta.requiresAuth && !isAuthenticated) {
    // 如果需要认证，但用户未登录
    // 重定向到登录页
    console.log('访问受限，跳转到登录页');
    next({ name: 'login' });
  } else if (to.name === 'login' && isAuthenticated) {
    // 如果用户已登录，但想访问登录页
    // 直接让他去首页
    console.log('已登录，跳转到首页');
    next({ name: 'home' });
  } else {
    // 其他所有情况，直接放行
    next();
  }
});

export default router
