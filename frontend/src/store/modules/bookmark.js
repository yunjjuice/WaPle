export default {
  state: {
    bookmark: false,
  },
  getters: {
    bookmark(state) {
      return state.bookmark;
    },
  },
  mutations: {
    trueBookmark(state) {
      state.bookmark = true;
    },
    falseBookmark(state) {
      state.bookmark = false;
    },
  },
  actions: {
    visibleBookmark({ commit }) {
      commit('trueBookmark');
    },
    invisibleBookmark({ commit }) {
      commit('falseBookmark');
    },
  },
};
