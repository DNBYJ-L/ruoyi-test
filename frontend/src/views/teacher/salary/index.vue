<template>
  <div class="app-container">
    <el-tabs v-model="activeTab" @tab-change="handleTabChange">
      <el-tab-pane label="管理员视图" name="admin">
        <el-row :gutter="20" style="margin-bottom: 20px">
          <el-col :span="8">
            <el-card shadow="hover" class="kpi-card">
              <template #header><span>全校薪资总额</span></template>
              <div class="kpi-value">{{ kpi.totalGross ? '¥' + Number(kpi.totalGross).toLocaleString() : '¥0' }}</div>
              <div class="kpi-sub">发放人数: {{ kpi.teacherCount || 0 }} 人 | 发放笔数: {{ kpi.totalCount || 0 }}</div>
            </el-card>
          </el-col>
          <el-col :span="8">
            <el-card shadow="hover" class="kpi-card">
              <template #header><span>实发总额</span></template>
              <div class="kpi-value" style="color:#67C23A">{{ kpi.totalActual ? '¥' + Number(kpi.totalActual).toLocaleString() : '¥0' }}</div>
              <div class="kpi-sub">实发占应发: {{ kpi.totalGross > 0 ? ((kpi.totalActual / kpi.totalGross) * 100).toFixed(1) : 0 }}%</div>
            </el-card>
          </el-col>
          <el-col :span="8">
            <el-card shadow="hover" class="kpi-card">
              <template #header><span>年度筛选</span></template>
              <el-date-picker v-model="salaryYear" type="year" value-format="YYYY" placeholder="选择年份" style="width:100%" @change="loadAdminData" />
            </el-card>
          </el-col>
        </el-row>

        <el-row :gutter="20" style="margin-bottom: 20px">
          <el-col :span="12">
            <el-card shadow="hover">
              <template #header><span>院系薪资对比</span></template>
              <div ref="deptChartRef" style="height: 350px"></div>
            </el-card>
          </el-col>
          <el-col :span="12">
            <el-card shadow="hover">
              <template #header><span>薪资构成分析</span></template>
              <div ref="pieChartRef" style="height: 350px"></div>
            </el-card>
          </el-col>
        </el-row>

        <el-card shadow="hover">
          <template #header><span>薪资记录列表</span></template>
          <el-form :model="salaryQuery" :inline="true" style="margin-bottom:10px">
            <el-form-item label="工号"><el-input v-model="salaryQuery.teacherNo" placeholder="工号" clearable style="width:140px" /></el-form-item>
            <el-form-item label="姓名"><el-input v-model="salaryQuery.teacherName" placeholder="姓名" clearable style="width:140px" /></el-form-item>
            <el-form-item label="年份"><el-input v-model="salaryQuery.salaryYear" placeholder="年份" clearable style="width:100px" /></el-form-item>
            <el-form-item><el-button type="primary" icon="Search" @click="loadSalaryList">搜索</el-button></el-form-item>
          </el-form>
          <el-table :data="salaryList" v-loading="salaryLoading" border size="small">
            <el-table-column label="工号" prop="teacherNo" width="110" />
            <el-table-column label="姓名" prop="teacherName" width="80" />
            <el-table-column label="院系" prop="deptName" width="120" />
            <el-table-column label="年月" width="90"><template #default="scope">{{ scope.row.salaryYear }}-{{ String(scope.row.salaryMonth).padStart(2, '0') }}</template></el-table-column>
            <el-table-column label="岗位工资" prop="postSalary" width="100" align="right" />
            <el-table-column label="薪级工资" prop="scaleSalary" width="100" align="right" />
            <el-table-column label="绩效" prop="basePerformance" width="100" align="right" />
            <el-table-column label="津贴" prop="talentAllowance" width="100" align="right" />
            <el-table-column label="税前应发" prop="grossSalary" width="110" align="right"><template #default="scope"><span style="font-weight:bold">{{ scope.row.grossSalary }}</span></template></el-table-column>
            <el-table-column label="代扣合计" width="110" align="right"><template #default="scope">{{ (Number(scope.row.socialInsurance || 0) + Number(scope.row.housingFund || 0) + Number(scope.row.incomeTax || 0)).toFixed(2) }}</template></el-table-column>
            <el-table-column label="实发金额" prop="actualSalary" width="110" align="right"><template #default="scope"><span style="font-weight:bold;color:#67C23A">{{ scope.row.actualSalary }}</span></template></el-table-column>
            <el-table-column label="状态" prop="issueStatus" width="80" align="center">
              <template #default="scope"><el-tag :type="scope.row.issueStatus === '1' ? 'success' : 'warning'" size="small">{{ scope.row.issueStatus === '1' ? '已发放' : '未发放' }}</el-tag></template>
            </el-table-column>
          </el-table>
          <pagination v-show="salaryTotal > 0" :total="salaryTotal" v-model:page="salaryQuery.pageNum" v-model:limit="salaryQuery.pageSize" @pagination="loadSalaryList" />
        </el-card>
      </el-tab-pane>

      <el-tab-pane label="个人视图" name="personal">
        <el-form :inline="true" style="margin-bottom: 15px">
          <el-form-item label="教师ID"><el-input v-model="personalTeacherId" placeholder="输入教师ID" style="width:160px" /></el-form-item>
          <el-form-item><el-button type="primary" icon="Search" @click="loadPersonalData">查询</el-button></el-form-item>
        </el-form>
        <el-row :gutter="20" v-if="personalList.length > 0">
          <el-col :span="8" v-for="(item, idx) in personalList.slice(0, 3)" :key="idx">
            <el-card shadow="hover">
              <div style="text-align:center">
                <div style="color:#999;font-size:14px">{{ item.salaryYear }}年{{ item.salaryMonth }}月</div>
                <div style="font-size:28px;font-weight:bold;color:#409EFF;margin:10px 0">¥{{ Number(item.actualSalary).toLocaleString() }}</div>
                <div style="font-size:12px;color:#999">应发: ¥{{ item.grossSalary }} | 代扣: ¥{{ (Number(item.socialInsurance || 0) + Number(item.housingFund || 0) + Number(item.incomeTax || 0)).toFixed(2) }}</div>
              </div>
            </el-card>
          </el-col>
        </el-row>
        <el-card shadow="hover" style="margin-top:20px">
          <template #header><span>近12月薪资趋势</span></template>
          <div ref="personalChartRef" style="height: 350px"></div>
        </el-card>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script setup name="TeacherSalary">
