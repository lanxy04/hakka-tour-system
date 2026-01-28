# 项目工具和版本清单

## 一、开发环境要求

### 1. JDK（Java开发工具包）
- **当前版本要求：** JDK 1.8 或更高版本
- **推荐版本：** JDK 8 / JDK 11 / JDK 17
- **下载地址：** https://www.oracle.com/java/technologies/downloads/

### 2. Maven（Java项目管理工具）
- **当前版本要求：** Maven 3.6 或更高版本
- **推荐版本：** Maven 3.8.x / Maven 3.9.x
- **下载地址：** https://maven.apache.org/download.cgi

### 3. MySQL（数据库）
- **当前版本要求：** MySQL 8.0
- **推荐版本：** MySQL 8.0.x
- **下载地址：** https://dev.mysql.com/downloads/mysql/
- **注意：** 不支持 MySQL 5.7 或更低版本

### 4. Redis（缓存数据库）
- **当前版本要求：** Redis 任意版本
- **推荐版本：** Redis 6.x / Redis 7.x
- **下载地址：** https://redis.io/download

### 5. Node.js（JavaScript运行环境）
- **当前版本要求：** Node.js 16 或更高版本
- **推荐版本：** Node.js 16.x / Node.js 18.x / Node.js 20.x
- **下载地址：** https://nodejs.org/

### 6. npm 或 yarn（包管理器）
- **当前版本要求：** npm 8.x / yarn 1.22.x
- **npm 通常随 Node.js 一起安装**
- **yarn 下载地址：** https://yarnpkg.com/

---

## 二、后端依赖版本（Maven pom.xml）

### 核心框架
- **Spring Boot**：2.7.14
- **MyBatis Spring Boot Starter**：2.3.1

### 数据库相关
- **MySQL Connector**：8.0.33
- **Druid（数据库连接池）**：1.2.18

### 工具库
- **Lombok**：随Spring Boot版本
- **Jackson（JSON处理）**：随Spring Boot版本
- **Validation（参数校验）**：随Spring Boot版本

### 项目配置
- **Java版本**：1.8
- **项目编码**：UTF-8

---

## 三、前端依赖版本（package.json）

### 核心框架
- **Vue.js**：^3.3.4
- **Vue Router**：^4.2.4
- **Axios**：^1.5.0

### UI组件库
- **Element Plus**：^2.3.14

### 数据可视化
- **ECharts**：^5.4.3

### 地图API
- **高德地图 JavaScript API**：2.0

### 构建工具
- **Vite**：^4.4.9
- **@vitejs/plugin-vue**：^4.3.4

---

## 四、开发工具

### IDE（集成开发环境）
- **IntelliJ IDEA**（推荐，用于后端开发）
- **Visual Studio Code**（推荐，用于前端开发）

### 数据库管理工具
- **Navicat Premium**（推荐）
- **DBeaver**
- **MySQL Workbench**

### API测试工具
- **Postman**（推荐）
- **Apifox**

### 版本控制工具
- **Git**
- **GitHub Desktop**（图形化Git工具，可选）

---

## 五、兼容性说明

### Java版本兼容性
- JDK 8：✅ 完全支持
- JDK 11：✅ 完全支持
- JDK 17：✅ 完全支持（需要调整Spring Boot版本）
- JDK 21：⚠️ 需要使用Spring Boot 3.x

### MySQL版本兼容性
- MySQL 5.7：❌ 不支持（使用的是MySQL 8.0的驱动）
- MySQL 8.0：✅ 完全支持
- MySQL 8.4：✅ 应该兼容

### Node.js版本兼容性
- Node.js 14：❌ 不支持（Vue 3需要Node.js 16+）
- Node.js 16：✅ 完全支持
- Node.js 18：✅ 完全支持
- Node.js 20：✅ 完全支持

---

## 六、版本修改指南

### 如果需要升级Java版本到JDK 17+

**修改 `backend/pom.xml`：**

```xml
<properties>
    <java.version>17</java.version>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
</properties>

<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>3.2.0</version>  <!-- 升级到Spring Boot 3.x -->
    <relativePath/>
</parent>

<!-- 注意：Spring Boot 3.x需要JDK 17+ -->
```

**同时需要修改：**
- `jakarta.persistence` 替代 `javax.persistence`
- 所有 `javax.*` 包改为 `jakarta.*`

### 如果需要降级到MySQL 5.7

**修改 `backend/pom.xml`：**

```xml
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>5.1.49</version>
</dependency>
```

**修改数据库连接URL：**

```yaml
spring:
  datasource:
    driver-class-name: com.mysql.jdbc.Driver  # 改为 com.mysql.jdbc.Driver
```

### 如果需要使用Vue 2.x

**修改 `frontend/package.json`：**

```json
{
  "dependencies": {
    "vue": "^2.7.14",
    "vue-router": "^3.6.5",
    "axios": "^1.5.0",
    "element-ui": "^2.15.14",  // 改为 element-ui
    "echarts": "^5.4.3"
  },
  "devDependencies": {
    "@vue/cli-service": "^5.0.8",
    "vue-template-compiler": "^2.7.14"
  }
}
```

**注意：** Vue 2和Vue 3的代码不兼容，需要大量修改。

---

## 七、推荐配置组合

### 稳定组合（推荐用于生产环境）
```
JDK 8
Spring Boot 2.7.14
MySQL 8.0.33
Redis 6.x
Node.js 18.x
Vue 3.3.4
```

### 现代化组合（推荐用于新项目）
```
JDK 17
Spring Boot 3.2.0
MySQL 8.0.33
Redis 7.x
Node.js 20.x
Vue 3.3.4
```

### 最低配置组合（适合学习和测试）
```
JDK 8
Spring Boot 2.7.14
MySQL 8.0
Redis 5.x
Node.js 16.x
Vue 3.3.4
```

---

## 八、版本查询命令

### 查看已安装的Java版本
```bash
java -version
```

### 查看已安装的Maven版本
```bash
mvn -version
```

### 查看已安装的MySQL版本
```bash
mysql --version
```

### 查看已安装的Redis版本
```bash
redis-server --version
```

### 查看已安装的Node.js版本
```bash
node -v
```

### 查看已安装的npm版本
```bash
npm -v
```

---

## 九、常见问题

### Q1: JDK版本不兼容怎么办？
A: 根据需要升级或降级JDK版本，并相应调整Spring Boot版本。

### Q2: MySQL连接失败？
A: 检查MySQL服务是否启动，版本是否匹配（需要8.0）。

### Q3: Redis连接失败？
A: 检查Redis服务是否启动，端口是否正确（默认6379）。

### Q4: npm install失败？
A: 可能是Node.js版本过低，需要升级到16或更高版本。

### Q5: Maven依赖下载慢？
A: 配置阿里云Maven镜像，修改 `~/.m2/settings.xml`。

---

## 十、需要您确认的信息

请检查以下版本是否符合您的需求：

1. Java版本：1.8 □
2. Maven版本：3.6+ □
3. MySQL版本：8.0 □
4. Redis版本：任意版本 □
5. Node.js版本：16+ □

如果需要修改版本，请告诉我：
1. 哪些工具/依赖需要修改版本
2. 修改为什么版本
3. 使用场景（学习/开发/生产）

我会根据您的需求调整项目配置文件。
