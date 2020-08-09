<template>
  <v-row justify="center">
    <v-dialog v-model="dialog" persistent max-width="290">
      <v-card>
        <v-card-title>정말 삭제하시겠습니까?</v-card-title>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="green darken-1" text
          @click="closeModal()">No</v-btn>
          <v-btn color="green darken-1" text
          @click="leaveGroup()">Yes</v-btn>
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
  data: () => ({
    // dialog: false,
  }),
  methods: {
    closeModal: () => store.commit('closeLeaveGroupDialog'),
    leaveGroup: () => store.dispatch('leaveGroup'),
  },
};
</script>
