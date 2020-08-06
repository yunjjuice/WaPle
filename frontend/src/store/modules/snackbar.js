export default {
  state: {
    snackbar: false,
    color: '',
    timeout: 3000,
    msg: '',
  },
  getters: {
    snackbar(state) {
      return state.snackbar;
    },
    color(state) {
      return state.color;
    },
    timeout(state) {
      return state.timeout;
    },
    msg(state) {
      return state.msg;
    },
  },
  mutations: {
    trueSnackbar(state) {
      state.snackbar = true;
    },
    falseSnackbar(state) {
      state.snackbar = false;
    },
    setColor(state, payload) {
      state.color = payload.color;
    },
    setMsg(state, payload) {
      state.msg = payload.msg;
    },
    setSnackbar(state, payload) {
      state.color = payload.color;
      state.msg = payload.msg;
    },
  },
  actions: {
    showSnackbar({ commit }, payload) {
      commit('trueSnackbar');
      commit('setSnackbar', payload);
      setTimeout(() => {
        commit('falseSnackbar');
      }, 3000);
    },
  },
};
