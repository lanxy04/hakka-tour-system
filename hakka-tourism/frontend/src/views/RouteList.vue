<template>
  <div class="route-list">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>我的路线</span>
          <el-button type="primary" @click="goToCustom">定制新路线</el-button>
        </div>
      </template>

      <el-empty v-if="routes.length === 0" description="暂无路线" />
      <el-row :gutter="20" v-else>
        <el-col :span="8" v-for="route in routes" :key="route.id">
          <el-card class="route-card" shadow="hover">
            <div class="route-info">
              <h3>{{ route.name }}</h3>
              <p class="route-desc">{{ route.description }}</p>
              <el-row :gutter="10">
                <el-col :span="12">
                  <p><el-icon><Calendar /></el-icon> {{ route.days }}天</p>
                </el-col>
                <el-col :span="12">
                  <p><el-icon><User /></el-icon> {{ route.peopleCount }}人</p>
                </el-col>
                <el-col :span="12">
                  <p><el-icon><Money /></el-icon> ¥{{ route.budget }}</p>
                </el-col>
                <el-col :span="12">
                  <p><el-icon><Clock /></el-icon> {{ formatDate(route.createTime) }}</p>
                </el-col>
              </el-row>
            </div>
            <div class="route-actions">
              <el-button type="primary" size="small" @click="viewRoute(route.id)">查看</el-button>
              <el-button type="danger" size="small" @click="deleteRoute(route.id)">删除</el-button>
              <el-button type="success" size="small" @click="createOrder(route.id)">下单</el-button>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </el-card>

    <el-dialog v-model="showMapDialog" title="路线地图" width="80%" top="5vh">
      <AmapRouteMap v-if="currentRoute && currentRoute.routeData" :routeData="currentRoute.routeData" height="600px" />
      <el-empty v-else description="暂无路线数据" />
    </el-dialog>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getUserRoutes, deleteRoute as deleteRouteApi, getRouteDetail } from '@/api/route'
import { createOrder } from '@/api/order'
import AmapRouteMap from '@/components/AmapRouteMap.vue'

export default {
  name: 'RouteList',
  components: {
    AmapRouteMap
  },
  setup() {
    const router = useRouter()
    const routes = ref([])
    const currentRoute = ref(null)
    const showMapDialog = ref(false)

    const userInfo = JSON.parse(localStorage.getItem('userInfo') || '{}')

    const loadRoutes = async () => {
      try {
        const res = await getUserRoutes(userInfo.id)
        routes.value = res.data
      } catch (error) {
        console.error('加载路线失败', error)
      }
    }

    const goToCustom = () => {
      router.push('/route/custom')
    }

    const viewRoute = async (id) => {
      try {
        const res = await getRouteDetail(id)
        currentRoute.value = res.data
        showMapDialog.value = true
      } catch (error) {
        console.error('加载路线详情失败', error)
      }
    }

    const deleteRoute = async (id) => {
      try {
        await ElMessageBox.confirm('确定删除该路线吗?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
        await deleteRouteApi(id)
        ElMessage.success('删除成功')
        loadRoutes()
      } catch (error) {
        console.error('删除失败', error)
      }
    }

    const createOrder = async (routeId) => {
      try {
        await createOrder({
          userId: userInfo.id,
          routeId: routeId,
          amount: 0
        })
        ElMessage.success('订单创建成功')
        router.push('/orders')
      } catch (error) {
        console.error('创建订单失败', error)
      }
    }

    const formatDate = (dateStr) => {
      if (!dateStr) return ''
      return dateStr.split('T')[0]
    }

    onMounted(() => {
      loadRoutes()
    })

    return {
      routes,
      currentRoute,
      showMapDialog,
      goToCustom,
      viewRoute,
      deleteRoute,
      createOrder,
      formatDate
    }
  }
}
</script>

<style scoped>
.route-card {
  margin-bottom: 20px;
}

.route-info h3 {
  font-size: 18px;
  font-weight: bold;
  margin-bottom: 10px;
}

.route-desc {
  color: #606266;
  margin-bottom: 15px;
  min-height: 40px;
}

.route-info p {
  font-size: 13px;
  color: #909399;
  margin: 5px 0;
  display: flex;
  align-items: center;
}

.route-actions {
  display: flex;
  justify-content: space-between;
  margin-top: 15px;
  padding-top: 15px;
  border-top: 1px solid #ebeef5;
}
</style>
