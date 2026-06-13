<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch">
      <el-form-item label="职称等级" prop="titleLevel">
        <el-input v-model="queryParams.titleLevel" placeholder="请输入职称" clearable style="width: 200px" @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5"><el-button type="primary" plain icon="Plus" @click="handleAdd" v-hasPermi="['teacher:salary:add']">新增</el-button></el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList" />
    </el-row>

    <el-table v-loading="loading" :data="configList" border>
      <el-table-column label="职称等级" prop="titleLevel" width="150" />
      <el-table-column label="岗位工资" prop="postSalary" width="120" align="right"><template #default="scope">¥{{ scope.row.postSalary }}</template></el-table-column>
      <el-table-column label="薪级工资" prop="scaleSalary" width="120" align="right"><template #default="scope">¥{{ scope.row.scaleSalary }}</template></el-table-column>
      <el-table-column label="基础性绩效" prop="basePerformance" width="120" align="right"><template #default="scope">¥{{ scope.row.basePerformance }}</template></el-table-column>
      <el-table-column label="人才津贴" prop="talentAllowance" width="120" align="right"><template #default="scope">¥{{ scope.row.talentAllowance }}</template></el-table-column>
      <el-table-column label="住房补贴" prop="housingAllowance" width="120" align="right"><template #default="scope">¥{{ scope.row.housingAllowance }}</template></el-table-column>
      <el-table-column label="状态" prop="status" width="80" align="center">
        <template #default="scope"><el-tag :type="scope.row.status === '0' ? 'success' : 'danger'" size="small">{{ scope.row.status === '0' ? '正常' : '停用' }}</el-tag></template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="160">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['teacher:salary:edit']">编辑</el-button>
          <el-button link type="danger" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['teacher:salary:edit']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNum" v-model:limit="queryParams.pageSize" @pagination="getList" />

    <el-dialog :title="title" v-model="open" width="600px" append-to-body>
      <el-form ref="formRef" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="职称等级" prop="titleLevel"><el-input v-model="form.titleLevel" placeholder="如：教授" /></el-form-item>
        <el-form-item label="岗位工资" prop="postSalary"><el-input-number v-model="form.postSalary" :min="0" :precision="2" style="width:100%" /></el-form-item>
        <el-form-item label="薪级工资" prop="scaleSalary"><el-input-number v-model="form.scaleSalary" :min="0" :precision="2" style="width:100%" /></el-form-item>
        <el-form-item label="基础性绩效" prop="basePerformance"><el-input-number v-model="form.basePerformance" :min="0" :precision="2" style="width:100%" /></el-form-item>
        <el-form-item label="人才津贴" prop="talentAllowance"><el-input-number v-model="form.talentAllowance" :min="0" :precision="2" style="width:100%" /></el-form-item>
        <el-form-item label="住房补贴" prop="housingAllowance"><el-input-number v-model="form.housingAllowance" :min="0" :precision="2" style="width:100%" /></el-form-item>
        <el-form-item label="状态" prop="status"><el-radio-group v-model="form.status"><el-radio value="0">正常</el-radio><el-radio value="1">停用</el-radio></el-radio-group></el-form-item>
        <el-form-item label="备注" prop="remark"><el-input v-model="form.remark" type="textarea" /></el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary" @click="submitForm">确 定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="SalaryConfig">
import { listSalaryConfig, getSalaryConfig, addSalaryConfig, updateSalaryConfig, delSalaryConfig } from '@/api/teacher/salary'

const { proxy } = getCurrentInstance()
const configList = ref([])
const loading = ref(true)
const showSearch = ref(true)
const total = ref(0)
const title = ref('')
const open = ref(false)

const data = reactive({
  form: {},
  queryParams: { pageNum: 1, pageSize: 10, titleLevel: undefined },
  rules: { titleLevel: [{ required: true, message: '职称等级不能为空', trigger: 'blur' }] }
})
const { queryParams, form, rules } = toRefs(data)

function getList() {
  loading.value = true
  listSalaryConfig(queryParams.value).then(res => { configList.value = res.rows; total.value = res.total; loading.value = false })
}
function reset() {
  form.value = { configId: undefined, titleLevel: '', postSalary: 0, scaleSalary: 0, basePerformance: 0, talentAllowance: 0, housingAllowance: 0, status: '0', remark: '' }
  proxy.resetForm('formRef')
}
function handleAdd() { reset(); title.value = '新增薪资配置'; open.value = true }
function handleUpdate(row) { reset(); getSalaryConfig(row.configId).then(res => { form.value = res.data; title.value = '修改薪资配置'; open.value = true }) }
function cancel() { open.value = false; reset() }
function submitForm() {
  proxy.$refs.formRef.validate(valid => {
    if (valid) {
      if (form.value.configId) { updateSalaryConfig(form.value).then(() => { proxy.$modal.msgSuccess('修改成功'); open.value = false; getList() }) }
      else { addSalaryConfig(form.value).then(() => { proxy.$modal.msgSuccess('新增成功'); open.value = false; getList() }) }
    }
  })
}
function handleDelete(row) { proxy.$modal.confirm('确认删除？').then(() => delSalaryConfig(row.configId)).then(() => getList()).catch(() => {}) }
function handleQuery() { queryParams.value.pageNum = 1; getList() }
function resetQuery() { proxy.resetForm('queryRef'); handleQuery() }

getList()
</script>
