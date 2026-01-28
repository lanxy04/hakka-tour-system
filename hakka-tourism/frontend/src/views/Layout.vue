<template>
  <el-container class="layout-container">
    <el-aside width="200px">
      <div class="logo">客家文旅</div>
      <el-menu
        :default-active="activeMenu"
        router
        background-color="#304156"
        text-color="#bfcbd9"
        active-text-color="#409eff"
      >
        <el-menu-item index="/resources">
          <el-icon><Location /></el-icon>
          <span>旅游资源</span>
        </el-menu-item>
        <el-menu-item index="/route/custom">
          <el-icon><TrendCharts /></el-icon>
          <span>定制路线</span>
        </el-menu-item>
        <el-menu-item index="/route/list">
          <el-icon><List /></el-icon>
          <span>我的路线</span>
        </el-menu-item>
        <el-menu-item index="/orders">
          <el-icon><Document /></el-icon>
          <span>我的订单</span>
        </el-menu-item>
        <el-menu-item index="/statistics">
          <el-icon><DataAnalysis /></el-icon>
          <span>数据统计</span>
        </el-menu-item>
        <el-menu-item index="/manage" v-if="isAdmin">
          <el-icon><Setting /></el-icon>
          <span>资源管理</span>
        </el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div class="header-content">
          <span>欢迎, {{ userInfo.realName || userInfo.username }}</span>
          <el-button type="text" @click="handleLogout">退出</el-button>
        </div>
      </el-header>
      <el-main>
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import { ref, computed, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { ElMessage } from 'element-plus'

export default {
  name: 'Layout',
  setup() {
    const router = useRouter()
    const route = useRoute()
    const userInfo = ref({})
    const activeMenu = computed(() => route.path)
    const isAdmin = computed(() => userInfo.value.role === 1)

    onMounted(() => {
      const user = localStorage.getItem('userInfo')
      if (user) {
        userInfo.value = JSON.parse(user)
      }
    })

    const handleLogout = () => {
      localStorage.removeItem('token')
      localStorage.removeItem('userInfo')
      ElMessage.success('退出成功')
      router.push('/login')
    }

    return {
      userInfo,
      activeMenu,
      isAdmin,
      handleLogout
    }
  }
}
</script>

<style scoped>
.layout-container {
  height: 100vh;
}

.el-aside {
  background-color: #304156;
  overflow-x: hidden;
}

.logo {
  height: 60px;
  line-height: 60px;
  text-align: center;
  font-size: 20px;
  font-weight: bold;
  color: white;
  background-color: #2b3a4a;
}

.el-header {
  background-color: #fff;
  color: #333;
  line-height: 60px;
  box-shadow: 0 1px 4px rgba(0, 21, 41, 0.08);
}

.header-content {
  display: flex;
  justify-content: space-between;
  padding: 0 20px;
}

.el-main {
  background-color: #f0f2f5;
  padding: 20px;
}
</style>
