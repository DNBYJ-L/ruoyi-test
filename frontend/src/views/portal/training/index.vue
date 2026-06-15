<template>
  <div class="app-container">
    <!-- 可报名的培训计划 -->
    <el-card shadow="hover" style="margin-bottom: 20px">
      <template #header>
        <div class="card-header">
          <span>可报名的培训计划</span>
          <el-button icon="Refresh" size="small" @click="loadPlans">刷新</el-button>
        </div>
      </template>
      <el-table v-loading="planLoading" :data="planList" border size="small">
        <el-table-column label="培训名称" prop="planName" min-width="180" show-overflow-tooltip />
        <el-table-column label="培训类型" width="100" align="center">
          <template #default="scope">
            <dict-tag :options="tea_training_type" :value="scope.row.trainingType" />
          </template>
        </el-table-column>
        <el-table-column label="授课讲师" prop="instructorName" width="100" />
        <el-table-column label="培训地点" prop="location" width="120" show-overflow-tooltip />
        <el-table-column label="学时" prop="totalHours" width="70" align="center" />
        <el-table-column label="名额" width="90" align="center">
          <template #default="scope">{{ scope.row.maxCount || '-' }}</template>
        </el-table-column>
        <el-table-column label="开始时间" prop="startDate" width="110" />
        <el-table-column label="结束时间" prop="endDate" width="110" />
        <el-table-column label="操作" width="90" align="center" fixed="right">
          <template #default="scope">
            <el-button type="primary" size="small" @click="handleEnroll(scope.row)" v-hasPermi="['portal:training:enroll']">报名</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-empty v-if="!planLoading && planList.length === 0" description="当前暂无可报名的培训" />
    </el-card>

    <!-- 我的报名记录 -->
    <el-card shadow="hover">
      <template #header>
        <div class="card-header">
          <span>我的报名记录</span>
          <el-button icon="Refresh" size="small" @click="loadEnrollments">刷新</el-button>
        </div>
      </template>
      <el-table v-loading="enrollLoading" :data="enrollList" border size="small">
        <el-table-column label="培训计划" prop="planName" min-width="200" show-overflow-tooltip />
        <el-table-column label="报名日期" prop="applyDate" width="120" />
        <el-table-column label="审批状态" width="100" align="center">
          <template #default="scope">
            <dict-tag :options="tea_approve_status" :value="scope.row.approveStatus" />
          </template>
        </el-table-column>
        <el-table-column label="审批意见" prop="approveRemark" show-overflow-tooltip />
      </el-table>
      <el-empty v-if="!enrollLoading && enrollList.length === 0" description="暂无报名记录" />
      <pagination v-show="enrollTotal > 0" :total="enrollTotal" v-model:page="enrollQuery.pageNum" v-model:limit="enrollQuery.pageSize" @pagination="loadEnrollments" />
    </el-card>
  </div>
</template>

<script setup name="PortalTraining">
import { getAvailablePlans, enrollTraining, getMyEnrollments } from '@/api/portal/training'

const { proxy } = getCurrentInstance()
const { tea_training_type } = proxy.useDict('tea_training_type')
const { tea_approve_status } = proxy.useDict('tea_approve_status')

const planList = ref([])
const planLoading = ref(false)
const enrollList = ref([])
const enrollLoading = ref(false)
const enrollTotal = ref(0)
const enrollQuery = reactive({ pageNum: 1, pageSize: 10 })

function loadPlans() {
  planLoading.value = true
  getAvailablePlans({ pageNum: 1, pageSize: 50 }).then(res => {
    planList.value = res.rows || []
    planLoading.value = false
  }).catch(() => { planLoading.value = false })
}

function loadEnrollments() {
  enrollLoading.value = true
  getMyEnrollments(enrollQuery).then(res => {
    enrollList.value = res.rows || []
    enrollTotal.value = res.total || 0
    enrollLoading.value = false
  }).catch(() => { enrollLoading.value = false })
}

function handleEnroll(row) {
  proxy.$modal.confirm(`确认报名参加「${row.planName}」培训？`).then(() => {
    return enrollTraining({ planId: row.planId })
  }).then(() => {
    proxy.$modal.msgSuccess('报名成功')
    loadEnrollments()
  }).catch(() => {})
}

loadPlans()
loadEnrollments()
</script>

<style scoped>
.card-header { display: flex; justify-content: space-between; align-items: center; }
</style>
