<template>
  <div class="wordbook-list">
    <!-- 使用 v-for 循环渲染 studyStore.wordbooks 数组 -->
    <el-card 
      v-for="book in studyStore.wordbooks" 
      :key="book.id" 
      class="box-card" 
      shadow="hover"
    >
      <template #header>
        <div class="card-header">
          <span>{{ book.name }}</span>
          <el-button class="button" text @click="startStudy(book.id)">开始学习</el-button>
        </div>
      </template>
      <div>{{ book.description }}</div>
      <div>共 {{ book.wordCount }} 词</div>
    </el-card>
  </div>
</template>

<script setup lang="ts">

import { onMounted } from 'vue';
import { useStudyStore } from '@/stores/study';
import { useRouter } from 'vue-router';
const router = useRouter();

const studyStore = useStudyStore();

onMounted(() => {
    studyStore.fetchWordbooks();
});

const startStudy = async (wordbookId: number) => {
  await studyStore.fetchWords(wordbookId); // 获取该词书的单词
  router.push('/study'); // 跳转到学习页面
};

</script>