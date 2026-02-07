# Workflow Management System

一个完整的工作流管理系统项目，包含 Java Spring Boot 后端和 Vue 3 前端。

## ✨ 主要特性

- **后端**：Spring Boot 3 + Spring Security + Activiti 7 + MySQL
- **前端**：Vue 3 + Element Plus + Vite
- **认证**：JWT Token 认证
- **工作流**：Activiti 流程引擎集成
- **容器化**：Docker & Docker Compose 支持
- **CI/CD**：GitHub Actions 工作流

## 📁 项目结构

```
.
├── backend/               # Java Spring Boot 后端应用
│   ├── src/
│   ├── pom.xml
│   ├── Dockerfile
│   ├── docker-compose.yml
│   └── README.md
├── frontend/              # Vue 3 前端应用
│   ├── src/
│   ├── package.json
│   ├── vite.config.js
│   ├── index.html
│   └── README.md
├── .github/workflows/     # GitHub Actions CI/CD
│   └── ci-cd.yml
├── .gitignore
└── README.md (此文件)
```

## 🚀 快速开始

### 后端启动（Java + Spring Boot）

```bash
# 方式 1：本地运行
cd backend
mvn clean package
mvn spring-boot:run

# 方式 2：Docker Compose
cd backend
docker-compose up -d
```

**后端地址**：`http://localhost:8080/api`

### 前端启动（Vue 3）

```bash
cd frontend

# 安装依赖
npm install

# 开发服务器
npm run dev

# 构建 (生产)
npm run build
```

**前端地址**：`http://localhost:5173`

## 🔐 默认用户

| 用户名 | 密码 | 角色 |
|------|-----|------|
| admin | admin123 | ADMIN, USER |
| user | user123 | USER |

## 📚 API 文档

### 认证接口

```bash
# 登录
POST /api/auth/login
{
  "username": "admin",
  "password": "admin123"
}

# 注册
POST /api/auth/register
{
  "username": "newuser",
  "password": "password123",
  "email": "user@example.com"
}

# 获取当前用户
GET /api/auth/me
Authorization: Bearer <token>
```

### 工作流接口

```bash
# 启动流程
POST /api/process/start
{
  "processDefinitionKey": "myProcess"
}

# 获取我的任务
GET /api/process/my-tasks

# 完成任务
POST /api/process/task/{taskId}/complete
```

## 🛠 环境变量

```bash
# 数据库
DB_USERNAME=root
DB_PASSWORD=root
MYSQL_ROOT_PASSWORD=root
MYSQL_DATABASE=workflow_db

# JWT
JWT_SECRET=your-secret-key-change-in-production
JWT_EXPIRATION=86400000  # 24 hours
```

## 🐳 Docker 部署

```bash
cd backend

# 构建镜像
docker build -t workflow-app:latest .

# 启动容器
docker-compose up -d

# 停止容器
docker-compose down
```

## 📦 技术栈

### 后端
- **框架**：Spring Boot 3.1.5
- **安全**：Spring Security + JWT
- **工作流**：Activiti 7.17
- **数据库**：MySQL 8.0
- **ORM**：Spring Data JPA / Hibernate
- **构建**：Maven
- **Java**：17+

### 前端
- **框架**：Vue 3
- **构建工具**：Vite
- **UI 库**：Element Plus
- **路由**：Vue Router
- **HTTP**：Axios
- **Node**：16+

## ⚙️ 配置说明

### 后端配置 (`backend/src/main/resources/application.yml`)
- 数据库连接
- JWT 设置
- Activiti 配置
- 日志级别

### 前端配置 (`frontend/vite.config.js`)
- 开发服务器代理
- API 端点
- 构建输出目录

## 🔒 安全建议

1. **不提交敏感信息**
   - `.gitignore` 已包含敏感文件
   - 使用环境变量管理密钥
   - 不要提交 `application-local.yml`

2. **生产环境**
   - 更改所有默认密码
   - 使用强 JWT_SECRET
   - 启用 HTTPS
   - 配置防火墙规则

3. **数据库**
   - 保护 MySQL 访问
   - 定期备份
   - 最小权限原则

4. **如果意外提交敏感数据**
   ```bash
   git filter-repo --invert-paths --path <file>
   git push -f origin main
   ```

## 📝 开发指南

详见各模块 README：
- [后端开发指南](./backend/README.md)
- [前端开发指南](./frontend/README.md)
- [中文完整文档](./README_CN.md)

## 🧪 测试

```bash
# 后端测试
cd backend
mvn test

# 前端测试 (需要配置)
cd frontend
npm test
```

## 📋 检查清单

启动前请确认：

- [ ] MySQL 已启动或 Docker 可用
- [ ] Java 17+ 已安装
- [ ] Node.js 16+ 已安装
- [ ] 端口 3306, 8080, 5173 未被占用
- [ ] 环境变量已配置
- [ ] JWT_SECRET 已更改 (生产环境)

## 🐛 常见问题

### Q: 如何重置数据库？
```bash
cd backend
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.jpa.hibernate.ddl-auto=create"
```

### Q: 前后端通信失败？
- 检查 backend 运行在 `:8080`
- 检查 frontend 中 `.env` 或 `vite.config.js` 配置
- 查看浏览器控制台网络请求

### Q: JWT token 过期？
- 调整 `application.yml` 中的 `jwt.expiration`
- 重新登录获取新 token

## 📄 许可证

MIT

## 👤 联系方式

Github: [qintaihu/git](https://github.com/qintaihu/git)

---

**最后更新**：2024年2月  
**状态**：✅ 完成项目脚手架生成
