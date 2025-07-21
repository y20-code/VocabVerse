<!-- src/layout/AppLayout.vue -->
<template>
  <div class="common-layout">
    <el-container>
      <!-- 顶部导航栏 -->
      <el-header class="app-header">
        <div class="logo">VocabVerse</div>
        <div class="flex-grow"/>
        <div class="user-controls">
          <!-- 【关键】只有在用户登录后才显示这些控件 -->
          <template v-if="userStore.isAuthenticated">
            <span class="username">你好, {{ userStore.userInfo.username }}</span>
            <el-button type="danger" plain @click="handleLogout">
              退出登录
            </el-button>
          </template>
        </div>
      </el-header>
      
      <!-- 主要内容区 -->
      <el-main class="app-main">
        <!-- 【核心】这里是“插座”，用来显示子路由页面的内容 -->
        <router-view />
      </el-main>
    </el-container>
  </div>
</template>

<script setup lang="ts">
import { useUserStore } from '@/stores/user';
import { useRouter } from 'vue-router';
import { ElMessage, ElMessageBox } from 'element-plus';

const userStore = useUserStore();
const router = useRouter();


const handleLogout = () => {
  ElMessageBox.confirm('您确定要退出登录吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning',
  }).then(() => {
    userStore.logout();
    ElMessage.success('您已成功退出登录');
    router.push('/login');
  }).catch(() => {
    ElMessage.info('已取消退出操作');
  });
};
</script>

<style scoped>
.app-header {
  display: flex;
  align-items: center;
  background-color: #ffffff;
  border-bottom: 1px solid var(--el-border-color-light);
  padding: 0 20px;
}
.logo {
  font-size: 1.5em;
  font-weight: bold;
  color: var(--el-color-primary);
}
.flex-grow {
  flex-grow: 1;
}
.user-controls {
  display: flex;
  align-items: center;
}
.username {
  margin-right: 15px;
  color: #606266;
}
.app-main {
  background-color: #f0f2f5;
  min-height: calc(100vh - 60px); /* 减去 header 的高度 */
}
</style>