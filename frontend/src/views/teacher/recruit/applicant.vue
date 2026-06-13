<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch">
      <el-form-item label="姓名" prop="name"><el-input v-model="queryParams.name" placeholder="姓名" clearable style="width:140px" @keyup.enter="handleQuery" /></el-form-item>
      <el-form-item label="人才层次" prop="talentLevel"><el-select v-model="queryParams.talentLevel" placeholder="层次" clearable style="width:130px"><el-option v-for="d in tea_talent_level" :key="d.value" :label="d.label" :value="d.value" /></el-select></el-form-item>
      <el-form-item label="状态" prop="status"><el-select v-model="queryParams.status" placeholder="状态" clearable style="width:120px"><el-option v-for="d in tea_applicant_status" :key="d.value" :label="d.label" :value="d.value" /></el-select></el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5"><el-button type="primary" plain icon="Plus" @click="handleAdd" v-hasPermi="['teacher:applicant:add']">新增</el-button></el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList" />
    </el-row>

    <el-table v-loading="loading" :data="applicantList" border size="small">
      <el-table-column label="姓名" prop="name" width="90" />
      <el-table-column label="性别" prop="gender" width="60" align="center"><template #default="scope"><dict-tag :options="sys_user_sex" :value="scope.row.gender" /></template></el-table-column>
      <el-table-column label="人才层次" prop="talentLevel" width="110"><template #default="scope"><dict-tag :options="tea_talent_level" :value="scope.row.talentLevel" /></template></el-table-column>
      <el-table-column label="现单位" prop="currentUnit" width="150" show-overflow-tooltip />
      <el-table-column label="最高学历" prop="educationLevel" width="90" />
      <el-table-column label="学科方向" prop="planDiscipline" width="130" show-overflow-tooltip />
      <el-table-column label="状态" prop="status" width="90" align="center"><template #default="scope"><dict-tag :options="tea_applicant_status" :value="scope.row.status" /></template></el-table-column>
      <el-table-column label="申请日期" prop="applyDate" width="110" />
      <el-table-column label="操作" align="center" width="260" fixed="right">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['teacher:applicant:edit']">编辑</el-button>
          <el-dropdown @command="(cmd) => handleApprove(scope.row, cmd)" v-hasPermi="['teacher:applicant:approve']" style="margin-left:8px">
            <el-button link type="success">审批<el-icon class="el-icon--right"><arrow-down /></el-icon></el-button>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item command="1" :disabled="scope.row.status !== '0'">初审通过</el-dropdown-item>
                <el-dropdown-item command="2" :disabled="scope.row.status !== '1'">进入面试</el-dropdown-item>
                <el-dropdown-item command="3" :disabled="scope.row.status !== '2'">录用</el-dropdown-item>
                <el-dropdown-item command="4" divided>拒绝</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
          <el-button link type="danger" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['teacher:applicant:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNum" v-model:limit="queryParams.pageSize" @pagination="getList" />

    <!-- 新增/编辑对话框 -->
    <el-dialog :title="title" v-model="open" width="900px" append-to-body destroy-on-close>
      <el-steps :active="activeStep" finish-status="success" align-center style="margin-bottom:20px">
        <el-step title="个人信息" @click="activeStep = 0" style="cursor:pointer" />
        <el-step title="教育背景" @click="activeStep = 1" style="cursor:pointer" />
        <el-step title="工作经历" @click="activeStep = 2" style="cursor:pointer" />
        <el-step title="科研成果" @click="activeStep = 3" style="cursor:pointer" />
      </el-steps>

      <!-- 个人信息 -->
      <el-form v-show="activeStep === 0" :model="form" label-width="100px">
        <el-row :gutter="20">
          <el-col :span="8"><el-form-item label="姓名" prop="name"><el-input v-model="form.name" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="性别"><el-select v-model="form.gender"><el-option v-for="d in sys_user_sex" :key="d.value" :label="d.label" :value="d.value" /></el-select></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="出生日期"><el-date-picker v-model="form.birthDate" type="date" value-format="YYYY-MM-DD" style="width:100%" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="国籍"><el-input v-model="form.nationality" placeholder="中国" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="现工作单位"><el-input v-model="form.currentUnit" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="现任职务"><el-input v-model="form.currentPosition" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="最高学历"><el-input v-model="form.educationLevel" placeholder="博士" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="最高学位"><el-input v-model="form.degreeType" placeholder="工学博士" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="手机号"><el-input v-model="form.phone" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="邮箱"><el-input v-model="form.email" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="人才层次"><el-select v-model="form.talentLevel" style="width:100%"><el-option v-for="d in tea_talent_level" :key="d.value" :label="d.label" :value="d.value" /></el-select></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="关联计划"><el-input v-model="form.planId" placeholder="计划ID" /></el-form-item></el-col>
        </el-row>
      </el-form>

      <!-- 教育背景 -->
      <div v-show="activeStep === 1">
        <el-button type="primary" plain icon="Plus" size="small" @click="addEducation" style="margin-bottom:10px">添加教育经历</el-button>
        <el-table :data="form.educations" border size="small">
          <el-table-column label="学校" width="160"><template #default="scope"><el-input v-model="scope.row.school" /></template></el-table-column>
          <el-table-column label="专业"><template #default="scope"><el-input v-model="scope.row.major" /></template></el-table-column>
          <el-table-column label="学历/学位" width="120"><template #default="scope"><el-input v-model="scope.row.degree" /></template></el-table-column>
          <el-table-column label="开始" width="140"><template #default="scope"><el-date-picker v-model="scope.row.startDate" type="date" value-format="YYYY-MM-DD" style="width:100%" /></template></el-table-column>
          <el-table-column label="结束" width="140"><template #default="scope"><el-date-picker v-model="scope.row.endDate" type="date" value-format="YYYY-MM-DD" style="width:100%" /></template></el-table-column>
          <el-table-column label="导师" width="100"><template #default="scope"><el-input v-model="scope.row.advisor" /></template></el-table-column>
          <el-table-column label="操作" width="60" align="center"><template #default="scope"><el-button link type="danger" icon="Delete" @click="form.educations.splice(scope.$index, 1)" /></template></el-table-column>
        </el-table>
      </div>

      <!-- 工作经历 -->
      <div v-show="activeStep === 2">
        <el-button type="primary" plain icon="Plus" size="small" @click="addWork" style="margin-bottom:10px">添加工作经历</el-button>
        <el-table :data="form.works" border size="small">
          <el-table-column label="单位" width="180"><template #default="scope"><el-input v-model="scope.row.unit" /></template></el-table-column>
          <el-table-column label="职务" width="120"><template #default="scope"><el-input v-model="scope.row.position" /></template></el-table-column>
          <el-table-column label="开始" width="140"><template #default="scope"><el-date-picker v-model="scope.row.startDate" type="date" value-format="YYYY-MM-DD" style="width:100%" /></template></el-table-column>
          <el-table-column label="结束" width="140"><template #default="scope"><el-date-picker v-model="scope.row.endDate" type="date" value-format="YYYY-MM-DD" style="width:100%" /></template></el-table-column>
          <el-table-column label="工作内容"><template #default="scope"><el-input v-model="scope.row.description" /></template></el-table-column>
          <el-table-column label="操作" width="60" align="center"><template #default="scope"><el-button link type="danger" icon="Delete" @click="form.works.splice(scope.$index, 1)" /></template></el-table-column>
        </el-table>
      </div>

      <!-- 科研成果 -->
      <div v-show="activeStep === 3">
        <el-button type="primary" plain icon="Plus" size="small" @click="addAchievement" style="margin-bottom:10px">添加科研成果</el-button>
        <el-table :data="form.achievements" border size="small">
          <el-table-column label="类型" width="120"><template #default="scope"><el-select v-model="scope.row.achievementType"><el-option v-for="d in tea_achievement_type" :key="d.value" :label="d.label" :value="d.value" /></el-select></template></el-table-column>
          <el-table-column label="成果名称"><template #default="scope"><el-input v-model="scope.row.title" /></template></el-table-column>
          <el-table-column label="级别/分区" width="120"><template #default="scope"><el-input v-model="scope.row.level" /></template></el-table-column>
          <el-table-column label="影响因子/经费" width="130"><template #default="scope"><el-input-number v-model="scope.row.amount" :min="0" :precision="2" style="width:100%" /></template></el-table-column>
          <el-table-column label="操作" width="60" align="center"><template #default="scope"><el-button link type="danger" icon="Delete" @click="form.achievements.splice(scope.$index, 1)" /></template></el-table-column>
        </el-table>
      </div>

      <template #footer>
        <el-button @click="activeStep > 0 && activeStep--" :disabled="activeStep === 0">上一步</el-button>
        <el-button @click="activeStep < 3 && activeStep++" :disabled="activeStep === 3">下一步</el-button>
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary" @click="submitForm">确 定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="Applicant">
import { listApplicant, getApplicant, addApplicant, updateApplicant, delApplicant, approveApplicant } from '@/api/teacher/recruit'
import { ArrowDown } from '@element-plus/icons-vue'

const { proxy } = getCurrentInstance()
const { sys_user_sex } = proxy.useDict('sys_user_sex')
const { tea_talent_level } = proxy.useDict('tea_talent_level')
const { tea_applicant_status } = proxy.useDict('tea_applicant_status')
const { tea_achievement_type } = proxy.useDict('tea_achievement_type')

const applicantList = ref([]); const loading = ref(true); const showSearch = ref(true); const total = ref(0)
const title = ref(''); const open = ref(false); const activeStep = ref(0)
const data = reactive({ form: {}, queryParams: { pageNum: 1, pageSize: 10, name: undefined, talentLevel: undefined, status: undefined } })
const { queryParams, form } = toRefs(data)

function getList() { loading.value = true; listApplicant(queryParams.value).then(res => { applicantList.value = res.rows || []; total.value = res.total || 0; loading.value = false }) }
function reset() { form.value = { applicantId: undefined, planId: undefined, name: '', gender: '0', birthDate: undefined, nationality: '中国', currentUnit: '', currentPosition: '', educationLevel: '', degreeType: '', phone: '', email: '', talentLevel: '', educations: [], works: [], achievements: [] }; activeStep.value = 0 }
function handleAdd() { reset(); title.value = '新增应聘者'; open.value = true }
function handleUpdate(row) { reset(); getApplicant(row.applicantId).then(res => { form.value = res.data; form.value.educations = form.value.educations || []; form.value.works = form.value.works || []; form.value.achievements = form.value.achievements || []; title.value = '修改应聘者'; open.value = true }) }
function cancel() { open.value = false }
function submitForm() { if (form.value.applicantId) updateApplicant(form.value).then(() => { proxy.$modal.msgSuccess('修改成功'); open.value = false; getList() }); else addApplicant(form.value).then(() => { proxy.$modal.msgSuccess('新增成功'); open.value = false; getList() }) }
function handleDelete(row) { proxy.$modal.confirm('确认删除？').then(() => delApplicant(row.applicantId)).then(() => getList()).catch(() => {}) }
function handleApprove(row, status) { proxy.$modal.confirm('确认将状态变更为' + ['待审','初审通过','面试中','录用','拒绝'][parseInt(status)] + '？').then(() => approveApplicant(row.applicantId, status)).then(() => { proxy.$modal.msgSuccess('审批成功'); getList() }).catch(() => {}) }
function handleQuery() { queryParams.value.pageNum = 1; getList() }
function resetQuery() { proxy.resetForm('queryRef'); handleQuery() }
function addEducation() { if (!form.value.educations) form.value.educations = []; form.value.educations.push({ school: '', major: '', degree: '', startDate: '', endDate: '', advisor: '', thesis: '' }) }
function addWork() { if (!form.value.works) form.value.works = []; form.value.works.push({ unit: '', position: '', startDate: '', endDate: '', description: '' }) }
function addAchievement() { if (!form.value.achievements) form.value.achievements = []; form.value.achievements.push({ achievementType: '', title: '', level: '', amount: 0 }) }
getList()
</script>
