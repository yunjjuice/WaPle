<template>
 <v-row justify="center">
    <v-dialog v-model="dialog" persistent max-width="290">
      <v-card>
        <v-card-title class="headline"></v-card-title>
        <v-card-text> <b>정말 탈퇴하실 건가요?</b></v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="green darken-1" text @click="closeModal()">취소</v-btn>
          <v-btn color="red darken-1" text @click="signout()">확인</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-row>
</template>

<script>
import api from '@/utils/api';
import store from '@/store/index';

export default {
  props: ['dialog'],
  methods: {
    closeModal() {
      this.$emit('close');
    },
    signout() {
      this.closeModal();
      api.delete(`/users/${this.$session.get('uid')}/${this.$session.get('refresh_token')}`, {
        headers: {
          token: this.$session.get('token'),
        },
      }).then(() => {
        this.$session.destroy();
        this.$router.push('/');
        window.location.reload();
      }).catch((err) => {
        console.error(err);
        store.dispatch('showSnackbar', { color: 'error', msg: '회원 탈퇴 실패, 다시 시도해주세요.' });
      });
    },
  },
};
</script>
