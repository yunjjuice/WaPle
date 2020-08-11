<template>
  <v-main>
    <v-toolbar color="#f5f5f5" dense flat>
      <v-btn icon class="hidden-xs-only" @click="moveBack">
        <v-icon>mdi-arrow-left</v-icon>
      </v-btn>
      <v-toolbar-title>{{ keyword }}</v-toolbar-title>
    </v-toolbar>
    <!-- TODO : 픽셀말고 높이 받아와서 스크롤 만들도록 수정 -->
    <v-container
      id="scroll-target"
      style="height: 85vh"
      class="overflow-y-auto"
    >
      <transition name="fade">
        <div class="loading" v-show="loading">
          <span class="fa fa-spinner fa-spin"></span> Loading
        </div>
      </transition>
        <!-- @scroll="onScroll" -->
      <v-row
        v-scroll:#scroll-target="onScroll"
        justify="center"
        style="height: 100vh"
      >
        <v-container>
          <v-row align='center' justify='center'>
            <v-col
              v-for="(item, i) in searchResult"
              :key="i"
              cols="12"
            >
              <v-card @click="clickCard(i)">
                <div>
                  <div class="d-flex flex-no-wrap justify-space-between">
                    <v-row>
                      <v-col cols="9">
                        <v-card-title
                          class="headline"
                          v-text="item.place_name"
                        ></v-card-title>
                        <v-card-text>
                          {{ item.road_address_name }}
                        </v-card-text>
                      </v-col>
                      <v-col cols="3">
                        <v-card-actions>
                          <v-tooltip bottom>
                            <template v-slot:activator="{ on, attrs }">
                              <v-btn
                                icon
                                v-bind="attrs"
                                v-on="on"
                                @click.stop="showDialog(item)"
                              >
                                <v-icon>mdi-bookmark-plus-outline</v-icon>
                              </v-btn>
                            </template>
                            <span>북마크 등록</span>
                          </v-tooltip>
                        </v-card-actions>
                      </v-col>
                    </v-row>
                  </div>
                </div>
              </v-card>
            </v-col>
          </v-row>
          <!-- 북마크 등록 다이얼로그 -->
          <v-dialog
            v-model="dialog"
            width="400"
            height="300"
          >
            <v-card align="center">
              <v-card-title class="headline yellow lighten-3">북마크등록</v-card-title>
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
                        @change="addTheme(theme)"
                      ></v-select>
                    </validation-provider>
                  </v-col>
                </v-row>
                <v-row justify="center">
                  <v-btn depressed color="primary" @click="isValid">추가하기</v-btn>
                </v-row>
              </validation-observer>
            </v-card>
          </v-dialog>
          <!-- 테마 추가 다이얼로그 -->
          <v-dialog v-model="themeDialog" max-width="600px">
            <v-card>
              <v-card-title>
                <span class="headline">테마 추가하기</span>
              </v-card-title>
              <v-card-text>
                <v-container>
                  <v-row>
                    <v-col cols="12">
                      <v-form ref="themeForm" v-model="themeValid">
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
                <v-btn color="blue darken-1" text @click="closeThemeModal">Close</v-btn>
                <v-btn color="blue darken-1" text @click="isThemeValid">Add</v-btn>
              </v-card-actions>
            </v-card>
          </v-dialog>
        </v-container>
      </v-row>
    </v-container>
  </v-main>
</template>

