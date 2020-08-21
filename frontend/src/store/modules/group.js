import Vue from 'vue';
import api from '@/utils/api';

export default {
  state: {
    leaveGroupDialog: false,
    selectedGroupId: '',
    selectedGroup: '',
  },
  getters: {
    leaveGroupDialog: (state) => state.leaveGroupDialog,
    selectedGroupId: (state) => state.selectedGroupId,
    selectedGroup: (state) => state.selectedGroup,
  },
  mutations: {
    openLeaveGroupDialog(state, payload) {
      state.leaveGroupDialog = true;
      state.selectedGroupId = payload.groupId;
      state.selectedGroup = payload;
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
          Vue.$toast.success('그룹 탈퇴 성공');
        }).catch((err) => {
          console.error(err);
          Vue.$toast.error('그룹 탈퇴 실패, 다시 시도해주세요.');
        });
    },
  },
};
