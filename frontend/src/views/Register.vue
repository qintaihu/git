<template>
  <div class="register-container">
    <el-card class="register-card">
      <template #header>
        <div class="register-header">
          <h1>Register</h1>
          <p>Create a new account</p>
        </div>
      </template>

      <el-form
        ref="formRef"
        :model="form"
        :rules="rules"
        @submit.prevent="handleRegister"
      >
        <el-form-item label="Username" prop="username">
          <el-input
            v-model="form.username"
            placeholder="Enter username"
            clearable
          />
        </el-form-item>

        <el-form-item label="Email" prop="email">
          <el-input
            v-model="form.email"
            type="email"
            placeholder="Enter email"
            clearable
          />
        </el-form-item>

        <el-form-item label="Password" prop="password">
          <el-input
            v-model="form.password"
            type="password"
            placeholder="Enter password"
            clearable
            show-password
          />
        </el-form-item>

        <el-form-item label="Confirm Password" prop="confirmPassword">
          <el-input
            v-model="form.confirmPassword"
            type="password"
            placeholder="Confirm password"
            clearable
            show-password
          />
        </el-form-item>

        <el-form-item>
          <el-button
            type="primary"
            native-type="submit"
            :loading="loading"
            class="register-button"
          >
            {{ loading ? 'Registering...' : 'Register' }}
          </el-button>
        </el-form-item>
      </el-form>

      <div v-if="errorMessage" class="error-message">
        {{ errorMessage }}
      </div>

      <div class="login-link">
        <router-link to="/login">Already have an account? Login here</router-link>
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
  username: '',
  email: '',
  password: '',
  confirmPassword: ''
})

const validatePasswordMatch = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('Please confirm password'))
  } else if (value !== form.value.password) {
    callback(new Error('Passwords do not match'))
  } else {
    callback()
  }
}

const rules = {
  username: [
    { required: true, message: 'Please enter username', trigger: 'blur' },
    { min: 3, max: 20, message: 'Username should be 3-20 characters', trigger: 'blur' }
  ],
  email: [
    { required: true, message: 'Please enter email', trigger: 'blur' },
    { type: 'email', message: 'Please enter valid email', trigger: 'blur' }
  ],
  password: [
    { required: true, message: 'Please enter password', trigger: 'blur' },
    { min: 6, max: 30, message: 'Password should be 6-30 characters', trigger: 'blur' }
  ],
  confirmPassword: [
    { required: true, message: 'Please confirm password', trigger: 'blur' },
    { validator: validatePasswordMatch, trigger: 'blur' }
  ]
}

const handleRegister = async () => {
  await formRef.value.validate()
  
  loading.value = true
  errorMessage.value = ''

  try {
    const result = await authService.register(
      form.value.username,
      form.value.password,
      form.value.email
    )
    
    if (result.success) {
      ElMessage.success('Registration successful! Please login.')
      await router.push('/login')
    } else {
      errorMessage.value = result.message || 'Registration failed'
      ElMessage.error(errorMessage.value)
    }
  } catch (error) {
    errorMessage.value = 'An error occurred during registration'
    ElMessage.error(errorMessage.value)
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.register-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.register-card {
  width: 100%;
  max-width: 450px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.15);
}

.register-header {
  text-align: center;
  padding: 20px 0;
}

.register-header h1 {
  margin: 0 0 10px 0;
  color: #333;
  font-size: 28px;
}

.register-header p {
  margin: 0;
  color: #999;
  font-size: 14px;
}

.register-button {
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

.login-link {
  text-align: center;
  margin-top: 20px;
  font-size: 14px;
}

.login-link a {
  color: #409eff;
  text-decoration: none;
}

.login-link a:hover {
  text-decoration: underline;
}
</style>
