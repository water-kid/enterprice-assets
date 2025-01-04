import request from '@/utils/request'

// 查询固定资产盘点明细列表
export function listAssets(query) {
  return request({
    url: '/assets/assets/list',
    method: 'get',
    params: query
  })
}

// 查询固定资产盘点明细详细
export function getAssets(id) {
  return request({
    url: '/assets/assets/' + id,
    method: 'get'
  })
}

// 新增固定资产盘点明细
export function addAssets(data) {
  return request({
    url: '/assets/assets',
    method: 'post',
    data: data
  })
}

// 修改固定资产盘点明细
export function updateAssets(data) {
  return request({
    url: '/assets/assets',
    method: 'put',
    data: data
  })
}

// 删除固定资产盘点明细
export function delAssets(id) {
  return request({
    url: '/assets/assets/' + id,
    method: 'delete'
  })
}



export function borrowAssets(data) {
  return request({
    url: '/assets/assets/borrow',
    method: 'post',
    data: data
  })
}

export function consumeAssets(data) {
  return request({
    url: '/assets/assets/consume',
    method: 'post',
    data: data
  })
}


export function returnAssets(id) {
  return request({
    url: '/assets/assets/back/'+id,
    method: 'post'
  })
}


export function getTop5Assets() {
  return request({
    url: '/assets/assets/statistic',
    method: 'get'
  })
}

