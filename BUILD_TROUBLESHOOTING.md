# 项目构建常见问题与解决方案

## 问题 1：Node.js 未安装

### 症状
```
npm : 无法将"npm"项识别为 cmdlet、函数、脚本文件或可运行程序的名称
node : 无法将"node"项识别为 cmdlet、函数、脚本文件或可运行程序的名称
```

### 原因
系统未安装 Node.js 或未添加到 PATH

### 解决方案
```powershell
# 方案 1：使用 Windows Package Manager (推荐)
winget install OpenJS.NodeJS

# 方案 2：直接下载安装
# 从 https://nodejs.org/ 下载 LTS 版本并安装

# 验证安装
node --version
npm --version
```

### 经验
- winget 比 choco 更可靠（choco 可能未安装）
- 安装后需要 PowerShell 重启或刷新环境变量
- Node.js 包含 npm，无需单独安装

---

## 问题 2：npm 安装时文件权限错误

### 症状
```
npm error code 1
npm error EPERM: operation not permitted, rmdir 'D:\File\WorkSpace\frontend\node_modules\...'
npm error EBUSY: resource busy or locked, rmdir 'D:\File\WorkSpace\frontend\node_modules\vue-demi'
npm error command C:\WINDOWS\system32\cmd.exe /d /s /c node install.js
npm error 'node' 不是内部或外部命令，也不是可运行的程序或批处理文件。
```

### 原因
1. Node 进程仍在运行，锁定了文件
2. 之前的安装失败，node_modules 损坏
3. npm/node 路径未正确设置

### 解决方案
```powershell
# 步骤 1：停止所有 node 进程
Get-Process node -ErrorAction SilentlyContinue | Stop-Process -Force -ErrorAction SilentlyContinue
Start-Sleep -Seconds 2

# 步骤 2：删除损坏的 node_modules 和 package-lock.json
Remove-Item -Path "D:\Your\Project\frontend\node_modules" -Recurse -Force -ErrorAction SilentlyContinue
Remove-Item -Path "D:\Your\Project\frontend\package-lock.json" -Force -ErrorAction SilentlyContinue

# 步骤 3：添加 Node.js 到 PATH（如果 npm 仍无法识别）
$env:PATH += ";C:\Program Files\nodejs"

# 步骤 4：重新安装
& "C:\Program Files\nodejs\npm.cmd" install

# 步骤 5：如果仍然失败，使用 --force 强制安装
& "C:\Program Files\nodejs\npm.cmd" install --force
```

### 经验
- Windows 文件锁定是常见问题，特别是在 node_modules
- 使用完整路径避免 PATH 问题：`C:\Program Files\nodejs\npm.cmd`
- `--force` 标志可以强制覆盖权限问题，但可能安装不稳定的版本

---

## 问题 3：esbuild 缺少平台特定二进制文件

### 症状
```
Error: The package "@esbuild/win32-x64" could not be found, and is needed by esbuild.

If you are installing esbuild with npm, make sure that you don't specify the 
"--no-optional" or "--omit=optional" flags. The optionalDependencies feature 
of "package.json" is used by esbuild to install the correct binary executable 
for your current platform.
```

### 原因
npm install 命令中使用了 `--no-optional`，导致 esbuild 的平台特定二进制文件未被下载

### 解决方案
```powershell
# 步骤 1：移除所有旧的安装
Get-Process node -ErrorAction SilentlyContinue | Stop-Process -Force -ErrorAction SilentlyContinue
Remove-Item -Path "node_modules" -Recurse -Force -ErrorAction SilentlyContinue
Remove-Item -Path "package-lock.json" -Force -ErrorAction SilentlyContinue

# 步骤 2：重新安装**不使用** --no-optional 或类似标志
& "C:\Program Files\nodejs\npm.cmd" install

# 步骤 3：可选地使用 --force 強制覆盖（如果有权限问题）
& "C:\Program Files\nodejs\npm.cmd" install --force
```

### 经验
- 避免使用 `--no-optional` 或 `--omit=optional`
- Vite 和 esbuild 等构建工具需要平台特定的二进制文件
- 完整安装默认包含所有可选依赖（optional dependencies）

---

## 问题 4：Java 版本不兼容

### 症状
```
[ERROR] Failed to execute goal org.apache.maven.plugins:maven-compiler-plugin:3.11.0:compile
[ERROR] Fatal error compiling: 无效的标记: --release
```

或在编译现代 Java/Spring Boot 代码时：
```
System only has Java 8, but project requires Java 17+
```

### 原因
项目配置要求 Java 17+，但系统只安装了 Java 8（或其他较低版本）

### 解决方案

#### 方案 A：升级 Java（推荐，如果可能）
```powershell
# 检查当前 Java 版本
java -version
javac -version

# 安装更新的 Java
winget install Eclipse.Temurin.21    # Java 21 LTS
# 或
winget install Eclipse.Temurin.17    # Java 17
```

#### 方案 B：降级项目配置（如果无法升级 Java）
编辑 `backend/pom.xml`：

