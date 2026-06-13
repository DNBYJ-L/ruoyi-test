<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch">
      <el-form-item label="培训计划" prop="planId"><el-input v-model="queryParams.planId" placeholder="计划ID" clearable style="width:120px" /></el-form-item>
      <el-form-item label="审批状态" prop="approveStatus"><el-select v-model="queryParams.approveStatus" placeholder="状态" clearable style="width:120px"><el-option v-for="d in tea_approve_status" :key="d.value" :label="d.label" :value="d.value" /></el-select></el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5"><el-button type="primary" plain icon="Plus" @click="handleAdd" v-hasPermi="['teacher:enrollment:add']">报名</el-button></el-col>
      <el-col :span="1.5"><el-button type="success" plain @click="handleBatchApprove('1')" v-hasPermi="['teacher:enrollment:approve']">批量通过</el-button></el-col>
      <el-col :span="1.5"><el-button type="danger" plain @click="handleBatchApprove('2')" v-hasPermi="['teacher:enrollment:approve']">批量拒绝</el-button></el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList" />
    </el-row>

    <el-table v-loading="loading" :data="enrollList" border size="small" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="培训计划" prop="planName" width="180" show-overflow-tooltip />
      <el-table-column label="教师" prop="teacherName" width="100" />
      <el-table-column label="院系" prop="deptName" width="140" show-overflow-tooltip />
      <el-table-column label="报名日期" prop="applyDate" width="110" />
      <el-table-column label="审批状态" prop="approveStatus" width="90" align="center"><template #default="scope"><dict-tag :options="tea_approve_status" :value="scope.row.approveStatus" /></template></el-table-column>
      <el-table-column label="审批意见" prop="approveRemark" show-overflow-tooltip />
      <el-table-column label="操作" align="center" width="200" fixed="right">
        <template #default="scope">
          <el-button link type="success" size="small" @click="handleApprove(scope.row, '1')" v-if="scope.row.approveStatus === '0'" v-hasPermi="['teacher:enrollment:approve']">通过</el-button>
          <el-button link type="danger" size="small" @click="handleApprove(scope.row, '2')" v-if="scope.row.approveStatus === '0'" v-hasPermi="['teacher:enrollment:approve']">拒绝</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNum" v-model:limit="queryParams.pageSize" @pagination="getList" />

    <el-dialog title="报名" v-model="open" width="500px" append-to-body destroy-on-close>
      <el-form ref="formRef" :model="form" label-width="80px">
        <el-form-item label="培训计划ID" prop="planId"><el-input v-model="form.planId" /></el-form-item>
        <el-form-item label="教师ID" prop="teacherId"><el-input v-model="form.teacherId" /></el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary" @click="submitForm">确 定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="TrainingEnrollment">
import { listEnrollment, addEnrollment, approveEnrollment } from '@/api/teacher/training'
const { proxy } = getCurrentInstance()
const { tea_approve_status } = proxy.useDict('tea_approve_status')
const enrollList = ref([]); const loading = ref(true); const showSearch = ref(true); const total = ref(0); const title = ref(''); const open = ref(false); const selectedIds = ref([])
const data = reactive({ form: {}, queryParams: { pageNum: 1, pageSize: 10, planId: undefined, approveStatus: undefined } })
const { queryParams, form } = toRefs(data)
function getList() { loading.value = true; listEnrollment(queryParams.value).then(res => { enrollList.value = res.rows || []; total.value = res.total || 0; loading.value = false }) }
function reset() { form.value = { planId: undefined, teacherId: undefined } }
function handleAdd() { reset(); title.value = '新增报名'; open.value = true }
function cancel() { open.value = false }
function submitForm() { addEnrollment(form.value).then(() => { proxy.$modal.msgSuccess('报名成功'); open.value = false; getList() }) }
function handleApprove(row, status) { proxy.$modal.confirm('确认' + (status === '1' ? '通过' : '拒绝') + '？').then(() => approveEnrollment(row.enrollmentId, status, '')).then(() => { proxy.$modal.msgSuccess('操作成功'); getList() }).catch(() => {}) }
function handleBatchApprove(status) { if (!selectedIds.value.length) { proxy.$modal.msgWarning('请选择记录'); return }; proxy.$modal.confirm('确认批量操作？').then(() => { const promises = selectedIds.value.map(id => approveEnrollment(id, status, '')); Promise.all(promises).then(() => { proxy.$modal.msgSuccess('操作成功'); getList() }) }).catch(() => {}) }
function handleDelete(row) { proxy.$modal.confirm('确认删除？').then(() => {}).catch(() => {}) }
function handleSelectionChange(selection) { selectedIds.value = selection.map(i => i.enrollmentId) }
function handleQuery() { queryParams.value.pageNum = 1; getList() }
function resetQuery() { proxy.resetForm('queryRef'); handleQuery() }
getList()
</script>
