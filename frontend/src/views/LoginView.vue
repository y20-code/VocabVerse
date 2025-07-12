<template>
  <el-form
    ref="ruleFormRef"
    style="max-width: 600px"
    :model="ruleForm"
    :rules="rules"
    label-width="80px"
  >
    <el-form-item label="用户名" prop="username">
      <el-input v-model="ruleForm.username" placeholder="请输入你的用户名"/>
    </el-form-item>
    <el-form-item label="密码" prop="password">
      <el-input v-model="ruleForm.password" type="password" placeholder="请输入你的密码"/>
    </el-form-item>

    <el-form-item>
      <el-button type="primary" @click="submitForm(ruleFormRef)">
        登录
      </el-button>
      <el-button @click="goToRegister">
        注册
      </el-button>
    </el-form-item>

  </el-form>
</template>

<script lang="ts" setup>

import { useUserStore } from '@/stores/user'; // 导入你的 user store
import { useRouter } from 'vue-router';      // 导入 router 用于跳转
import { reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'; // 导入 Element Plus 的消息提示组件
import type { FormInstance, FormRules } from 'element-plus'

const userStore = useUserStore(); // 获取 store 实例
const router = useRouter();       // 获取 router 实例

interface RuleForm {
  username: string
  password: string
}

const ruleFormRef = ref<FormInstance>()
const ruleForm = reactive<RuleForm>({
  username: '',
  password: '',
})

const rules = reactive<FormRules<RuleForm>>({
  username: [
    { required: true, message: '请输入你的用户名', trigger: 'blur' },
    { min: 3, max: 16, message: '长度应为 3 到 16', trigger: 'blur' },
  ],
  password: [
    { required: true, message: '请输入你的密码', trigger: 'blur' },
    { min: 3, max: 16, message: '长度应为 3 到 16', trigger: 'blur' },
  ],
})

const goToRegister = () => {
    router.push('/register') // 跳转到注册页
}

const submitForm = async (formEl: FormInstance | undefined) => {
  if (!formEl) return

  // 步骤一：使用 try-catch 包裹整个校验和提交过程
  try {
    // 【关键】直接 await validate() 方法本身。
    // 如果校验失败，它会抛出异常，直接被下面的 catch 捕获。
    await formEl.validate();

    // 如果代码能执行到这里，说明上面的 validate() 成功了
    console.log('表单校验成功，准备调用登录接口...');

    // 步骤二：调用 userStore 的 login action
    // 【关键】将登录逻辑也放在同一个 try 块中
    await userStore.login(ruleForm);

    // 步骤三：处理登录成功
    ElMessage.success('登录成功！');
    router.push('/');

  } catch (error: any) {
    // 步骤四：统一处理所有失败情况
    // 无论是“表单校验失败”还是“登录接口失败”，都会被这里捕获
    if (error && error.response) {
      // 如果是 axios 的错误，它会有 response 对象
      ElMessage.error(error.response.data?.message || '登录失败，服务器错误');
    } else {
      // 否则，大概率是表单校验失败的错误
      console.log('表单校验失败:', error);
      ElMessage.warning('请检查输入项是否正确');
    }
  }
}

</script>
