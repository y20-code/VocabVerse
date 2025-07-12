import axios, { type AxiosInstance, type AxiosRequestConfig } from 'axios';
import { useUserStore } from '@/stores/user';
import { ElMessage } from 'element-plus';
import router from '@/router';


export interface ApiResponse<T = any> {
  code: number;
  message: string;
  data: T;
}

// 创建 Axios 实例
const instance: AxiosInstance = axios.create({
  // baseURL: '/api/v1', // 使用 Vite 的 proxy 代理时，这里可以用相对路径
  baseURL: 'http://localhost:8088/api/v1', // 在开发环境下，也可以直接写死后端地址
  timeout: 10000, // 请求超时时间
});

// 请求拦截器 (Request Interceptor)
instance.interceptors.request.use(
  (config) => {
    // 在发送请求之前做些什么
    // 1. 获取 Pinia store
    // 注意：拦截器执行时，可能还未完成 Pinia 的安装，
    // 直接在文件顶部调用 useUserStore() 可能会报错。
    // 最安全的方式是在拦截器函数内部获取。
    const userStore = useUserStore();
    
    // 2. 如果存在 token，则为每个请求头添加 Authorization
    if (userStore.token) {
      config.headers.Authorization = `Bearer ${userStore.token}`;
    }
    
    return config;
  },
  (error) => {
    // 对请求错误做些什么
    return Promise.reject(error);
  }
);

// 响应拦截器 (Response Interceptor)
instance.interceptors.response.use(
  (response) => {
    // 对响应数据做点什么
    // 假设后端的标准响应格式是 { code, message, data }
    const res = response.data;

    // 如果业务状态码不是 200，就判定为错误
    if (res.code !== 200) {
      ElMessage.error(res.message || 'Error');
      
      // 比如：50008: 非法 token; 50012: 其他客户端登录; 50014: Token 过期;
      if (res.code === 50008 || res.code === 50012 || res.code === 50014) {
        // 可以做一些登出操作
        const userStore = useUserStore();
        userStore.logout();
        router.push('/login');
      }
      
      return Promise.reject(new Error(res.message || 'Error'));
    } else {
      // 如果业务状态码是 200，则直接返回响应数据
      return response;
    }
  },
  (error) => {
    // 对响应错误做点什么
    console.error('API Error: ' + error); // for debug
    ElMessage.error(error.message);
    return Promise.reject(error);
  }
);

// 导出封装好的实例
export default instance;