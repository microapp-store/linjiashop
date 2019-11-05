import Cookies from 'js-cookie'

const state = {
    device: 'desktop',
    language: Cookies.get('language') || 'zh',
    size: Cookies.get('size') || 'medium',
    category:[]
}


const mutations = {
    TOGGLE_DEVICE: (state, device) => {
        state.device = device
    },
    TOGGLE_CATEGORY: (state, category) => {
        console.log(2,category)
        state.category = category
    }
}


const actions = {
    toggleDevice({ commit }, device) {
        commit('TOGGLE_DEVICE', device)
    },
    toggleCategory({ commit }, category) {
        console.log(1,category)
        commit('TOGGLE_CATEGORY', category)
    }
}

export default {
    namespaced: true,
    state,
    mutations,
    actions
}
