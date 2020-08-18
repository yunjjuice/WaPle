<template>
 <v-row justify="center">
    <v-dialog v-model="dialog" persistent max-width="290">
      <v-card>
        <v-card-title class="headline"></v-card-title>
        <v-card-text> <b>그룹을 지우시나요?</b></v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="green darken-1" text @click="closeModal()">취소</v-btn>
          <v-btn color="green darken-1" text @click="leaveGroup()">확인</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-row>
</template>

<script>
import store from '@/store/index';

export default {
  computed: {
    dialog: () => store.getters.leaveGroupDialog,
  },
  methods: {
    closeModal: () => store.commit('closeLeaveGroupDialog'),
    leaveGroup: () => store.dispatch('leaveGroup'),
  },
};
</script>
