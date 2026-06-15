<template>
  <div class="app-container">
    <el-skeleton :loading="loading" animated>
      <template #default>
        <!-- 未关联提示 -->
        <el-empty v-if="!teacher.teacherId" description="当前账号未关联教师信息">
          <template #description>
            <p style="color:#999">请联系管理员在「教师管理 > 个人信息管理」中为您的账号关联教师记录</p>
          </template>
        </el-empty>
        <template v-else>
        <!-- 基本信息卡片 -->
        <el-card shadow="hover" style="margin-bottom: 20px">
          <template #header>
            <div class="card-header">
              <span>个人基本信息</span>
              <el-button type="primary" size="small" @click="handleEdit" v-hasPermi="['portal:info:edit']">编辑</el-button>
            </div>
          </template>
          <el-descriptions :column="3" border>
            <el-descriptions-item label="工号">{{ teacher.teacherNo }}</el-descriptions-item>
            <el-descriptions-item label="姓名">{{ teacher.teacherName }}</el-descriptions-item>
            <el-descriptions-item label="性别">
              <dict-tag :options="sys_user_sex" :value="teacher.gender" />
            </el-descriptions-item>
            <el-descriptions-item label="出生日期">{{ teacher.birthDate }}</el-descriptions-item>
            <el-descriptions-item label="民族">{{ teacher.nation }}</el-descriptions-item>
            <el-descriptions-item label="政治面貌">{{ teacher.politicalStatus }}</el-descriptions-item>
            <el-descriptions-item label="手机号">{{ maskPhone(teacher.phone) }}</el-descriptions-item>
            <el-descriptions-item label="邮箱">{{ teacher.email }}</el-descriptions-item>
            <el-descriptions-item label="所属院系">{{ teacher.deptName }}</el-descriptions-item>
            <el-descriptions-item label="办公地址">{{ teacher.officeAddress }}</el-descriptions-item>
            <el-descriptions-item label="状态">
              <dict-tag :options="tea_teacher_status" :value="teacher.status" />
            </el-descriptions-item>
            <el-descriptions-item label="紧急联系人">{{ teacher.emergencyContact }}</el-descriptions-item>
          </el-descriptions>
        </el-card>

        <!-- 子表 Tab -->
        <el-card shadow="hover">
          <el-tabs v-model="activeTab">
            <!-- 学历信息 -->
            <el-tab-pane label="学历信息" name="education">
              <el-table :data="teacher.educations || []" border size="small">
                <el-table-column label="学历" prop="educationLevel" width="120" />
                <el-table-column label="毕业院校" prop="graduateSchool" />
                <el-table-column label="专业" prop="major" />
                <el-table-column label="学位类型" prop="degreeType" width="120" />
                <el-table-column label="获得时间" prop="obtainDate" width="120" />
                <el-table-column label="最高学历" width="90" align="center">
                  <template #default="scope">
                    <el-tag :type="scope.row.isHighest === '1' ? 'success' : 'info'" size="small">{{ scope.row.isHighest === '1' ? '是' : '否' }}</el-tag>
                  </template>
                </el-table-column>
              </el-table>
              <el-empty v-if="!teacher.educations || teacher.educations.length === 0" description="暂无学历信息" />
            </el-tab-pane>

            <!-- 职称记录 -->
            <el-tab-pane label="职称记录" name="title">
              <el-table :data="teacher.titleRecords || []" border size="small">
                <el-table-column label="职称名称" prop="titleName" width="140" />
                <el-table-column label="职称系列" prop="titleSeries" width="160" />
                <el-table-column label="聘任岗位" prop="hirePosition" width="140" />
                <el-table-column label="取得时间" prop="obtainDate" width="120" />
                <el-table-column label="当前职称" width="90" align="center">
                  <template #default="scope">
                    <el-tag :type="scope.row.isCurrent === '1' ? 'success' : 'info'" size="small">{{ scope.row.isCurrent === '1' ? '是' : '否' }}</el-tag>
                  </template>
                </el-table-column>
              </el-table>
              <el-empty v-if="!teacher.titleRecords || teacher.titleRecords.length === 0" description="暂无职称记录" />
            </el-tab-pane>

            <!-- 学科信息 -->
            <el-tab-pane label="学科信息" name="discipline">
              <el-table :data="teacher.disciplines || []" border size="small">
                <el-table-column label="一级学科" prop="firstDiscipline" width="200" />
                <el-table-column label="二级学科" prop="secondDiscipline" />
                <el-table-column label="研究方向" prop="researchDirection" />
              </el-table>
              <el-empty v-if="!teacher.disciplines || teacher.disciplines.length === 0" description="暂无学科信息" />
            </el-tab-pane>
          </el-tabs>
        </el-card>
        </template>
      </template>
    </el-skeleton>

    <!-- 编辑对话框 -->
    <el-dialog title="编辑个人信息" v-model="editOpen" width="800px" append-to-body destroy-on-close>
      <el-form ref="editFormRef" :model="form" :rules="rules" label-width="100px">
        <el-row :gutter="20">
          <el-col :span="12"><el-form-item label="工号" prop="teacherNo"><el-input v-model="form.teacherNo" @blur="checkNo" /><span v-if="noChecked === false" style="color:red;font-size:12px"> 工号已存在</span><span v-if="noChecked === true" style="color:green;font-size:12px"> 工号可用</span></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="姓名" prop="teacherName"><el-input v-model="form.teacherName" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="性别" prop="gender"><el-select v-model="form.gender" placeholder="请选择"><el-option v-for="dict in sys_user_sex" :key="dict.value" :label="dict.label" :value="dict.value" /></el-select></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="出生日期" prop="birthDate"><el-date-picker v-model="form.birthDate" type="date" value-format="YYYY-MM-DD" placeholder="选择日期" style="width:100%" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="身份证号" prop="idCard"><el-input v-model="form.idCard" @blur="parseIdCard" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="民族" prop="nation"><el-select v-model="form.nation" placeholder="请选择" filterable clearable><el-option v-for="dict in tea_nation" :key="dict.value" :label="dict.label" :value="dict.value" /></el-select></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="政治面貌"><el-select v-model="form.politicalStatus" placeholder="请选择" clearable><el-option v-for="dict in tea_political" :key="dict.value" :label="dict.label" :value="dict.value" /></el-select></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="手机号" prop="phone"><el-input v-model="form.phone" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="邮箱" prop="email"><el-input v-model="form.email" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="办公地址"><el-input v-model="form.officeAddress" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="紧急联系人"><el-input v-model="form.emergencyContact" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="紧急电话"><el-input v-model="form.emergencyPhone" /></el-form-item></el-col>
        </el-row>
      </el-form>
      <template #footer>
        <el-button @click="editOpen = false">取 消</el-button>
        <el-button type="primary" @click="submitEdit">确 定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="PortalInfo">
