<template>
  <v-main>
    <v-toolbar color="#f5f5f5" dense flat>
      <v-btn icon @click="moveBack">
        <v-icon>mdi-arrow-left</v-icon>
      </v-btn>
      <v-toolbar-title>{{ keyword }}</v-toolbar-title>
    </v-toolbar>
    <v-container
      id="scroll-target"
      style="height: 85vh"
      class="overflow-y-auto"
      :class="{ safari: isSafari && $vuetify.breakpoint.mdAndDown}"
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
      >
        <v-container>
          <v-row align='center' justify='center'>
            <v-col
              v-for="(item, i) in searchResult"
              :key="i"
              cols="12"
              style="padding: 3px; height: 5.1rem;"
            >
              <v-card
                @click="clickCard(i)"
                style="height: 5rem; box-shadow: none !important;"
              >
                <div>
                  <div class="d-flex flex-no-wrap justify-space-between">
                    <v-row>
                      <v-col cols="9">
                        <v-card-title
                          class="headline"
                          v-text="item.place_name"
                          style="font-size: 1rem !important; padding-top: 0.5rem;padding-bottom:0;"
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
                                @click.stop="showBookmarkDialog(item)"
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
                <v-divider style="position: relative; top: -2.4rem;"></v-divider>
              </v-card>
            </v-col>
          </v-row>
        </v-container>
      </v-row>
      <bookmark-add-modal
        :bookmarkDialog="bookmarkDialog"
        :bookmarkPlace="place"
      ></bookmark-add-modal>
    </v-container>
  </v-main>
</template>

<script>
import store from '@/store/index';
import EventBus from '@/utils/EventBus';

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
      loading: false,
      page: 1,
      bookmarkDialog: false,
    };
  },
  components: {
    BookmarkAddModal: () => import('@/components/items/BookmarkAddModal.vue'),
  },
  watch: {
    bottom() {
      if (this.bottom && !this.noData) {
        store.dispatch('updateNoData', true);
        store.dispatch('updateBottom', false);
        this.loading = true;
        setTimeout(() => {
          store.dispatch('updatePage', this.page += 1);
          store.dispatch('search', this.keyword);
          this.loading = false;
        }, 500);
      }
    },
  },
  computed: {
    searchResult: () => store.getters.result,
    keyword: () => store.getters.keyword,
    noData: () => store.getters.noData,
    bottom: () => store.getters.bottom,
    isSafari: () => store.getters.isSafari,
  },
  methods: {
    moveBack() {
      this.$router.go(-1);
    },
    showBookmarkDialog(item) {
      this.place = item;
      this.bookmarkDialog = !this.bookmarkDialog;
    },
    clickCard(index) {
      EventBus.$emit('moveMap', { lat: this.searchResult[index].y, lng: this.searchResult[index].x, index });
    },
    onScroll(e) {
      const { scrollTop, clientHeight, scrollHeight } = e.target;
      if (scrollTop + clientHeight >= scrollHeight) {
        store.dispatch('updateBottom', true);
      }
    },
  },
};
</script>

<style scoped>
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
.d-flex.flex-no-wrap.justify-space-between:hover{
  background-color: #d2d2d4;
  opacity: 0.8;
}
.safari {
  height: calc(75vh - 50px) !important;
}
</style>
