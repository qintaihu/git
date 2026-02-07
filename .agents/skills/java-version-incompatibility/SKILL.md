# Java Version Incompatibility

## Problem
Maven build fails with compiler errors when project requires Java version that isn't installed.

```
[ERROR] Failed to execute goal org.apache.maven.plugins:maven-compiler-plugin:3.11.0:compile
[ERROR] Fatal error compiling: 无效的标记: --release

Cannot compile with Java 17 when only Java 8 is installed
```

## Root Cause
- Project is configured for Java 17+ (Spring Boot 3.1.5+)
- System only has Java 8 (or other incompatible version) installed
- Maven `--release` flag incompatible with Java 8
- Spring Boot version and Java version mismatch:
  - Spring Boot 3.0+ requires Java 17+
  - Spring Boot 2.7.x requires Java 8-11

## Solution

### Option A: Upgrade Java (Recommended if possible)

#### Step 1: Check Current Java Version
```powershell
java -version
javac -version
```

#### Step 2: Install Java 17 LTS
```powershell
winget install Eclipse.Temurin.17
# or for Java 21 LTS:
winget install Eclipse.Temurin.21
```

#### Step 3: Verify Installation
```powershell
java -version
```

#### Step 4: Rebuild Project
```powershell
mvn clean package -DskipTests
```

---

### Option B: Downgrade Project Configuration (if Java upgrade not possible)

#### Step 1: Edit pom.xml
Change the Spring Boot parent version and Java configuration:

```xml
<!-- BEFORE (requires Java 17) -->
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>3.1.5</version>
    <relativePath/>
</parent>

<properties>
    <java.version>17</java.version>
    <maven.compiler.source>17</maven.compiler.source>
    <maven.compiler.target>17</maven.compiler.target>
</properties>

<!-- AFTER (compatible with Java 8) -->
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.7.14</version>
    <relativePath/>
</parent>

<properties>
    <java.version>8</java.version>
    <maven.compiler.source>8</maven.compiler.source>
    <maven.compiler.target>8</maven.compiler.target>
</properties>
```

#### Step 2: Rebuild Project
```powershell
mvn clean package -DskipTests
```

## Java Version Compatibility Reference
| Spring Boot | Java Version | Status |
|---|---|---|
| 3.1+ | Java 17+ | Current |
| 2.7.x | Java 8-11 | LTS |
| 2.6.x | Java 8-11 | Maintenance |

## Related Skills
- maven-dependency-issues
