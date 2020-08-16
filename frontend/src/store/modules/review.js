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
    setReadDialog(state) {
      state.readDialog = !state.readDialog;
    },
    setWriteDialog(state) {
      state.writeDialog = !state.writeDialog;
    },
    setReview(state, payload) {
      state.review = payload;
      if (payload.images !== '') {
      // Parse image paths
        const images = payload.media.split(';');
        images.pop(); // Last value is empty string
        for (let index = 0; index < images.length; index += 1) {
          images[index] = state.path + images[index];
        }
        state.review.images = images;
      }
    },
  },
  actions: {
    changeReadDialog({ commit }) {
      commit('setReadDialog');
    },
    changeWriteDialog({ commit }) {
      commit('setWriteDialog');
    },
    selectReview({ commit }, review) {
      commit('setReview', review);
    },
  },
};
