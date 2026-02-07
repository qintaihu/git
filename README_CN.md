# 项目根目录 README

## Workflow Management System

一个基于 Spring Boot + Activiti + Vue 3 的工作流管理系统。

## 项目结构

```
.
├── backend/           # Java Spring Boot 后端
│   ├── src/
│   ├── pom.xml
│   ├── Dockerfile
│   └── docker-compose.yml
├── frontend/          # Vue 3 前端
│   ├── src/
│   ├── package.json
│   ├── vite.config.js
│   └── index.html
├── .github/workflows/ # GitHub Actions CI/CD
└── README.md
```

## 技术栈

### 后端
- **Spring Boot 3.1.5** - 框架
- **Spring Security** - 权限认证
- **Spring Data JPA** - ORM
- **Activiti 7.17** - 工作流引擎
- **MySQL 8.0** - 数据库
- **JJWT** - JWT 令牌
- **Java 17** - 编程语言
- **Maven** - 构建工具

### 前端
- **Vue 3** - 框架
- **Vite** - 构建工具
- **Element Plus** - UI 组件库
- **Vue Router** - 路由
- **Axios** - HTTP 请求
- **Node.js 16+** - 运行环境

## 快速开始

### 后端启动

```bash
cd backend

# 本地开发
mvn spring-boot:run

# 或使用 Docker Compose
docker-compose up -d
```

后端地址: `http://localhost:8080/api`

### 前端启动

```bash
cd frontend

# 安装依赖
npm install

# 开发服务器
npm run dev

# 构建
npm run build
```

前端地址: `http://localhost:5173`

## 默认账户

- 用户名: `admin` / 密码: `admin123` (管理员)
- 用户名: `user` / 密码: `user123` (普通用户)

## 关键 API 端点

### 认证
- `POST /api/auth/login` - 登录
- `POST /api/auth/register` - 注册
- `GET /api/auth/me` - 获取当前用户信息

### 工作流
- `POST /api/process/start` - 启动流程
- `GET /api/process/my-tasks` - 获取我的任务
- `POST /api/process/task/{taskId}/complete` - 完成任务

## 环境变量

```bash
# 数据库配置
DB_USERNAME=root
DB_PASSWORD=root

# JWT 配置
JWT_SECRET=your-secret-key-change-in-production

# MySQL
MYSQL_ROOT_PASSWORD=root
MYSQL_DATABASE=workflow_db
```

## 生产部署

### Docker 部署

```bash
cd backend
docker build -t workflow-app:latest .
docker-compose up -d
```

### 安全建议

1. **不提交敏感信息到仓库**
   - 使用 `.gitignore` 忽略敏感文件
   - 配置文件使用环境变量

2. **认证与授权**
   - 更改默认密码
   - 复杂的 JWT_SECRET
   - 启用 HTTPS

3. **数据库**
   - 强密码
   - 备份策略
   - 访问控制

4. **Git 历史清理**
   如果意外提交敏感数据，使用 `git filter-repo` 清除：
   ```bash
   git filter-repo --invert-paths --path <file>
   git push -f origin main
   ```

## 开发指南

### 后端开发

1. 修改实体类 (`src/main/java/com/example/app/entity/`)
2. 创建仓库接口 (`src/main/java/com/example/app/repository/`)
3. 实现业务逻辑 (`src/main/java/com/example/app/service/`)
4. 创建控制器端点 (`src/main/java/com/example/app/controller/`)
5. 编写单元测试

### 前端开发

1. 创建 Vue 组件 (`src/components/` 或 `src/views/`)
2. 创建路由 (`src/router/index.js`)
3. 使用 authService 进行认证
4. 调用 API (`src/services/`)
5. 样式编写 (scoped CSS)

## 常见问题

### 数据库连接失败
- 检查 MySQL 是否运行
- 验证主机名、端口、用户名、密码
- 确保数据库 `workflow_db` 已创建

### JWT 验证失败
- 确保 JWT_SECRET 一致
- 检查 Token 格式 (`Bearer <token>`)
- 验证 Token 没有过期

### 前后端通信问题
- 检查 CORS 配置
- 验证 API 基础 URL
- 使用浏览器开发者工具查看网络请求

## 贡献

欢迎提交 Issue 和 Pull Request。

## 许可证

MIT

## 联系方式

项目维护者: qintaihu@example.com
