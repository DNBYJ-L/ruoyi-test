<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch">
      <el-form-item label="工号" prop="teacherNo">
        <el-input v-model="queryParams.teacherNo" placeholder="请输入工号" clearable style="width: 160px" @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="姓名" prop="teacherName">
        <el-input v-model="queryParams.teacherName" placeholder="请输入姓名" clearable style="width: 160px" @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="所属院系" prop="deptId">
        <el-tree-select v-model="queryParams.deptId" :data="deptOptions" :props="{ value: 'id', label: 'label', children: 'children' }" value-key="id" placeholder="请选择院系" check-strictly style="width: 200px" />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="教师状态" clearable style="width: 120px">
          <el-option v-for="dict in tea_teacher_status" :key="dict.value" :label="dict.label" :value="dict.value" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5"><el-button type="primary" plain icon="Plus" @click="handleAdd" v-hasPermi="['teacher:info:add']">新增</el-button></el-col>
      <el-col :span="1.5"><el-button type="danger" plain icon="Delete" :disabled="multiple" @click="handleDelete" v-hasPermi="['teacher:info:remove']">删除</el-button></el-col>
      <el-col :span="1.5"><el-button type="warning" plain icon="Download" @click="handleExport" v-hasPermi="['teacher:info:export']">导出</el-button></el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList" />
    </el-row>

    <el-table v-loading="loading" :data="teacherList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="工号" prop="teacherNo" width="120" />
      <el-table-column label="姓名" prop="teacherName" width="100" />
      <el-table-column label="性别" prop="gender" width="60" align="center">
        <template #default="scope"><dict-tag :options="sys_user_sex" :value="scope.row.gender" /></template>
      </el-table-column>
      <el-table-column label="所属院系" prop="deptName" width="150" />
      <el-table-column label="手机号" prop="phone" width="130">
        <template #default="scope">{{ maskPhone(scope.row.phone) }}</template>
      </el-table-column>
      <el-table-column label="身份证号" prop="idCard" width="180">
        <template #default="scope">{{ maskIdCard(scope.row.idCard) }}</template>
      </el-table-column>
      <el-table-column label="状态" prop="status" width="80" align="center">
        <template #default="scope"><dict-tag :options="tea_teacher_status" :value="scope.row.status" /></template>
      </el-table-column>
      <el-table-column label="关联账号" prop="loginName" width="110" align="center">
        <template #default="scope">
          <el-tag v-if="scope.row.loginName" type="success" size="small">{{ scope.row.loginName }}</el-tag>
          <el-tag v-else type="info" size="small">未关联</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="200" fixed="right">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['teacher:info:edit']">编辑</el-button>
          <el-button link type="danger" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['teacher:info:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNum" v-model:limit="queryParams.pageSize" @pagination="getList" />

    <!-- 新增/编辑对话框 -->
    <el-dialog :title="title" v-model="open" width="900px" append-to-body destroy-on-close>
      <el-steps :active="activeStep" finish-status="success" align-center style="margin-bottom: 20px">
        <el-step title="基本信息" @click="activeStep = 0" style="cursor:pointer" />
        <el-step title="学历信息" @click="activeStep = 1" style="cursor:pointer" />
        <el-step title="职称信息" @click="activeStep = 2" style="cursor:pointer" />
        <el-step title="学科信息" @click="activeStep = 3" style="cursor:pointer" />
      </el-steps>

      <!-- 基本信息 -->
      <el-form v-show="activeStep === 0" ref="basicFormRef" :model="form" :rules="basicRules" label-width="100px">
        <el-row :gutter="20">
          <el-col :span="12"><el-form-item label="工号" prop="teacherNo"><el-input v-model="form.teacherNo" placeholder="T+年份+4位序号" @blur="checkNo" /><span v-if="noChecked === false" style="color:red;font-size:12px">工号已存在</span><span v-if="noChecked === true" style="color:green;font-size:12px">工号可用</span></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="姓名" prop="teacherName"><el-input v-model="form.teacherName" placeholder="请输入姓名" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="性别" prop="gender"><el-select v-model="form.gender" placeholder="请选择"><el-option v-for="dict in sys_user_sex" :key="dict.value" :label="dict.label" :value="dict.value" /></el-select></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="出生日期" prop="birthDate"><el-date-picker v-model="form.birthDate" type="date" value-format="YYYY-MM-DD" placeholder="选择日期" style="width:100%" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="身份证号" prop="idCard"><el-input v-model="form.idCard" placeholder="18位身份证号" @blur="parseIdCard" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="民族" prop="nation"><el-select v-model="form.nation" placeholder="请选择" filterable><el-option v-for="dict in tea_nation" :key="dict.value" :label="dict.label" :value="dict.value" /></el-select></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="政治面貌" prop="politicalStatus"><el-select v-model="form.politicalStatus" placeholder="请选择"><el-option v-for="dict in tea_political" :key="dict.value" :label="dict.label" :value="dict.value" /></el-select></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="手机号" prop="phone"><el-input v-model="form.phone" placeholder="请输入手机号" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="邮箱" prop="email"><el-input v-model="form.email" placeholder="请输入邮箱" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="所属院系" prop="deptId"><el-tree-select v-model="form.deptId" :data="deptOptions" :props="{ value: 'id', label: 'label', children: 'children' }" value-key="id" placeholder="请选择" check-strictly style="width:100%" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="办公地址" prop="officeAddress"><el-input v-model="form.officeAddress" placeholder="请输入办公地址" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="状态" prop="status"><el-select v-model="form.status" placeholder="请选择"><el-option v-for="dict in tea_teacher_status" :key="dict.value" :label="dict.label" :value="dict.value" /></el-select></el-form-item></el-col>
          <el-col :span="12">
            <el-form-item label="关联账号">
              <div style="width: 100%">
                <el-input
                  :model-value="form.userId ? (selectedUserDisplay || '') : ''"
                  placeholder="点击选择关联账号"
                  readonly
                  @click="openUserPicker"
                >
                  <template #prefix><el-icon><User /></el-icon></template>
                  <template #suffix>
                    <el-icon v-if="form.userId" style="cursor:pointer" @click.stop="onClearUser"><CircleClose /></el-icon>
                  </template>
                </el-input>
                <div v-if="form.loginName && !form.userId && !userCleared" style="color: #999; font-size: 12px; margin-top: 2px">当前关联: {{ form.loginName }}</div>
              </div>
            </el-form-item>
          </el-col>
          <el-col :span="12"><el-form-item label="紧急联系人"><el-input v-model="form.emergencyContact" placeholder="请输入" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="紧急电话"><el-input v-model="form.emergencyPhone" placeholder="请输入" /></el-form-item></el-col>
        </el-row>
      </el-form>

      <!-- 学历信息 -->
      <div v-show="activeStep === 1">
        <el-button type="primary" plain icon="Plus" size="small" @click="addEducation" style="margin-bottom:10px">添加学历</el-button>
        <el-table :data="form.educations" border size="small">
          <el-table-column label="学历" width="140"><template #default="scope"><el-select v-model="scope.row.educationLevel" placeholder="选择学历"><el-option v-for="d in tea_education_level" :key="d.value" :label="d.label" :value="d.value" /></el-select></template></el-table-column>
          <el-table-column label="毕业院校"><template #default="scope"><el-input v-model="scope.row.graduateSchool" placeholder="院校名称" /></template></el-table-column>
          <el-table-column label="专业"><template #default="scope"><el-input v-model="scope.row.major" placeholder="专业名称" /></template></el-table-column>
          <el-table-column label="学位类型" width="120"><template #default="scope"><el-select v-model="scope.row.degreeType" placeholder="选择"><el-option v-for="d in tea_degree_type" :key="d.value" :label="d.label" :value="d.value" /></el-select></template></el-table-column>
          <el-table-column label="获得时间" width="160"><template #default="scope"><el-date-picker v-model="scope.row.obtainDate" type="date" value-format="YYYY-MM-DD" placeholder="日期" style="width:100%" /></template></el-table-column>
          <el-table-column label="最高" width="80" align="center"><template #default="scope"><el-switch v-model="scope.row.isHighest" active-value="1" inactive-value="0" /></template></el-table-column>
          <el-table-column label="操作" width="60" align="center"><template #default="scope"><el-button link type="danger" icon="Delete" @click="form.educations.splice(scope.$index, 1)" /></template></el-table-column>
        </el-table>
      </div>

      <!-- 职称信息 -->
      <div v-show="activeStep === 2">
        <el-button type="primary" plain icon="Plus" size="small" @click="addTitleRecord" style="margin-bottom:10px">添加职称</el-button>
        <el-table :data="form.titleRecords" border size="small" v-if="form.titleRecords && form.titleRecords.length > 0">
          <el-table-column label="职称名称" width="160"><template #default="scope"><el-select v-model="scope.row.titleName" placeholder="选择职称" filterable><el-option label="教授" value="教授" /><el-option label="副教授" value="副教授" /><el-option label="讲师" value="讲师" /><el-option label="助教" value="助教" /></el-select></template></el-table-column>
          <el-table-column label="职称系列" width="140"><template #default="scope"><el-input v-model="scope.row.titleSeries" placeholder="如：高校教师系列" /></template></el-table-column>
          <el-table-column label="聘任岗位" width="140"><template #default="scope"><el-input v-model="scope.row.hirePosition" placeholder="如：专技四级" /></template></el-table-column>
          <el-table-column label="取得时间" width="160"><template #default="scope"><el-date-picker v-model="scope.row.obtainDate" type="date" value-format="YYYY-MM-DD" placeholder="日期" style="width:100%" /></template></el-table-column>
          <el-table-column label="当前职称" width="100" align="center"><template #default="scope"><el-switch v-model="scope.row.isCurrent" active-value="1" inactive-value="0" /></template></el-table-column>
          <el-table-column label="操作" width="60" align="center"><template #default="scope"><el-button link type="danger" icon="Delete" @click="form.titleRecords.splice(scope.$index, 1)" /></template></el-table-column>
        </el-table>
        <el-empty v-else description="暂无职称记录，点击上方按钮添加" />
      </div>

      <!-- 学科信息 -->
      <div v-show="activeStep === 3">
        <el-button type="primary" plain icon="Plus" size="small" @click="addDiscipline" style="margin-bottom:10px">添加学科</el-button>
        <el-table :data="form.disciplines" border size="small">
          <el-table-column label="一级学科" width="180"><template #default="scope"><el-select v-model="scope.row.firstDiscipline" placeholder="选择" filterable><el-option v-for="d in tea_first_discipline" :key="d.value" :label="d.label" :value="d.value" /></el-select></template></el-table-column>
          <el-table-column label="二级学科"><template #default="scope"><el-input v-model="scope.row.secondDiscipline" placeholder="请输入" /></template></el-table-column>
          <el-table-column label="研究方向"><template #default="scope"><el-input v-model="scope.row.researchDirection" placeholder="请输入" /></template></el-table-column>
          <el-table-column label="操作" width="60" align="center"><template #default="scope"><el-button link type="danger" icon="Delete" @click="form.disciplines.splice(scope.$index, 1)" /></template></el-table-column>
        </el-table>
      </div>

      <template #footer>
        <div>
          <el-button @click="activeStep > 0 && activeStep--" :disabled="activeStep === 0">上一步</el-button>
          <el-button @click="activeStep < 3 && activeStep++" :disabled="activeStep === 3">下一步</el-button>
          <el-button @click="cancel">取 消</el-button>
          <el-button type="primary" @click="submitForm">确 定</el-button>
        </div>
      </template>
    </el-dialog>

    <!-- 关联账号选择弹窗 -->
    <el-dialog title="选择关联账号" v-model="userPickerOpen" width="500px" append-to-body destroy-on-close>
      <el-input v-model="userSearchKey" placeholder="输入用户名或昵称模糊搜索" clearable @input="doSearchUsers" style="margin-bottom: 12px">
        <template #prefix><el-icon><Search /></el-icon></template>
      </el-input>
      <div v-loading="userSearchLoading" style="max-height: 320px; overflow-y: auto; border: 1px solid #ebeef5; border-radius: 4px; padding: 8px">
        <div v-if="userSearchList.length === 0 && !userSearchLoading" style="text-align: center; color: #999; padding: 30px 0">暂无数据，请输入关键词搜索</div>
        <el-checkbox-group v-model="userPickerChecked">
          <div v-for="u in userSearchList" :key="u.userId" style="padding: 6px 0; border-bottom: 1px solid #f5f5f5">
            <el-checkbox :label="u.userId" @change="onCheckUser(u)">
              <span>{{ u.userName }}</span>
              <span style="color: #999; margin-left: 8px">{{ u.nickName }}</span>
              <span v-if="u.phonenumber" style="color: #bbb; margin-left: 8px; font-size: 12px">{{ u.phonenumber }}</span>
            </el-checkbox>
          </div>
        </el-checkbox-group>
      </div>
      <template #footer>
        <el-button type="danger" plain @click="clearUserLink">清除关联</el-button>
        <el-button @click="userPickerOpen = false">取 消</el-button>
        <el-button type="primary" @click="confirmUserPicker">确 定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="TeacherInfo">
import { listTeacher, getTeacher, addTeacher, updateTeacher, delTeacher, checkTeacherNo, exportTeacher } from '@/api/teacher/info'
import { listDept } from '@/api/system/dept'
import { listUser } from '@/api/system/user'

const { proxy } = getCurrentInstance()
const { sys_user_sex } = proxy.useDict('sys_user_sex')
const { tea_teacher_status } = proxy.useDict('tea_teacher_status')
const { tea_nation } = proxy.useDict('tea_nation')
const { tea_political } = proxy.useDict('tea_political')
const { tea_education_level } = proxy.useDict('tea_education_level')
const { tea_degree_type } = proxy.useDict('tea_degree_type')
const { tea_first_discipline } = proxy.useDict('tea_first_discipline')

const teacherList = ref([])
const loading = ref(true)
const showSearch = ref(true)
const ids = ref([])
const single = ref(true)
const multiple = ref(true)
const total = ref(0)
const title = ref('')
const open = ref(false)
const activeStep = ref(0)
const noChecked = ref(null)
const deptOptions = ref([])
const userSearchLoading = ref(false)
const userOptions = ref([])
const userCleared = ref(false)
const userPickerOpen = ref(false)
const userSearchKey = ref('')
const userSearchList = ref([])
const userPickerChecked = ref([])
const selectedUserDisplay = ref('')
const tempPickedUser = ref(null)

const data = reactive({
  form: {},
  queryParams: { pageNum: 1, pageSize: 10, teacherNo: undefined, teacherName: undefined, deptId: undefined, status: undefined },
  basicRules: {
    teacherNo: [{ required: true, message: '工号不能为空', trigger: 'blur' }],
    teacherName: [{ required: true, message: '姓名不能为空', trigger: 'blur' }],
    idCard: [{ pattern: /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/, message: '身份证号格式不正确', trigger: 'blur' }],
    email: [{ type: 'email', message: '邮箱格式不正确', trigger: 'blur' }],
    phone: [{ pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/, message: '手机号格式不正确', trigger: 'blur' }]
  }
})
const { queryParams, form, basicRules } = toRefs(data)

function getList() {
  loading.value = true
  listTeacher(queryParams.value).then(response => {
    teacherList.value = response.rows
    total.value = response.total
    loading.value = false
  })
}

function getDeptTree() {
  listDept().then(response => {
    const list = response.data || []
    const map = {}
    const tree = []
    list.forEach(item => { map[item.deptId] = { id: item.deptId, label: item.deptName, children: [] } })
    list.forEach(item => {
      if (item.parentId && map[item.parentId]) { map[item.parentId].children.push(map[item.deptId]) }
      else { tree.push(map[item.deptId]) }
    })
    deptOptions.value = tree
  })
}

function reset() {
  form.value = { teacherId: undefined, teacherNo: undefined, teacherName: undefined, gender: '0', birthDate: undefined, idCard: undefined, nation: undefined, politicalStatus: undefined, phone: undefined, email: undefined, officeAddress: undefined, emergencyContact: undefined, emergencyPhone: undefined, deptId: undefined, userId: undefined, status: '0', remark: undefined, educations: [], titleRecords: [], disciplines: [] }
  activeStep.value = 0
  noChecked.value = null
  userCleared.value = false
  userOptions.value = []
  selectedUserDisplay.value = ''
  tempPickedUser.value = null
  proxy.resetForm('basicFormRef')
}

function handleAdd() { reset(); title.value = '新增教师'; open.value = true }
function handleUpdate(row) {
  reset()
  const teacherId = row.teacherId || ids.value[0]
  getTeacher(teacherId).then(response => {
    form.value = response.data
    form.value.educations = form.value.educations || []
    form.value.titleRecords = form.value.titleRecords || []
    form.value.disciplines = form.value.disciplines || []
    // 如果已关联用户，显示在选项中
    if (form.value.userId && form.value.loginName) {
      selectedUserDisplay.value = form.value.loginName + ' (' + form.value.teacherName + ')'
    } else {
      selectedUserDisplay.value = ''
    }
    title.value = '修改教师'
    open.value = true
  })
}
function cancel() { open.value = false; reset() }
function submitForm() {
  const data = { ...form.value }
  // 如果用户清空了关联账号，发送哨兵值 -1
  if (userCleared.value) {
    data.userId = -1
  }
  if (form.value.teacherId) { updateTeacher(data).then(() => { proxy.$modal.msgSuccess('修改成功'); open.value = false; getList() }) }
  else { addTeacher(data).then(() => { proxy.$modal.msgSuccess('新增成功'); open.value = false; getList() }) }
}
function handleDelete(row) {
  const teacherIds = row.teacherId || ids.value
  proxy.$modal.confirm('确认删除选中的教师？').then(() => delTeacher(teacherIds)).then(() => getList()).catch(() => {})
}
function handleSelectionChange(selection) { ids.value = selection.map(item => item.teacherId); single.value = selection.length !== 1; multiple.value = !selection.length }
function handleQuery() { queryParams.value.pageNum = 1; getList() }
function resetQuery() { proxy.resetForm('queryRef'); handleQuery() }
function handleExport() { proxy.download('/teacher/info/export', { ...queryParams.value }, `teacher_${new Date().getTime()}.xlsx`) }

function addEducation() { if (!form.value.educations) form.value.educations = []; form.value.educations.push({ educationLevel: '', graduateSchool: '', major: '', degreeType: '', obtainDate: '', isHighest: '0' }) }
function addTitleRecord() { if (!form.value.titleRecords) form.value.titleRecords = []; form.value.titleRecords.push({ titleName: '', titleSeries: '', obtainDate: '', hirePosition: '', isCurrent: '0' }) }
function addDiscipline() { if (!form.value.disciplines) form.value.disciplines = []; form.value.disciplines.push({ firstDiscipline: '', secondDiscipline: '', researchDirection: '' }) }

function checkNo() {
  if (!form.value.teacherNo) return
  checkTeacherNo({ teacherNo: form.value.teacherNo, teacherId: form.value.teacherId }).then(res => { noChecked.value = res.data })
}
function parseIdCard() {
  const id = form.value.idCard
  if (id && id.length === 18) {
    const year = id.substring(6, 10), month = id.substring(10, 12), day = id.substring(12, 14)
    form.value.birthDate = `${year}-${month}-${day}`
    form.value.gender = parseInt(id.charAt(16)) % 2 === 1 ? '0' : '1'
  }
}
function maskPhone(phone) { return phone ? phone.replace(/(\d{3})\d{4}(\d{4})/, '$1****$2') : '' }
function maskIdCard(id) { return id ? id.replace(/(\d{6})\d{8}(\d{4})/, '$1********$2') : '' }

function searchUsers(query) {
  if (!query || query.length < 1) { userOptions.value = []; return }
  userSearchLoading.value = true
  listUser({ userName: query, pageNum: 1, pageSize: 20 }).then(res => {
    userOptions.value = (res.rows || []).map(u => ({ userId: u.userId, userName: u.userName, nickName: u.nickName }))
  }).finally(() => { userSearchLoading.value = false })
}
function onClearUser() {
  userCleared.value = true
  form.value.userId = undefined
  selectedUserDisplay.value = ''
  tempPickedUser.value = null
}

function openUserPicker() {
  userSearchKey.value = ''
  userSearchList.value = []
  userPickerChecked.value = form.value.userId ? [form.value.userId] : []
  tempPickedUser.value = null
  userPickerOpen.value = true
}
function doSearchUsers() {
  const key = userSearchKey.value.trim()
  if (!key) { userSearchList.value = []; return }
  userSearchLoading.value = true
  listUser({ userName: key, pageNum: 1, pageSize: 50 }).then(res => {
    userSearchList.value = (res.rows || []).map(u => ({ userId: u.userId, userName: u.userName, nickName: u.nickName, phonenumber: u.phonenumber }))
  }).finally(() => { userSearchLoading.value = false })
}
function onCheckUser(u) {
  // 单选模式：只保留当前勾选的
  userPickerChecked.value = [u.userId]
  tempPickedUser.value = u
}
function confirmUserPicker() {
  if (tempPickedUser.value) {
    form.value.userId = tempPickedUser.value.userId
    selectedUserDisplay.value = tempPickedUser.value.userName + ' (' + tempPickedUser.value.nickName + ')'
    userCleared.value = false
  }
  userPickerOpen.value = false
}
function clearUserLink() {
  form.value.userId = undefined
  selectedUserDisplay.value = ''
  userPickerChecked.value = []
  tempPickedUser.value = null
  userCleared.value = true
  userPickerOpen.value = false
}

getDeptTree()
getList()
</script>
