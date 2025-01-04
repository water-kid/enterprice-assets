import request from '@/utils/request'

// 查询固定资产盘点明细列表
export function generateCodeImg(code) {
    return request({
        url: '/assets/generate-qrcode?code='+code,
        method: 'get',
        responseType:"blob"
    })
}
