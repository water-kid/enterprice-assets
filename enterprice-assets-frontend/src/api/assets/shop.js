import request from '@/utils/request'

// 查询店铺详情统计列表
export function listShop(query) {
  return request({
    url: '/assets/shop/list',
    method: 'get',
    params: query
  })
}

// 查询店铺详情统计详细
export function getShop(id) {
  return request({
    url: '/assets/shop/' + id,
    method: 'get'
  })
}

// 新增店铺详情统计
export function addShop(data) {
  return request({
    url: '/assets/shop',
    method: 'post',
    data: data
  })
}

// 修改店铺详情统计
export function updateShop(data) {
  return request({
    url: '/assets/shop',
    method: 'put',
    data: data
  })
}

// 删除店铺详情统计
export function delShop(id) {
  return request({
    url: '/assets/shop/' + id,
    method: 'delete'
  })
}
