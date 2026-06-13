import request from '@/utils/request'

// 考核方案
export function listAssessPlan(query) { return request({ url: '/teacher/assessment-plan/list', method: 'get', params: query }) }
export function getAssessPlan(planId) { return request({ url: '/teacher/assessment-plan/' + planId, method: 'get' }) }
export function addAssessPlan(data) { return request({ url: '/teacher/assessment-plan', method: 'post', data }) }
export function updateAssessPlan(data) { return request({ url: '/teacher/assessment-plan', method: 'put', data }) }
export function delAssessPlan(planIds) { return request({ url: '/teacher/assessment-plan/' + planIds, method: 'delete' }) }

// 考核记录
export function listAssessRecord(query) { return request({ url: '/teacher/assessment-record/list', method: 'get', params: query }) }
export function getAssessRecord(recordId) { return request({ url: '/teacher/assessment-record/' + recordId, method: 'get' }) }
export function addAssessRecord(data) { return request({ url: '/teacher/assessment-record', method: 'post', data }) }
export function updateAssessRecord(data) { return request({ url: '/teacher/assessment-record', method: 'put', data }) }
export function delAssessRecord(recordIds) { return request({ url: '/teacher/assessment-record/' + recordIds, method: 'delete' }) }
export function submitAssessRecord(recordId) { return request({ url: '/teacher/assessment-record/submit/' + recordId, method: 'post' }) }
export function getScoreStat(planId) { return request({ url: '/teacher/assessment-record/score-stat', method: 'get', params: { planId } }) }

// 审核管理
export function getAuditTrail(recordId) { return request({ url: '/teacher/assessment-audit/trail/' + recordId, method: 'get' }) }
export function doAudit(recordId, params) { return request({ url: '/teacher/assessment-audit/do-audit/' + recordId, method: 'put', params }) }
