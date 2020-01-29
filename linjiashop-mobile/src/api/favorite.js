import request from '@/utils/request'

export default {
    add: function (idGoods) {
        return request({
            url: '/user/favorite/add/' + idGoods,
            method: 'POST'
        })
    },
    ifLike: function (idGoods) {
        return request({
            url: '/user/favorite/ifLike/' + idGoods,
            method: 'get'
        })
    }
}
