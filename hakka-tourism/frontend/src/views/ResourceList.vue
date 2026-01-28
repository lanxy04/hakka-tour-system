<template>
  <div class="resource-list">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>旅游资源</span>
        </div>
      </template>

      <el-form :model="searchForm" inline class="search-form">
        <el-form-item label="地区">
          <el-input v-model="searchForm.region" placeholder="请输入地区" />
        </el-form-item>
        <el-form-item label="类型">
          <el-select v-model="searchForm.type" placeholder="请选择类型" clearable>
            <el-option label="景点" value="景点" />
            <el-option label="美食" value="美食" />
            <el-option label="住宿" value="住宿" />
            <el-option label="文化体验" value="文化体验" />
          </el-select>
        </el-form-item>
        <el-form-item label="主题">
          <el-input v-model="searchForm.theme" placeholder="请输入主题" />
        </el-form-item>
        <el-form-item label="排序">
          <el-select v-model="searchForm.sortBy" placeholder="请选择排序方式">
            <el-option label="默认" value="default" />
            <el-option label="热度" value="hot" />
            <el-option label="评分" value="score" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">搜索</el-button>
          <el-button @click="handleReset">重置</el-button>
        </el-form-item>
      </el-form>

      <el-row :gutter="20" class="resource-grid">
        <el-col :span="6" v-for="resource in resources" :key="resource.id">
          <el-card class="resource-card" @click="viewDetail(resource.id)" shadow="hover">
            <div class="resource-image">
              <img :src="resource.images || 'https://via.placeholder.com/300x200'" :alt="resource.name" />
            </div>
            <div class="resource-info">
              <h3 class="resource-name">{{ resource.name }}</h3>
              <p class="resource-meta">
                <el-icon><Location /></el-icon>
                {{ resource.region }}
              </p>
              <p class="resource-meta">
                <el-icon><Star /></el-icon>
                {{ resource.score || 0 }}
              </p>
              <p class="resource-meta">
                <el-icon><View /></el-icon>
                {{ resource.hotCount || 0 }}
              </p>
              <p class="resource-price">¥{{ resource.price }}</p>
            </div>
          </el-card>
        </el-col>
      </el-row>

      <el-pagination
        v-model:current-page="pagination.currentPage"
        v-model:page-size="pagination.pageSize"
        :total="pagination.total"
        :page-sizes="[8, 16, 24]"
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        style="margin-top: 20px; text-align: center"
      />
    </el-card>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { getResourceList, searchResource } from '@/api/resource'

export default {
  name: 'ResourceList',
  setup() {
    const router = useRouter()
    const resources = ref([])
    const searchForm = ref({
      region: '',
      type: '',
      theme: '',
      sortBy: 'default'
    })
    const pagination = ref({
      currentPage: 1,
      pageSize: 8,
      total: 0
    })

    const loadResources = async () => {
      try {
        if (Object.values(searchForm.value).some(v => v)) {
          const res = await searchResource(searchForm.value)
          resources.value = res.data
        } else {
          const res = await getResourceList()
          resources.value = res.data
        }
        pagination.value.total = resources.value.length
      } catch (error) {
        console.error('加载资源失败', error)
      }
    }

    const handleSearch = () => {
      pagination.value.currentPage = 1
      loadResources()
    }

    const handleReset = () => {
      searchForm.value = {
        region: '',
        type: '',
        theme: '',
        sortBy: 'default'
      }
      loadResources()
    }

    const viewDetail = (id) => {
      router.push(`/resources/${id}`)
    }

    const handleSizeChange = (val) => {
      pagination.value.pageSize = val
    }

    const handleCurrentChange = (val) => {
      pagination.value.currentPage = val
    }

    onMounted(() => {
      loadResources()
    })

    return {
      resources,
      searchForm,
      pagination,
      handleSearch,
      handleReset,
      viewDetail,
      handleSizeChange,
      handleCurrentChange
    }
  }
}
</script>

<style scoped>
.search-form {
  margin-bottom: 20px;
}

.resource-grid {
  margin-top: 20px;
}

.resource-card {
  cursor: pointer;
  margin-bottom: 20px;
  transition: all 0.3s;
}

.resource-card:hover {
  transform: translateY(-5px);
}

.resource-image {
  height: 200px;
  overflow: hidden;
}

.resource-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.resource-info {
  padding: 10px 0;
}

.resource-name {
  font-size: 16px;
  font-weight: bold;
  margin-bottom: 10px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.resource-meta {
  font-size: 12px;
  color: #909399;
  margin: 5px 0;
  display: flex;
  align-items: center;
}

.resource-price {
  font-size: 18px;
  color: #f56c6c;
  font-weight: bold;
  margin-top: 10px;
}
</style>
