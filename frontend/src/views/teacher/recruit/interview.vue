<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch">
      <el-form-item label="结果" prop="result"><el-select v-model="queryParams.result" placeholder="结果" clearable style="width:120px"><el-option v-for="d in tea_interview_result" :key="d.value" :label="d.label" :value="d.value" /></el-select></el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5"><el-button type="primary" plain icon="Plus" @click="handleAdd" v-hasPermi="['teacher:interview:add']">新增</el-button></el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList" />
    </el-row>

    <el-table v-loading="loading" :data="interviewList" border size="small">
      <el-table-column label="应聘者" prop="applicantName" width="100" />
      <el-table-column label="人才层次" prop="applicantTalentLevel" width="110"><template #default="scope"><dict-tag :options="tea_talent_level" :value="scope.row.applicantTalentLevel" /></template></el-table-column>
      <el-table-column label="面试类型" prop="interviewType" width="100" />
      <el-table-column label="面试时间" prop="interviewDate" width="160" />
      <el-table-column label="教学试讲" prop="teachingScore" width="90" align="right"><template #default="scope"><b :style="{ color: scope.row.teachingScore >= 85 ? '#67C23A' : scope.row.teachingScore >= 60 ? '#E6A23C' : '#F56C6C' }">{{ scope.row.teachingScore }}</b></template></el-table-column>
      <el-table-column label="学术报告" prop="academicScore" width="90" align="right"><template #default="scope"><b :style="{ color: scope.row.academicScore >= 85 ? '#67C23A' : scope.row.academicScore >= 60 ? '#E6A23C' : '#F56C6C' }">{{ scope.row.academicScore }}</b></template></el-table-column>
      <el-table-column label="答辩" prop="defenseScore" width="90" align="right"><template #default="scope"><b :style="{ color: scope.row.defenseScore >= 85 ? '#67C23A' : scope.row.defenseScore >= 60 ? '#E6A23C' : '#F56C6C' }">{{ scope.row.defenseScore }}</b></template></el-table-column>
      <el-table-column label="综合得分" prop="totalScore" width="100" align="right"><template #default="scope"><b style="font-size:14px">{{ scope.row.totalScore }}</b></template></el-table-column>
      <el-table-column label="结果" prop="result" width="80" align="center"><template #default="scope"><dict-tag :options="tea_interview_result" :value="scope.row.result" /></template></el-table-column>
      <el-table-column label="操作" align="center" width="150" fixed="right">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['teacher:interview:edit']">编辑</el-button>
          <el-button link type="danger" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['teacher:interview:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNum" v-model:limit="queryParams.pageSize" @pagination="getList" />

    <el-dialog :title="title" v-model="open" width="700px" append-to-body destroy-on-close>
      <el-form ref="formRef" :model="form" label-width="100px">
        <el-form-item label="应聘者ID" prop="applicantId"><el-input v-model="form.applicantId" placeholder="应聘者ID" /></el-form-item>
        <el-form-item label="面试类型"><el-input v-model="form.interviewType" placeholder="如：综合面试" /></el-form-item>
        <el-form-item label="面试时间"><el-date-picker v-model="form.interviewDate" type="datetime" value-format="YYYY-MM-DD HH:mm:ss" style="width:100%" /></el-form-item>
        <el-divider content-position="left">面试评分</el-divider>
        <el-row :gutter="24">
          <el-col :span="8">
            <el-form-item label="教学试讲">
              <el-input-number v-model="form.teachingScore" :min="0" :max="100" :precision="1" controls-position="right" style="width:100%" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="学术报告">
              <el-input-number v-model="form.academicScore" :min="0" :max="100" :precision="1" controls-position="right" style="width:100%" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="答辩">
              <el-input-number v-model="form.defenseScore" :min="0" :max="100" :precision="1" controls-position="right" style="width:100%" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="综合得分">
          <el-tag size="large" type="primary" style="font-size:16px;padding:6px 16px">{{ computedTotal }}</el-tag>
          <span style="margin-left:12px;color:#999;font-size:12px">= 教学×40% + 学术×40% + 答辩×20%</span>
        </el-form-item>
        <el-divider />
        <el-form-item label="面试意见"><el-input v-model="form.opinion" type="textarea" :rows="3" /></el-form-item>
        <el-form-item label="结果"><el-select v-model="form.result" style="width:100%"><el-option v-for="d in tea_interview_result" :key="d.value" :label="d.label" :value="d.value" /></el-select></el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary" @click="submitForm">确 定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="InterviewRecord">
import { listInterview, getInterview, addInterview, updateInterview, delInterview } from '@/api/teacher/recruit'

const { proxy } = getCurrentInstance()
const { tea_interview_result } = proxy.useDict('tea_interview_result')
const { tea_talent_level } = proxy.useDict('tea_talent_level')
const interviewList = ref([]); const loading = ref(true); const showSearch = ref(true); const total = ref(0)
const title = ref(''); const open = ref(false)
const data = reactive({ form: {}, queryParams: { pageNum: 1, pageSize: 10, result: undefined } })
const { queryParams, form } = toRefs(data)

const computedTotal = computed(() => {
  const t = form.value.teachingScore || 0; const a = form.value.academicScore || 0; const d = form.value.defenseScore || 0
  return (t * 0.4 + a * 0.4 + d * 0.2).toFixed(2)
})

function getList() { loading.value = true; listInterview(queryParams.value).then(res => { interviewList.value = res.rows || []; total.value = res.total || 0; loading.value = false }) }
function reset() { form.value = { interviewId: undefined, applicantId: undefined, interviewType: '', interviewDate: '', teachingScore: 0, academicScore: 0, defenseScore: 0, totalScore: 0, opinion: '', result: '0' } }
function handleAdd() { reset(); title.value = '新增面试记录'; open.value = true }
function handleUpdate(row) { reset(); getInterview(row.interviewId).then(res => { form.value = res.data; title.value = '修改面试记录'; open.value = true }) }
function cancel() { open.value = false }
function submitForm() { form.value.totalScore = parseFloat(computedTotal.value); if (form.value.interviewId) updateInterview(form.value).then(() => { proxy.$modal.msgSuccess('修改成功'); open.value = false; getList() }); else addInterview(form.value).then(() => { proxy.$modal.msgSuccess('新增成功'); open.value = false; getList() }) }
function handleDelete(row) { proxy.$modal.confirm('确认删除？').then(() => delInterview(row.interviewId)).then(() => getList()).catch(() => {}) }
function handleQuery() { queryParams.value.pageNum = 1; getList() }
function resetQuery() { proxy.resetForm('queryRef'); handleQuery() }
getList()
</script>
