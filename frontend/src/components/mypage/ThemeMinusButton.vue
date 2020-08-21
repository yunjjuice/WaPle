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
          <v-icon style="position: relative; left: -0.6rem;">
            mdi-minus-circle-outline
          </v-icon>
        </v-btn>
      </template>
      <span>테마 삭제</span>
    </v-tooltip>
    <v-dialog v-model="dialog" persistent max-width="290">
      <v-card>
        <v-card-title class="headline"></v-card-title>
        <v-card-text><b>테마를 지우시나요?</b></v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="green darken-1" text @click="dialog = false">취소</v-btn>
          <v-btn color="green darken-1" text @click="delTheme(groupId, themeId)">확인</v-btn>
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
      }).then(() => {
        this.$emit('delTheme');
        this.$toast.success('테마 삭제 성공');
      }).catch((err) => {
        console.error(err);
        this.$toast.error('테마 삭제 실패, 다시 시도해주세요.');
      });
    },
  },
};
</script>
