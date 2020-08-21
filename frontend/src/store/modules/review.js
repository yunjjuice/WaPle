import Vue from 'vue';
import api from '@/utils/api';

const path = 'http://i3a204.p.ssafy.io/img/';

export default {
  state: {
    readDialog: false, // review 읽기 창
    writeDialog: false, // review 쓰기 창
    review: {}, // 선택된 리뷰
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
          images[index] = path + images[index];
        }
        state.review.images = images;
      }
    },
    setUpdatedReview(state, { title, content }) {
      state.review.title = title;
      state.review.content = content;
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
    removeReview({ getters }) {
      api.delete(`/reviews/${getters.review.reviewId}`, {
        headers: {
          token: Vue.prototype.$session.get('token'),
        },
      }).then(() => {
        Vue.$toast.success('리뷰 삭제 완료');
        // TODO 리뷰 목록 refresh
      })
        .catch((err) => {
          console.error(err);
          Vue.$toast.error('리뷰 삭제 실패, 다시 시도해주세요.');
        });
    },
    updateReview({ commit }, review) { // 리뷰 수정
      api.put('/reviews', review, {
        headers: {
          token: Vue.prototype.$session.get('token'),
        },
      }).then(() => {
        // 다시 안 불러오고 야매로 선택된 데이터 수정했습니다
        commit('setUpdatedReview', { title: review.title, content: review.content });
        Vue.$toast.success('리뷰 수정 완료');
      }).catch((err) => {
        console.error(err);
        Vue.$toast.error('리뷰 수정 실패, 다시 시도해주세요.');
      });
    },
  },
};
