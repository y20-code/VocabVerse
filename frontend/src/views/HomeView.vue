<!-- src/views/HomeView.vue -->
<template>
  <div class="home-container">
    <h1>欢迎回来，{{ userStore.userInfo.username || '新朋友' }}！</h1>
    <p>你已成功登录 VocabVerse！</p>
    <p>你的 Token 是：</p>
    <div class="token-display">{{ userStore.token }}</div>
    <el-button type="danger" @click="handleLogout">退出登录</el-button>
  </div>
</template>

<script setup lang="ts">
import { useUserStore } from '@/stores/user';
import { useRouter } from 'vue-router';
import { ElMessage } from 'element-plus';

const userStore = useUserStore();
const router = useRouter();

const handleLogout = () => {
  userStore.logout(); // 调用 store 的登出 action
  ElMessage.success('您已成功退出登录');
  router.push('/login'); // 跳转回登录页
};
</script>

<style scoped>
.home-container {
  text-align: center;
  padding: 50px;
}
.token-display {
  word-break: break-all;
  background-color: #f4f4f5;
  padding: 15px;
  border-radius: 4px;
  margin-top: 10px;
  font-family: 'Courier New', Courier, monospace;
}
</style>