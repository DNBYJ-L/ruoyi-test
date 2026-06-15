<template>
  <div class="app-container">
    <!-- 当前考核方案 -->
    <el-card shadow="hover" style="margin-bottom: 20px">
      <template #header>
        <div class="card-header"><span>进行中的考核方案</span></div>
      </template>
      <el-table v-loading="planLoading" :data="planList" border size="small">
        <el-table-column label="方案名称" prop="planName" min-width="200" show-overflow-tooltip />
        <el-table-column label="考核类型" width="100" align="center">
          <template #default="scope">
            <dict-tag :options="tea_assessment_type" :value="scope.row.assessmentType" />
          </template>
        </el-table-column>
        <el-table-column label="考核年度" prop="assessmentYear" width="90" align="center" />
        <el-table-column label="开始时间" prop="startDate" width="110" />
        <el-table-column label="结束时间" prop="endDate" width="110" />
        <el-table-column label="操作" width="120" align="center">
          <template #default="scope">
            <el-button type="primary" size="small" @click="handleFill(scope.row)" v-hasPermi="['portal:assess:edit']">填报</el-button>
            <el-button type="info" size="small" @click="handleViewRecords(scope.row)">我的</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-empty v-if="!planLoading && planList.length === 0" description="当前暂无进行中的考核" />
    </el-card>

    <!-- 我的考核记录 -->
    <el-card shadow="hover">
      <template #header>
        <div class="card-header">
          <span>我的考核记录</span>
          <el-button icon="Refresh" size="small" @click="loadRecords">刷新</el-button>
        </div>
      </template>
      <el-table v-loading="recordLoading" :data="recordList" border size="small">
        <el-table-column label="方案" prop="planName" min-width="180" show-overflow-tooltip />
        <el-table-column label="教学" prop="teachingScore" width="70" align="right" />
        <el-table-column label="科研" prop="researchScore" width="70" align="right" />
        <el-table-column label="服务" prop="serviceScore" width="70" align="right" />
        <el-table-column label="总分" prop="totalScore" width="80" align="right">
          <template #default="scope"><b>{{ scope.row.totalScore }}</b></template>
        </el-table-column>
        <el-table-column label="等级" prop="grade" width="80" align="center">
          <template #default="scope"><dict-tag :options="tea_assessment_grade" :value="scope.row.grade" /></template>
        </el-table-column>
        <el-table-column label="状态" prop="status" width="90" align="center">
          <template #default="scope"><dict-tag :options="tea_assessment_status" :value="scope.row.status" /></template>
        </el-table-column>
        <el-table-column label="操作" width="160" align="center" fixed="right">
          <template #default="scope">
            <el-button link type="primary" icon="Edit" @click="handleEdit(scope.row)" v-if="scope.row.status === '0' || scope.row.status === '5'" v-hasPermi="['portal:assess:edit']">编辑</el-button>
            <el-button link type="success" @click="handleSubmit(scope.row)" v-if="scope.row.status === '0'" v-hasPermi="['portal:assess:submit']">提交</el-button>
            <el-button link type="info" icon="View" @click="handleDetail(scope.row)">详情</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-empty v-if="!recordLoading && recordList.length === 0" description="暂无考核记录" />
    </el-card>

    <!-- 填报/编辑对话框 -->
    <el-dialog :title="formTitle" v-model="formOpen" width="900px" append-to-body destroy-on-close>
      <el-steps :active="activeTab" finish-status="success" align-center style="margin-bottom:20px">
        <el-step title="基本信息" @click="activeTab = 0" style="cursor:pointer" />
        <el-step title="教学工作" @click="activeTab = 1" style="cursor:pointer" />
        <el-step title="科研工作" @click="activeTab = 2" style="cursor:pointer" />
        <el-step title="社会服务" @click="activeTab = 3" style="cursor:pointer" />
      </el-steps>

      <!-- 基本信息 -->
      <el-form v-show="activeTab === 0" :model="form" label-width="100px">
        <el-row :gutter="20">
          <el-col :span="8"><el-form-item label="考核方案"><el-input :value="form.planName" disabled /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="教学得分"><el-input-number v-model="form.teachingScore" :min="0" :max="100" :precision="1" style="width:100%" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="科研得分"><el-input-number v-model="form.researchScore" :min="0" :max="100" :precision="1" style="width:100%" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="服务得分"><el-input-number v-model="form.serviceScore" :min="0" :max="100" :precision="1" style="width:100%" /></el-form-item></el-col>
        </el-row>
        <el-form-item label="自我评价"><el-input v-model="form.selfEvaluation" type="textarea" :rows="3" /></el-form-item>
      </el-form>

      <!-- 教学工作 -->
      <div v-show="activeTab === 1">
        <el-button type="primary" plain icon="Plus" size="small" @click="addTeaching" style="margin-bottom:10px">添加课程</el-button>
        <el-table :data="form.teachings" border size="small">
          <el-table-column label="课程名称"><template #default="scope"><el-input v-model="scope.row.courseName" /></template></el-table-column>
          <el-table-column label="课时" width="80"><template #default="scope"><el-input-number v-model="scope.row.classHours" :min="0" size="small" style="width:100%" /></template></el-table-column>
          <el-table-column label="学生数" width="80"><template #default="scope"><el-input-number v-model="scope.row.studentCount" :min="0" size="small" style="width:100%" /></template></el-table-column>
          <el-table-column label="评教分" width="90"><template #default="scope"><el-input-number v-model="scope.row.evalScore" :min="0" :max="100" :precision="1" size="small" style="width:100%" /></template></el-table-column>
          <el-table-column label="教学获奖" width="140"><template #default="scope"><el-input v-model="scope.row.awards" /></template></el-table-column>
          <el-table-column label="操作" width="60" align="center"><template #default="scope"><el-button link type="danger" icon="Delete" @click="form.teachings.splice(scope.$index, 1)" /></template></el-table-column>
        </el-table>
      </div>

      <!-- 科研工作 -->
      <div v-show="activeTab === 2">
        <el-button type="primary" plain icon="Plus" size="small" @click="addResearch" style="margin-bottom:10px">添加成果</el-button>
        <el-table :data="form.researches" border size="small">
          <el-table-column label="类型" width="100"><template #default="scope"><el-select v-model="scope.row.researchType"><el-option label="论文" value="论文" /><el-option label="项目" value="项目" /><el-option label="获奖" value="获奖" /><el-option label="专利" value="专利" /></el-select></template></el-table-column>
          <el-table-column label="名称"><template #default="scope"><el-input v-model="scope.row.title" /></template></el-table-column>
          <el-table-column label="期刊/来源" width="140"><template #default="scope"><el-input v-model="scope.row.journalOrSource" /></template></el-table-column>
          <el-table-column label="级别" width="100"><template #default="scope"><el-input v-model="scope.row.level" /></template></el-table-column>
          <el-table-column label="角色" width="100"><template #default="scope"><el-input v-model="scope.row.role" /></template></el-table-column>
          <el-table-column label="操作" width="60" align="center"><template #default="scope"><el-button link type="danger" icon="Delete" @click="form.researches.splice(scope.$index, 1)" /></template></el-table-column>
        </el-table>
      </div>

      <!-- 社会服务 -->
      <div v-show="activeTab === 3">
        <el-button type="primary" plain icon="Plus" size="small" @click="addService" style="margin-bottom:10px">添加服务</el-button>
        <el-table :data="form.services" border size="small">
          <el-table-column label="类型" width="120"><template #default="scope"><el-select v-model="scope.row.serviceType"><el-option label="学术兼职" value="学术兼职" /><el-option label="成果转化" value="成果转化" /><el-option label="科普" value="科普" /><el-option label="其他" value="其他" /></el-select></template></el-table-column>
          <el-table-column label="内容描述"><template #default="scope"><el-input v-model="scope.row.content" /></template></el-table-column>
          <el-table-column label="影响/效果" width="160"><template #default="scope"><el-input v-model="scope.row.impact" /></template></el-table-column>
          <el-table-column label="操作" width="60" align="center"><template #default="scope"><el-button link type="danger" icon="Delete" @click="form.services.splice(scope.$index, 1)" /></template></el-table-column>
        </el-table>
      </div>

      <template #footer>
        <el-button @click="activeTab > 0 && activeTab--" :disabled="activeTab === 0">上一步</el-button>
        <el-button @click="activeTab < 3 && activeTab++" :disabled="activeTab === 3">下一步</el-button>
        <el-button @click="formOpen = false">取 消</el-button>
        <el-button type="primary" @click="submitForm">保 存</el-button>
      </template>
    </el-dialog>

    <!-- 详情对话框 -->
    <el-dialog title="考核详情" v-model="detailOpen" width="800px" append-to-body>
      <div v-if="detailData">
        <el-descriptions :column="3" border style="margin-bottom:15px">
          <el-descriptions-item label="方案">{{ detailData.planName }}</el-descriptions-item>
          <el-descriptions-item label="教学得分">{{ detailData.teachingScore }}</el-descriptions-item>
          <el-descriptions-item label="科研得分">{{ detailData.researchScore }}</el-descriptions-item>
          <el-descriptions-item label="服务得分">{{ detailData.serviceScore }}</el-descriptions-item>
          <el-descriptions-item label="总分"><b>{{ detailData.totalScore }}</b></el-descriptions-item>
          <el-descriptions-item label="等级"><dict-tag :options="tea_assessment_grade" :value="detailData.grade" /></el-descriptions-item>
          <el-descriptions-item label="状态"><dict-tag :options="tea_assessment_status" :value="detailData.status" /></el-descriptions-item>
        </el-descriptions>
        <el-divider content-position="left">自我评价</el-divider>
        <p>{{ detailData.selfEvaluation || '无' }}</p>

        <!-- 审核轨迹 -->
        <el-divider content-position="left" v-if="detailAudit.length > 0">审核轨迹</el-divider>
        <el-timeline v-if="detailAudit.length > 0">
          <el-timeline-item v-for="item in detailAudit" :key="item.auditId" :type="item.result === '1' ? 'success' : item.result === '2' ? 'danger' : 'info'" :timestamp="item.auditTime" placement="top">
            <b>{{ item.auditNode }}</b> - {{ item.auditorName }}
            <p>{{ item.result === '1' ? '通过' : '退回' }}：{{ item.opinion || '' }}</p>
          </el-timeline-item>
        </el-timeline>
      </div>
    </el-dialog>
  </div>
