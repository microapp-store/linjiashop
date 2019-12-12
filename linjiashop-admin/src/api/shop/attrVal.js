import request from '@/utils/request'

export function getAttrBy(idCategory) {
    return request({
        url: '/shop/attr/val/getAttrByCategoryAndGoods/'+idCategory,
        method: 'get'
    })
}
