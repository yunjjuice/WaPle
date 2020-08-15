<template>
<v-main>
  <v-dialog
    v-model="dialog"
    width="400"
    height="300"
  >
    <v-card align="center">
      <v-card-title class="headline yellow lighten-3">북마크등록</v-card-title>
      <v-card-text>
      <validation-observer ref="observer">
        <v-row justify="center">
          <v-col cols="15" sm="6">
            <validation-provider v-slot="{ errors }" name="group" rules="required">
              <v-select
                :items="groups"
                v-model="group"
                item-text="name"
                label="group"
                return-object
                required
                :error-messages="errors"
                @change="findTheme"
              ></v-select>
            </validation-provider>
          </v-col>
        </v-row>
        <v-row justify="center">
          <v-col class="d-flex" cols="15" sm="6">
            <validation-provider v-slot="{ errors }" name="theme" rules="required">
              <v-select
                :items="themes"
                v-model="theme"
                item-text="name"
                label="theme"
                return-object
                required
                :error-messages="errors"
                @change="showThemeModal(theme)"
              ></v-select>
            </validation-provider>
          </v-col>
        </v-row>
      </validation-observer>
      </v-card-text>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn color="green darken-1" text @click="closeBookmarkModal">취소</v-btn>
        <v-btn color="green darken-1" text @click="isValid">추가</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
  <theme-add-modal
    :themeDialog="themeDialog"
    :groupId="group.groupId"
    @closeTheme="closeThemeModal"
    @addTheme="updateTheme"
  ></theme-add-modal>
</v-main>
</template>

<script>
import api from '@/utils/api';
import store from '@/store/index';
import { ValidationObserver, ValidationProvider, extend } from 'vee-validate';
import { required } from 'vee-validate/dist/rules';

extend('required', {
  ...required,
  message: '{_field_} can not be empty',
});

export default {
  data() {
    return {
      dialog: false,
      themeDialog: false,
      groups: [],
      group: '',
      themes: [],
      theme: '',
      groupVal: null,
    };
  },
  props: [
    'bookmarkDialog',
    'bookmarkPlace',
  ],
  components: {
    ValidationObserver,
    ValidationProvider,
    ThemeAddModal: () => import('@/components/items/ThemeAddModal.vue'),
  },
  watch: {
    bookmarkDialog() {
      this.dialog = !this.dialog;
    },
  },
  created() {
    api.get(`/groups/of/${this.$session.get('uid')}`)
      .then(({ data }) => {
        this.groups = data;
      });
  },
  methods: {
    findTheme() { // 그룹이 선택되면 그 그룹의 테마를 띄워줌
      api.get(`/themes/${this.group.groupId}`, {
        headers: {
          token: this.$session.get('token'),
        },
      }).then(({ data }) => {
        this.themes = data;
        this.themes.push({
          name: '테마 추가하기 ...',
          act: 'add',
        });
      });
    },
    showThemeModal(theme) {
      if (theme.act === 'add') {
        this.themeDialog = true;
      }
    },
    updateTheme(theme) {
      this.findTheme();
      this.theme = theme;
    },
    closeThemeModal() {
      this.themeDialog = !this.themeDialog;
      this.theme = '';
    },
    makeBookmark() { // 북마크 등록
      api.post('/bookmarks', {
        address: this.bookmarkPlace.road_address_name,
        groupId: this.group.groupId,
        lat: this.bookmarkPlace.y,
        lng: this.bookmarkPlace.x,
        name: this.bookmarkPlace.place_name,
        placeId: this.bookmarkPlace.id,
        themeId: this.theme.themeId,
        url: this.bookmarkPlace.place_url,
        tel: this.bookmarkPlace.phone,
        category: this.bookmarkPlace.category_name,
        userId: this.$session.get('uid'),
      }, {
        headers: {
          token: this.$session.get('token'),
        },
      }).then(() => {
        const payload = { color: 'success', msg: '북마크가 등록되었습니다' };
        store.dispatch('showSnackbar', payload);
      }).catch(() => {
        const payload = { color: 'error', msg: '북마크 등록에 실패했습니다' };
        store.dispatch('showSnackbar', payload);
      });
      this.closeBookmarkModal();
    },
    isValid() { // 그룹과 테마가 다 선택되었는지 확인
      this.$refs.observer.validate()
        .then((result) => {
          if (result) { // 입력이 다 되었다면 요청 보내기
            this.makeBookmark();
          }
        });
    },
    closeBookmarkModal() {
      this.$refs.observer.reset();
      this.dialog = !this.dialog;
    },
  },
};
</script>

<style>

</style>
