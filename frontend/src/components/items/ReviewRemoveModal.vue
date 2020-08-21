<template>
  <v-dialog v-model="dialog" persistent max-width="290">
    <v-card align="center">
      <v-card-title>삭제하시겠습니까?</v-card-title>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn color="green darken-1" text @click="closeModal()">취소</v-btn>
        <v-btn color="green darken-1" text @click="removeReview()">확인</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
import store from '@/store/index';

export default {
  props: ['dialog'],
  methods: {
    closeModal() {
      this.$emit('close');
    },
    removeReview() {
      store.dispatch('removeReview');
      this.closeModal();
    },
  },
};
</script>

<style>

</style>
