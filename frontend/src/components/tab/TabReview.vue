<template>
<v-main>
  <v-container
    align='center'
    justify='center'
    id="scroll-target"
    style="height: calc(90vh - 4rem)"
    class="overflow-y-auto"
  >
    <transition name="fade">
      <div class="loading" v-show="loading">
        <span class="fa fa-spinner fa-spin"></span> Loading
      </div>
    </transition>
    <v-divider style="position: relative;top: 0rem; margin: 0;"></v-divider>
    <v-row
      align='center'
      justify='center'
      v-scroll:#scroll-target="onScroll"
    >
      <v-col
        v-for="(item, i) in filteredArray"
        :key="i"
        cols="12"
        style="padding: 3px; height: 5.1rem;"
      >
        <v-card
          @click="infowindow(i)"
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
                        <v-badge
                          color="blue"
                          :content="item.count"
                        >
                          <v-icon style="font-size: 1rem;">mdi-text-box-multiple-outline</v-icon>
                        </v-badge>
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
      items: [],
      bottom: false,
      loading: false,
      noData: false,
      offset: 1,
      limit: 10,
    };
  },
  components: {
    AppointmentModal: () => import('@/components/items/AppointmentModal.vue'),
  },
  created() {
    store.dispatch('invisibleBookmark');
  },
  mounted() {
    this.readAllReview();
  },
  computed: {
    appointmentDialog: () => store.getters.appointmentDialog,
    uniquePlace() {
      return this.items.reduce((seed, cur) => Object.assign(seed, { [cur.placeId]: cur }), {});
    },
    filteredArray() {
      const ret = {};
      for (let i = 0; i < this.items.length; i += 1) {
        const key = this.items[i].placeId;
        ret[key] = {
          placeId: key,
          name: this.items[i].name,
          address: this.items[i].address,
          lat: this.items[i].lat,
          lng: this.items[i].lng,
          url: this.items[i].url,
          count: ret[key] && ret[key].count ? ret[key].count + 1 : 1,
        };
      }
      return Object.values(ret);
    },
  },
  watch: {
    bottom() {
      if (this.bottom && !this.noData) {
        this.noData = true;
        this.offset += 1;
        this.bottom = false;
        this.readAllReview();
      }
    },
  },
  methods: {
    infowindow(index) {
      EventBus.$emit('moveMap', { lat: this.filteredArray[index].lat, lng: this.filteredArray[index].lng, index });
    },
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
    onScroll(e) {
      const { scrollTop, clientHeight, scrollHeight } = e.target;
      if (scrollTop + clientHeight >= scrollHeight) {
        this.bottom = true;
      }
    },
    readAllReview() {
      api.get(`/reviews/all/${this.$session.get('uid')}/${this.limit}/${this.offset}`, {
        headers: {
          token: this.$session.get('token'),
        },
      }).then(({ data }) => {
        this.loading = true;
        setTimeout(() => {
          if (data.length === 0) {
            this.noData = true;
          } else {
            this.noData = false;
            this.items = this.items.concat(data);
            this.$store.dispatch('doUpdate', this.filteredArray);
          }
          this.loading = false;
        }, 500);
      });
    },
  },
};
</script>

<style scoped>
.v-main {
  padding-top: 0px !important;
}
.d-flex.flex-no-wrap.justify-space-between:hover{
  background-color: #d2d2d4;
  opacity: 0.8;
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
