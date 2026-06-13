import request from '@/utils/request'

// 培训计划
export function listTrainingPlan(query) { return request({ url: '/teacher/training-plan/list', method: 'get', params: query }) }
export function getTrainingPlan(planId) { return request({ url: '/teacher/training-plan/' + planId, method: 'get' }) }
export function addTrainingPlan(data) { return request({ url: '/teacher/training-plan', method: 'post', data }) }
export function updateTrainingPlan(data) { return request({ url: '/teacher/training-plan', method: 'put', data }) }
export function delTrainingPlan(planIds) { return request({ url: '/teacher/training-plan/' + planIds, method: 'delete' }) }

// 报名管理
export function listEnrollment(query) { return request({ url: '/teacher/training-enrollment/list', method: 'get', params: query }) }
export function getEnrollment(enrollmentId) { return request({ url: '/teacher/training-enrollment/' + enrollmentId, method: 'get' }) }
export function addEnrollment(data) { return request({ url: '/teacher/training-enrollment', method: 'post', data }) }
export function approveEnrollment(enrollmentId, approveStatus, approveRemark) { return request({ url: `/teacher/training-enrollment/approve/${enrollmentId}/${approveStatus}`, method: 'put', params: { approveRemark } }) }

// 结业管理
export function listTrainingResult(query) { return request({ url: '/teacher/training-result/list', method: 'get', params: query }) }
export function getTrainingResult(resultId) { return request({ url: '/teacher/training-result/' + resultId, method: 'get' }) }
export function addTrainingResult(data) { return request({ url: '/teacher/training-result', method: 'post', data }) }
export function updateTrainingResult(data) { return request({ url: '/teacher/training-result', method: 'put', data }) }
export function getHoursStat(teacherId) { return request({ url: '/teacher/training-result/hours-stat', method: 'get', params: { teacherId } }) }