<script>
import store from '@/store/index';
import api from '@/utils/api';
import EventBus from '@/utils/EventBus';
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
      place: {}, // 선택된 장소 정보
      groups: [], // 데이터에서 받아온 그룹 목록
      group: {}, // 선택된 그룹
      themes: [], // 데이터에서 받아온 테마 목록
      theme: {}, // 선택된 테마
      themeName: '',
      rules: {
        required: (value) => !!value || 'theme can not be empty',
        counter: (value) => (value && value.length <= 50) || 'Max 50 chracters',
      },
      themeValid: true,
      bottom: false,
      loading: false,
      page: 1,
    };
  },
  watch: {
    bottom() {
      if (this.bottom) {
        this.loading = true;
        setTimeout(() => {
          store.dispatch('updatePage', this.page += 1);
          store.dispatch('search', this.keyword);
          this.loading = false;
        }, 500);
        this.bottom = false;
      }
    },
  },
  components: {
    ValidationObserver,
    ValidationProvider,
  },
  computed: {
    searchResult: () => store.getters.result,
    keyword: () => store.getters.keyword,
  },
  methods: {
    moveBack() {
      this.$router.go(-1);
    },
    findTheme() {
      api.get(`/themes/${this.group.groupId}`, { headers: { token: this.$session.get('token') } })
        .then(({ data }) => {
          this.themes = data;
          this.themes.push({
            name: '테마 추가하기 ...',
            act: 'add',
          });
        });
    },
    showDialog(item) {
      this.dialog = true;
      this.place = item;
      this.themes = [];
      this.group = null;
      this.theme = null;
      requestAnimationFrame(() => {
        this.$refs.observer.reset();
      });
    },
    makeBookmark() {
      // 북마크 등록
      api.post('/bookmarks', {
        address: this.place.road_address_name,
        groupId: this.group.groupId,
        lat: this.place.y,
        lng: this.place.x,
        name: this.place.place_name,
        placeId: this.place.id,
        themeId: this.theme.themeId,
        url: this.place.place_url,
        userId: this.$session.get('uid'),
      }, {
        headers: {
          token: this.$session.get('token'),
        },
      }).then((res) => {
        if (res.status === 201) {
          const payload = { color: 'success', msg: '북마크가 등록되었습니다' };
          store.dispatch('showSnackbar', payload);
        }
      });
      this.dialog = false;
    },
    isValid() { // 그룹과 테마가 다 선택되었는지 확인
      this.$refs.observer.validate()
        .then((result) => {
          if (result) { // 입력이 다 되었다면 요청 보내기
            this.makeBookmark();
          }
        });
    },
    addTheme(theme) {
      if (theme.act === 'add') {
        this.themeDialog = true;
      }
    },
    makeTheme() {
      api.post('/themes', {
        groupId: this.group.groupId,
        icon: 'gg.ico',
        name: this.themeName,
      }, {
        headers: {
          token: this.$session.get('token'),
        },
      }).then(({ data }) => {
        const payload = { color: 'success', msg: '테마 생성 성공' };
        store.dispatch('showSnackbar', payload);
        this.themes.splice(this.themes.length - 1, 0, data);
        this.theme = '';
        this.themeDialog = false;
      });
    },
    isThemeValid() {
      this.$refs.themeForm.validate();
      if (this.themeValid) {
        this.makeTheme();
      }
    },
    closeThemeModal() {
      if (!this.valid) {
        this.$refs.themeForm.reset();
      }
      this.themeDialog = false;
      this.theme = '';
    },
    clickCard(index) {
      EventBus.$emit('moveMap', { lat: this.searchResult[index].y, lng: this.searchResult[index].x, index });
    },
    onScroll(e) {
      const { scrollTop, clientHeight, scrollHeight } = e.target;
      if (scrollTop + clientHeight === scrollHeight) {
        this.bottom = true;
      }
    },
  },
  created() {
    api.get(`/groups/of/${this.$session.get('uid')}`)
      .then(({ data }) => {
        this.groups = data;
      });
  },
};
</script>

<style>
.loading {
  text-align: center;
  position: absolute;
  color: #fff;
  z-index: 9;
  background: grey;
  padding: 8px 18px;
  border-radius: 5px;
  left: calc(50% - 45px);
  top: calc(50% - 18px);
}
.fade-enter-active, .fade-leave-active {
  transition: opacity .5s
}
.fade-enter, .fade-leave-to {
  opacity: 0
}
</style>
