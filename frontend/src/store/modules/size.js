export default {
  state: {
    height: '',
    width: '',
    isSafari: false,
  },
  getters: {
    height(state) {
      return state.height;
    },
    width(state) {
      return state.width;
    },
    isSafari(state) {
      return state.isSafari;
    },
  },
  mutations: {
    setHeight(state, payload) {
      state.height = payload.height;
    },
    setWidth(state, payload) {
      state.width = payload.width;
    },
    setSafari(state, payload) {
      state.isSafari = payload.isSafari;
    },
  },
  actions: {
    updateHeight({ commit }, height) {
      commit('setHeight', { height });
    },
    updateWidth({ commit }, width) {
      commit('setWidth', { width });
    },
    updateSafari({ commit }, isSafari) {
      commit('setSafari', { isSafari });
    },
  },
};
