import request from '@/utils/request'

// 引进计划
export function listRecruitPlan(query) { return request({ url: '/teacher/recruit-plan/list', method: 'get', params: query }) }
export function getRecruitPlan(planId) { return request({ url: '/teacher/recruit-plan/' + planId, method: 'get' }) }
export function addRecruitPlan(data) { return request({ url: '/teacher/recruit-plan', method: 'post', data }) }
export function updateRecruitPlan(data) { return request({ url: '/teacher/recruit-plan', method: 'put', data }) }
export function delRecruitPlan(planIds) { return request({ url: '/teacher/recruit-plan/' + planIds, method: 'delete' }) }

// 应聘管理
export function listApplicant(query) { return request({ url: '/teacher/applicant/list', method: 'get', params: query }) }
export function getApplicant(applicantId) { return request({ url: '/teacher/applicant/' + applicantId, method: 'get' }) }
export function addApplicant(data) { return request({ url: '/teacher/applicant', method: 'post', data }) }
export function updateApplicant(data) { return request({ url: '/teacher/applicant', method: 'put', data }) }
export function delApplicant(applicantIds) { return request({ url: '/teacher/applicant/' + applicantIds, method: 'delete' }) }
export function approveApplicant(applicantId, targetStatus) { return request({ url: `/teacher/applicant/approve/${applicantId}/${targetStatus}`, method: 'put' }) }

// 面试管理
export function listInterview(query) { return request({ url: '/teacher/interview/list', method: 'get', params: query }) }
export function getInterview(interviewId) { return request({ url: '/teacher/interview/' + interviewId, method: 'get' }) }
export function addInterview(data) { return request({ url: '/teacher/interview', method: 'post', data }) }
export function updateInterview(data) { return request({ url: '/teacher/interview', method: 'put', data }) }
export function delInterview(interviewIds) { return request({ url: '/teacher/interview/' + interviewIds, method: 'delete' }) }
