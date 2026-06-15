<template>
  <div class="app-container">
    <el-card shadow="hover">
      <template #header>
        <div class="card-header">
          <span>我的考核结果</span>
          <el-button icon="Refresh" size="small" @click="loadRecords">刷新</el-button>
        </div>
      </template>

      <el-table v-loading="loading" :data="recordList" border size="small">
        <el-table-column label="考核方案" prop="planName" min-width="200" show-overflow-tooltip />
        <el-table-column label="考核年度" width="90" align="center" />
        <el-table-column label="教学分" prop="teachingScore" width="80" align="right" />
        <el-table-column label="科研分" prop="researchScore" width="80" align="right" />
        <el-table-column label="服务分" prop="serviceScore" width="80" align="right" />
        <el-table-column label="总分" prop="totalScore" width="90" align="right">
          <template #default="scope"><b style="font-size:15px">{{ scope.row.totalScore }}</b></template>
        </el-table-column>
        <el-table-column label="等级" prop="grade" width="90" align="center">
          <template #default="scope">
            <dict-tag :options="tea_assessment_grade" :value="scope.row.grade" />
          </template>
        </el-table-column>
        <el-table-column label="状态" prop="status" width="90" align="center">
          <template #default="scope">
            <dict-tag :options="tea_assessment_status" :value="scope.row.status" />
          </template>
        </el-table-column>
        <el-table-column label="操作" width="90" align="center" fixed="right">
          <template #default="scope">
            <el-button link type="primary" icon="View" @click="handleDetail(scope.row)">详情</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-empty v-if="!loading && recordList.length === 0" description="暂无考核结果" />
    </el-card>

    <!-- 详情对话框 -->
    <el-dialog title="考核结果详情" v-model="detailOpen" width="800px" append-to-body>
      <div v-if="detailData">
        <!-- 分数概览 -->
        <el-row :gutter="20" style="margin-bottom:20px">
          <el-col :span="6">
            <el-card shadow="never" class="score-card">
              <el-statistic title="教学分" :value="detailData.teachingScore || 0" :precision="1" />
            </el-card>
          </el-col>
          <el-col :span="6">
            <el-card shadow="never" class="score-card">
              <el-statistic title="科研分" :value="detailData.researchScore || 0" :precision="1" />
            </el-card>
          </el-col>
          <el-col :span="6">
            <el-card shadow="never" class="score-card">
              <el-statistic title="服务分" :value="detailData.serviceScore || 0" :precision="1" />
            </el-card>
          </el-col>
          <el-col :span="6">
            <el-card shadow="never" class="score-card total">
              <el-statistic title="总分" :value="detailData.totalScore || 0" :precision="1" />
            </el-card>
          </el-col>
        </el-row>

        <el-descriptions :column="2" border>
          <el-descriptions-item label="考核方案">{{ detailData.planName }}</el-descriptions-item>
          <el-descriptions-item label="等级"><dict-tag :options="tea_assessment_grade" :value="detailData.grade" /></el-descriptions-item>
          <el-descriptions-item label="状态"><dict-tag :options="tea_assessment_status" :value="detailData.status" /></el-descriptions-item>
          <el-descriptions-item label="提交时间">{{ detailData.submitTime || '-' }}</el-descriptions-item>
          <el-descriptions-item label="院系评价" :span="2">{{ detailData.deptEvaluation || '-' }}</el-descriptions-item>
        </el-descriptions>

        <!-- 审核轨迹 -->
        <el-divider content-position="left" v-if="detailAudit.length > 0">审核轨迹</el-divider>
        <el-timeline v-if="detailAudit.length > 0">
          <el-timeline-item v-for="item in detailAudit" :key="item.auditId"
            :type="item.result === '1' ? 'success' : item.result === '2' ? 'danger' : 'info'"
            :timestamp="item.auditTime" placement="top">
            <b>{{ item.auditNode }}</b> - {{ item.auditorName }}
            <p>{{ item.result === '1' ? '通过' : '退回' }}：{{ item.opinion || '' }}</p>
          </el-timeline-item>
        </el-timeline>
      </div>
    </el-dialog>
  </div>
</template>

<script setup name="PortalAssessResult">
import { getMyRecords, getRecordDetail } from '@/api/portal/assessment'

const { proxy } = getCurrentInstance()
const { tea_assessment_status } = proxy.useDict('tea_assessment_status')
const { tea_assessment_grade } = proxy.useDict('tea_assessment_grade')

const recordList = ref([])
const loading = ref(false)
const detailOpen = ref(false)
const detailData = ref(null)
const detailAudit = ref([])

function loadRecords() {
  loading.value = true
  getMyRecords({ pageNum: 1, pageSize: 50 }).then(res => {
    recordList.value = res.rows || []
    loading.value = false
  }).catch(() => { loading.value = false })
}

function handleDetail(row) {
  getRecordDetail(row.recordId).then(res => {
    detailData.value = res.data
    detailAudit.value = res.auditTrail || []
    detailOpen.value = true
  })
}

loadRecords()
</script>

<style scoped>
.card-header { display: flex; justify-content: space-between; align-items: center; }
.score-card { text-align: center; }
.score-card.total { background-color: #f0f9eb; }
</style>
