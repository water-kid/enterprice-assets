import request from '@/utils/request'

// 查询外借信息列表
export function listLoans(query) {
    return request({
        url: '/assets/loans/list',
        method: 'get',
        params: query
    })
}

// 查询外借信息详细
export function getLoans(id) {
    return request({
        url: '/assets/loans/' + id,
        method: 'get'
    })
}

// 新增外借信息
export function addLoans(data) {
    return request({
        url: '/assets/loans',
        method: 'post',
        data: data
    })
}

// 修改外借信息
export function updateLoans(data) {
    return request({
        url: '/assets/loans',
        method: 'put',
        data: data
    })
}

// 删除外借信息
export function delLoans(id) {
    return request({
        url: '/assets/loans/' + id,
        method: 'delete'
    })
}
