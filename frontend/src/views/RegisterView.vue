<template>
  <el-form
    ref="ruleFormRef"
    style="max-width: 600px"
    :model="ruleForm"
    status-icon
    :rules="rules"
    label-width="80px"
    class="demo-ruleForm"
  >
    <el-form-item label="用户名" prop="username">
      <el-input v-model="ruleForm.username" placeholder="请输入你的用户名"/>
    </el-form-item>
    <el-form-item label="密码" prop="pass">
      <el-input v-model="ruleForm.pass" type="password" autocomplete="off" placeholder="请输入你的密码" />
    </el-form-item>
    <el-form-item label="确认密码" prop="checkPass">
      <el-input
        v-model="ruleForm.checkPass"
        type="password"
        autocomplete="off"
        placeholder="请再次输入你的密码"
      />
    </el-form-item>

    <el-form-item label="角色" prop="role">
      <el-select v-model="ruleForm.role" placeholder="请选择你的角色">
        <el-option label="学生" value="STUDENT" />
        <el-option label="老师" value="TEACHER" />
        <el-option label="家长" value="PARENT" />
      </el-select>
  </el-form-item>
    
    <el-form-item>
      <el-button type="primary" @click="submitForm(ruleFormRef)">
        注册
      </el-button>
      <el-button @click="goToLogin">登录</el-button>
    </el-form-item>
  </el-form>
</template>

<script lang="ts" setup>
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router';
import type { FormInstance, FormRules } from 'element-plus'
import { useUserStore } from '@/stores/user'; // 导入你的 user store
import { ElMessage } from 'element-plus'; // 导入 Element Plus 的消息提示组件

const router = useRouter(); // 获取路由实例
const ruleFormRef = ref<FormInstance>()
const userStore = useUserStore(); // 获取 store 实例

const validatePass = (rule: any, value: any, callback: any) => {
  if (value === '') {
    callback(new Error('请输入密码'))
  } else {
    if (ruleForm.checkPass !== '') {
      if (!ruleFormRef.value) return
      ruleFormRef.value.validateField('checkPass')
    }
    callback()
  }
}
const validatePass2 = (rule: any, value: any, callback: any) => {
  if (value === '') {
    callback(new Error('请再次输入密码'))
  } else if (value !== ruleForm.pass) {
    callback(new Error('两次输入的密码不匹配'))
  } else {
    callback()
  }
}

const ruleForm = reactive({
  pass: '',
  checkPass: '',
  username: '',
  role: 'STUDENT', // 默认角色为学生
})

const rules = reactive<FormRules<typeof ruleForm>>({
  pass: [
    { required: true,min: 3, max: 16, message: '长度应为 3 到 16', trigger: 'blur' },
    { validator: validatePass, trigger: 'blur' }],
  checkPass: [
    { required: true, min: 3, max: 16, message: '长度应为 3 到 16', trigger: 'blur' },
    { validator: validatePass2, trigger: 'blur' }],
  username: [
    { required: true, message: '请输入你的用户名', trigger: 'blur' },
    { min: 3, max: 16, message: '长度应为 3 到 16', trigger: 'blur' },
  ],
  role: [
    { required: true, message: '请选择你的角色', trigger: 'change' },
  ],
})

const goToLogin = () => {
  router.push('/login') // 跳转到登录页
}

const submitForm = async (formEl: FormInstance | undefined) => {
  if (!formEl) return
  try{
    await formEl.validate();

    console.log('表单校验成功，准备调用注册接口...');
    // 调用注册接口
    // 这里需要根据你的实际 API 调用逻辑来实现注册功能
    // 假设有一个 userStore.register 方法来处理注册逻辑
    const registerData = {
      username: ruleForm.username,
      password: ruleForm.pass, // 后端需要的是 password，不是 pass
      role: ruleForm.role,
    };
    await userStore.register(registerData);

    ElMessage.success('注册成功！即将跳转到登录页...');
    setTimeout(() => {
      router.push('/login');
    }, 1500); // 注册成功后跳转到登录页

  } catch (error: any) {
    ElMessage.error(error.response.data?.message || '注册失败，服务器错误');
  }
}

</script>
