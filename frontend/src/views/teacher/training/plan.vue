<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch">
      <el-form-item label="培训名称" prop="planName"><el-input v-model="queryParams.planName" placeholder="培训名称" clearable style="width:160px" /></el-form-item>
      <el-form-item label="类型" prop="trainingType"><el-select v-model="queryParams.trainingType" placeholder="类型" clearable style="width:120px"><el-option v-for="d in tea_training_type" :key="d.value" :label="d.label" :value="d.value" /></el-select></el-form-item>
      <el-form-item label="状态" prop="status"><el-select v-model="queryParams.status" placeholder="状态" clearable style="width:120px"><el-option v-for="d in tea_training_status" :key="d.value" :label="d.label" :value="d.value" /></el-select></el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5"><el-button type="primary" plain icon="Plus" @click="handleAdd" v-hasPermi="['teacher:training:add']">新增</el-button></el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList" />
    </el-row>

    <el-table v-loading="loading" :data="planList" border size="small">
      <el-table-column label="培训名称" prop="planName" width="180" show-overflow-tooltip />
      <el-table-column label="类型" prop="trainingType" width="100" align="center"><template #default="scope"><dict-tag :options="tea_training_type" :value="scope.row.trainingType" /></template></el-table-column>
      <el-table-column label="对象" prop="targetAudience" width="120" show-overflow-tooltip />
      <el-table-column label="人数限制" prop="maxCount" width="80" align="center" />
      <el-table-column label="时间" width="200"><template #default="scope">{{ scope.row.startDate }} ~ {{ scope.row.endDate }}</template></el-table-column>
      <el-table-column label="课时" prop="totalHours" width="60" align="center" />
      <el-table-column label="主讲人" prop="instructorName" width="100" />
      <el-table-column label="预算" prop="budget" width="90" align="right" />
      <el-table-column label="状态" prop="status" width="80" align="center"><template #default="scope"><dict-tag :options="tea_training_status" :value="scope.row.status" /></template></el-table-column>
      <el-table-column label="操作" align="center" width="150" fixed="right">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['teacher:training:edit']">编辑</el-button>
          <el-button link type="danger" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['teacher:training:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNum" v-model:limit="queryParams.pageSize" @pagination="getList" />

    <el-dialog :title="title" v-model="open" width="800px" append-to-body destroy-on-close>
      <el-form ref="formRef" :model="form" label-width="100px">
        <el-row :gutter="20">
          <el-col :span="12"><el-form-item label="培训名称" prop="planName"><el-input v-model="form.planName" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="培训类型"><el-select v-model="form.trainingType" style="width:100%"><el-option v-for="d in tea_training_type" :key="d.value" :label="d.label" :value="d.value" /></el-select></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="培训对象"><el-input v-model="form.targetAudience" placeholder="如：新入职教师" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="人数限制"><el-input-number v-model="form.maxCount" :min="1" style="width:100%" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="开始时间"><el-date-picker v-model="form.startDate" type="date" value-format="YYYY-MM-DD" style="width:100%" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="结束时间"><el-date-picker v-model="form.endDate" type="date" value-format="YYYY-MM-DD" style="width:100%" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="总课时"><el-input-number v-model="form.totalHours" :min="0" style="width:100%" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="主讲人"><el-input v-model="form.instructorName" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="预算(元)"><el-input-number v-model="form.budget" :min="0" :precision="2" style="width:100%" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="地点/平台"><el-input v-model="form.location" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="状态"><el-select v-model="form.status" style="width:100%"><el-option v-for="d in tea_training_status" :key="d.value" :label="d.label" :value="d.value" /></el-select></el-form-item></el-col>
        </el-row>
        <el-divider content-position="left">课程大纲</el-divider>
        <el-button type="primary" plain icon="Plus" size="small" @click="addCourse" style="margin-bottom:10px">添加课程</el-button>
        <el-table :data="form.courses" border size="small">
          <el-table-column label="课程名称"><template #default="scope"><el-input v-model="scope.row.courseName" /></template></el-table-column>
          <el-table-column label="授课教师" width="120"><template #default="scope"><el-input v-model="scope.row.instructor" /></template></el-table-column>
          <el-table-column label="课时" width="80"><template #default="scope"><el-input-number v-model="scope.row.hours" :min="0" size="small" style="width:100%" /></template></el-table-column>
          <el-table-column label="操作" width="60" align="center"><template #default="scope"><el-button link type="danger" icon="Delete" @click="form.courses.splice(scope.$index, 1)" /></template></el-table-column>
        </el-table>
      </el-form>
      <template #footer>
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary" @click="submitForm">确 定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="TrainingPlan">
import { listTrainingPlan, getTrainingPlan, addTrainingPlan, updateTrainingPlan, delTrainingPlan } from '@/api/teacher/training'
const { proxy } = getCurrentInstance()
const { tea_training_type } = proxy.useDict('tea_training_type')
const { tea_training_status } = proxy.useDict('tea_training_status')
const planList = ref([]); const loading = ref(true); const showSearch = ref(true); const total = ref(0); const title = ref(''); const open = ref(false)
const data = reactive({ form: {}, queryParams: { pageNum: 1, pageSize: 10, planName: undefined, trainingType: undefined, status: undefined } })
const { queryParams, form } = toRefs(data)
function getList() { loading.value = true; listTrainingPlan(queryParams.value).then(res => { planList.value = res.rows || []; total.value = res.total || 0; loading.value = false }) }
function reset() { form.value = { planId: undefined, planName: '', trainingType: '1', targetAudience: '', maxCount: 30, startDate: undefined, endDate: undefined, totalHours: 0, location: '', instructorName: '', instructorTitle: '', budget: 0, status: '0', courses: [] } }
function handleAdd() { reset(); title.value = '新增培训计划'; open.value = true }
function handleUpdate(row) { reset(); getTrainingPlan(row.planId).then(res => { form.value = res.data; form.value.courses = form.value.courses || []; title.value = '修改培训计划'; open.value = true }) }
function cancel() { open.value = false }
function submitForm() { if (form.value.planId) updateTrainingPlan(form.value).then(() => { proxy.$modal.msgSuccess('修改成功'); open.value = false; getList() }); else addTrainingPlan(form.value).then(() => { proxy.$modal.msgSuccess('新增成功'); open.value = false; getList() }) }
function handleDelete(row) { proxy.$modal.confirm('确认删除？').then(() => delTrainingPlan(row.planId)).then(() => getList()).catch(() => {}) }
function handleQuery() { queryParams.value.pageNum = 1; getList() }
function resetQuery() { proxy.resetForm('queryRef'); handleQuery() }
function addCourse() { if (!form.value.courses) form.value.courses = []; form.value.courses.push({ courseName: '', instructor: '', hours: 0 }) }
getList()
</script>
