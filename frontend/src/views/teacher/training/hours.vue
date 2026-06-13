<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true">
      <el-form-item label="教师ID" prop="teacherId"><el-input v-model="queryParams.teacherId" placeholder="留空查看全部" clearable style="width:160px" /></el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">查询</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="20" style="margin-bottom:20px">
      <el-col :span="8">
        <el-card shadow="hover"><el-statistic title="总培训人次" :value="stats.totalCount" /></el-card>
      </el-col>
      <el-col :span="8">
        <el-card shadow="hover"><el-statistic title="总认定学时" :value="stats.totalHours" /></el-card>
      </el-col>
      <el-col :span="8">
        <el-card shadow="hover"><el-statistic title="涉及院系" :value="stats.deptCount" /></el-card>
      </el-col>
    </el-row>

    <el-table :data="hoursList" border size="small" v-loading="loading">
      <el-table-column label="教师" prop="teacherName" width="120" />
      <el-table-column label="院系" prop="deptName" width="180" />
      <el-table-column label="累计学时" prop="totalHours" width="100" align="right" />
      <el-table-column label="5年要求" width="100" align="center"><template #default>360</template></el-table-column>
      <el-table-column label="完成进度" width="200">
        <template #default="scope">
          <el-progress :percentage="Math.min(100, Math.round((scope.row.totalHours / 360) * 100))" :color="scope.row.totalHours >= 360 ? '#67C23A' : '#E6A23C'" :stroke-width="16" />
        </template>
      </el-table-column>
      <el-table-column label="状态" width="80" align="center">
        <template #default="scope"><el-tag :type="scope.row.totalHours >= 360 ? 'success' : 'warning'" size="small">{{ scope.row.totalHours >= 360 ? '达标' : '未达标' }}</el-tag></template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script setup name="TrainingHours">
import { getHoursStat } from '@/api/teacher/training'
const loading = ref(false)
const hoursList = ref([])
const stats = reactive({ totalCount: 0, totalHours: 0, deptCount: 0 })
const queryParams = reactive({ teacherId: undefined })

function handleQuery() {
  loading.value = true
  getHoursStat(queryParams.teacherId || undefined).then(res => {
    hoursList.value = res.data || []
    stats.totalCount = hoursList.value.length
    stats.totalHours = hoursList.value.reduce((s, i) => s + (i.totalHours || 0), 0)
    stats.deptCount = new Set(hoursList.value.map(i => i.deptName)).size
    loading.value = false
  })
}
handleQuery()
</script>
