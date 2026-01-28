<template>
  <div class="route-custom">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>定制旅游路线</span>
        </div>
      </template>

      <el-form :model="routeForm" :rules="rules" ref="routeFormRef" label-width="120px">
        <el-form-item label="路线名称" prop="name">
          <el-input v-model="routeForm.name" placeholder="请输入路线名称" />
        </el-form-item>
        <el-form-item label="出行天数" prop="days">
          <el-input-number v-model="routeForm.days" :min="1" :max="10" />
        </el-form-item>
        <el-form-item label="人数" prop="peopleCount">
          <el-input-number v-model="routeForm.peopleCount" :min="1" :max="20" />
        </el-form-item>
        <el-form-item label="预算" prop="budget">
          <el-input-number v-model="routeForm.budget" :min="0" :step="100" />
        </el-form-item>
        <el-form-item label="偏好主题" prop="preferences">
          <el-select v-model="routeForm.preferences" placeholder="请选择偏好主题">
            <el-option label="历史文化" value="历史文化" />
            <el-option label="自然风光" value="自然风光" />
            <el-option label="美食体验" value="美食体验" />
            <el-option label="民俗风情" value="民俗风情" />
            <el-option label="休闲度假" value="休闲度假" />
          </el-select>
        </el-form-item>
        <el-form-item label="出发时间" prop="startTime">
          <el-date-picker
            v-model="routeForm.startTime"
            type="date"
            placeholder="选择出发时间"
            format="YYYY-MM-DD"
          />
        </el-form-item>
        <el-form-item label="路线描述">
          <el-input
            v-model="routeForm.description"
            type="textarea"
            :rows="4"
            placeholder="请输入路线描述"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSubmit">生成路线</el-button>
          <el-button @click="handleReset">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <el-card v-if="generatedRoute" style="margin-top: 20px">
      <template #header>
        <div class="card-header">
          <span>推荐路线</span>
          <el-button type="success" @click="handleSaveRoute">保存路线</el-button>
        </div>
      </template>

      <AmapRouteMap v-if="generatedRoute.routeData" :routeData="generatedRoute.routeData" height="500px" style="margin-bottom: 20px" />

      <el-timeline>
        <el-timeline-item
          v-for="(day, index) in parseRouteData(generatedRoute.routeData)"
          :key="index"
          :timestamp="'第' + day.day + '天'"
          placement="top"
        >
          <el-card>
            <h3>{{ day.region }}</h3>
            <el-row :gutter="20">
              <el-col :span="8" v-for="resource in day.resources" :key="resource.id">
                <div class="day-resource">
                  <h4>{{ resource.name }}</h4>
                  <p>{{ resource.description }}</p>
                  <p>地址: {{ resource.address || day.region }}</p>
                  <p>价格: ¥{{ resource.price }}</p>
                </div>
              </el-col>
            </el-row>
          </el-card>
        </el-timeline-item>
      </el-timeline>
    </el-card>
  </div>
</template>

<script>
import { ref, computed } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { createCustomRoute } from '@/api/route'
import AmapRouteMap from '@/components/AmapRouteMap.vue'

export default {
  name: 'RouteCustom',
  components: {
    AmapRouteMap
  },
  setup() {
    const router = useRouter()
    const routeFormRef = ref()
    const generatedRoute = ref(null)

    const userInfo = JSON.parse(localStorage.getItem('userInfo') || '{}')

    const routeForm = ref({
      userId: userInfo.id,
      name: '',
      description: '',
      days: 3,
      budget: 2000,
      peopleCount: 2,
      preferences: '',
      startTime: ''
    })

    const rules = {
      name: [{ required: true, message: '请输入路线名称', trigger: 'blur' }],
      days: [{ required: true, message: '请输入出行天数', trigger: 'blur' }],
      budget: [{ required: true, message: '请输入预算', trigger: 'blur' }],
      peopleCount: [{ required: true, message: '请输入人数', trigger: 'blur' }],
      preferences: [{ required: true, message: '请选择偏好主题', trigger: 'change' }],
      startTime: [{ required: true, message: '请选择出发时间', trigger: 'change' }]
    }

    const handleSubmit = async () => {
      try {
        await routeFormRef.value.validate()
        const res = await createCustomRoute(routeForm.value)
        generatedRoute.value = res.data
        ElMessage.success('路线生成成功')
      } catch (error) {
        console.error('生成路线失败', error)
      }
    }

    const handleReset = () => {
      routeFormRef.value.resetFields()
      generatedRoute.value = null
    }

    const handleSaveRoute = () => {
      ElMessage.success('路线已保存到我的路线')
      router.push('/route/list')
    }

    const parseRouteData = (routeData) => {
      try {
        return JSON.parse(routeData)
      } catch (error) {
        return []
      }
    }

    return {
      routeForm,
      rules,
      routeFormRef,
      generatedRoute,
      handleSubmit,
      handleReset,
      handleSaveRoute,
      parseRouteData
    }
  }
}
</script>

<style scoped>
.day-resource {
  background-color: #f5f7fa;
  padding: 15px;
  border-radius: 4px;
  margin-bottom: 10px;
}

.day-resource h4 {
  margin-bottom: 10px;
  color: #303133;
}

.day-resource p {
  font-size: 13px;
  color: #606266;
  margin: 5px 0;
}
</style>
