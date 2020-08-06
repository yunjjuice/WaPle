export default {
  rootState: {
    place: {},
  },
  rootGetters: {
    place(state) {
      return state.place;
    },
  },
  mutations: {
    setPlace(state, payload) {
      state.place = payload.place;
    },
  },
  actions: {
    selectPlace({ commit }, place) {
      commit('setPlace', { place });
    },
  },
};
