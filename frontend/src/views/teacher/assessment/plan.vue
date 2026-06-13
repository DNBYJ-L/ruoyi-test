<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch">
      <el-form-item label="方案名称" prop="planName"><el-input v-model="queryParams.planName" placeholder="方案名称" clearable style="width:160px" /></el-form-item>
      <el-form-item label="考核类型" prop="assessmentType"><el-select v-model="queryParams.assessmentType" placeholder="类型" clearable style="width:120px"><el-option v-for="d in tea_assessment_type" :key="d.value" :label="d.label" :value="d.value" /></el-select></el-form-item>
      <el-form-item label="年度" prop="assessmentYear"><el-input v-model="queryParams.assessmentYear" placeholder="年度" clearable style="width:100px" /></el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5"><el-button type="primary" plain icon="Plus" @click="handleAdd" v-hasPermi="['teacher:assess:add']">新增</el-button></el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList" />
    </el-row>

    <el-table v-loading="loading" :data="planList" border size="small">
      <el-table-column label="方案名称" prop="planName" width="200" show-overflow-tooltip />
      <el-table-column label="考核类型" prop="assessmentType" width="100" align="center"><template #default="scope"><dict-tag :options="tea_assessment_type" :value="scope.row.assessmentType" /></template></el-table-column>
      <el-table-column label="考核年度" prop="assessmentYear" width="90" align="center" />
      <el-table-column label="开始日期" prop="startDate" width="110" />
      <el-table-column label="结束日期" prop="endDate" width="110" />
      <el-table-column label="状态" prop="status" width="90" align="center">
        <template #default="scope"><el-tag :type="['info','primary','success'][parseInt(scope.row.status)] || 'info'" size="small">{{ ['草稿','进行中','已结束'][parseInt(scope.row.status)] || '草稿' }}</el-tag></template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="150" fixed="right">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['teacher:assess:edit']">编辑</el-button>
          <el-button link type="danger" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['teacher:assess:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNum" v-model:limit="queryParams.pageSize" @pagination="getList" />

    <el-dialog :title="title" v-model="open" width="600px" append-to-body destroy-on-close>
      <el-form ref="formRef" :model="form" label-width="100px">
        <el-form-item label="方案名称"><el-input v-model="form.planName" /></el-form-item>
        <el-row :gutter="20">
          <el-col :span="12"><el-form-item label="考核类型"><el-select v-model="form.assessmentType" style="width:100%"><el-option v-for="d in tea_assessment_type" :key="d.value" :label="d.label" :value="d.value" /></el-select></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="考核年度"><el-input-number v-model="form.assessmentYear" :min="2020" :max="2030" style="width:100%" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="开始日期"><el-date-picker v-model="form.startDate" type="date" value-format="YYYY-MM-DD" style="width:100%" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="结束日期"><el-date-picker v-model="form.endDate" type="date" value-format="YYYY-MM-DD" style="width:100%" /></el-form-item></el-col>
        </el-row>
        <el-form-item label="状态"><el-select v-model="form.status" style="width:100%"><el-option label="草稿" value="0" /><el-option label="进行中" value="1" /><el-option label="已结束" value="2" /></el-select></el-form-item>
        <el-form-item label="备注"><el-input v-model="form.remark" type="textarea" :rows="3" /></el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary" @click="submitForm">确 定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="AssessmentPlan">
import { listAssessPlan, getAssessPlan, addAssessPlan, updateAssessPlan, delAssessPlan } from '@/api/teacher/assessment'
const { proxy } = getCurrentInstance()
const { tea_assessment_type } = proxy.useDict('tea_assessment_type')
const planList = ref([]); const loading = ref(true); const showSearch = ref(true); const total = ref(0); const title = ref(''); const open = ref(false)
const data = reactive({ form: {}, queryParams: { pageNum: 1, pageSize: 10, planName: undefined, assessmentType: undefined, assessmentYear: undefined } })
const { queryParams, form } = toRefs(data)
function getList() { loading.value = true; listAssessPlan(queryParams.value).then(res => { planList.value = res.rows || []; total.value = res.total || 0; loading.value = false }) }
function reset() { form.value = { planId: undefined, planName: '', assessmentType: '1', assessmentYear: new Date().getFullYear(), startDate: undefined, endDate: undefined, status: '0', remark: '' } }
function handleAdd() { reset(); title.value = '新增考核方案'; open.value = true }
function handleUpdate(row) { reset(); getAssessPlan(row.planId).then(res => { form.value = res.data; title.value = '修改考核方案'; open.value = true }) }
function cancel() { open.value = false }
function submitForm() { if (form.value.planId) updateAssessPlan(form.value).then(() => { proxy.$modal.msgSuccess('修改成功'); open.value = false; getList() }); else addAssessPlan(form.value).then(() => { proxy.$modal.msgSuccess('新增成功'); open.value = false; getList() }) }
function handleDelete(row) { proxy.$modal.confirm('确认删除？').then(() => delAssessPlan(row.planId)).then(() => getList()).catch(() => {}) }
function handleQuery() { queryParams.value.pageNum = 1; getList() }
function resetQuery() { proxy.resetForm('queryRef'); handleQuery() }
getList()
</script>
