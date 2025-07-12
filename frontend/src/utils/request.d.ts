// src/utils/request.d.ts

/**
 * 后端 API 响应的通用结构
 * @template T - 响应数据 data 的具体类型
 */
export interface ApiResponse<T = any> {
  /**
   * 业务状态码 (e.g., 200 for success, 401 for auth error)
   */
  code: number;

  /**
   * 响应消息，通常用于显示给用户
   */
  message: string;

  /**
   * 核心响应数据
   */
  data: T;
}