export default {
  state: {
    readDialog: false, // review 읽기 창
    writeDialog: false, // review 쓰기 창
    review: {}, // 선택된 리뷰
    path: 'http://i3a204.p.ssafy.io/img/',
  },
  getters: {
    readDialog(state) {
      return state.readDialog;
    },
    writeDialog(state) {
      return state.writeDialog;
    },
    review(state) {
      return state.review;
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
    setReview(state, payload) {
      // Parse image paths
      const images = payload.media.split(';');
      images.pop(); // Last value is empty string
      for (let index = 0; index < images.length; index += 1) {
        images[index] = state.path + images[index];
      }
      state.review = payload;
      state.review.images = images;
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
    selectReview({ commit }, review) {
      commit('setReview', review);
    },
  },
};
