import request from '@/utils/request'

// 查询教师列表
export function listTeacher(query) {
  return request({
    url: '/teacher/info/list',
    method: 'get',
    params: query
  })
}

// 查询教师详细
export function getTeacher(teacherId) {
  return request({
    url: '/teacher/info/' + teacherId,
    method: 'get'
  })
}

// 新增教师
export function addTeacher(data) {
  return request({
    url: '/teacher/info',
    method: 'post',
    data: data
  })
}

// 修改教师
export function updateTeacher(data) {
  return request({
    url: '/teacher/info',
    method: 'put',
    data: data
  })
}

// 删除教师
export function delTeacher(teacherId) {
  return request({
    url: '/teacher/info/' + teacherId,
    method: 'delete'
  })
}

// 校验工号是否重复
export function checkTeacherNo(data) {
  return request({
    url: '/teacher/info/checkTeacherNo',
    method: 'get',
    params: data
  })
}

// 导出教师
export function exportTeacher(query) {
  return request({
    url: '/teacher/info/export',
    method: 'post',
    params: query,
    responseType: 'blob'
  })
}
