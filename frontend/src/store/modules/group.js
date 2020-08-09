import Vue from 'vue';
import api from '@/utils/api';

export default {
  state: {
    leaveGroupDialog: false,
    selectedGroupId: '',
  },
  getters: {
    leaveGroupDialog: (state) => state.leaveGroupDialog,
    selectedGroupId: (state) => state.selectedGroupId,
  },
  mutations: {
    openLeaveGroupDialog(state, payload) {
      state.leaveGroupDialog = true;
      state.selectedGroupId = payload;
    },
    closeLeaveGroupDialog(state) {
      state.leaveGroupDialog = false;
    },
  },
  actions: {
    leaveGroup({ getters, commit, dispatch }) {
      api.delete(`groups/${getters.selectedGroupId}/${Vue.prototype.$session.get('uid')}`)
        .then(() => {
          dispatch('getGroups');
          commit('closeLeaveGroupDialog');
        })
        .catch((err) => console.log(err));
    },
  },
};
