import request from '@/utils/request'

// 获取当前教师个人信息
export function getMyInfo() {
  return request({
    url: '/portal/info',
    method: 'get'
  })
}

// 修改当前教师个人信息
export function updateMyInfo(data) {
  return request({
    url: '/portal/info',
    method: 'put',
    data: data
  })
}

// 校验工号是否可用
export function checkMyTeacherNo(data) {
  return request({
    url: '/portal/info/checkTeacherNo',
    method: 'get',
    params: data
  })
}
