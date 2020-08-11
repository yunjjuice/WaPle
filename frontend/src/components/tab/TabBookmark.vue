<template>
<v-main>
  <v-container>
    <v-row align='center' justify='center'>
      <v-col
        v-for="(item, i) in items"
          :key="i"
          cols="12"
      >
        <v-card
          @click="click"
        >
          <div class="d-flex flex-no-wrap justify-space-between">
            <div>
              <v-card-title
                class="headline"
                v-text="item.name"
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
      items: [],
    };
  },
  components: {
    AppointmentModal: () => import('@/components/items/AppointmentModal.vue'),
  },
  mounted() {
    this.callAll(this.limit, this.offset);
  },
  computed: {
    appointmentDialog: () => store.getters.appointmentDialog,
  },
  created() {
    store.dispatch('visibleBookmark');
    EventBus.$on('userSelect', (data) => {
      if (data.length === 0) {
        this.callAll(this.limit, this.offset);
      } else {
        const groups = [];
        for (let index = 0; index < data.length; index += 1) {
          groups.push({ groupId: data[index].groupId, themeId: data[index].themeId });
        }
        const searchData = {
          userId: this.$session.get('uid'),
          limit: 10,
          offset: 1,
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
          this.items = res.data;
          this.$store.dispatch('doUpdate', this.items);
        });
      }
    });
  },
  methods: {
    click() {
      alert('click');
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
      store.dispatch('showWriteDialog');
      store.dispatch('getGroups');
    },
    callAll(limit, offset) {
      api.get(`/bookmarks/all/${this.$session.get('uid')}/${limit}/${offset}`, {
        headers: {
          token: this.$session.get('token'),
        },
      }).then(({ data }) => {
        this.items = data;
        this.$store.dispatch('doUpdate', this.items);
      }).catch((error) => {
        console.log(error.response);
      });
    },
  },
};
</script>

<style scoped>
.v-main {
  padding-top: 0px !important;
}
</style>
