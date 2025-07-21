import {ref} from 'vue';
import {defineStore} from 'pinia';
import type { Wordbook, Word, LearningRecordPayload } from '@/types/study';
import request from '@/utils/request';

export const useStudyStore = defineStore('study', () => {

    const wordbooks = ref<Wordbook[]>([]);
    const currentWordList = ref<Word[]>([]);
    const currentWordIndex = ref<number>(0);

    const actions = {
        async fetchWordbooks() {
            const response = await request.get('/wordbooks');
            wordbooks.value = response.data.data;
        },

        async fetchWords(wordbookId: number) {
            const response = await request.get(`/wordbooks/${wordbookId}/words`);
            currentWordList.value = response.data.data;
            currentWordIndex.value = 0; // 重置当前单词索引
        },

        async recordLearning(payload: LearningRecordPayload) {
            await request.post('/learning-records', payload);
        },
        async fetchwordcount(wordbookId: number) {

        }
    }

    return {
        wordbooks,
        currentWordList,
        currentWordIndex,
        ...actions
    }
});