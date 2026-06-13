<template>
  <div class="app-container">
    <el-form :inline="true">
      <el-form-item label="方案ID"><el-input v-model="planId" placeholder="方案ID" clearable style="width:140px" /></el-form-item>
      <el-form-item><el-button type="primary" icon="Search" @click="handleQuery">查询</el-button></el-form-item>
    </el-form>

    <el-row :gutter="20" style="margin-bottom:20px">
      <el-col :span="6">
        <el-card shadow="hover"><el-statistic title="参评人数" :value="stats.totalCount" /></el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover"><el-statistic title="平均总分" :value="stats.avgScore" :precision="1" /></el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover"><el-statistic title="优秀人数" :value="stats.excellentCount" /></el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover"><el-statistic title="不合格人数" :value="stats.failCount" /></el-card>
      </el-col>
    </el-row>

    <el-table :data="statList" border size="small" v-loading="loading">
      <el-table-column label="院系" prop="deptName" width="180" />
      <el-table-column label="人数" prop="totalCount" width="80" align="center" />
      <el-table-column label="平均总分" prop="avgScore" width="100" align="right">
        <template #default="scope"><b>{{ Number(scope.row.avgScore).toFixed(1) }}</b></template>
      </el-table-column>
      <el-table-column label="平均教学" prop="avgTeaching" width="100" align="right">
        <template #default="scope">{{ Number(scope.row.avgTeaching).toFixed(1) }}</template>
      </el-table-column>
      <el-table-column label="平均科研" prop="avgResearch" width="100" align="right">
        <template #default="scope">{{ Number(scope.row.avgResearch).toFixed(1) }}</template>
      </el-table-column>
      <el-table-column label="平均服务" prop="avgService" width="100" align="right">
        <template #default="scope">{{ Number(scope.row.avgService).toFixed(1) }}</template>
      </el-table-column>
      <el-table-column label="优秀" prop="excellentCount" width="70" align="center">
        <template #default="scope"><el-tag type="success" size="small">{{ scope.row.excellentCount }}</el-tag></template>
      </el-table-column>
      <el-table-column label="合格" prop="passCount" width="70" align="center">
        <template #default="scope"><el-tag type="primary" size="small">{{ scope.row.passCount }}</el-tag></template>
      </el-table-column>
      <el-table-column label="基本合格" prop="basicCount" width="80" align="center">
        <template #default="scope"><el-tag type="warning" size="small">{{ scope.row.basicCount }}</el-tag></template>
      </el-table-column>
      <el-table-column label="不合格" prop="failCount" width="70" align="center">
        <template #default="scope"><el-tag type="danger" size="small">{{ scope.row.failCount }}</el-tag></template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script setup name="AssessmentResult">
import { getScoreStat } from '@/api/teacher/assessment'
const loading = ref(false)
const planId = ref(undefined)
const statList = ref([])
const stats = reactive({ totalCount: 0, avgScore: 0, excellentCount: 0, failCount: 0 })

function handleQuery() {
  loading.value = true
  getScoreStat(planId.value || undefined).then(res => {
    statList.value = res.data || []
    stats.totalCount = statList.value.reduce((s, i) => s + (i.totalCount || 0), 0)
    stats.avgScore = statList.value.length > 0 ? statList.value.reduce((s, i) => s + (i.avgScore || 0), 0) / statList.value.length : 0
    stats.excellentCount = statList.value.reduce((s, i) => s + (i.excellentCount || 0), 0)
    stats.failCount = statList.value.reduce((s, i) => s + (i.failCount || 0), 0)
    loading.value = false
  })
}
handleQuery()
</script>
