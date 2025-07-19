
/**
 * 词书对象的类型定义
 * 与后端 com.yls.vocabverse.entity.Wordbook.java 对应
 */
export interface Wordbook {
  id: number; // 或者 Long 类型在前端通常对应 number
  name: string;
  description: string | null; // description 可能为 null
  wordCount: number;
}

/**
 * 单词对象的类型定义
 * 与后端 com.yls.vocabverse.entity.Word.java 对应
 */
export interface Word {
  id: number;
  wordbookId: number;
  word: string;
  pronunciation: string | null;
  definition: string;
  exampleSentence: string | null;
}

/**
 * 学习记录请求载荷的类型定义
 * 与后端 com.yls.vocabverse.dto.LearningRecordRequest.java 对应
 */
export interface LearningRecordPayload {
  wordId: number;
  isKnown: boolean;
}