import * as echarts from 'echarts'
import { listSalary, getSalaryKpi, getSalaryByDept, getPersonalSalary } from '@/api/teacher/salary'

const activeTab = ref('admin')
const salaryYear = ref(new Date().getFullYear().toString())
const kpi = ref({})
const deptData = ref([])
const salaryList = ref([])
const salaryLoading = ref(false)
const salaryTotal = ref(0)
const salaryQuery = reactive({ pageNum: 1, pageSize: 10, teacherNo: '', teacherName: '', salaryYear: '' })
const personalTeacherId = ref('')
const personalList = ref([])

const deptChartRef = ref(null)
const pieChartRef = ref(null)
const personalChartRef = ref(null)

function loadAdminData() {
  const year = salaryYear.value || undefined
  getSalaryKpi(year).then(res => { kpi.value = res.data || {} })
  getSalaryByDept(year).then(res => { deptData.value = res.data || []; renderDeptChart() })
  loadSalaryList()
}

function loadSalaryList() {
  salaryLoading.value = true
  const params = { ...salaryQuery, salaryYear: salaryQuery.salaryYear || salaryYear.value }
  listSalary(params).then(res => { salaryList.value = res.rows || []; salaryTotal.value = res.total || 0; salaryLoading.value = false; renderPieChart() })
}

function loadPersonalData() {
  if (!personalTeacherId.value) return
  getPersonalSalary(personalTeacherId.value).then(res => {
    personalList.value = res.data || []
    renderPersonalChart()
  })
}

function renderDeptChart() {
  if (!deptChartRef.value) return
  const chart = echarts.init(deptChartRef.value)
  const depts = deptData.value.map(d => d.deptName || '未知')
  chart.setOption({
    tooltip: { trigger: 'axis' },
    xAxis: { type: 'category', data: depts, axisLabel: { rotate: 30 } },
    yAxis: { type: 'value', name: '金额(元)' },
    series: [
      { name: '薪资总额', type: 'bar', data: deptData.value.map(d => Number(d.totalSalary || 0)), itemStyle: { color: '#409EFF' } },
      { name: '人均薪资', type: 'bar', data: deptData.value.map(d => Number(d.avgSalary || 0).toFixed(2)), itemStyle: { color: '#67C23A' } }
    ],
    legend: { data: ['薪资总额', '人均薪资'] },
    grid: { bottom: 80 }
  })
}

function renderPieChart() {
  if (!pieChartRef.value || salaryList.value.length === 0) return
  const chart = echarts.init(pieChartRef.value)
  let postTotal = 0, perfTotal = 0, allowTotal = 0, otherTotal = 0
  salaryList.value.forEach(r => {
    postTotal += Number(r.postSalary || 0) + Number(r.scaleSalary || 0)
    perfTotal += Number(r.basePerformance || 0) + Number(r.rewardPerformance || 0)
    allowTotal += Number(r.talentAllowance || 0) + Number(r.housingAllowance || 0)
    otherTotal += Number(r.otherAllowance || 0)
  })
  chart.setOption({
    tooltip: { trigger: 'item', formatter: '{b}: ¥{c} ({d}%)' },
    legend: { bottom: 10 },
    series: [{
      type: 'pie', radius: ['40%', '70%'],
      data: [
        { value: postTotal.toFixed(2), name: '基本工资' },
        { value: perfTotal.toFixed(2), name: '绩效工资' },
        { value: allowTotal.toFixed(2), name: '津贴补贴' },
        { value: otherTotal.toFixed(2), name: '其他' }
      ]
    }]
  })
}

function renderPersonalChart() {
  if (!personalChartRef.value) return
  const chart = echarts.init(personalChartRef.value)
  const sorted = [...personalList.value].sort((a, b) => (a.salaryYear * 100 + a.salaryMonth) - (b.salaryYear * 100 + b.salaryMonth)).slice(-12)
  chart.setOption({
    tooltip: { trigger: 'axis' },
    xAxis: { type: 'category', data: sorted.map(r => `${r.salaryYear}-${String(r.salaryMonth).padStart(2, '0')}`) },
    yAxis: { type: 'value', name: '金额(元)' },
    series: [
      { name: '应发', type: 'line', data: sorted.map(r => Number(r.grossSalary || 0)), smooth: true },
      { name: '实发', type: 'line', data: sorted.map(r => Number(r.actualSalary || 0)), smooth: true, areaStyle: { opacity: 0.3 } }
    ],
    legend: { data: ['应发', '实发'] }
  })
}

function handleTabChange(tab) {
  if (tab === 'admin') { nextTick(() => { loadAdminData() }) }
}

onMounted(() => { loadAdminData() })
onUnmounted(() => { /* charts disposed automatically */ })
</script>

<style scoped>
.kpi-card { text-align: center; }
.kpi-value { font-size: 28px; font-weight: bold; color: #409EFF; margin: 10px 0; }
.kpi-sub { font-size: 13px; color: #999; }
</style>
