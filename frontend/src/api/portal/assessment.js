import request from '@/utils/request'

// 查看当前考核方案列表
export function getAssessmentPlans(query) {
  return request({
    url: '/portal/assessment/plans',
    method: 'get',
    params: query
  })
}

// 我的考核记录列表
export function getMyRecords(query) {
  return request({
    url: '/portal/assessment/records',
    method: 'get',
    params: query
  })
}

// 考核记录详情（含审核轨迹）
export function getRecordDetail(recordId) {
  return request({
    url: '/portal/assessment/record/' + recordId,
    method: 'get'
  })
}

// 新增考核填报
export function fillAssessment(data) {
  return request({
    url: '/portal/assessment/record',
    method: 'post',
    data: data
  })
}

// 修改考核填报
export function updateAssessment(data) {
  return request({
    url: '/portal/assessment/record',
    method: 'put',
    data: data
  })
}

// 提交考核审核
export function submitAssessment(recordId) {
  return request({
    url: '/portal/assessment/submit/' + recordId,
    method: 'post'
  })
}
