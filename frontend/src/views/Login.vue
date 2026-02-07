<template>
  <div class="login-container">
    <el-card class="login-card">
      <template #header>
        <div class="login-header">
          <h1>Login</h1>
          <p>Welcome to Workflow Management System</p>
        </div>
      </template>

      <el-form
        ref="formRef"
        :model="form"
        :rules="rules"
        @submit.prevent="handleLogin"
      >
        <el-form-item label="Username" prop="username">
          <el-input
            v-model="form.username"
            placeholder="Enter your username"
            clearable
          />
        </el-form-item>

        <el-form-item label="Password" prop="password">
          <el-input
            v-model="form.password"
            type="password"
            placeholder="Enter your password"
            clearable
            show-password
          />
        </el-form-item>

        <el-form-item>
          <el-button
            type="primary"
            native-type="submit"
            :loading="loading"
            class="login-button"
          >
            {{ loading ? 'Logging in...' : 'Login' }}
          </el-button>
        </el-form-item>
      </el-form>

      <div v-if="errorMessage" class="error-message">
        {{ errorMessage }}
      </div>

      <div class="register-link">
        <router-link to="/register">Don't have an account? Register here</router-link>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import authService from '../services/authService'

const router = useRouter()
const formRef = ref(null)
const loading = ref(false)
const errorMessage = ref('')

const form = ref({
  username: 'admin',
  password: 'admin123'
})

const rules = {
  username: [
    { required: true, message: 'Please enter username', trigger: 'blur' }
  ],
  password: [
    { required: true, message: 'Please enter password', trigger: 'blur' }
  ]
}

const handleLogin = async () => {
  await formRef.value.validate()
  
  loading.value = true
  errorMessage.value = ''

  try {
    const result = await authService.login(form.value.username, form.value.password)
    
    if (result.success) {
      ElMessage.success('Login successful!')
      await router.push('/dashboard')
    } else {
      errorMessage.value = result.message || 'Login failed'
      ElMessage.error(errorMessage.value)
    }
  } catch (error) {
    errorMessage.value = 'An error occurred during login'
    ElMessage.error(errorMessage.value)
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.login-card {
  width: 100%;
  max-width: 450px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.15);
}

.login-header {
  text-align: center;
  padding: 20px 0;
}

.login-header h1 {
  margin: 0 0 10px 0;
  color: #333;
  font-size: 28px;
}

.login-header p {
  margin: 0;
  color: #999;
  font-size: 14px;
}

.login-button {
  width: 100%;
  height: 40px;
  font-size: 16px;
}

.error-message {
  color: #f56c6c;
  font-size: 14px;
  margin-top: -10px;
  margin-bottom: 15px;
  text-align: center;
}

.register-link {
  text-align: center;
  margin-top: 20px;
  font-size: 14px;
}

.register-link a {
  color: #409eff;
  text-decoration: none;
}

.register-link a:hover {
  text-decoration: underline;
}
</style>
