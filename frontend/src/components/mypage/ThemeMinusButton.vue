<template>
  <v-row justify="center">
    <v-tooltip bottom>
      <template v-slot:activator="{ on, attrs }">
        <v-btn
          icon
          v-bind="attrs"
          v-on="on"
          @click.stop="dialog = true"
        >
          <v-icon>
            mdi-minus-circle-outline
          </v-icon>
        </v-btn>
      </template>
      <span>테마 삭제</span>
    </v-tooltip>
    <v-dialog v-model="dialog" persistent max-width="290">
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
