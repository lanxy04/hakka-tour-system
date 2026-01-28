<template>
  <div class="resource-manage">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>资源管理</span>
          <el-button type="primary" @click="handleAdd">新增资源</el-button>
        </div>
      </template>

      <el-table :data="resources" style="width: 100%">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="name" label="名称" width="150" />
        <el-table-column prop="region" label="地区" width="100" />
        <el-table-column prop="type" label="类型" width="100" />
        <el-table-column prop="theme" label="主题" width="100" />
        <el-table-column prop="score" label="评分" width="80" />
        <el-table-column prop="hotCount" label="热度" width="80" />
        <el-table-column prop="price" label="价格" width="100" />
        <el-table-column label="操作" width="200">
          <template #default="scope">
            <el-button type="primary" size="small" @click="handleEdit(scope.row)">编辑</el-button>
            <el-button type="danger" size="small" @click="handleDelete(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog
      v-model="dialogVisible"
      :title="dialogTitle"
      width="600px"
      @close="handleDialogClose"
    >
      <el-form :model="form" :rules="rules" ref="formRef" label-width="100px">
        <el-form-item label="名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入资源名称" />
        </el-form-item>
        <el-form-item label="地区" prop="region">
          <el-input v-model="form.region" placeholder="请输入地区" />
        </el-form-item>
        <el-form-item label="类型" prop="type">
          <el-select v-model="form.type" placeholder="请选择类型">
            <el-option label="景点" value="景点" />
            <el-option label="美食" value="美食" />
            <el-option label="住宿" value="住宿" />
            <el-option label="文化体验" value="文化体验" />
          </el-select>
        </el-form-item>
        <el-form-item label="主题" prop="theme">
          <el-input v-model="form.theme" placeholder="请输入主题" />
        </el-form-item>
        <el-form-item label="地址" prop="address">
          <el-input v-model="form.address" placeholder="请输入地址" />
        </el-form-item>
        <el-form-item label="价格" prop="price">
          <el-input-number v-model="form.price" :min="0" :step="10" />
        </el-form-item>
        <el-form-item label="图片URL" prop="images">
          <el-input v-model="form.images" placeholder="请输入图片URL" />
        </el-form-item>
        <el-form-item label="标签" prop="tags">
          <el-input v-model="form.tags" placeholder="请输入标签，用逗号分隔" />
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input
            v-model="form.description"
            type="textarea"
            :rows="4"
            placeholder="请输入资源描述"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import { ref, onMounted, computed } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import {
  getResourceList,
  createResource,
  updateResource,
  deleteResource
} from '@/api/resource'

export default {
  name: 'ResourceManage',
  setup() {
    const resources = ref([])
    const dialogVisible = ref(false)
    const formRef = ref()
    const isEdit = ref(false)
    const currentId = ref(null)

    const form = ref({
      name: '',
      region: '',
      type: '',
      theme: '',
      description: '',
      address: '',
      price: 0,
      images: '',
      tags: ''
    })

    const rules = {
      name: [{ required: true, message: '请输入资源名称', trigger: 'blur' }],
      region: [{ required: true, message: '请输入地区', trigger: 'blur' }],
      type: [{ required: true, message: '请选择类型', trigger: 'change' }],
      theme: [{ required: true, message: '请输入主题', trigger: 'blur' }],
      address: [{ required: true, message: '请输入地址', trigger: 'blur' }],
      price: [{ required: true, message: '请输入价格', trigger: 'blur' }]
    }

    const dialogTitle = computed(() => (isEdit.value ? '编辑资源' : '新增资源'))

    const loadResources = async () => {
      try {
        const res = await getResourceList()
        resources.value = res.data
      } catch (error) {
        console.error('加载资源失败', error)
      }
    }

    const handleAdd = () => {
      isEdit.value = false
      currentId.value = null
      form.value = {
        name: '',
        region: '',
        type: '',
        theme: '',
        description: '',
        address: '',
        price: 0,
        images: '',
        tags: ''
      }
      dialogVisible.value = true
    }

    const handleEdit = (row) => {
      isEdit.value = true
      currentId.value = row.id
      form.value = { ...row }
      dialogVisible.value = true
    }

    const handleDelete = async (id) => {
      try {
        await ElMessageBox.confirm('确定删除该资源吗?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
        await deleteResource(id)
        ElMessage.success('删除成功')
        loadResources()
      } catch (error) {
        console.error('删除失败', error)
      }
    }

    const handleSubmit = async () => {
      try {
        await formRef.value.validate()
        if (isEdit.value) {
          await updateResource({ ...form.value, id: currentId.value })
          ElMessage.success('更新成功')
        } else {
          await createResource(form.value)
          ElMessage.success('创建成功')
        }
        dialogVisible.value = false
        loadResources()
      } catch (error) {
        console.error('提交失败', error)
      }
    }

    const handleDialogClose = () => {
      formRef.value?.resetFields()
    }

    onMounted(() => {
      loadResources()
    })

    return {
      resources,
      dialogVisible,
      dialogTitle,
      form,
      rules,
      formRef,
      handleAdd,
      handleEdit,
      handleDelete,
      handleSubmit,
      handleDialogClose
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
