<template>
  <div v-if="currentWord">
    <el-card class="word-card">
      <div class="word-display">{{ currentWord.word }}</div>
      <div class="pronunciation">{{ currentWord.pronunciation }}</div>
      <div class="definition">{{ currentWord.definition }}</div>
      <!-- ... 例句等 ... -->
    </el-card>
    <div class="action-buttons">
      <el-button type="danger" @click="handleAction(false)">不认识</el-button>
      <el-button type="success" @click="handleAction(true)">认识</el-button>
    </div>
  </div>
  <div v-else>
    <p>正在加载单词或本组已学习完毕...</p>
  </div>
</template>


<script setup lang="ts">
import { computed } from 'vue';
import { useStudyStore } from '@/stores/study';

const studyStore = useStudyStore();

// 创建一个计算属性，它会根据 currentWordIndex 自动返回当前要显示的单词
const currentWord = computed(() => {

  if (studyStore.currentWordList.length > 0) {
    return studyStore.currentWordList[studyStore.currentWordIndex];
  }

  return null; // 如果列表为空，返回 null
  
});

const handleAction = async (isKnown: boolean) => {
  if (!currentWord.value) return;

  // 1. 记录学习行为
  await studyStore.recordLearning({ 
    wordId: currentWord.value.id, 
    isKnown: isKnown 
  });

  // 2. 切换到下一个单词
  if (studyStore.currentWordIndex < studyStore.currentWordList.length - 1) {
    studyStore.currentWordIndex++;
  } else {
    // 所有单词都学完了
    alert('本组单词学习完毕！');
    // 可以跳转回词书列表页
    // router.push('/wordbooks');
  }
};
</script>