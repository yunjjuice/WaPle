<template>
<v-main>
  {{ groupName }} 그룹에 초대받았습니다.
  <v-btn @click="invite">
    초대 수락하기
  </v-btn>
</v-main>
</template>

<script>
import store from '@/store/index';
import api from '@/utils/api';
import JWT from 'jwt-decode';

export default {
  data() {
    return {
      groupName: '',
    };
  },
  props: ['code'],
  methods: {
    invite() {
      api.post('/groups/member', {
        token: this.code,
        userId: this.$session.get('uid'),
      }).then(() => {
        this.$router.push('/');
        store.dispatch('showSnackbar', { color: 'success', msg: '그룹 가입 성공' });
      }).catch((err) => {
        console.error(err);
        store.dispatch('showSnackbar', { color: 'error', msg: '그룹 가입 실패, 다시 시도해주세요.' });
      });
    },
  },
  created() {
    const decodeData = JWT(this.code);
    this.groupName = decodeData.name;
  },
};
</script>

<style>

</style>
