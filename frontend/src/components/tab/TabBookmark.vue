<template>
<v-main>
  <v-container
    align='center'
    justify='center'
    id="scroll-target"
    style="height: calc(90vh - 4rem)"
    class="overflow-y-auto"
    :class="{ safari: isSafari && $vuetify.breakpoint.mdAndDown}"
  >
    <transition name="fade">
      <div class="loading" v-show="loading">
        <span class="fa fa-spinner fa-spin"></span> Loading
      </div>
    </transition>
    <div v-if="items.length == 0"
      class="justify-space-between v-card__text"
      style="color: gray">
      아직 북마크를 추가하지 않으신 것 같아요! <br>
      <br>
      <!-- 960px 기준으로 검색 버튼 위치가 달라짐 -->
      <template v-if="windowWidth >= 960">
        우측 하단의 <v-icon>mdi-magnify</v-icon>검색 창에서
      </template>
      <template v-else>
        상단 바의 <v-icon>mdi-magnify</v-icon> 버튼을 눌러
      </template>
      장소를 검색하고, <br>
      <v-icon>mdi-bookmark-plus-outline</v-icon> 버튼을 누르면 <br>
      북마크를 등록할 수 있어요 :)
    </div>
    <v-row
      v-scroll:#scroll-target="onScroll"
      justify="center"
    >
      <v-col
        v-for="(item, i) in items"
        :key="i"
        d-flex
        cols="12"
        style="padding: 3px; height: 5.1rem;"
      >
        <v-card
          @click="setItem(i)"
          style="height: 5rem; box-shadow: none !important;"
        >
          <div class="d-flex flex-no-wrap justify-space-between">
            <div>
              <v-card-title
                class="headline"
                v-text="item.name"
                style="font-size: 1rem !important; padding-top: 0.5rem; padding-bottom: 0;"
              >
              </v-card-title>
                <v-card-actions style="font-size: 1rem; position: absolute; top: 1%; right: 1%">
                  <v-menu
                    v-model="menu[i]"
                    :close-on-content-click='false'
                    bottom
                    offset-y
                  >
                    <template v-slot:activator='{ on, attrs }'>
                      <v-btn
                        v-bind='attrs'
                        v-on='on'
                        icon
                        @click="getThemes(i)"
                      >
                        <v-icon>mdi-dots-vertical</v-icon>
                      </v-btn>
                    </template>
                    <v-list>
                      <v-list-item-group v-for="(theme, index) in themes" :key="index">
                        <v-list-item-title hover class="ma-0 pa-1">
                          <v-checkbox
                            :value="theme"
                            :label="theme.groupName + '-' + theme.themeName"
                            v-model="result"
                            class="ma-0 pa-0"
                            dense
                            on-icon='mdi-check-circle'
                            off-icon='mdi-check-circle-outline'
                          >
                          </v-checkbox>
                        </v-list-item-title>
                      </v-list-item-group>
                      <bookmark-modal
                        :modifyThemes="result"
                        :menu.sync="menu[i]"
                        :item="item"
                        :original="original"
                        :index="i"
                        v-on:close="closeMenu"
                      >
                      </bookmark-modal>
                    </v-list>
                  </v-menu>
                </v-card-actions>
                <v-card-actions>
                  <v-tooltip bottom>
                    <template v-slot:activator="{ on, attrs }">
                      <v-btn
                        icon
                        v-bind="attrs"
                        v-on="on"
                        @click.stop="showDialog(item)"
                      >
                        <v-icon style="font-size: 1rem;">mdi-calendar-plus</v-icon>
                      </v-btn>
                    </template>
                    <span>약속 추가</span>
                  </v-tooltip>
                  <v-tooltip bottom>
                    <template v-slot:activator="{ on, attrs }">
                      <v-btn
                        icon
                        v-bind="attrs"
                        v-on="on"
                        @click.stop="readReview(item)"
                      >
                        <v-icon style="font-size: 1rem;">mdi-text-box-multiple-outline</v-icon>
                      </v-btn>
                    </template>
                    <span>리뷰 읽기</span>
                  </v-tooltip>
                  <v-tooltip bottom>
                    <template v-slot:activator="{ on, attrs }">
                      <v-btn
                        icon
                        v-bind="attrs"
                        v-on="on"
                        @click.stop="writeReview(item)"
                      >
                        <v-icon style="font-size: 1rem;">mdi-pencil-plus-outline</v-icon>
                      </v-btn>
                    </template>
                    <span>리뷰 쓰기</span>
                  </v-tooltip>
                </v-card-actions>
              </div>
          </div>
          <v-divider style="position: relative; top: -1.75rem;"></v-divider>
        </v-card>
      </v-col>
    </v-row>
    <appointment-modal :dialog="appointmentDialog" />
  </v-container>
</v-main>
</template>

<script>
import store from '@/store/index';
import api from '@/utils/api';
import EventBus from '@/utils/EventBus';

