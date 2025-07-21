<template>
  <div class="home-container">
    <h1 v-if="userStore.userInfo.username">
      欢迎回来，{{ userStore.userInfo.username }}！
    </h1>
    <h1 v-else>
      欢迎来到 VocabVerse！
    </h1>
    <p>你已成功登录！现在可以开始你的学习之旅了。</p>
    <el-button type="primary" @click="goToStudy">
      开始学习
    </el-button>
  </div>
</template>

<script setup lang="ts">
import { useUserStore } from '@/stores/user';
import { useRouter } from 'vue-router';

const userStore = useUserStore();
const router = useRouter();

const goToStudy = () => {
  router.push('/wordbooks');
};
</script>

<style scoped>
.home-container {
  text-align: center;
  padding: 50px;
}
</style>