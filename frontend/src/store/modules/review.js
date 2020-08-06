export default {
  state: {
    readDialog: false, // review 읽기 창
    writeDialog: false, // review 쓰기 창
  },
  getters: {
    readDialog(state) {
      return state.readDialog;
    },
    writeDialog(state) {
      return state.writeDialog;
    },
  },
  mutations: {
    trueReadDialog(state) {
      state.readDialog = true;
    },
    falseReadDialog(state) {
      state.readDialog = false;
    },
    trueWriteDialog(state) {
      state.writeDialog = true;
    },
    falseWriteDialog(state) {
      state.writeDialog = false;
    },
  },
  actions: {
    showReadDialog({ commit }) {
      commit('trueReadDialog');
    },
    closeReadDialog({ commit }) {
      commit('falseReadDialog');
    },
    showWriteDialog({ commit }) {
      commit('trueWriteDialog');
    },
    closeWriteDialog({ commit }) {
      commit('falseWriteDialog');
    },
  },
};
