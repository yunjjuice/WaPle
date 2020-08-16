<template>
<v-main style="height: 80%">
  <v-container
    align='center'
    justify='center'
    id="scroll-target"
    style="height: calc(75vh - 50px)"
    class="overflow-y-auto"
  >
    <transition name="fade">
      <div class="loading" v-show="loading">
        <span class="fa fa-spinner fa-spin"></span> Loading
      </div>
    </transition>
    <v-row
      v-scroll:#scroll-target="onScroll"
      justify="center"
    >
      <v-col
        v-for="(item, i) in items"
        :key="i"
        d-flex
        cols="12"
      >
        <v-card
          @click="setItem(i)"
        >
          <div class="d-flex flex-no-wrap justify-space-between">
            <div>
              <v-card-title
                class="headline"
                v-text="item.name"
              >
              </v-card-title>
                <v-card-actions style="position: absolute; top: 1%; right: 1%">
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
                        <v-icon>mdi-calendar-plus</v-icon>
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
                        <v-icon>mdi-text-box-multiple-outline</v-icon>
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
                        <v-icon>mdi-pencil-plus-outline</v-icon>
                      </v-btn>
                    </template>
                    <span>리뷰 쓰기</span>
                  </v-tooltip>
                </v-card-actions>
              </div>
          </div>
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
  },
  computed: {
    appointmentDialog: () => store.getters.appointmentDialog,
    themes: () => store.getters.groupTheme,
  },
  watch: {
    bottom() {
      if (this.bottom && !this.noData) {
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
      }).catch((error) => {
        console.log(error.response);
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
        .catch((error) => {
          console.log(error.response);
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
      console.log('scroll height : ', scrollTop + clientHeight);
      console.log('cliet height : ', scrollHeight);
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
      }).catch((error) => {
        console.log(error);
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
</style>
