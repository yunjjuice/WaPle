<template>
<v-main>
    <v-dialog
      v-model="dialog"
      max-width="600px"
    >
      <v-card>
        <v-card-title class="yellow lighten-3">테마 수정하기</v-card-title>
        <v-card-text>
          <v-container>
            <v-row align="center" justify="center">
              <v-col align="center" cols="3">
                <v-tooltip bottom>
                  <template v-slot:activator="{ on, attrs }">
                    <v-btn
                      @click="iconDialog = !iconDialog"
                      icon
                      v-bind="attrs"
                      v-on="on"
                    >
                      <v-avatar width="33px" height="44px">
                        <img :src="`${marker.icon}`">
                      </v-avatar>
                    </v-btn>
                  </template>
                  <span>아이콘 선택</span>
                </v-tooltip>
              </v-col>
              <v-col align="center" cols="8">
                <v-form ref="form" v-model="themeValid">
                  <v-text-field
                    label="테마 이름"
                    v-model="themeName"
                    required
                    :rules="[rules.required, rules.counter]"
                    counter
                    @keyup.enter.stop.prevent="isThemeValid"
                    maxlength="10"
                  />
                </v-form>
              </v-col>
            </v-row>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="green darken-1" text @click="closeModal">취소</v-btn>
          <v-btn color="green darken-1" text @click="isThemeValid">확인</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
    <icon-select-modal
      :iconDialog="iconDialog"
      @select="(newMarker) => {marker = newMarker}"
      @close="iconDialog = !iconDialog"
    ></icon-select-modal>
</v-main>
</template>

<script>
import api from '@/utils/api';

export default {
  props: ['theme', 'dialog'],
  data() {
    return {
      themeName: '',
      marker: {},
      rules: {
        required: (value) => !!value || 'theme can not be empty',
        counter: (value) => (value && value.length <= 10) || 'Max 10 chracters',
      },
      themeValid: true,
      iconDialog: false,
    };
  },
  components: {
    IconSelectModal: () => import('@/components/items/IconSelectModal.vue'),
  },
  watch: {
    theme: {
      deep: true,
      handler(val) {
        this.themeName = val.name;
        this.marker.icon = val.icon;
      },
    },
  },
  methods: {
    closeModal() {
      this.$emit('close');
    },
    isThemeValid() {
      this.$refs.form.validate();
      if (this.themeValid) {
        this.editTheme();
      }
    },
    editTheme() {
      api.put('/themes', {
        groupId: this.theme.groupId,
        themeId: this.theme.themeId,
        icon: this.marker.icon,
        name: this.themeName,
      }, {
        headers: {
          token: this.$session.get('token'),
        },
      }).then(() => {
        this.$emit('update');
        this.$toast.success('테마 수정 성공');
      }).catch((err) => {
        console.error(err);
        this.$toast.error('테마 수정 실패, 다시 시도해주세요.');
      });
      this.closeModal();
    },
  },
};
</script>

<style>

</style>
