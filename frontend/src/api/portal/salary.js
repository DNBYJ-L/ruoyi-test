import request from '@/utils/request'

// 查询个人薪资列表
export function getMySalaryList() {
  return request({
    url: '/portal/salary/list',
    method: 'get'
  })
}

// 查询薪资明细
export function getMySalaryDetail(recordId) {
  return request({
    url: '/portal/salary/' + recordId,
    method: 'get'
  })
}
