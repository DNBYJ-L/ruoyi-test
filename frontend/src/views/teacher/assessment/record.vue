<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch">
      <el-form-item label="方案ID" prop="planId"><el-input v-model="queryParams.planId" placeholder="方案ID" clearable style="width:120px" /></el-form-item>
      <el-form-item label="状态" prop="status"><el-select v-model="queryParams.status" placeholder="状态" clearable style="width:120px"><el-option v-for="d in tea_assessment_status" :key="d.value" :label="d.label" :value="d.value" /></el-select></el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5"><el-button type="primary" plain icon="Plus" @click="handleAdd" v-hasPermi="['teacher:assess:add']">新增</el-button></el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList" />
    </el-row>

    <el-table v-loading="loading" :data="recordList" border size="small">
      <el-table-column label="方案" prop="planName" width="150" show-overflow-tooltip />
      <el-table-column label="教师" prop="teacherName" width="90" />
      <el-table-column label="院系" prop="deptName" width="130" show-overflow-tooltip />
      <el-table-column label="教学" prop="teachingScore" width="70" align="right" />
      <el-table-column label="科研" prop="researchScore" width="70" align="right" />
      <el-table-column label="服务" prop="serviceScore" width="70" align="right" />
      <el-table-column label="总分" prop="totalScore" width="80" align="right"><template #default="scope"><b>{{ scope.row.totalScore }}</b></template></el-table-column>
      <el-table-column label="等级" prop="grade" width="80" align="center"><template #default="scope"><dict-tag :options="tea_assessment_grade" :value="scope.row.grade" /></template></el-table-column>
      <el-table-column label="状态" prop="status" width="90" align="center"><template #default="scope"><dict-tag :options="tea_assessment_status" :value="scope.row.status" /></template></el-table-column>
      <el-table-column label="操作" align="center" width="200" fixed="right">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)">编辑</el-button>
          <el-button link type="success" @click="handleSubmit(scope.row)" v-if="scope.row.status === '0'">提交</el-button>
          <el-button link type="danger" icon="Delete" @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNum" v-model:limit="queryParams.pageSize" @pagination="getList" />

    <el-dialog :title="title" v-model="open" width="900px" append-to-body destroy-on-close>
      <el-steps :active="activeTab" finish-status="success" align-center style="margin-bottom:20px">
        <el-step title="基本信息" @click="activeTab = 0" style="cursor:pointer" />
        <el-step title="教学工作" @click="activeTab = 1" style="cursor:pointer" />
        <el-step title="科研工作" @click="activeTab = 2" style="cursor:pointer" />
        <el-step title="社会服务" @click="activeTab = 3" style="cursor:pointer" />
      </el-steps>

      <!-- 基本信息 -->
      <el-form v-show="activeTab === 0" :model="form" label-width="100px">
        <el-row :gutter="20">
          <el-col :span="8"><el-form-item label="方案ID"><el-input v-model="form.planId" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="教师ID"><el-input v-model="form.teacherId" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="考核等级"><el-select v-model="form.grade" clearable style="width:100%"><el-option v-for="d in tea_assessment_grade" :key="d.value" :label="d.label" :value="d.value" /></el-select></el-form-item></el-col>
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
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary" @click="submitForm">确 定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="AssessmentRecord">
import { listAssessRecord, getAssessRecord, addAssessRecord, updateAssessRecord, delAssessRecord, submitAssessRecord } from '@/api/teacher/assessment'
const { proxy } = getCurrentInstance()
const { tea_assessment_status } = proxy.useDict('tea_assessment_status')
const { tea_assessment_grade } = proxy.useDict('tea_assessment_grade')
const recordList = ref([]); const loading = ref(true); const showSearch = ref(true); const total = ref(0); const title = ref(''); const open = ref(false); const activeTab = ref(0)
const data = reactive({ form: {}, queryParams: { pageNum: 1, pageSize: 10, planId: undefined, status: undefined } })
const { queryParams, form } = toRefs(data)
function getList() { loading.value = true; listAssessRecord(queryParams.value).then(res => { recordList.value = res.rows || []; total.value = res.total || 0; loading.value = false }) }
function reset() { form.value = { recordId: undefined, planId: undefined, teacherId: undefined, teachingScore: 0, researchScore: 0, serviceScore: 0, grade: '', selfEvaluation: '', teachings: [], researches: [], services: [] }; activeTab.value = 0 }
function handleAdd() { reset(); title.value = '新增考核填报'; open.value = true }
function handleUpdate(row) { reset(); getAssessRecord(row.recordId).then(res => { form.value = res.data; form.value.teachings = form.value.teachings || []; form.value.researches = form.value.researches || []; form.value.services = form.value.services || []; title.value = '修改考核填报'; open.value = true }) }
function cancel() { open.value = false }
function submitForm() { if (form.value.recordId) updateAssessRecord(form.value).then(() => { proxy.$modal.msgSuccess('修改成功'); open.value = false; getList() }); else addAssessRecord(form.value).then(() => { proxy.$modal.msgSuccess('新增成功'); open.value = false; getList() }) }
function handleSubmit(row) { proxy.$modal.confirm('确认提交？提交后不可修改').then(() => submitAssessRecord(row.recordId)).then(() => { proxy.$modal.msgSuccess('提交成功'); getList() }).catch(() => {}) }
function handleDelete(row) { proxy.$modal.confirm('确认删除？').then(() => delAssessRecord(row.recordId)).then(() => getList()).catch(() => {}) }
function handleQuery() { queryParams.value.pageNum = 1; getList() }
function resetQuery() { proxy.resetForm('queryRef'); handleQuery() }
function addTeaching() { if (!form.value.teachings) form.value.teachings = []; form.value.teachings.push({ courseName: '', classHours: 0, studentCount: 0, evalScore: 0, awards: '' }) }
function addResearch() { if (!form.value.researches) form.value.researches = []; form.value.researches.push({ researchType: '', title: '', journalOrSource: '', level: '', role: '' }) }
function addService() { if (!form.value.services) form.value.services = []; form.value.services.push({ serviceType: '', content: '', impact: '' }) }
getList()
</script>
