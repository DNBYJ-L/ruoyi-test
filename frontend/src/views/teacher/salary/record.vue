<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch">
      <el-form-item label="工号" prop="teacherNo"><el-input v-model="queryParams.teacherNo" placeholder="工号" clearable style="width:140px" /></el-form-item>
      <el-form-item label="姓名" prop="teacherName"><el-input v-model="queryParams.teacherName" placeholder="姓名" clearable style="width:140px" /></el-form-item>
      <el-form-item label="年份" prop="salaryYear"><el-input v-model="queryParams.salaryYear" placeholder="年份" clearable style="width:100px" /></el-form-item>
      <el-form-item label="月份" prop="salaryMonth"><el-input v-model="queryParams.salaryMonth" placeholder="月份" clearable style="width:80px" /></el-form-item>
      <el-form-item label="状态" prop="issueStatus">
        <el-select v-model="queryParams.issueStatus" placeholder="发放状态" clearable style="width:120px">
          <el-option label="未发放" value="0" /><el-option label="已发放" value="1" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5"><el-button type="primary" plain icon="Plus" @click="handleAdd" v-hasPermi="['teacher:salary:add']">新增</el-button></el-col>
      <el-col :span="1.5"><el-button type="success" plain icon="DataLine" @click="handleBatchGenerate" v-hasPermi="['teacher:salary:add']">批量生成</el-button></el-col>
      <el-col :span="1.5"><el-button type="warning" plain icon="Download" @click="handleExport" v-hasPermi="['teacher:salary:export']">导出</el-button></el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList" />
    </el-row>

    <el-table v-loading="loading" :data="salaryList" border size="small">
      <el-table-column label="工号" prop="teacherNo" width="110" />
      <el-table-column label="姓名" prop="teacherName" width="80" />
      <el-table-column label="院系" prop="deptName" width="130" />
      <el-table-column label="年月" width="90"><template #default="scope">{{ scope.row.salaryYear }}-{{ String(scope.row.salaryMonth).padStart(2, '0') }}</template></el-table-column>
      <el-table-column label="岗位工资" prop="postSalary" width="90" align="right" />
      <el-table-column label="薪级工资" prop="scaleSalary" width="90" align="right" />
      <el-table-column label="基础绩效" prop="basePerformance" width="90" align="right" />
      <el-table-column label="奖励绩效" prop="rewardPerformance" width="90" align="right" />
      <el-table-column label="人才津贴" prop="talentAllowance" width="90" align="right" />
      <el-table-column label="住房补贴" prop="housingAllowance" width="90" align="right" />
      <el-table-column label="其他" prop="otherAllowance" width="80" align="right" />
      <el-table-column label="税前应发" prop="grossSalary" width="100" align="right"><template #default="scope"><b>{{ scope.row.grossSalary }}</b></template></el-table-column>
      <el-table-column label="社保" prop="socialInsurance" width="80" align="right" />
      <el-table-column label="公积金" prop="housingFund" width="80" align="right" />
      <el-table-column label="个税" prop="incomeTax" width="80" align="right" />
      <el-table-column label="实发" prop="actualSalary" width="100" align="right"><template #default="scope"><b style="color:#67C23A">{{ scope.row.actualSalary }}</b></template></el-table-column>
      <el-table-column label="状态" width="70" align="center"><template #default="scope"><el-tag :type="scope.row.issueStatus === '1' ? 'success' : 'warning'" size="small">{{ scope.row.issueStatus === '1' ? '已发放' : '未发放' }}</el-tag></template></el-table-column>
      <el-table-column label="操作" align="center" width="140" fixed="right">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['teacher:salary:edit']">编辑</el-button>
          <el-button link type="danger" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['teacher:salary:edit']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNum" v-model:limit="queryParams.pageSize" @pagination="getList" />

    <el-dialog :title="title" v-model="open" width="800px" append-to-body destroy-on-close>
      <el-form ref="formRef" :model="form" label-width="90px">
        <el-row :gutter="20">
          <el-col :span="8"><el-form-item label="教师" prop="teacherId"><el-select v-model="form.teacherId" placeholder="请选择教师" filterable style="width:100%"><el-option v-for="t in teacherOptions" :key="t.teacherId" :label="`${t.teacherNo} - ${t.teacherName}`" :value="t.teacherId" /></el-select></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="年份" prop="salaryYear"><el-input-number v-model="form.salaryYear" :min="2020" :max="2030" style="width:100%" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="月份" prop="salaryMonth"><el-input-number v-model="form.salaryMonth" :min="1" :max="12" style="width:100%" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="岗位工资"><el-input-number v-model="form.postSalary" :min="0" :precision="2" style="width:100%" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="薪级工资"><el-input-number v-model="form.scaleSalary" :min="0" :precision="2" style="width:100%" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="基础绩效"><el-input-number v-model="form.basePerformance" :min="0" :precision="2" style="width:100%" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="奖励绩效"><el-input-number v-model="form.rewardPerformance" :min="0" :precision="2" style="width:100%" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="人才津贴"><el-input-number v-model="form.talentAllowance" :min="0" :precision="2" style="width:100%" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="住房补贴"><el-input-number v-model="form.housingAllowance" :min="0" :precision="2" style="width:100%" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="其他津贴"><el-input-number v-model="form.otherAllowance" :min="0" :precision="2" style="width:100%" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="代扣社保"><el-input-number v-model="form.socialInsurance" :min="0" :precision="2" style="width:100%" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="代扣公积金"><el-input-number v-model="form.housingFund" :min="0" :precision="2" style="width:100%" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="代扣个税"><el-input-number v-model="form.incomeTax" :min="0" :precision="2" style="width:100%" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="发放状态"><el-select v-model="form.issueStatus" style="width:100%"><el-option label="未发放" value="0" /><el-option label="已发放" value="1" /></el-select></el-form-item></el-col>
        </el-row>
      </el-form>
      <template #footer>
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary" @click="submitForm">确 定</el-button>
      </template>
    </el-dialog>

    <!-- 批量生成对话框 -->
    <el-dialog title="批量生成薪资" v-model="batchOpen" width="400px" append-to-body>
      <el-form label-width="80px">
        <el-form-item label="年份"><el-input-number v-model="batchYear" :min="2020" :max="2030" style="width:100%" /></el-form-item>
        <el-form-item label="月份"><el-input-number v-model="batchMonth" :min="1" :max="12" style="width:100%" /></el-form-item>
      </el-form>
      <el-alert type="info" :closable="false" style="margin-bottom:10px">将根据教师当前职称和薪资配置，为所有在职教师生成指定月份的薪资记录（已有记录的教师将自动跳过）。</el-alert>
      <template #footer>
        <el-button @click="batchOpen = false">取 消</el-button>
        <el-button type="primary" :loading="batchLoading" @click="doBatchGenerate">生 成</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="SalaryRecord">
