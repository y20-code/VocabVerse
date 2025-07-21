import { createRouter, createWebHistory } from 'vue-router'
import AppLayout from '@/layout/AppLayout.vue';
import { useUserStore } from '@/stores/user';

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
      // --- 独立布局的页面 (不需要顶部导航栏) ---
  {
    path: '/login',
    name: 'login',
    component: () => import('../views/LoginView.vue'),
  },
  {
    path: '/register',
    name: 'register',
    component: () => import('../views/RegisterView.vue'),
  },

  // --- 【关键】使用 AppLayout 布局的页面组 ---
  {
    path: '/',
    component: AppLayout, // 父路由使用 AppLayout 组件
    redirect: '/home', // 访问'/'时，自动重定向到'/home'
    children: [ // 所有子路由都会被渲染在 AppLayout 的 <router-view> 中
      {
        path: 'home', // 访问 /home 时
        name: 'home',
        component: () => import('../views/HomeView.vue'),
        meta: { requiresAuth: true } // 标记需要认证
      },
      {
        path: 'wordbooks', // 访问 /wordbooks 时
        name: 'wordbooks',
        component: () => import('../views/WordbookSelectView.vue'),
        meta: { requiresAuth: true }
      },
      {
        path: 'study', // 访问 /study 时
        name: 'study',
        component: () => import('../views/StudyView.vue'),
        meta: { requiresAuth: true }
      }
      
    ]
  }
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
