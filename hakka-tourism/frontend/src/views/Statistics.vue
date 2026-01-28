<template>
  <div class="statistics">
    <el-row :gutter="20">
      <el-col :span="6">
        <el-card class="stat-card">
          <div class="stat-item">
            <div class="stat-icon" style="background-color: #409eff">
              <el-icon :size="30"><View /></el-icon>
            </div>
            <div class="stat-content">
              <div class="stat-title">资源访问量</div>
              <div class="stat-value">{{ resourceViewCount }}</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="stat-card">
          <div class="stat-item">
            <div class="stat-icon" style="background-color: #67c23a">
              <el-icon :size="30"><TrendCharts /></el-icon>
            </div>
            <div class="stat-content">
              <div class="stat-title">路线生成量</div>
              <div class="stat-value">{{ routeGenerateCount }}</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="stat-card">
          <div class="stat-item">
            <div class="stat-icon" style="background-color: #e6a23c">
              <el-icon :size="30"><Document /></el-icon>
            </div>
            <div class="stat-content">
              <div class="stat-title">订单创建量</div>
              <div class="stat-value">{{ orderCreateCount }}</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="stat-card">
          <div class="stat-item">
            <div class="stat-icon" style="background-color: #f56c6c">
              <el-icon :size="30"><User /></el-icon>
            </div>
            <div class="stat-content">
              <div class="stat-title">用户总数</div>
              <div class="stat-value">{{ userCount }}</div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20" style="margin-top: 20px">
      <el-col :span="12">
        <el-card>
          <template #header>
            <span>资源访问趋势</span>
          </template>
          <div ref="resourceChartRef" style="height: 400px"></div>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card>
          <template #header>
            <span>路线生成趋势</span>
          </template>
          <div ref="routeChartRef" style="height: 400px"></div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20" style="margin-top: 20px">
      <el-col :span="24">
        <el-card>
          <template #header>
            <span>订单创建趋势</span>
          </template>
          <div ref="orderChartRef" style="height: 400px"></div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { ref, onMounted, nextTick } from 'vue'
import * as echarts from 'echarts'
import { getStatisticsByType } from '@/api/statistics'

export default {
  name: 'Statistics',
  setup() {
    const resourceViewCount = ref(0)
    const routeGenerateCount = ref(0)
    const orderCreateCount = ref(0)
    const userCount = ref(150)

    const resourceChartRef = ref(null)
    const routeChartRef = ref(null)
    const orderChartRef = ref(null)

    const loadStatistics = async () => {
      try {
        const resourceStats = await getStatisticsByType('resource_view')
        const routeStats = await getStatisticsByType('route_generate')
        const orderStats = await getStatisticsByType('order_create')

        resourceViewCount.value = resourceStats.data.reduce((sum, item) => sum + item.value, 0)
        routeGenerateCount.value = routeStats.data.reduce((sum, item) => sum + item.value, 0)
        orderCreateCount.value = orderStats.data.reduce((sum, item) => sum + item.value, 0)

        nextTick(() => {
          initResourceChart(resourceStats.data)
          initRouteChart(routeStats.data)
          initOrderChart(orderStats.data)
        })
      } catch (error) {
        console.error('加载统计数据失败', error)
      }
    }

    const initResourceChart = (data) => {
      const chart = echarts.init(resourceChartRef.value)
      const option = {
        tooltip: {
          trigger: 'axis'
        },
        xAxis: {
          type: 'category',
          data: data.map(item => item.date).reverse()
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            name: '访问量',
            type: 'line',
            data: data.map(item => item.value).reverse(),
            smooth: true,
            areaStyle: {}
          }
        ]
      }
      chart.setOption(option)
    }

    const initRouteChart = (data) => {
      const chart = echarts.init(routeChartRef.value)
      const option = {
        tooltip: {
          trigger: 'axis'
        },
        xAxis: {
          type: 'category',
          data: data.map(item => item.date).reverse()
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            name: '生成量',
            type: 'bar',
            data: data.map(item => item.value).reverse(),
            itemStyle: {
              color: '#67c23a'
            }
          }
        ]
      }
      chart.setOption(option)
    }

    const initOrderChart = (data) => {
      const chart = echarts.init(orderChartRef.value)
      const option = {
        tooltip: {
          trigger: 'axis'
        },
        xAxis: {
          type: 'category',
          data: data.map(item => item.date).reverse()
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            name: '订单量',
            type: 'line',
            data: data.map(item => item.value).reverse(),
            smooth: true,
            itemStyle: {
              color: '#e6a23c'
            }
          }
        ]
      }
      chart.setOption(option)
    }

    onMounted(() => {
      loadStatistics()
    })

    return {
      resourceViewCount,
      routeGenerateCount,
      orderCreateCount,
      userCount,
      resourceChartRef,
      routeChartRef,
      orderChartRef
    }
  }
}
</script>

<style scoped>
.stat-card {
  margin-bottom: 20px;
}

.stat-item {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.stat-icon {
  width: 60px;
  height: 60px;
  border-radius: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
}

.stat-content {
  text-align: right;
}

.stat-title {
  font-size: 14px;
  color: #909399;
  margin-bottom: 5px;
}

.stat-value {
  font-size: 24px;
  font-weight: bold;
  color: #303133;
}
</style>
