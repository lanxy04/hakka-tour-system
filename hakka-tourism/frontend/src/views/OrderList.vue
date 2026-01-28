<template>
  <div class="order-list">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>我的订单</span>
        </div>
      </template>

      <el-table :data="orders" style="width: 100%">
        <el-table-column prop="orderNo" label="订单号" width="180" />
        <el-table-column prop="routeId" label="路线ID" width="100" />
        <el-table-column prop="amount" label="金额" width="120">
          <template #default="scope">
            ¥{{ scope.row.amount }}
          </template>
        </el-table-column>
        <el-table-column prop="status" label="状态" width="100">
          <template #default="scope">
            <el-tag v-if="scope.row.status === 0" type="warning">待支付</el-tag>
            <el-tag v-else-if="scope.row.status === 1" type="success">已支付</el-tag>
            <el-tag v-else-if="scope.row.status === 2" type="danger">已取消</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="180">
          <template #default="scope">
            {{ formatDateTime(scope.row.createTime) }}
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <template #default="scope">
            <el-button
              v-if="scope.row.status === 0"
              type="primary"
              size="small"
              @click="payOrder(scope.row)"
            >
              支付
            </el-button>
            <el-button
              v-if="scope.row.status === 0"
              type="danger"
              size="small"
              @click="cancelOrder(scope.row)"
            >
              取消
            </el-button>
            <el-button size="small" @click="viewOrder(scope.row)">查看</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getUserOrders, updateOrder } from '@/api/order'

export default {
  name: 'OrderList',
  setup() {
    const orders = ref([])

    const userInfo = JSON.parse(localStorage.getItem('userInfo') || '{}')

    const loadOrders = async () => {
      try {
        const res = await getUserOrders(userInfo.id)
        orders.value = res.data
      } catch (error) {
        console.error('加载订单失败', error)
      }
    }

    const payOrder = async (order) => {
      try {
        order.status = 1
        await updateOrder(order)
        ElMessage.success('支付成功')
        loadOrders()
      } catch (error) {
        console.error('支付失败', error)
      }
    }

    const cancelOrder = async (order) => {
      try {
        order.status = 2
        await updateOrder(order)
        ElMessage.success('取消成功')
        loadOrders()
      } catch (error) {
        console.error('取消失败', error)
      }
    }

    const viewOrder = (order) => {
      ElMessage.info(`查看订单: ${order.orderNo}`)
    }

    const formatDateTime = (dateStr) => {
      if (!dateStr) return ''
      const date = new Date(dateStr)
      return date.toLocaleString('zh-CN', {
        year: 'numeric',
        month: '2-digit',
        day: '2-digit',
        hour: '2-digit',
        minute: '2-digit'
      })
    }

    onMounted(() => {
      loadOrders()
    })

    return {
      orders,
      payOrder,
      cancelOrder,
      viewOrder,
      formatDateTime
    }
  }
}
</script>

<style scoped>
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