</template>

<script setup name="PortalAssessment">
import { getAssessmentPlans, getMyRecords, getRecordDetail, fillAssessment, updateAssessment, submitAssessment } from '@/api/portal/assessment'

const { proxy } = getCurrentInstance()
const { tea_assessment_status } = proxy.useDict('tea_assessment_status')
const { tea_assessment_grade } = proxy.useDict('tea_assessment_grade')
const { tea_assessment_type } = proxy.useDict('tea_assessment_type')

const planList = ref([])
const planLoading = ref(false)
const recordList = ref([])
const recordLoading = ref(false)
const formOpen = ref(false)
const formTitle = ref('')
const activeTab = ref(0)
const form = ref({})
const detailOpen = ref(false)
const detailData = ref(null)
const detailAudit = ref([])

function loadPlans() {
  planLoading.value = true
  getAssessmentPlans({ pageNum: 1, pageSize: 20 }).then(res => {
    planList.value = res.rows || []
    planLoading.value = false
  }).catch(() => { planLoading.value = false })
}

function loadRecords() {
  recordLoading.value = true
  getMyRecords({ pageNum: 1, pageSize: 50 }).then(res => {
    recordList.value = res.rows || []
    recordLoading.value = false
  }).catch(() => { recordLoading.value = false })
}

