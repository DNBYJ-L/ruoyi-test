<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch">
      <el-form-item label="培训计划" prop="planId">
        <el-select
          v-model="queryParams.planId"
          filterable
          remote
          clearable
          placeholder="输入计划名称搜索"
          :remote-method="searchPlans"
          :loading="planSearchLoading"
          style="width:200px"
        >
          <el-option v-for="p in planOptions" :key="p.planId" :label="p.planName" :value="p.planId" />
        </el-select>
      </el-form-item>
      <el-form-item label="合格状态" prop="passStatus">
        <el-select v-model="queryParams.passStatus" placeholder="状态" clearable style="width:120px">
          <el-option label="合格" value="1" /><el-option label="不合格" value="0" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5"><el-button type="primary" plain icon="Plus" @click="handleAdd" v-hasPermi="['teacher:result:add']">录入成绩</el-button></el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList" />
    </el-row>

    <el-table v-loading="loading" :data="resultList" border size="small">
      <el-table-column label="培训计划" prop="planName" width="180" show-overflow-tooltip />
      <el-table-column label="教师" prop="teacherName" width="100" />
      <el-table-column label="考核方式" prop="assessmentType" width="90" align="center"><template #default="scope"><dict-tag :options="tea_training_assess_type" :value="scope.row.assessmentType" /></template></el-table-column>
      <el-table-column label="成绩" prop="examScore" width="120" align="center">
        <template #default="scope">
          <el-input-number v-model="scope.row.examScore" :min="0" :max="100" :precision="1" size="small" controls-position="right" style="width:100px" @change="quickSave(scope.row)" />
        </template>
      </el-table-column>
      <el-table-column label="学时" prop="creditHours" width="70" align="center" />
      <el-table-column label="证书编号" prop="certificateNo" width="140" />
      <el-table-column label="满意度" prop="satisfactionScore" width="80" align="center"><template #default="scope"><el-rate :model-value="Number(scope.row.satisfactionScore)" disabled /></template></el-table-column>
      <el-table-column label="合格" prop="passStatus" width="100" align="center">
        <template #default="scope">
          <el-switch v-model="scope.row.passStatus" active-value="1" inactive-value="0" active-text="合格" inactive-text="不合格" inline-prompt @change="quickSave(scope.row)" />
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="130" fixed="right">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)">编辑</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNum" v-model:limit="queryParams.pageSize" @pagination="getList" />

    <el-dialog :title="title" v-model="open" width="600px" append-to-body destroy-on-close>
      <el-form ref="formRef" :model="form" label-width="100px">
        <el-row :gutter="20">
          <el-col :span="12"><el-form-item label="培训计划ID"><el-input v-model="form.planId" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="教师ID"><el-input v-model="form.teacherId" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="考核方式"><el-select v-model="form.assessmentType" style="width:100%"><el-option v-for="d in tea_training_assess_type" :key="d.value" :label="d.label" :value="d.value" /></el-select></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="成绩"><el-input-number v-model="form.examScore" :min="0" :max="100" :precision="1" style="width:100%" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="认定学时"><el-input-number v-model="form.creditHours" :min="0" style="width:100%" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="证书编号"><el-input v-model="form.certificateNo" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="满意度"><el-rate v-model="form.satisfactionScore" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="合格"><el-switch v-model="form.passStatus" active-value="1" inactive-value="0" /></el-form-item></el-col>
          <el-col :span="24"><el-form-item label="备注"><el-input v-model="form.remark" type="textarea" :rows="2" /></el-form-item></el-col>
        </el-row>
      </el-form>
      <template #footer>
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary" @click="submitForm">确 定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="TrainingResult">
import { listTrainingResult, getTrainingResult, addTrainingResult, updateTrainingResult, listTrainingPlan } from '@/api/teacher/training'
const { proxy } = getCurrentInstance()
const { tea_training_assess_type } = proxy.useDict('tea_training_assess_type')
const resultList = ref([]); const loading = ref(true); const showSearch = ref(true); const total = ref(0); const title = ref(''); const open = ref(false)
const planSearchLoading = ref(false); const planOptions = ref([])
const data = reactive({ form: {}, queryParams: { pageNum: 1, pageSize: 10, planId: undefined, passStatus: undefined } })
const { queryParams, form } = toRefs(data)
function getList() { loading.value = true; listTrainingResult(queryParams.value).then(res => { resultList.value = res.rows || []; total.value = res.total || 0; loading.value = false }) }
function reset() { form.value = { resultId: undefined, planId: undefined, teacherId: undefined, examScore: 0, assessmentType: '1', passStatus: '0', certificateNo: '', creditHours: 0, satisfactionScore: 0, remark: '' } }
function handleAdd() { reset(); title.value = '录入成绩'; open.value = true }
function handleUpdate(row) { reset(); getTrainingResult(row.resultId).then(res => { form.value = res.data; form.value.satisfactionScore = Number(res.data.satisfactionScore); title.value = '修改成绩'; open.value = true }) }
function cancel() { open.value = false }
function submitForm() { if (form.value.resultId) updateTrainingResult(form.value).then(() => { proxy.$modal.msgSuccess('修改成功'); open.value = false; getList() }); else addTrainingResult(form.value).then(() => { proxy.$modal.msgSuccess('新增成功'); open.value = false; getList() }) }
function handleQuery() { queryParams.value.pageNum = 1; getList() }
function resetQuery() { proxy.resetForm('queryRef'); planOptions.value = []; handleQuery() }
function quickSave(row) {
  updateTrainingResult({ resultId: row.resultId, examScore: row.examScore, passStatus: row.passStatus }).then(() => {
    proxy.$modal.msgSuccess('已保存')
  })
}
function searchPlans(query) {
  if (!query) { planOptions.value = []; return }
  planSearchLoading.value = true
  listTrainingPlan({ planName: query, pageNum: 1, pageSize: 20 }).then(res => {
    planOptions.value = (res.rows || []).map(p => ({ planId: p.planId, planName: p.planName }))
  }).finally(() => { planSearchLoading.value = false })
}
// 初始加载所有计划供选择
listTrainingPlan({ pageNum: 1, pageSize: 50 }).then(res => { planOptions.value = (res.rows || []).map(p => ({ planId: p.planId, planName: p.planName })) })
getList()
</script>
