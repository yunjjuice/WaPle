export default {
  state: {
    height: '',
    width: '',
  },
  getters: {
    height(state) {
      return state.height;
    },
    width(state) {
      return state.width;
    },
  },
  mutations: {
    setHeight(state, payload) {
      state.height = payload.height;
    },
    setWidth(state, payload) {
      state.width = payload.width;
    },
  },
  actions: {
    updateHeight({ commit }, height) {
      commit('setHeight', { height });
      console.log(height);
    },
    updateWidth({ commit }, width) {
      commit('setWidth', { width });
      console.log(width);
    },
  },
};
