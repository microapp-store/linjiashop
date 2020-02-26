import Cookies from 'js-cookie'
import storage from '@/utils/storage'

const state = {
    device: 'desktop',
    language: Cookies.get('language') || 'zh',
    size: Cookies.get('size') || 'medium',
    category:[],
    user:{},
    token:''
}


const mutations = {
    TOGGLE_DEVICE: (state, device) => {
        state.device = device
    },
    TOGGLE_CATEGORY: (state, category) => {
        state.category = category
    },
    TOGGLE_USER: (state, user) => {
        state.user = user
        storage.setUser(user)

    },
    TOGGLE_TOKEN: (state, token) => {
        state.token = token
        storage.setToken(token)

    }
}


const actions = {
    toggleDevice({ commit }, device) {
        commit('TOGGLE_DEVICE', device)
    },
    toggleCategory({ commit }, category) {
        commit('TOGGLE_CATEGORY', category)
    },
    toggleUser({ commit }, user) {
        user.genderStr = user.gender?('male' === user.gender?'男':'女'):'请选择'
        commit('TOGGLE_USER', user)
    },
    toggleToken({ commit }, token) {
        commit('TOGGLE_TOKEN', token)
    },
}

export default {
    namespaced: true,
    state,
    mutations,
    actions
}