function resetForm(planId, planName) {
  form.value = {
    recordId: undefined, planId, planName, teachingScore: 0, researchScore: 0, serviceScore: 0,
    selfEvaluation: '', teachings: [], researches: [], services: []
  }
  activeTab.value = 0
}

function handleFill(plan) {
  resetForm(plan.planId, plan.planName)
  formTitle.value = `填报考核 - ${plan.planName}`
  formOpen.value = true
}

function handleEdit(row) {
  getRecordDetail(row.recordId).then(res => {
    form.value = res.data || {}
    form.value.planName = row.planName
    form.value.teachings = form.value.teachings || []
    form.value.researches = form.value.researches || []
    form.value.services = form.value.services || []
    formTitle.value = '编辑考核填报'
    activeTab.value = 0
    formOpen.value = true
  })
}

function handleViewRecords(plan) {
  loadRecords()
}

function handleSubmit(row) {
  proxy.$modal.confirm('确认提交？提交后不可修改').then(() => {
    return submitAssessment(row.recordId)
  }).then(() => {
    proxy.$modal.msgSuccess('提交成功')
    loadRecords()
  }).catch(() => {})
}

function handleDetail(row) {
  getRecordDetail(row.recordId).then(res => {
    detailData.value = res.data
    detailAudit.value = res.auditTrail || []
    detailOpen.value = true
  })
}

function submitForm() {
  if (form.value.recordId) {
    updateAssessment(form.value).then(() => { proxy.$modal.msgSuccess('保存成功'); formOpen.value = false; loadRecords() })
  } else {
    fillAssessment(form.value).then(() => { proxy.$modal.msgSuccess('填报成功'); formOpen.value = false; loadRecords() })
  }
}

function addTeaching() { if (!form.value.teachings) form.value.teachings = []; form.value.teachings.push({ courseName: '', classHours: 0, studentCount: 0, evalScore: 0, awards: '' }) }
function addResearch() { if (!form.value.researches) form.value.researches = []; form.value.researches.push({ researchType: '', title: '', journalOrSource: '', level: '', role: '' }) }
function addService() { if (!form.value.services) form.value.services = []; form.value.services.push({ serviceType: '', content: '', impact: '' }) }

loadPlans()
loadRecords()
</script>

<style scoped>
.card-header { display: flex; justify-content: space-between; align-items: center; }
</style>
