export default {
  state: {
    items: [], // 장소 정보를 저장
    item: {}, // 선택된 장소 정보를 저장
    result: [], // 검색 정보를 저장
    keyword: '', // 검색 키워드
    page: 1,
  },
  getters: {
    items(state) {
      return state.items;
    },
    item(state) {
      return state.item;
    },
    result(state) {
      return state.result;
    },
    keyword(state) {
      return state.keyword;
    },
    page(state) {
      return state.page;
    },
  },
  mutations: {
    setItems(state, payload) {
      state.items = payload.items;
    },
    setItem(state, payload) {
      state.item = payload.item;
    },
    setResult(state, payload) {
      state.result = state.result.concat(payload.result);
    },
    setKeyword(state, payload) {
      state.keyword = payload.keyword;
    },
    setPage(state, payload) {
      state.page = payload.page;
    },
    initResult(state, payload) {
      state.result = payload.result;
    },
  },
  actions: {
    doUpdate({ commit }, items) {
      commit('setItems', { items });
    },
    updateItem({ commit }, item) {
      commit('setItem', { item });
    },
    updateKeyword({ commit }, keyword) {
      commit('setKeyword', { keyword });
    },
    updatePage({ commit }, page) {
      commit('setPage', { page });
    },
    initResult({ commit }, result) {
      commit('initResult', { result });
    },
    search({ commit, getters }) {
      const places = new window.kakao.maps.services.Places();
      const callback = function (result, status) {
        if (status === window.kakao.maps.services.Status.OK) {
          commit('setResult', { result });
        } else if (status === window.kakao.maps.services.Status.ZERO_RESULT) {
          alert('검색 결과가 존재하지 않습니다.');
        } else if (status === window.kakao.maps.services.Status.ERROR) {
          alert('검색 결과 중 오류가 발생했습니다.');
        }
      };
      places.keywordSearch(getters.keyword, callback, {
        size: 10,
        page: getters.page,
      });
    },
  },
};
