<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true">
      <el-form-item label="状态" prop="status"><el-select v-model="queryParams.status" placeholder="状态" clearable style="width:140px"><el-option v-for="d in tea_assessment_status" :key="d.value" :label="d.label" :value="d.value" /></el-select></el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-table v-loading="loading" :data="recordList" border size="small">
      <el-table-column label="方案" prop="planName" width="150" show-overflow-tooltip />
      <el-table-column label="教师" prop="teacherName" width="90" />
      <el-table-column label="院系" prop="deptName" width="130" />
      <el-table-column label="总分" prop="totalScore" width="80" align="right" />
      <el-table-column label="等级" prop="grade" width="80" align="center"><template #default="scope"><dict-tag :options="tea_assessment_grade" :value="scope.row.grade" /></template></el-table-column>
      <el-table-column label="状态" prop="status" width="100" align="center"><template #default="scope"><dict-tag :options="tea_assessment_status" :value="scope.row.status" /></template></el-table-column>
      <el-table-column label="操作" align="center" width="200" fixed="right">
        <template #default="scope">
          <el-button link type="primary" @click="showTrail(scope.row)">轨迹</el-button>
          <el-button link type="success" @click="handleAudit(scope.row)" v-if="['1','2','3'].includes(scope.row.status)" v-hasPermi="['teacher:assess:audit']">审核</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNum" v-model:limit="queryParams.pageSize" @pagination="getList" />

    <!-- 审核对话框 -->
    <el-dialog title="审核" v-model="auditOpen" width="500px" append-to-body>
      <el-form label-width="80px">
        <el-form-item label="审核节点"><el-select v-model="auditForm.auditNode" style="width:100%"><el-option v-for="d in tea_audit_node" :key="d.value" :label="d.label" :value="d.value" /></el-select></el-form-item>
        <el-form-item label="审核结果"><el-radio-group v-model="auditForm.result"><el-radio label="1">通过</el-radio><el-radio label="2">退回</el-radio></el-radio-group></el-form-item>
        <el-form-item label="审核意见"><el-input v-model="auditForm.opinion" type="textarea" :rows="3" /></el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="auditOpen = false">取 消</el-button>
        <el-button type="primary" @click="submitAudit">确 定</el-button>
      </template>
    </el-dialog>

    <!-- 审核轨迹对话框 -->
    <el-dialog title="审核轨迹" v-model="trailOpen" width="600px" append-to-body>
      <el-timeline v-if="trailList.length > 0">
        <el-timeline-item v-for="item in trailList" :key="item.auditId" :timestamp="item.auditTime" placement="top">
          <el-card shadow="hover">
            <el-row :gutter="10">
              <el-col :span="6"><el-tag>{{ item.auditNode }}</el-tag></el-col>
              <el-col :span="6">{{ item.auditorName }}</el-col>
              <el-col :span="4"><el-tag :type="item.result === '1' ? 'success' : item.result === '2' ? 'danger' : 'info'" size="small">{{ ['待审','通过','退回'][parseInt(item.result)] }}</el-tag></el-col>
              <el-col :span="8">{{ item.opinion }}</el-col>
            </el-row>
          </el-card>
        </el-timeline-item>
      </el-timeline>
      <el-empty v-else description="暂无审核记录" />
    </el-dialog>
  </div>
</template>

<script setup name="AssessmentAudit">
import { listAssessRecord, getAuditTrail, doAudit } from '@/api/teacher/assessment'
const { proxy } = getCurrentInstance()
const { tea_assessment_status } = proxy.useDict('tea_assessment_status')
const { tea_assessment_grade } = proxy.useDict('tea_assessment_grade')
const { tea_audit_node } = proxy.useDict('tea_audit_node')
const { tea_audit_result } = proxy.useDict('tea_audit_result')
const recordList = ref([]); const loading = ref(true); const total = ref(0)
const auditOpen = ref(false); const trailOpen = ref(false); const currentRecordId = ref(null)
const trailList = ref([])
const auditForm = reactive({ auditNode: '院系', result: '1', opinion: '' })
const queryParams = reactive({ pageNum: 1, pageSize: 10, status: undefined })

function getList() { loading.value = true; listAssessRecord(queryParams).then(res => { recordList.value = res.rows || []; total.value = res.total || 0; loading.value = false }) }
function handleAudit(row) { currentRecordId.value = row.recordId; auditForm.auditNode = '院系'; auditForm.result = '1'; auditForm.opinion = ''; auditOpen.value = true }
function submitAudit() { doAudit(currentRecordId.value, auditForm).then(() => { proxy.$modal.msgSuccess('审核完成'); auditOpen.value = false; getList() }) }
function showTrail(row) { currentRecordId.value = row.recordId; getAuditTrail(row.recordId).then(res => { trailList.value = res.data || []; trailOpen.value = true }) }
function handleQuery() { queryParams.pageNum = 1; getList() }
function resetQuery() { proxy.resetForm('queryRef'); handleQuery() }
getList()
</script>
