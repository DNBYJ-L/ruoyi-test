<template>
  <div class="app-container">
    <el-card shadow="hover">
      <template #header>
        <div class="card-header">
          <span>我的薪资记录</span>
        </div>
      </template>

      <el-table v-loading="loading" :data="salaryList" border size="small">
        <el-table-column label="年月" width="100">
          <template #default="scope">{{ scope.row.salaryYear }}-{{ String(scope.row.salaryMonth).padStart(2, '0') }}</template>
        </el-table-column>
        <el-table-column label="岗位工资" prop="postSalary" width="95" align="right" />
        <el-table-column label="薪级工资" prop="scaleSalary" width="95" align="right" />
        <el-table-column label="基础绩效" prop="basePerformance" width="95" align="right" />
        <el-table-column label="奖励绩效" prop="rewardPerformance" width="95" align="right" />
        <el-table-column label="人才津贴" prop="talentAllowance" width="95" align="right" />
        <el-table-column label="住房补贴" prop="housingAllowance" width="95" align="right" />
        <el-table-column label="其他" prop="otherAllowance" width="80" align="right" />
        <el-table-column label="税前应发" prop="grossSalary" width="105" align="right">
          <template #default="scope"><b>{{ scope.row.grossSalary }}</b></template>
        </el-table-column>
        <el-table-column label="社保" prop="socialInsurance" width="85" align="right" />
        <el-table-column label="公积金" prop="housingFund" width="85" align="right" />
        <el-table-column label="个税" prop="incomeTax" width="80" align="right" />
        <el-table-column label="实发工资" prop="actualSalary" width="110" align="right">
          <template #default="scope"><b style="color:#67C23A">{{ scope.row.actualSalary }}</b></template>
        </el-table-column>
        <el-table-column label="状态" width="80" align="center">
          <template #default="scope">
            <el-tag :type="scope.row.issueStatus === '1' ? 'success' : 'warning'" size="small">
              {{ scope.row.issueStatus === '1' ? '已发放' : '未发放' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="80" align="center" fixed="right">
          <template #default="scope">
            <el-button link type="primary" icon="View" @click="handleDetail(scope.row)">详情</el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-empty v-if="!loading && salaryList.length === 0" description="暂无薪资记录" />
    </el-card>

    <!-- 薪资明细对话框 -->
    <el-dialog title="薪资明细" v-model="detailOpen" width="600px" append-to-body>
      <el-descriptions :column="2" border v-if="currentRecord">
        <el-descriptions-item label="发放年月">{{ currentRecord.salaryYear }}年{{ currentRecord.salaryMonth }}月</el-descriptions-item>
        <el-descriptions-item label="发放状态">
          <el-tag :type="currentRecord.issueStatus === '1' ? 'success' : 'warning'" size="small">
            {{ currentRecord.issueStatus === '1' ? '已发放' : '未发放' }}
          </el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="岗位工资">{{ currentRecord.postSalary }}</el-descriptions-item>
        <el-descriptions-item label="薪级工资">{{ currentRecord.scaleSalary }}</el-descriptions-item>
        <el-descriptions-item label="基础绩效">{{ currentRecord.basePerformance }}</el-descriptions-item>
        <el-descriptions-item label="奖励绩效">{{ currentRecord.rewardPerformance }}</el-descriptions-item>
        <el-descriptions-item label="人才津贴">{{ currentRecord.talentAllowance }}</el-descriptions-item>
        <el-descriptions-item label="住房补贴">{{ currentRecord.housingAllowance }}</el-descriptions-item>
        <el-descriptions-item label="其他津贴">{{ currentRecord.otherAllowance }}</el-descriptions-item>
        <el-descriptions-item label="税前应发"><b>{{ currentRecord.grossSalary }}</b></el-descriptions-item>
        <el-descriptions-item label="代扣社保">{{ currentRecord.socialInsurance }}</el-descriptions-item>
        <el-descriptions-item label="代扣公积金">{{ currentRecord.housingFund }}</el-descriptions-item>
        <el-descriptions-item label="代扣个税">{{ currentRecord.incomeTax }}</el-descriptions-item>
        <el-descriptions-item label="实发工资"><b style="color:#67C23A;font-size:16px">{{ currentRecord.actualSalary }}</b></el-descriptions-item>
      </el-descriptions>
    </el-dialog>
  </div>
</template>

<script setup name="PortalSalary">
import { getMySalaryList } from '@/api/portal/salary'

const salaryList = ref([])
const loading = ref(true)
const detailOpen = ref(false)
const currentRecord = ref(null)

function getList() {
  loading.value = true
  getMySalaryList().then(res => {
    salaryList.value = res.data || []
    loading.value = false
  }).catch(() => { loading.value = false })
}

function handleDetail(row) {
  currentRecord.value = row
  detailOpen.value = true
}

getList()
</script>

<style scoped>
.card-header { display: flex; justify-content: space-between; align-items: center; }
</style>
