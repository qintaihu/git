<template>
  <div class="dashboard-container">
    <el-header class="dashboard-header">
      <div class="header-left">
        <h1>Dashboard</h1>
      </div>
      <div class="header-right">
        <span class="username">{{ username }}</span>
        <el-button text type="primary" @click="handleLogout">Logout</el-button>
      </div>
    </el-header>

    <el-main class="dashboard-main">
      <el-row :gutter="20">
        <el-col :xs="24" :sm="12" :md="8">
          <el-card class="dashboard-card">
            <template #header>
              <div class="card-header">
                <span>Welcome</span>
              </div>
            </template>
            <div class="welcome-content">
              <p>Hello, {{ username }}!</p>
              <p>Welcome  to Workflow Management System</p>
            </div>
          </el-card>
        </el-col>

        <el-col :xs="24" :sm="12" :md="8">
          <el-card class="dashboard-card">
            <template #header>
              <div class="card-header">
                <span>Quick Actions</span>
              </div>
            </template>
            <el-space direction="vertical" fill>
              <el-button type="primary" block>View My Tasks</el-button>
              <el-button type="success" block>Start Process</el-button>
              <el-button type="info" block>View Processes</el-button>
            </el-space>
          </el-card>
        </el-col>

        <el-col :xs="24" :sm="12" :md="8">
          <el-card class="dashboard-card">
            <template #header>
              <div class="card-header">
                <span>Statistics</span>
              </div>
            </template>
            <el-statistic title="Active Tasks" :value="5" />
            <el-divider />
            <el-statistic title="Completed Processes" :value="12" />
          </el-card>
        </el-col>
      </el-row>

      <el-card class="dashboard-card" style="margin-top: 20px;">
        <template #header>
          <div class="card-header">
            <span>Recent Activities</span>
          </div>
        </template>
        <el-table :data="activities" stripe>
          <el-table-column prop="id" label="ID" width="100" />
          <el-table-column prop="title" label="Title" />
          <el-table-column prop="status" label="Status" width="100">
            <template #default="scope">
              <el-tag :type="scope.row.status === 'completed' ? 'success' : 'warning'">
                {{ scope.row.status }}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="date" label="Date" width="180" />
        </el-table>
      </el-card>
    </el-main>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import authService from '../services/authService'

const router = useRouter()
const username = ref('User')

const activities = ref([
  { id: 1, title: 'Process A', status: 'completed', date: '2024-02-01' },
  { id: 2, title: 'Process B', status: 'pending', date: '2024-02-02' },
  { id: 3, title: 'Process C', status: 'completed', date: '2024-02-03' }
])

const handleLogout = () => {
  authService.logout()
  ElMessage.success('Logged out successfully')
  router.push('/login')
}

onMounted(async () => {
  const user = await authService.getCurrentUser()
  if (user) {
    username.value = user.username
  }
})
</script>

<style scoped>
.dashboard-container {
  height: 100%;
  display: flex;
  flex-direction: column;
  background-color: #f5f7fa;
}

.dashboard-header {
  background-color: #fff;
  border-bottom: 1px solid #ebeef5;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 20px;
  box-shadow: 0 1px 4px rgba(0, 21, 41, 0.08);
}

.dashboard-header h1 {
  margin: 0;
  color: #333;
  font-size: 24px;
}

.header-left,
.header-right {
  display: flex;
  align-items: center;
  gap: 20px;
}

.header-right {
  gap: 10px;
}

.username {
  color: #666;
  font-size: 14px;
}

.dashboard-main {
  flex: 1;
  padding: 20px;
  overflow-y: auto;
}

.dashboard-card {
  box-shadow: 0 1px 4px rgba(0, 21, 41, 0.08);
}

.card-header {
  font-weight: 600;
  color: #333;
}

.welcome-content {
  text-align: center;
  padding: 20px 0;
}

.welcome-content p {
  margin: 10px 0;
  color: #666;
  font-size: 16px;
}

.el-statistic {
  text-align: center;
  padding: 15px 0;
}
</style>
