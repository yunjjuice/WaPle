<template>
<v-main>
  {{ groupName }} 그룹에 초대받았습니다.
  <v-btn @click="invite">
    초대 수락하기
  </v-btn>
</v-main>
</template>

<script>
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
      })
        .then(() => this.$router.push('/'))
        .catch(() => alert('에러 발생'));
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
