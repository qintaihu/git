# Maven Dependency Issues

## Problem
Maven build fails because it cannot find or download required dependencies from repositories.

```
[ERROR] Failed to execute goal on project workflow-app: Could not resolve dependencies
[ERROR] Failure to find org.activiti:activiti-spring-boot-starter-basic:jar:7.17.0 in 
https://maven.aliyun.com/nexus/content/groups/public/ was cached in the local repository
```

## Root Cause
- Maven repository mirror doesn't contain the dependency (e.g., Aliyun mirror is incomplete)
- Dependency version doesn't exist or has been removed from public repositories
- Network connectivity issues preventing download from remote repository
- Local Maven cache has corrupted/stale entries

## Solution

### Option A: Switch to Maven Central Repository

#### Step 1: Update pom.xml
Add repository configuration to explicitly use Maven Central:

```xml
<repositories>
    <repository>
        <id>central</id>
        <name>Maven Central Repository</name>
        <url>https://repo.maven.apache.org/maven2</url>
    </repository>
    <repository>
        <id>spring-milestones</id>
        <name>Spring Milestones</name>
        <url>https://repo.spring.io/milestone</url>
    </repository>
</repositories>
```

#### Step 2: Rebuild Project
```powershell
mvn clean install
```

---

### Option B: Temporarily Remove Unavailable Dependency

#### Step 1: Comment Out the Dependency
```xml
<!-- Temporarily unavailable, will re-enable later
<dependency>
    <groupId>org.activiti</groupId>
    <artifactId>activiti-spring-boot-starter-basic</artifactId>
    <version>7.17.0</version>
</dependency>
-->
```

#### Step 2: Rebuild Project
```powershell
mvn clean package -DskipTests
```

#### Step 3: Re-enable When Repository Access is Restored
Once the repository is accessible, uncomment the dependency and rebuild.

---

### Option C: Clear Local Cache and Retry

#### Step 1: Remove Local Maven Repository
```powershell
Remove-Item -Path "$env:USERPROFILE\.m2\repository" -Recurse -Force
```

#### Step 2: Verify Network Connection
Ensure you have internet access and can reach Maven repositories.

#### Step 3: Rebuild with Verbose Logging
```powershell
mvn clean install -X
```

This will re-download all dependencies from scratch with detailed debug output.

---

### Option D: Configure Custom Maven Mirror (China Users)

If using Aliyun mirror, add backup to settings.xml:

```xml
<!-- ~/.m2/settings.xml -->
<mirrors>
    <mirror>
        <id>aliyun</id>
        <name>Aliyun Maven Mirror</name>
        <url>https://maven.aliyun.com/repository/public</url>
        <mirrorOf>*</mirrorOf>
    </mirror>
</mirrors>
```

## Tips
- Smaller/older libraries may not exist in all mirrors
- Spring Boot dependencies are generally available in Maven Central
- Activiti 7.x may require specific repository configuration
- Always test build in CI/CD with public repositories for reliability

## Related Skills
- java-version-incompatibility
