import request from '@/utils/request'

export function getAttrBy(idCategory,idGoods) {
    return request({
        url: '/shop/attr/val/getAttrByCategoryAndGoods/'+idCategory+'/'+idGoods,
        method: 'get'
    })
}