import { listSalary, getSalary, addSalary, updateSalary, delSalary, exportSalary, batchGenerateSalary } from '@/api/teacher/salary'
import { listTeacher } from '@/api/teacher/info'

const { proxy } = getCurrentInstance()
const salaryList = ref([])
const loading = ref(true)
const showSearch = ref(true)
const total = ref(0)
const title = ref('')
const open = ref(false)
const teacherOptions = ref([])
const batchOpen = ref(false)
const batchLoading = ref(false)
const now = new Date()
const batchYear = ref(now.getFullYear())
const batchMonth = ref(now.getMonth() + 1)

const data = reactive({
  form: {},
  queryParams: { pageNum: 1, pageSize: 10, teacherNo: '', teacherName: '', salaryYear: '', salaryMonth: '', issueStatus: '' }
})
const { queryParams, form } = toRefs(data)

function getList() {
  loading.value = true
  listSalary(queryParams.value).then(res => { salaryList.value = res.rows || []; total.value = res.total || 0; loading.value = false })
}
function loadTeachers() {
  listTeacher({ pageNum: 1, pageSize: 999, status: '0' }).then(res => { teacherOptions.value = res.rows || [] })
}
function reset() {
  form.value = { recordId: undefined, teacherId: undefined, salaryYear: new Date().getFullYear(), salaryMonth: new Date().getMonth() + 1, postSalary: 0, scaleSalary: 0, basePerformance: 0, rewardPerformance: 0, talentAllowance: 0, housingAllowance: 0, otherAllowance: 0, socialInsurance: 0, housingFund: 0, incomeTax: 0, actualSalary: 0, grossSalary: 0, issueStatus: '0' }
}
function handleAdd() { reset(); loadTeachers(); title.value = '新增薪资记录'; open.value = true }
function handleUpdate(row) { reset(); loadTeachers(); getSalary(row.recordId).then(res => { form.value = res.data; title.value = '修改薪资记录'; open.value = true }) }
function cancel() { open.value = false; reset() }
function submitForm() {
  if (form.value.recordId) { updateSalary(form.value).then(() => { proxy.$modal.msgSuccess('修改成功'); open.value = false; getList() }) }
  else { addSalary(form.value).then(() => { proxy.$modal.msgSuccess('新增成功'); open.value = false; getList() }) }
}
function handleDelete(row) { proxy.$modal.confirm('确认删除？').then(() => delSalary(row.recordId)).then(() => getList()).catch(() => {}) }
function handleQuery() { queryParams.value.pageNum = 1; getList() }
function resetQuery() { proxy.resetForm('queryRef'); handleQuery() }
function handleExport() { proxy.download('/teacher/salary/export', { ...queryParams.value }, `salary_${new Date().getTime()}.xlsx`) }
function handleBatchGenerate() { batchYear.value = now.getFullYear(); batchMonth.value = now.getMonth() + 1; batchOpen.value = true }
function doBatchGenerate() {
  batchLoading.value = true
  batchGenerateSalary(batchYear.value, batchMonth.value).then(res => {
    proxy.$modal.msgSuccess(res.msg || '生成完成')
    batchOpen.value = false
    getList()
  }).finally(() => { batchLoading.value = false })
}

getList()
</script>
