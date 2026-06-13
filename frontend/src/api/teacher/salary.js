import request from '@/utils/request'

// 查询薪资记录列表
export function listSalary(query) {
  return request({
    url: '/teacher/salary/list',
    method: 'get',
    params: query
  })
}

// 查询薪资记录详情
export function getSalary(recordId) {
  return request({
    url: '/teacher/salary/' + recordId,
    method: 'get'
  })
}

// 获取教师个人薪资记录
export function getPersonalSalary(teacherId) {
  return request({
    url: '/teacher/salary/personal/' + teacherId,
    method: 'get'
  })
}

// 新增薪资记录
export function addSalary(data) {
  return request({
    url: '/teacher/salary',
    method: 'post',
    data: data
  })
}

// 修改薪资记录
export function updateSalary(data) {
  return request({
    url: '/teacher/salary',
    method: 'put',
    data: data
  })
}

// 删除薪资记录
export function delSalary(recordId) {
  return request({
    url: '/teacher/salary/' + recordId,
    method: 'delete'
  })
}

// 获取全校薪资KPI
export function getSalaryKpi(salaryYear) {
  return request({
    url: '/teacher/salary/kpi',
    method: 'get',
    params: { salaryYear }
  })
}

// 获取院系薪资对比
export function getSalaryByDept(salaryYear) {
  return request({
    url: '/teacher/salary/dept-stat',
    method: 'get',
    params: { salaryYear }
  })
}

// 导出薪资记录
export function exportSalary(query) {
  return request({
    url: '/teacher/salary/export',
    method: 'post',
    params: query,
    responseType: 'blob'
  })
}

// 批量生成薪资记录
export function batchGenerateSalary(salaryYear, salaryMonth) {
  return request({
    url: '/teacher/salary/batch-generate',
    method: 'post',
    params: { salaryYear, salaryMonth }
  })
}

// 查询薪资配置列表
export function listSalaryConfig(query) {
  return request({
    url: '/teacher/salary-config/list',
    method: 'get',
    params: query
  })
}

// 查询薪资配置详情
export function getSalaryConfig(configId) {
  return request({
    url: '/teacher/salary-config/' + configId,
    method: 'get'
  })
}

// 新增薪资配置
export function addSalaryConfig(data) {
  return request({
    url: '/teacher/salary-config',
    method: 'post',
    data: data
  })
}

// 修改薪资配置
export function updateSalaryConfig(data) {
  return request({
    url: '/teacher/salary-config',
    method: 'put',
    data: data
  })
}

// 删除薪资配置
export function delSalaryConfig(configId) {
  return request({
    url: '/teacher/salary-config/' + configId,
    method: 'delete'
  })
}
