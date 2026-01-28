<template>
  <div class="resource-detail">
    <el-card v-if="resource">
      <template #header>
        <div class="card-header">
          <span>{{ resource.name }}</span>
          <el-button type="primary" @click="handleRate">评分</el-button>
        </div>
      </template>

      <el-row :gutter="20">
        <el-col :span="12">
          <div class="detail-image">
            <img :src="resource.images || 'https://via.placeholder.com/600x400'" :alt="resource.name" />
          </div>
        </el-col>
        <el-col :span="12">
          <div class="detail-info">
            <el-descriptions :column="1" border>
              <el-descriptions-item label="地区">{{ resource.region }}</el-descriptions-item>
              <el-descriptions-item label="类型">{{ resource.type }}</el-descriptions-item>
              <el-descriptions-item label="主题">{{ resource.theme }}</el-descriptions-item>
              <el-descriptions-item label="地址">{{ resource.address }}</el-descriptions-item>
              <el-descriptions-item label="价格">¥{{ resource.price }}</el-descriptions-item>
              <el-descriptions-item label="评分">{{ resource.score || 0 }}</el-descriptions-item>
              <el-descriptions-item label="热度">{{ resource.hotCount || 0 }}</el-descriptions-item>
            </el-descriptions>

            <div class="detail-description">
              <h3>详细介绍</h3>
              <p>{{ resource.description }}</p>
            </div>

            <div class="detail-tags">
              <el-tag v-for="(tag, index) in parseTags(resource.tags)" :key="index" style="margin-right: 10px">
                {{ tag }}
              </el-tag>
            </div>

            <el-button type="primary" size="large" @click="addToRoute" style="margin-top: 20px">
              添加到路线
            </el-button>
          </div>
        </el-col>
      </el-row>
    </el-card>

    <el-dialog v-model="rateDialogVisible" title="评分" width="30%">
      <el-rate v-model="rateValue" show-text />
      <template #footer>
        <el-button @click="rateDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitRate">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { ElMessage } from 'element-plus'
import { getResourceDetail, rateResource } from '@/api/resource'

export default {
  name: 'ResourceDetail',
  setup() {
    const route = useRoute()
    const resource = ref(null)
    const rateDialogVisible = ref(false)
    const rateValue = ref(0)

    const loadResourceDetail = async () => {
      try {
        const res = await getResourceDetail(route.params.id)
        resource.value = res.data
      } catch (error) {
        console.error('加载资源详情失败', error)
      }
    }

    const parseTags = (tags) => {
      if (!tags) return []
      return tags.split(',').map(tag => tag.trim())
    }

    const handleRate = () => {
      rateDialogVisible.value = true
    }

    const submitRate = async () => {
      try {
        await rateResource(resource.value.id, rateValue.value)
        ElMessage.success('评分成功')
        rateDialogVisible.value = false
        loadResourceDetail()
      } catch (error) {
        console.error('评分失败', error)
      }
    }

    const addToRoute = () => {
      ElMessage.success('已添加到待选资源')
    }

    onMounted(() => {
      loadResourceDetail()
    })

    return {
      resource,
      rateDialogVisible,
      rateValue,
      parseTags,
      handleRate,
      submitRate,
      addToRoute
    }
  }
}
</script>

<style scoped>
.detail-image img {
  width: 100%;
  height: 400px;
  object-fit: cover;
  border-radius: 4px;
}

.detail-info {
  padding: 20px 0;
}

.detail-description {
  margin: 20px 0;
}

.detail-description h3 {
  margin-bottom: 10px;
  font-size: 16px;
}

.detail-description p {
  line-height: 1.8;
  color: #606266;
}

.detail-tags {
  margin-top: 20px;
}
</style>
