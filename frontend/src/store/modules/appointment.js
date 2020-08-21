import Vue from 'vue';
import api from '@/utils/api';

export default {
  state: {
    appointmentName: '', // 새로 만들 약속명
    groups: [], // 사용자의 그룹 목록
    group: {}, // 선택된 그룹
    appointmentDate: '', // 약속 시간
    appointments: [], // 기존에 만들어진 약속 리스트
    appointment: {}, // 선택된 그룹
    groupsThemes: [], // 유저가 속한 모든 그룹과 그 그룹에 속한 테마
    boookmark: {},
    groupTheme: [],
  },
  getters: {
    appointmentName(state) {
      return state.appointmentName;
    },
    groups(state) {
      return state.groups;
    },
    group(state) {
      return state.group;
    },
    appointmentDate(state) {
      return state.appointmentDate;
    },
    appointments(state) {
      return state.appointments;
    },
    appointment(state) {
      return state.appointment;
    },
    place(state, getters, rootState) {
      return rootState.place;
    },
    groupsThemes(state) {
      return state.groupsThemes;
    },
    boookmark(state) {
      return state.boookmark;
    },
    groupTheme(state) {
      return state.groupTheme;
    },
  },
  mutations: {
    setAppointmentName(state, payload) {
      state.appointmentName = payload.appointmentName;
    },
    setGroups(state, payload) {
      state.groups = payload.data;
    },
    setGroup(state, payload) {
      state.group = payload;
    },
    setAppointmentDate(state, payload) {
      state.appointmentDate = payload.appointmentDate;
    },
    setAppointments(state, payload) {
      state.appointments = payload.data;
    },
    setAppointment(state, payload) {
      state.appointment = payload.appointment;
    },
    setGroupsThemes(state, payload) {
      state.groupsThemes = payload.data;
    },
    setBoookmark(state, payload) {
      state.boookmark = payload;
    },
    setGroupTheme(state, payload) {
      state.groupTheme = payload.data;
    },
  },
  actions: {
    // 그룹 목록 가져옴
    getGroups({ commit }) {
      api.get(`/groups/of/${Vue.prototype.$session.get('uid')}`)
        .then(({ data }) => {
          commit('setGroups', { data });
        });
    },
    // 약속 목록 가져옴
    getAppointments({ commit }) {
      api.get(`/promises/${Vue.prototype.$session.get('uid')}`)
        .then(({ data }) => {
          commit('setAppointments', { data });
        });
    },
    // 선택된 값으로 업데이트 시켜줌
    updateAppointmentName({ commit }, appointmentName) {
      commit('setAppointmentName', { appointmentName });
    },
    updateGroup({ commit }, group) {
      commit('setGroup', { group });
    },
    updateAppointmentDate({ commit }, appointmentDate) {
      commit('setAppointmentDate', { appointmentDate });
    },
    updateAppointment({ commit }, appointment) {
      commit('setAppointment', { appointment });
    },
    updateBoookmark({ commit }, boookmark) {
      commit('setBoookmark', { boookmark });
    },
    async makeAppointment({ getters }) { // 새 약속 추가
      try {
        const { data } = await api.post('/promises', {
          groupId: getters.group.group.groupId,
          promiseDate: getters.appointmentDate,
          title: getters.appointmentName,
        });
        await api.post('/votes', {
          groupId: getters.group.group.groupId,
          placeId: getters.place.place.placeId,
          promiseId: data,
          userId: Vue.prototype.$session.get('uid'),
        });
        Vue.$toast.success('새로운 약속 만들기 성공');
      } catch (err) {
        console.error(err);
        Vue.$toast.error('약속 만들기 실패, 다시 시도해주세요.');
      }
    },
    addAppointment({ getters }) { // 기존 약속 추가
      api.post('/votes', {
        groupId: getters.appointment.groupId,
        placeId: getters.place.place.placeId,
        promiseId: getters.appointment.promiseId,
        userId: Vue.prototype.$session.get('uid'),
      }, {
        headers: {
          token: Vue.prototype.$session.get('token'),
        },
      }).then(() => {
        Vue.$toast.success('약속 장소 추가 성공');
      }).catch((err) => {
        console.error(err);
        Vue.$toast.error('약속 장소 추가 실패, 다시 시도해주세요.');
      });
    },
    updateAppointmentInfo({ dispatch }, { appointment }) { // 약속 정보 업데이트
      api.put('/promises', appointment)
        .then(() => {
          Vue.$toast.success('약속 수정 성공');
          dispatch('getAppointments');
        })
        .catch((err) => {
          console.error(err);
          Vue.$toast.error('약속 수정 실패, 다시 시도해주세요.');
        });
    },
    removeAppointment({ dispatch }, { groupId, promiseId }) {
      api.delete(`/promises/${groupId}/${promiseId}`)
        .then(() => {
          Vue.$toast.success('약속 삭제 성공');
          dispatch('getAppointments');
        })
        .catch((err) => {
          console.error(err);
          Vue.$toast.error('약속 삭제 실패, 다시 시도해주세요.');
        });
    },
    getGroupsThemes({ commit }) {
      api.get(`/themes/all/${Vue.prototype.$session.get('uid')}`, {
        headers: {
          token: Vue.prototype.$session.get('token'),
        },
      }).then(({ data }) => {
        commit('setGroupsThemes', { data });
      }).catch((err) => {
        console.error(err);
        Vue.$toast.error('조회 실패, 다시 시도해주세요.');
      });
    },
    getGroupTheme({ commit }) {
      api.get(`/themes/place/${Vue.prototype.$session.get('uid')}`, {
        headers: {
          token: Vue.prototype.$session.get('token'),
        },
      }).then(({ data }) => {
        commit('setGroupTheme', { data });
      }).catch((err) => {
        console.error(err);
        Vue.$toast.error('조회 실패, 다시 시도해주세요.');
      });
    },
  },
};
