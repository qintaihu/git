## 项目脚手架与生成提示词

下面是为初始化一个 Java 后端（Spring Boot + Spring Security + Activiti）和一个 Vue 登录页所准备的一组可直接用于代码生成器或大模型的提示词（中文）。你可以将这些提示词逐条提交给代码生成工具以生成对应的代码与文件。

---

### 后端 总览（生成完整 Spring Boot + Activiti + Spring Security 项目）
提示词：
请生成一个基于 Spring Boot 的 Java 项目脚手架，使用 Java 17，构建工具 Maven。要求包含：Spring Boot、Spring Security（基于 JWT 的认证）、MySQL 数据库配置、Activiti 工作流引擎集成（流程部署、任务查询示例），并提供基础的用户表（用户名、密码（BCrypt）、角色）和 REST 登录接口（返回 JWT）。请生成项目结构、pom.xml（或 build.gradle）、主要配置文件（application.yml）、主类、基础实体/仓库/服务/控制器、Activiti 配置与示例流程 XML、以及必要的 README 片段。所有密码使用 BCrypt，JWT 带过期时间，给出示例环境变量配置（DB、JWT_SECRET）。文件内容要完整、可直接运行。

### 后端 文件级：构建文件
提示词：
请生成 Maven 的 pom.xml（或 Gradle 的 build.gradle）包含：Spring Boot starter（web、data-jpa、security）、spring-boot-starter-actuator、activiti-spring-boot-starter、mysql-connector-java、jjwt（或使用 spring-security-oauth2-jose 用来创建/解析 JWT）、lombok（可选）、测试依赖。加上插件配置用于构建可执行 jar。

### 后端 文件级：配置与入口
提示词：
生成 application.yml，包含 profile（dev）、MySQL 连接、JPA（hibernate ddl-auto=update）、Activiti 数据源配置（如需要单独数据源请说明）、JWT 配置项（secret、expiration）、server 端口。并生成 Application.java 主类，启用 Activiti 自动配置与注解扫描。

### 后端 文件级：安全与认证
提示词：
生成 Spring Security 配置类（SecurityConfig），采用 JWT 认证：包含 JwtAuthenticationFilter、JwtUtil（生成/解析 token）、UserDetailsService 实现（从数据库读取用户及权限），密码编码器为 BCryptPasswordEncoder。创建 AuthController，暴露 /api/auth/login（接受 username/password，返回 JWT）和 /api/auth/register（示例注册，用于测试）。保证登录失败/成功的标准 JSON 返回。

### 后端 文件级：用户领域
提示词：
生成 User 实体（id, username, password, email, roles）、Role 实体或简单字符串角色处理、UserRepository（JpaRepository）、UserService（注册、按用户名查找、权限校验）、并为初始数据提供 DataLoader（启动时创建一个 admin 账户，密码经过 BCrypt 加密）。

### 后端 文件级：Activiti 集成
提示词：
生成 Activiti 的配置说明与示例：如何在 application.yml 中启用 Activiti、部署一个简单的流程定义（process.bpmn20.xml）示例（含 userTask），生成一个 ProcessService 类示例（启动流程、查询任务、完成任务），并提供一个 REST 接口示例（/api/process/start）来启动示例流程。

### 后端 文件级：DB 与 Docker
提示词：
生成 Dockerfile（基于 OpenJDK 17），以及 docker-compose.yml 包含 app 服务与 mysql 服务（设置 MYSQL_ROOT_PASSWORD、MYSQL_DATABASE、MYSQL_USER、MYSQL_PASSWORD 环境变量），并把 Activiti 使用的表初始化考虑在内。提供启动命令与注意事项。

### 后端：测试与 README
提示词：
生成基础集成测试示例（SpringBootTest），测试登录流程（注册 -> 登录 -> 用 token 访问受保护接口）。并补充 README 的快速启动步骤及生成/配置 JWT secret 的说明。

---

### 前端 总览（Vue 登录页脚手架）
提示词：
请生成一个 Vue 3 前端项目的最小登录页面实现，使用 Composition API，并使用 Element Plus 作为 UI 组件库。要求：登录表单（用户名、密码）、表单验证、调用后端 /api/auth/login（POST），接收 JWT 后存到 localStorage，并在请求头加入 Authorization: Bearer <token>；实现路由守卫（未登录无法访问受保护页面）；提供简单样式与响应式布局。生成 package.json、main.js、router 配置样例、以及如何在项目中引入和按需加载 Element Plus 的示例、Login.vue 组件代码和一个受保护的 Dashboard.vue 示例。

### 前端 组件级：Auth Service
提示词：
生成一个 authService.js（或 ts），包含 login(credentials)、logout()、getToken()、isAuthenticated() 方法。login 要调用后端并处理错误信息。提供示例 axios 实例配置，把 token 自动注入到请求头。

### 前端 组件级：Login.vue
提示词：
生成 Login.vue，包含：表单字段、提交处理、加载状态、错误提示、表单验证（简单必填），成功后跳转到 /dashboard。显示友好错误消息（例如 “用户名或密码错误”）。

---

### 集成 & 部署 提示
提示词：
生成 CI/CD 示例（GitHub Actions）工作流：构建后端 jar，构建前端静态资源，使用 docker-compose 在目标服务器上部署（或生成镜像并 push 到 registry）。包括 secrets 配置说明（不要把 JWT secret 或 DB 密码写入代码库，示例使用 GitHub Secrets）。

### 安全与清理 提示
提示词：
请在 README 中提醒：不要将敏感配置（私钥、数据库密码、WireGuard private key 等）提交到仓库；示例 .gitignore 应包含配置文件（例如 *.conf、application-local.yml、.env.local）。并生成一段如何在发现敏感数据后撤销与清理 Git 历史（简要说明使用 git filter-repo 或 BFG 的步骤与警告）。

---

如果你需要，我可以把这些提示词逐条转换为具体文件并在仓库中生成代码（后端与前端脚手架、Docker 配置与 CI 流水线）。要我直接开始生成代码并提交到仓库吗？

