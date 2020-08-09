<template>
  <v-row justify="center">
    <v-dialog v-model="dialog" persistent max-width="290">
      <template v-slot:activator="{ on, attrs }">
        <v-icon
          v-bind="attrs"
          v-on="on"
        >
          mdi-minus-circle-outline
        </v-icon>
      </template>
      <v-card>
        <v-card-title>정말 삭제하시겠습니까?</v-card-title>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="green darken-1" text @click="dialog = false">No</v-btn>
          <v-btn color="green darken-1" text @click="delTheme(groupId, themeId)">Yes</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-row>
</template>

<script>
import api from '@/utils/api';

export default {
  name: 'ThemeMinusButton',
  props: {
    groupId: null,
    themeId: Number,
  },
  components: {
  },
  data() {
    return {
      dialog: false,
    };
  },
  methods: {
    delTheme(groupId, themeId) {
      this.dialog = false;
      api.delete(`/themes/${groupId}/${themeId}/`, {
        headers: {
          token: this.$session.get('token'),
        },
      })
        .then(() => {
          this.$emit('delTheme');
        })
        .catch((err) => console.log(err));
    },
  },
};
</script>
