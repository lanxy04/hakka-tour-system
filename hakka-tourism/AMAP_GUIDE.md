# 高德地图集成说明

## 概述

本系统已集成高德地图API，用于在路线规划页面可视化展示旅游路线。

## 功能特性

- 在"定制路线"页面实时显示推荐路线的地图
- 在"我的路线"页面点击"查看"按钮可在地图上查看保存的路线
- 不同天数的路线用不同颜色区分
- 支持点击地图标记查看景点详细信息
- 自动根据景点位置调整地图视野

## 获取高德地图密钥

### 步骤1：注册高德开放平台账号

访问 [高德开放平台](https://console.amap.com/) 并注册/登录

### 步骤2：创建应用

1. 进入「应用管理」→「我的应用」
2. 点击「创建新应用」
3. 填写应用名称（如：客家文化旅游系统）
4. 选择应用类型：个人开发或企业开发

### 步骤3：添加 Key

1. 在刚创建的应用中点击「添加 Key」
2. 选择服务平台：「Web端 (JS API)」
3. 填写 Key 名称（如：web-key）
4. 配置域名白名单：
   - 本地开发：输入 `localhost,127.0.0.1`
   - 生产环境：输入您的实际域名（如：`www.example.com`）
5. 提交后获得 Key 和 安全密钥

## 配置项目

### 方法1：直接修改 index.html

打开 `frontend/index.html`，找到以下代码并替换：

```html
<script type="text/javascript">
  window._AMapSecurityConfig = {
    securityJsCode: '您的高德地图安全密钥'
  }
</script>
<script type="text/javascript" src="https://webapi.amap.com/maps?v=2.0&key=您的高德地图Key"></script>
```

将 `您的高德地图安全密钥` 和 `您的高德地图Key` 替换为您在步骤3中获得的真实值。

### 方法2：使用环境变量（推荐）

1. 创建 `.env` 文件（如果还没有）

2. 添加以下环境变量：

```env
VITE_AMAP_KEY=您的高德地图Key
VITE_AMAP_SECURITY_CODE=您的高德地图安全密钥
```

3. 修改 `frontend/index.html`：

```html
<script type="text/javascript">
  window._AMapSecurityConfig = {
    securityJsCode: import.meta.env.VITE_AMAP_SECURITY_CODE
  }
</script>
<script type="text/javascript" src="https://webapi.amap.com/maps?v=2.0&key=${import.meta.env.VITE_AMAP_KEY}"></script>
```

## 使用示例

### 1. 定制路线页面

在"定制路线"页面填写表单后，点击"生成路线"，系统会：
- 生成个性化旅游路线
- 在地图上自动标注所有景点
- 用不同颜色连接不同天数的景点
- 自动调整地图视野以显示所有景点

### 2. 我的路线页面

在"我的路线"页面：
- 点击任意路线卡片上的"查看"按钮
- 弹出对话框展示该路线的地图
- 可在地图上查看详细的景点信息和路线

## 组件说明

### AmapRouteMap 组件

位置：`frontend/src/components/AmapRouteMap.vue`

**Props：**
- `routeData`：路线数据（JSON字符串）
- `height`：地图高度，默认 `600px`

**特性：**
- 自动解析路线数据
- 使用高德地理编码API将地址转换为经纬度
- 支持多天路线展示
- 交互式标记和信息窗口

## 常见问题

### Q1: 地图不显示？

**原因和解决方案：**
- 检查Key和安全密钥是否正确配置
- 确认域名白名单是否包含当前访问域名
- 打开浏览器控制台查看错误信息

### Q2: 标记点位置不正确？

**原因和解决方案：**
- 地理编码API返回的位置可能不精确
- 建议在资源表中添加经纬度字段
- 如果有经纬度，优先使用经纬度定位

### Q3: 地图显示但是没有标记？

**原因和解决方案：**
- 检查routeData是否正确
- 确认资源的地址字段是否有值
- 查看控制台是否有地理编码错误

### Q4: 提示"安全密钥未设置"？

**解决方案：**
- 2021年12月后创建的Key必须配置安全密钥
- 确保`_AMapSecurityConfig`在地图API加载前设置
- 检查安全密钥格式是否正确

## 调试技巧

### 查看控制台日志

打开浏览器开发者工具（F12），查看控制台输出，关注：
- 高德地图API加载状态
- 地理编码请求和响应
- 标记点创建信息

### 使用高德地图开发者工具

访问 [高德地图JavaScript API 示例](https://lbs.amap.com/api/javascript-api/example) 查看更多示例代码。

## 配额说明

高德地图免费版配额：
- 每日免费调用量：30万次
- 并发数：5 QPS
- 个人开发完全够用

## 扩展功能

如需扩展地图功能，可以考虑：

1. **路线规划**：使用高德地图驾车/步行路线规划API
2. **周边搜索**：显示景点周边的美食、住宿等
3. **实时导航**：集成实时导航功能
4. **位置分享**：分享路线到社交媒体

## 参考资料

- [高德开放平台官网](https://lbs.amap.com/)
- [JavaScript API 文档](https://lbs.amap.com/api/javascript-api/summary)
- [Key和安全密钥说明](https://lbs.amap.com/api/javascript-api/guide/abc/load)
