import request from '@/utils/request'

export default {
    add: function (idGoods) {
        return request({
            url: '/user/favorite/add/' + idGoods,
            method: 'POST'
        })
    },
    dislike: function (idGoods) {
        return request({
            url: '/user/favorite/dislike/' + idGoods,
            method: 'POST'
        })
    },
    dislikeBatch: function (params) {
        return request({
            url: '/user/favorite/dislikeBatch' ,
            method: 'POST',
            data:params
        })
    },
    ifLike: function (idGoods) {
        return request({
            url: '/user/favorite/ifLike/' + idGoods,
            method: 'GET'
        })
    },
    list: function () {
        return request({
            url: '/user/favorite/list',
            method: 'GET'
        })
    },
}
