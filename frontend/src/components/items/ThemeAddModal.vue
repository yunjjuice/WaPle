<template>
<v-main>
    <v-dialog
      v-model="dialog"
      max-width="600px"
    >
      <v-card>
        <v-card-title>
          <span class="headline">테마 추가하기</span>
        </v-card-title>
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
                <v-form ref="themeForm" v-model="themeValid">
                  <v-text-field
                    label="테마 이름"
                    v-model="themeName"
                    required
                    :rules="[rules.required, rules.counter]"
                    counter
                    @keyup.enter="isThemeValid"
                    maxlength="15"
                  />
                </v-form>
              </v-col>
            </v-row>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="green darken-1" text @click="closeModal">취소</v-btn>
          <v-btn color="green darken-1" text @click="isThemeValid">추가</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
    <icon-select-modal
      :iconDialog="iconDialog"
      @select="selectMarker"
      @close="iconDialog = !iconDialog"
    ></icon-select-modal>
</v-main>
</template>

<script>
import store from '@/store/index';
import api from '@/utils/api';

export default {
  props: [
    'groupId',
    'themeDialog',
  ],
  data() {
    return {
      dialog: false,
      themeName: '',
      rules: {
        required: (value) => !!value || 'theme can not be empty',
        counter: (value) => (value && value.length <= 15) || 'Max 15 chracters',
      },
      themeValid: true,
      iconDialog: false,
      marker: {
        icon: '/markers/default.png',
        name: 'default',
      },
    };
  },
  components: {
    IconSelectModal: () => import('@/components/items/IconSelectModal.vue'),
  },
  watch: {
    themeDialog() {
      this.dialog = !this.dialog;
    },
  },
  methods: {
    closeModal() {
      this.$refs.themeForm.reset();
      this.marker = {
        icon: '/markers/default.png',
        name: 'default',
      };
      this.$emit('closeTheme');
    },
    selectMarker(marker) {
      this.marker = marker;
    },
    makeTheme() {
      api.post('/themes', {
        groupId: this.groupId,
        icon: this.marker.icon,
        name: this.themeName,
      }, {
        headers: {
          token: this.$session.get('token'),
        },
      }).then(({ data }) => {
        this.$emit('updateTheme'); // 그룹 페이지에서
        this.$emit('addTheme', data); // 북마크 모달에서
        store.dispatch('showSnackbar', { color: 'success', msg: '테마 생성 성공' });
      }).catch((err) => {
        console.error(err);
        store.dispatch('showSnackbar', { color: 'error', msg: '테마 생성 실패, 다시 시도해주세요.' });
      });
      this.closeModal();
    },
    isThemeValid() {
      this.$refs.themeForm.validate();
      if (this.themeValid) {
        this.makeTheme();
      }
    },
  },
};
</script>

<style>

</style>