export default {
  data() {
    return {
      limit: 10,
      offset: 1,
      item: {},
      items: [],
      result: [],
      menu: [],
      original: [],
      bottom: false,
      loading: false,
      filterFlag: false,
      filterData: [],
      noData: false,
      windowWidth: window.innerWidth,
    };
  },
  components: {
    AppointmentModal: () => import('@/components/items/AppointmentModal.vue'),
    BookmarkModal: () => import('@/components/items/BookmarkModal.vue'),
  },
  mounted() {
    store.dispatch('getGroupsThemes');
    store.dispatch('getGroupTheme');
    this.callAll(this.limit, this.offset);
    window.addEventListener('resize', () => {
      this.windowWidth = window.innerWidth;
    });
  },
  computed: {
    appointmentDialog: () => store.getters.appointmentDialog,
    themes: () => store.getters.groupTheme,
    isSafari: () => store.getters.isSafari,
  },
  watch: {
    bottom() {
      if (this.bottom && !this.noData) {
        this.noData = true;
        this.offset += 1;
        this.bottom = false;
        if (this.filterFlag) {
          this.filterSearch();
        } else {
          this.callAll(this.limit, this.offset);
        }
      }
    },
  },
  created() {
    store.dispatch('visibleBookmark');
    EventBus.$on('deleteCard', (data) => {
      this.deleteCard(data);
    });
    EventBus.$on('userSelect', (data) => {
      this.offset = 1;
      this.filterData = data;
      this.noData = false;
      if (this.filterData.length === 0) {
        this.filterFlag = false;
        this.callAll(this.limit, this.offset);
      } else {
        this.filterFlag = true;
        this.filterSearch();
      }
    });
  },
  methods: {
    showDialog(item) {
      store.dispatch('selectPlace', item);
      store.dispatch('openAppointmentDialog');
      store.dispatch('getGroups');
      store.dispatch('getAppointments');
    },
    readReview(item) {
      this.$store.dispatch('updateItem', item);
      this.$router.push('./reviewlist');
    },
    writeReview(item) {
      store.dispatch('updateItem', item);
      store.dispatch('changeWriteDialog');
      store.dispatch('getGroups');
    },
    callAll(limit, offset) {
      api.get(`/bookmarks/all/${this.$session.get('uid')}/${limit}/${offset}`, {
        headers: {
          token: this.$session.get('token'),
        },
      }).then(({ data }) => {
        if (data.length === 0) {
          this.noData = true;
        } else {
          this.noData = false;
          this.loading = true;
          setTimeout(() => {
            if (this.offset === 1) {
              this.items = data;
            } else {
              this.items = this.items.concat(data);
            }
            this.initMenu(this.items.length, data.length);
            this.$store.dispatch('doUpdate', this.items);
            this.loading = false;
          }, 500);
        }
      }).catch((err) => {
        console.error(err);
        this.$toast.error('북마크 조회 실패, 다시 시도해주세요.');
      });
    },
    setItem(index) {
      this.item = this.items[index];
      store.dispatch('updateBoookmark', this.item);
      EventBus.$emit('moveMap', { lat: this.item.lat, lng: this.item.lng, index });
    },
    getThemes(i) {
      this.item = this.items[i];
      store.dispatch('updateBoookmark', this.item);
      api
        .get(`/themes/place/${this.$session.get('uid')}/${this.item.placeId}`, {
          headers: {
            token: this.$session.get('token'),
          },
        })
        .then(({ data }) => {
          this.result = data;
          this.original = data;
        })
        .catch((err) => {
          console.error(err);
          this.$toast.error('테마 조회 실패, 다시 시도해주세요.');
        });
    },
    closeMenu(index) {
      this.menu[index] = false;
    },
    initMenu(first, last) {
      this.menu = [];
      for (let index = first > 0 ? first : 1; index <= last; index += 1) {
        this.menu.push(false);
      }
    },
    onScroll(e) {
      const { scrollTop, clientHeight, scrollHeight } = e.target;
      if (scrollTop + clientHeight >= scrollHeight) {
        this.bottom = true;
      }
    },
    filterSearch() {
      const data = this.filterData;
      const groups = [];
      for (let index = 0; index < data.length; index += 1) {
        groups.push({ groupId: data[index].groupId, themeId: data[index].themeId });
      }
      const searchData = {
        userId: this.$session.get('uid'),
        limit: this.limit,
        offset: this.offset,
        groups,
      };
      api.get('/bookmarks', {
        params: {
          searchType: encodeURI(JSON.stringify(searchData)),
        },
        headers: {
          token: this.$session.get('token'),
        },
      }).then((res) => {
        if (res.data.length === 0) {
          this.noData = true;
        } else {
          this.noData = false;
          this.loading = true;
          setTimeout(() => {
            if (this.offset === 1) {
              this.items = res.data;
            } else {
              this.items = this.items.concat(res.data);
            }
            this.$store.dispatch('doUpdate', this.items);
            this.loading = false;
          }, 500);
        }
      }).catch((err) => {
        console.error(err);
        this.$toast.error('북마크 조회 실패, 다시 시도해주세요.');
      });
    },
    deleteCard(index) {
      this.items.splice(index, 1);
    },
  },
};
</script>

<style scoped>
.v-main {
  padding-top: 0px !important;
}
.v-list-item__title.ma-0.pa-1:hover {
  background-color: lightgrey !important;
}
.v-input__control .v-input--selection-controls__input label {
  margin-bottom: 0rem;
}
label.v-label.theme--light {
  margin-bottom: 0px;
}
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
