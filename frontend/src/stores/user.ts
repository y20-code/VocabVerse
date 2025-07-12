// src/stores/user.ts
import { defineStore } from 'pinia';
import { ref } from 'vue';
import request,{type ApiResponse } from '@/utils/request';


// ... (你的类型定义，如 LoginData, UserInfo) ...

export const useUserStore = defineStore('user', () => {
  // --- State ---
  const token = ref<string>('');
  const userInfo = ref<any>({}); // 暂时用 any

  // --- Actions ---
  const setToken = (newToken: string) => {
    token.value = newToken;
  };

  const setUserInfo = (newUserInfo: any) => {
    userInfo.value = newUserInfo;
  };

  const login = async (loginData: any) => {
    try {
      const response = await request.post<ApiResponse<{ token: string }>>('/auth/login', loginData);
      if (response.data.code === 200) {
        setToken(response.data.data.token);
        // 可以在这里解析 Token 或请求 /user/info 接口来获取用户信息
        // setUserInfo(...)
      } else {
        throw new Error(response.data.message || '登录失败');
      }
    } catch (error) {
      // 清理可能存在的旧数据
      logout(); 
      throw error;
    }
  };

  /**
   * [这是什么？] 用户登出 Action
   * [它做什么？] 清除所有与用户登录状态相关的数据，重置为初始状态。
   */
  const logout = () => {
    // 1. 清除 state 中的 token
    token.value = '';
    // 2. 清除 state 中的用户信息
    userInfo.value = {};
    
    // 注意：pinia-plugin-persistedstate 会自动监听到 state 的变化，
    // 并将 localStorage 中对应的数据也一并清除。
    // 你不需要手动操作 localStorage。
    console.log('用户状态已清除');
  };

  /**
   * [这是什么？] 用户注册
   * [它做什么？] 调用注册接口，处理用户注册逻辑。
   */
  const register = async (registerData: any) => {
    try {
      const response = await request.post<ApiResponse<null>>('/auth/register', registerData);
      if (response.data.code !== 200) {
        throw new Error(response.data.message || '注册失败');
      }
    } catch (error) {
      console.error('注册失败:', error);
      throw error;
    }
  }


  // --- 返回 store 的公共 API ---
  return { 
    token, 
    userInfo, 
    login, 
    logout, // 【关键】将新创建的 logout 方法导出
    setToken,
    setUserInfo,
    register
  };
}, {
  persist: true,
});