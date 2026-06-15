<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch">
      <el-form-item label="学科方向" prop="discipline"><el-input v-model="queryParams.discipline" placeholder="学科方向" clearable style="width:160px" @keyup.enter="handleQuery" /></el-form-item>
      <el-form-item label="年度" prop="planYear"><el-input v-model="queryParams.planYear" placeholder="年度" clearable style="width:100px" /></el-form-item>
      <el-form-item label="状态" prop="status"><el-select v-model="queryParams.status" placeholder="状态" clearable style="width:120px"><el-option v-for="d in tea_recruit_status" :key="d.value" :label="d.label" :value="d.value" /></el-select></el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5"><el-button type="primary" plain icon="Plus" @click="handleAdd" v-hasPermi="['teacher:recruit:add']">新增</el-button></el-col>
      <el-col :span="1.5"><el-button type="danger" plain icon="Delete" :disabled="multiple" @click="handleDelete" v-hasPermi="['teacher:recruit:remove']">删除</el-button></el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList" />
    </el-row>

    <el-table v-loading="loading" :data="planList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="院系" prop="deptName" width="150" />
      <el-table-column label="学科方向" prop="discipline" width="150" />
      <el-table-column label="人才层次" prop="talentLevel" width="120"><template #default="scope"><dict-tag :options="tea_talent_level" :value="scope.row.talentLevel" /></template></el-table-column>
      <el-table-column label="年度" prop="planYear" width="80" align="center" />
      <el-table-column label="计划/已录" width="120" align="center"><template #default="scope">{{ scope.row.actualCount }}/{{ scope.row.planCount }}<el-progress :percentage="scope.row.planCount > 0 ? Math.round(scope.row.actualCount / scope.row.planCount * 100) : 0" :stroke-width="6" style="margin-top:4px" /></template></el-table-column>
      <el-table-column label="状态" prop="status" width="130" align="center">
        <template #default="scope">
          <el-select v-model="scope.row.status" size="small" style="width:100%" @change="handleStatusChange(scope.row)">
            <el-option v-for="d in tea_recruit_status" :key="d.value" :label="d.label" :value="d.value" />
          </el-select>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" prop="createTime" width="160" />
      <el-table-column label="操作" align="center" width="200" fixed="right">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['teacher:recruit:edit']">编辑</el-button>
          <el-button link type="danger" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['teacher:recruit:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNum" v-model:limit="queryParams.pageSize" @pagination="getList" />

    <el-dialog :title="title" v-model="open" width="600px" append-to-body destroy-on-close>
      <el-form ref="formRef" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="所属院系" prop="deptId"><el-tree-select v-model="form.deptId" :data="deptOptions" :props="{ value: 'id', label: 'label', children: 'children' }" value-key="id" placeholder="请选择" check-strictly style="width:100%" /></el-form-item>
        <el-form-item label="学科方向" prop="discipline"><el-input v-model="form.discipline" placeholder="请输入" /></el-form-item>
        <el-row :gutter="20">
          <el-col :span="12"><el-form-item label="计划人数" prop="planCount"><el-input-number v-model="form.planCount" :min="1" style="width:100%" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="年度" prop="planYear"><el-input-number v-model="form.planYear" :min="2020" :max="2030" style="width:100%" /></el-form-item></el-col>
        </el-row>
        <el-form-item label="人才层次" prop="talentLevel"><el-select v-model="form.talentLevel" placeholder="请选择" style="width:100%"><el-option v-for="d in tea_talent_level" :key="d.value" :label="d.label" :value="d.value" /></el-select></el-form-item>
        <el-form-item label="备注"><el-input v-model="form.remark" type="textarea" :rows="3" /></el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary" @click="submitForm">确 定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="RecruitPlan">
import { listRecruitPlan, getRecruitPlan, addRecruitPlan, updateRecruitPlan, delRecruitPlan } from '@/api/teacher/recruit'
import { listDept } from '@/api/system/dept'

const { proxy } = getCurrentInstance()
const { tea_recruit_status } = proxy.useDict('tea_recruit_status')
const { tea_talent_level } = proxy.useDict('tea_talent_level')
const planList = ref([]); const loading = ref(true); const showSearch = ref(true); const total = ref(0); const title = ref(''); const open = ref(false)
const deptOptions = ref([]); const ids = ref([]); const multiple = ref(true)
const data = reactive({
  form: {}, queryParams: { pageNum: 1, pageSize: 10, discipline: undefined, planYear: undefined, status: undefined },
  rules: { discipline: [{ required: true, message: '学科方向不能为空', trigger: 'blur' }], planCount: [{ required: true, message: '计划人数不能为空', trigger: 'blur' }] }
})
const { queryParams, form, rules } = toRefs(data)

function getList() { loading.value = true; listRecruitPlan(queryParams.value).then(res => { planList.value = res.rows || []; total.value = res.total || 0; loading.value = false }) }
function loadDeptTree() {
  listDept().then(res => {
    const list = res.data || []; const map = {}; const tree = []
    list.forEach(i => { map[i.deptId] = { id: i.deptId, label: i.deptName, children: [] } })
    list.forEach(i => { if (i.parentId && map[i.parentId]) map[i.parentId].children.push(map[i.deptId]); else tree.push(map[i.deptId]) })
    deptOptions.value = tree
  })
}
function reset() { form.value = { planId: undefined, deptId: undefined, discipline: '', planCount: 1, planYear: new Date().getFullYear(), talentLevel: '', status: '0', remark: '' }; proxy.resetForm('formRef') }
function handleAdd() { reset(); loadDeptTree(); title.value = '新增引进计划'; open.value = true }
function handleUpdate(row) { reset(); loadDeptTree(); getRecruitPlan(row.planId).then(res => { form.value = res.data; title.value = '修改引进计划'; open.value = true }) }
function cancel() { open.value = false }
function submitForm() { proxy.$refs.formRef.validate(valid => { if (!valid) return; if (form.value.planId) updateRecruitPlan(form.value).then(() => { proxy.$modal.msgSuccess('修改成功'); open.value = false; getList() }); else addRecruitPlan(form.value).then(() => { proxy.$modal.msgSuccess('新增成功'); open.value = false; getList() }) }) }
function handleDelete(row) { proxy.$modal.confirm('确认删除？').then(() => delRecruitPlan(row.planId || ids.value)).then(() => getList()).catch(() => {}) }
function handleSelectionChange(selection) { ids.value = selection.map(i => i.planId); multiple.value = !selection.length }
function handleQuery() { queryParams.value.pageNum = 1; getList() }
function resetQuery() { proxy.resetForm('queryRef'); handleQuery() }
function handleStatusChange(row) { updateRecruitPlan({ planId: row.planId, status: row.status }).then(() => proxy.$modal.msgSuccess('状态修改成功')).catch(() => getList()) }
getList()
</script>
