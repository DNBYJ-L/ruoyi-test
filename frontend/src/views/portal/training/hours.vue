<template>
  <div class="app-container">
    <!-- 学时统计卡片 -->
    <el-row :gutter="20" style="margin-bottom: 20px">
      <el-col :span="8">
        <el-card shadow="hover" class="stat-card">
          <el-statistic title="累计学时" :value="statData.totalHours">
            <template #suffix>学时</template>
          </el-statistic>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card shadow="hover" class="stat-card">
          <el-statistic title="已结业" :value="statData.passCount">
            <template #suffix>项</template>
          </el-statistic>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card shadow="hover" class="stat-card">
          <el-statistic title="结业通过率" :value="passRate" :precision="1">
            <template #suffix>%</template>
          </el-statistic>
        </el-card>
      </el-col>
    </el-row>

    <!-- 结业成绩列表 -->
    <el-card shadow="hover">
      <template #header>
        <div class="card-header">
          <span>结业成绩</span>
          <el-button icon="Refresh" size="small" @click="loadResults">刷新</el-button>
        </div>
      </template>
      <el-table v-loading="loading" :data="resultList" border size="small">
        <el-table-column label="培训名称" prop="planName" min-width="200" show-overflow-tooltip />
        <el-table-column label="考核方式" width="100" align="center">
          <template #default="scope">
            <span v-if="scope.row.assessmentType === '1'">考试</span>
            <span v-else-if="scope.row.assessmentType === '2'">作业</span>
            <span v-else-if="scope.row.assessmentType === '3'">实践</span>
            <span v-else>综合</span>
          </template>
        </el-table-column>
        <el-table-column label="考试成绩" prop="examScore" width="90" align="center" />
        <el-table-column label="学时" prop="creditHours" width="70" align="center" />
        <el-table-column label="通过状态" width="90" align="center">
          <template #default="scope">
            <el-tag :type="scope.row.passStatus === '1' ? 'success' : 'danger'" size="small">
              {{ scope.row.passStatus === '1' ? '合格' : '不合格' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="证书编号" prop="certificateNo" width="150" />
        <el-table-column label="满意度" prop="satisfactionScore" width="80" align="center" />
      </el-table>
      <el-empty v-if="!loading && resultList.length === 0" description="暂无结业记录" />
    </el-card>
  </div>
</template>

<script setup name="PortalHours">
import { getMyResults, getMyHours } from '@/api/portal/training'

const { proxy } = getCurrentInstance()
const resultList = ref([])
const loading = ref(false)
const statData = ref({ totalHours: 0, passCount: 0 })
const passRate = computed(() => {
  const total = resultList.value.length
  if (total === 0) return 0
  const pass = resultList.value.filter(r => r.passStatus === '1').length
  return (pass / total * 100)
})

function loadResults() {
  loading.value = true
  getMyResults({ pageNum: 1, pageSize: 100 }).then(res => {
    resultList.value = res.rows || []
    loading.value = false
  }).catch(() => { loading.value = false })
}

function loadHoursStat() {
  getMyHours().then(res => {
    statData.value = res.data || { totalHours: 0, passCount: 0 }
  })
}

loadResults()
loadHoursStat()
</script>

<style scoped>
.card-header { display: flex; justify-content: space-between; align-items: center; }
.stat-card { text-align: center; }
</style>