```xml
<!-- 将此部分 -->
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>3.1.5</version>  <!-- Spring Boot 3 需要 Java 17+ -->
    <relativePath/>
</parent>

<properties>
    <java.version>17</java.version>
    <maven.compiler.source>17</maven.compiler.source>
    <maven.compiler.target>17</maven.compiler.target>
</properties>

<!-- 改为此部分 -->
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.7.14</version>  <!-- Spring Boot 2.7 支持 Java 8-11 -->
    <relativePath/>
</parent>

<properties>
    <java.version>8</java.version>
    <maven.compiler.source>8</maven.compiler.source>
    <maven.compiler.target>8</maven.compiler.target>
</properties>
```

### 经验
- Spring Boot 版本与 Java 版本必须兼容：
  - Spring Boot 2.7.x → Java 8-11
  - Spring Boot 3.0+ → Java 17+
- 多个 Java 版本可共存，但需要配置 JAVA_HOME
- `javac --release` 标志需要 Java 9+

---

## 问题 5：Maven 依赖下载失败

### 症状
```
[ERROR] Failed to execute goal on project workflow-app: Could not resolve dependencies for project...
[ERROR] Failure to find org.activiti:activiti-spring-boot-starter-basic:jar:7.17.0 in 
https://maven.aliyun.com/nexus/content/groups/public/ was cached in the local repository
```

### 原因
1. 使用的 Maven 仓库镜像不包含该依赖（如阿里云镜像）
2. 依赖版本不存在或已下架
3. 网络问题或仓库暂时不可用

### 解决方案

#### 方案 A：切换到 Maven 中央仓库
编辑 `~/.m2/settings.xml` 或 `pom.xml`：

```xml
<!-- 在 pom.xml 中添加 -->
<repositories>
    <repository>
        <id>central</id>
        <url>https://repo.maven.apache.org/maven2</url>
    </repository>
    <repository>
        <id>spring-milestones</id>
        <url>https://repo.spring.io/milestone</url>
    </repository>
</repositories>
```

#### 方案 B：移除问题依赖（临时方案）
```xml
<!-- 注释掉无法下载的依赖 -->
<!-- 
<dependency>
    <groupId>org.activiti</groupId>
    <artifactId>activiti-spring-boot-starter-basic</artifactId>
    <version>7.17.0</version>
</dependency>
-->
```

#### 方案 C：清除本地缓存并重新下载
```bash
# Windows PowerShell
Remove-Item -Path "$env:USERPROFILE\.m2\repository\org\activiti" -Recurse -Force

# 然后重新运行 Maven
mvn clean install
```

### 经验
- 阿里云镜像速度快但覆盖度不完整，适合国内开发
- Maven 中央仓库最全但国内网络可能较慢
- 某些老版本或小众库可能根本不存在，需要找替代品
- 考虑在 Maven/Gradle 中使用多个仓库源

---

## 快速参考：命令速查表

```powershell
# === Node.js & npm ===
# 安装 Node.js
winget install OpenJS.NodeJS

# 添加到 PATH
$env:PATH += ";C:\Program Files\nodejs"

# 完全清除 npm 安装
Get-Process node -ErrorAction SilentlyContinue | Stop-Process -Force
Remove-Item -Path "node_modules" -Recurse -Force -ErrorAction SilentlyContinue
Remove-Item -Path "package-lock.json" -Force -ErrorAction SilentlyContinue

# 重新安装依赖
& "C:\Program Files\nodejs\npm.cmd" install --force

# === Java ===
# 检查版本
java -version
javac -version

# 安装 Java
winget install Eclipse.Temurin.21
winget install Eclipse.Temurin.17

# === Maven ===
# 清空本地仓库缓存
Remove-Item -Path "$env:USERPROFILE\.m2\repository" -Recurse -Force

# 编译测试
mvn clean compile

# 完整构建
mvn clean package -DskipTests
```

---

## 权限问题的通用解决方案

如果遇到 Windows 文件权限锁定问题：

```powershell
# 1. 关闭所有相关进程
Get-Process java,node,mvn,npm -ErrorAction SilentlyContinue | Stop-Process -Force

# 2. 以管理员身份运行 PowerShell 重试
# （右键点击 PowerShell → 以管理员身份运行）

# 3. 使用完整路径避免 PATH 问题
& "C:\Program Files\nodejs\npm.cmd" install

# 4. 强制覆盖权限
& "C:\Program Files\nodejs\npm.cmd" install --force --verbose

# 5. 清除 npm 缓存
& "C:\Program Files\nodejs\npm.cmd" cache clean --force
```

---

## 预防建议

1. **定期更新**：`npm update`, `mvn versions:display-updates`
2. **使用 LTS 版本**：Node.js LTS, Java LTS
3. **锁定依赖版本**：在 package.json 和 pom.xml 中指定确切版本
4. **独立环境**：使用 Docker 或虚拟机隔离构建环境
5. **缓存管理**：定期清理 npm 和 Maven 缓存
   ```powershell
   & "C:\Program Files\nodejs\npm.cmd" cache clean --force
   mvn dependency:purge-local-repository
   ```

