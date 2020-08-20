<template>
  <v-dialog v-model="dialog" max-width="290">
    <v-card align="center">
      <v-card-title>약속을 삭제하시겠습니까?</v-card-title>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn color="green darken-1" text @click="closeModal()">취소</v-btn>
        <v-btn color="green darken-1" text @click="removeAppointment()">확인</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
import store from '@/store/index';

export default {
  data() {
    return {
      dialog: false,
    };
  },
  props: ['removeDialog', 'appointment'],
  watch: {
    removeDialog() {
      this.dialog = !this.dialog;
    },
  },
  methods: {
    closeModal() {
      this.$emit('close');
    },
    removeAppointment() {
      store.dispatch('removeAppointment', {
        groupId: this.appointment.groupId,
        promiseId: this.appointment.promiseId,
      });
      this.closeModal();
    },
  },
};
</script>

<style>

</style>
