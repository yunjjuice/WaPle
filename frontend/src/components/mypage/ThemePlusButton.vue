<template>
  <div class="text-center">
    <v-tooltip bottom>
      <template v-slot:activator="{ on, attrs }">
        <v-btn
          icon
          v-bind="attrs"
          v-on="on"
          @click.stop="dialog = true"
        >
          <v-icon>
            mdi-plus-circle-outline
          </v-icon>
        </v-btn>
      </template>
      <span>테마 추가</span>
    </v-tooltip>
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
                        <img :src="`/img/markers/${marker.icon}`">
                      </v-avatar>
                    </v-btn>
                  </template>
                  <span>아이콘 선택</span>
                </v-tooltip>
              </v-col>
              <v-col align="center" cols="8">
                <v-form ref="themeForm" v-model="valid">
                  <v-text-field
                    label="테마 이름"
                    v-model="themeName"
                    required
                    :rules="[rules.required, rules.counter]"
                    counter
                    maxlength="50"
                  />
                </v-form>
              </v-col>
            </v-row>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="blue darken-1" text @click="closeModal">취소</v-btn>
          <v-btn color="blue darken-1" text @click="isValid">추가</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
    <icon-select-modal
      :iconDialog="iconDialog"
      @select="selectMarker"
      @close="iconDialog = !iconDialog"
    ></icon-select-modal>
  </div>
</template>

<script>
import store from '@/store/index';
import api from '@/utils/api';

export default {
  name: 'AddGroupUserButton',
  props: {
    groupId: Number,
  },
  data() {
    return {
      dialog: false,
      themeName: '',
      rules: {
        required: (value) => !!value || 'theme can not be empty',
        counter: (value) => (value && value.length <= 50) || 'Max 50 chracters',
      },
      valid: true,
      iconDialog: false,
      marker: {
        icon: 'default.png',
        name: 'default',
      },
    };
  },
  components: {
    IconSelectModal: () => import('@/components/items/IconSelectModal.vue'),
  },
  methods: {
    isValid() {
      this.$refs.themeForm.validate();
      if (this.valid) {
        this.addTheme();
      }
    },
    addTheme() {
      api.post('themes/', {
        groupId: this.groupId,
        icon: this.marker.icon,
        name: this.themeName,
      },
      {
        headers: {
          token: this.$session.get('token'),
        },
      })
        .then(() => {
          const payload = { color: 'success', msg: '테마 생성 성공' };
          store.dispatch('showSnackbar', payload);
          this.themeName = '';
          this.$emit('addTheme');
        })
        .catch(() => {
          const payload = { color: 'error', msg: '테마 생성 실패' };
          store.dispatch('showSnackbar', payload);
        });
      this.closeModal();
    },
    closeModal() {
      this.$refs.themeForm.reset();
      this.dialog = false;
      this.marker = {
        icon: 'default.png',
        name: 'default',
      };
    },
    selectMarker(marker) {
      this.marker = marker;
    },
  },
};
</script>