import { ElMessage } from 'element-plus'
import { getMyInfo, updateMyInfo, checkMyTeacherNo } from '@/api/portal/info'

const { proxy } = getCurrentInstance()
const { sys_user_sex } = proxy.useDict('sys_user_sex')
const { tea_teacher_status } = proxy.useDict('tea_teacher_status')
const { tea_nation } = proxy.useDict('tea_nation')
const { tea_political } = proxy.useDict('tea_political')

const teacher = ref({})
const loading = ref(true)
const editOpen = ref(false)
const activeTab = ref('education')
const noChecked = ref(null)
const form = ref({})

const rules = {
  teacherNo: [{ required: true, message: '工号不能为空', trigger: 'blur' }],
  teacherName: [{ required: true, message: '姓名不能为空', trigger: 'blur' }],
  idCard: [{ pattern: /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/, message: '身份证号格式不正确', trigger: 'blur' }],
  email: [{ type: 'email', message: '邮箱格式不正确', trigger: 'blur' }],
  phone: [{ pattern: /^1[3-9]\d{9}$/, message: '手机号格式不正确', trigger: 'blur' }]
}

function loadInfo() {
  loading.value = true
  getMyInfo().then(res => {
    teacher.value = res.data || {}
    teacher.value.educations = teacher.value.educations || []
    teacher.value.titleRecords = teacher.value.titleRecords || []
    teacher.value.disciplines = teacher.value.disciplines || []
    loading.value = false
  }).catch(() => {
    loading.value = false
    ElMessage.warning('当前账号未关联教师信息，请联系管理员在「个人信息管理」中为您的账号关联教师记录')
  })
}

function handleEdit() {
  form.value = { ...teacher.value }
  noChecked.value = null
  editOpen.value = true
}

function checkNo() {
  if (!form.value.teacherNo) return
  checkMyTeacherNo({ teacherNo: form.value.teacherNo, teacherId: form.value.teacherId }).then(res => { noChecked.value = res.data })
}

function parseIdCard() {
  const id = form.value.idCard
  if (id && id.length === 18) {
    const year = id.substring(6, 10), month = id.substring(10, 12), day = id.substring(12, 14)
    form.value.birthDate = `${year}-${month}-${day}`
    form.value.gender = parseInt(id.charAt(16)) % 2 === 1 ? '0' : '1'
  }
}

function submitEdit() {
  proxy.$refs.editFormRef.validate(valid => {
    if (!valid) return
    updateMyInfo(form.value).then(() => {
      proxy.$modal.msgSuccess('修改成功')
      editOpen.value = false
      loadInfo()
    })
  })
}

function maskPhone(phone) { return phone ? phone.replace(/(\d{3})\d{4}(\d{4})/, '$1****$2') : '' }

loadInfo()
</script>

<style scoped>
.card-header { display: flex; justify-content: space-between; align-items: center; }
</style>
