import request from '@/utils/request'

// 查看可报名的培训计划
export function getAvailablePlans(query) {
  return request({
    url: '/portal/training/plans',
    method: 'get',
    params: query
  })
}

// 自主报名培训
export function enrollTraining(data) {
  return request({
    url: '/portal/training/enroll',
    method: 'post',
    data: data
  })
}

// 我的报名记录
export function getMyEnrollments(query) {
  return request({
    url: '/portal/training/enrollments',
    method: 'get',
    params: query
  })
}

// 我的结业成绩
export function getMyResults(query) {
  return request({
    url: '/portal/training/results',
    method: 'get',
    params: query
  })
}

// 我的学时统计
export function getMyHours() {
  return request({
    url: '/portal/training/hours',
    method: 'get'
  })
}
