# 客家文化旅游资源管理系统

## 项目简介

这是一个基于Spring Boot + Vue.js + MyBatis + Redis + ECharts开发的客家文化旅游资源管理系统，提供旅游资源管理、查询检索、定制推荐、用户管理和数据统计等功能。

## 技术栈

### 后端
- Spring Boot 2.7.14
- MyBatis 2.3.1
- MySQL 8.0
- Redis
- Druid 数据库连接池
- Lombok

### 前端
- Vue.js 3.3.4
- Vue Router 4.2.4
- Axios 1.5.0
- Element Plus 2.3.14
- ECharts 5.4.3
- Vite 4.4.9

## 功能模块

### 1. 资源整合模块
- 旅游资源的录入、分类、编辑、删除
- 批量导入导出功能
- 资源信息的标准化管理

### 2. 查询检索模块
- 多条件组合查询（地区、资源类型、主题特色等）
- 模糊查询功能
- 按热度、评分等维度排序

### 3. 定制推荐模块
- 基于用户出行时间、人数、预算、兴趣偏好等信息
- 智能生成个性化旅游路线
- 支持路线修改与保存

### 4. 用户管理模块
- 用户注册、登录
- 个人信息修改
- 订单管理
- 区分普通用户与管理员权限

### 5. 数据统计模块
- 旅游资源访问量统计
- 定制路线生成量统计
- 以图表形式展示数据（ECharts）

## 项目结构

```
hakka-tourism/
├── backend/                      # 后端Spring Boot项目
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/hakka/tour/
│   │   │   │   ├── controller/    # 控制器层
│   │   │   │   ├── service/       # 服务层
│   │   │   │   ├── mapper/        # 数据访问层
│   │   │   │   ├── entity/        # 实体类
│   │   │   │   ├── dto/           # 数据传输对象
│   │   │   │   └── config/        # 配置类
│   │   │   └── resources/
│   │   │       ├── application.yml    # 配置文件
│   │   │       └── mapper/            # MyBatis XML映射文件
│   │   └── test/
│   └── pom.xml
├── frontend/                     # 前端Vue.js项目
│   ├── src/
│   │   ├── api/               # API接口
│   │   ├── components/        # 组件
│   │   ├── views/             # 页面
│   │   ├── router/            # 路由
│   │   ├── utils/             # 工具类
│   │   ├── assets/            # 静态资源
│   │   ├── App.vue
│   │   └── main.js
│   ├── index.html
│   ├── package.json
│   └── vite.config.js
└── database/
    └── init.sql                # 数据库初始化脚本
```

## 部署步骤

### 前置要求

1. JDK 1.8 或更高版本
2. Maven 3.6 或更高版本
3. MySQL 8.0
4. Redis
5. Node.js 16 或更高版本
6. npm 或 yarn

### 1. 数据库配置

```bash
# 登录MySQL
mysql -u root -p

# 执行初始化脚本
source /workspace/database/init.sql

# 或者使用命令行
mysql -u root -p < /workspace/database/init.sql
```

修改数据库连接配置文件 `backend/src/main/resources/application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/hakka_tourism?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai&useSSL=false
    username: root
    password: your_password  # 修改为你的MySQL密码
  redis:
    host: localhost
    port: 6379
    # 如果Redis有密码，添加以下配置
    # password: your_redis_password
```

### 2. 后端部署

```bash
# 进入后端目录
cd /workspace/backend

# 使用Maven打包
mvn clean package -DskipTests

# 运行项目
java -jar target/hakka-tourism-1.0.0.jar

# 或者使用Maven直接运行
mvn spring-boot:run
```

后端服务将在 `http://localhost:8080` 启动，API基础路径为 `/api`

### 3. 前端部署

```bash
# 进入前端目录
cd /workspace/frontend

# 安装依赖
npm install

# 启动开发服务器
npm run dev

# 构建生产版本
npm run build
```

前端开发服务器将在 `http://localhost:3000` 启动

### 4. 访问系统

1. 打开浏览器访问 `http://localhost:3000`
2. 使用管理员账号登录：
   - 用户名: admin
   - 密码: admin123
3. 或使用普通用户账号：
   - 用户名: user
   - 密码: user123

## API接口文档

### 用户模块
- `POST /api/user/register` - 用户注册
- `POST /api/user/login` - 用户登录
- `PUT /api/user/update` - 更新用户信息
- `GET /api/user/{id}` - 获取用户信息
- `GET /api/user/list` - 获取所有用户
- `DELETE /api/user/{id}` - 删除用户

### 资源模块
- `POST /api/resource/create` - 创建资源
- `PUT /api/resource/update` - 更新资源
- `DELETE /api/resource/{id}` - 删除资源
- `GET /api/resource/{id}` - 获取资源详情
- `GET /api/resource/list` - 获取所有资源
- `POST /api/resource/search` - 搜索资源
- `POST /api/resource/{id}/rate` - 资源评分

### 路线模块
- `POST /api/route/custom` - 定制路线
- `PUT /api/route/update` - 更新路线
- `DELETE /api/route/{id}` - 删除路线
- `GET /api/route/{id}` - 获取路线详情
- `GET /api/route/user/{userId}` - 获取用户路线
- `GET /api/route/list` - 获取所有路线

### 订单模块
- `POST /api/order/create` - 创建订单
- `PUT /api/order/update` - 更新订单
- `DELETE /api/order/{id}` - 删除订单
- `GET /api/order/{id}` - 获取订单详情
- `GET /api/order/user/{userId}` - 获取用户订单
- `GET /api/order/list` - 获取所有订单

### 统计模块
- `GET /api/statistics/{type}` - 获取指定类型的统计数据
- `GET /api/statistics/all` - 获取所有统计数据

## 数据库表结构

### user - 用户表
- id: 用户ID
- username: 用户名
- password: 密码
- email: 邮箱
- phone: 手机号
- real_name: 真实姓名
- role: 角色（0-普通用户，1-管理员）

### resource - 资源表
- id: 资源ID
- name: 资源名称
- region: 地区
- type: 类型（景点/美食/住宿/文化体验）
- theme: 主题特色
- description: 描述信息
- address: 地址
- score: 评分
- hot_count: 热度
- price: 价格
- images: 图片URL
- tags: 标签

### route - 路线表
- id: 路线ID
- user_id: 用户ID
- name: 路线名称
- description: 路线描述
- days: 天数
- budget: 预算
- people_count: 人数
- route_data: 路线数据（JSON格式）
- status: 状态（0-草稿，1-已保存）

### order - 订单表
- id: 订单ID
- user_id: 用户ID
- route_id: 路线ID
- order_no: 订单号
- amount: 金额
- status: 状态（0-待支付，1-已支付，2-已取消）

### statistics - 统计表
- id: 统计ID
- statistic_type: 统计类型
- statistic_value: 统计值
- record_date: 记录日期
- remarks: 备注

## 常见问题

### 1. 数据库连接失败
检查 `application.yml` 中的数据库配置是否正确，确保MySQL服务已启动。

### 2. Redis连接失败
检查Redis服务是否启动，确保Redis配置正确。

### 3. 前端无法访问后端API
确保后端服务已启动，检查 `vite.config.js` 中的代理配置是否正确。

### 4. 跨域问题
后端已配置CORS，确保前端请求地址正确。

## 开发工具

- IntelliJ IDEA: 后端开发
- Visual Studio Code: 前端开发
- Navicat Premium: 数据库管理
- Postman: API测试
- Git: 版本控制

## 许可证

本项目仅供学习和参考使用。

## 联系方式

如有问题，请联系开发者。
