import Vue from 'vue';
import Vuex from 'vuex';

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    items: [], // 장소 정보를 저장
    result: [], // 검색 정보를 저장
    keyword: '', // 검색 키워드
  },
  getters: {
    items(state) {
      return state.items;
    },
    result(state) {
      return state.result;
    },
    keyword(state) {
      return state.keyword;
    },
  },
  mutations: {
    setItems(state, payload) {
      state.items = payload.items;
    },
    setResult(state, payload) {
      state.result = payload.result;
    },
    setKeyword(state, payload) {
      state.keyword = payload.keyword;
    },
  },
  actions: {
    doUpdate({ commit }, items) {
      commit('setItems', { items });
    },
    updateKeyword({ commit }, keyword) {
      commit('setKeyword', { keyword });
    },
    search({ commit, getters }) {
      const places = new window.kakao.maps.services.Places();
      const callback = function (result, status) {
        if (status === window.kakao.maps.services.Status.OK) {
          console.log(result);
          commit('setResult', { result });
        } else if (status === window.kakao.maps.services.Status.ZERO_RESULT) {
          alert('검색 결과가 존재하지 않습니다.');
        } else if (status === window.kakao.maps.services.Status.ERROR) {
          alert('검색 결과 중 오류가 발생했습니다.');
        }
      };
      places.keywordSearch(getters.keyword, callback);
    },
  },
  modules: {
  },
});
