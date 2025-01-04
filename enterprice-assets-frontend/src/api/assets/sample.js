import request from '@/utils/request'

// 查询样品库存管理列表
export function listSample(query) {
  return request({
    url: '/assets/sample/list',
    method: 'get',
    params: query
  })
}

// 查询样品库存管理详细
export function getSample(id) {
  return request({
    url: '/assets/sample/' + id,
    method: 'get'
  })
}

// 新增样品库存管理
export function addSample(data) {
  return request({
    url: '/assets/sample',
    method: 'post',
    data: data
  })
}

// 修改样品库存管理
export function updateSample(data) {
  return request({
    url: '/assets/sample',
    method: 'put',
    data: data
  })
}

// 删除样品库存管理
export function delSample(id) {
  return request({
    url: '/assets/sample/' + id,
    method: 'delete'
  })
}
