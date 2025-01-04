import request from '@/utils/request'

// 查询手机号管理与使用列表
export function listPhone(query) {
  return request({
    url: '/assets/phone/list',
    method: 'get',
    params: query
  })
}

// 查询手机号管理与使用详细
export function getPhone(id) {
  return request({
    url: '/assets/phone/' + id,
    method: 'get'
  })
}

// 新增手机号管理与使用
export function addPhone(data) {
  return request({
    url: '/assets/phone',
    method: 'post',
    data: data
  })
}

// 修改手机号管理与使用
export function updatePhone(data) {
  return request({
    url: '/assets/phone',
    method: 'put',
    data: data
  })
}

// 删除手机号管理与使用
export function delPhone(id) {
  return request({
    url: '/assets/phone/' + id,
    method: 'delete'
  })
}
