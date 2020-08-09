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
                    <sapn>약속 추가</sapn>
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

export default {
  data() {
    return {
      items: [],
    };
  },
  components: {
    AppointmentModal: () => import('@/components/items/AppointmentModal.vue'),
  },
  created() {
    store.dispatch('invisibleBookmark');
  },
  mounted() {
    api.get(`/reviews/all/${this.$session.get('uid')}/10/1`, {
      headers: {
        token: this.$session.get('token'),
      },
    }).then(({ data }) => {
      this.items = data;
      this.$store.dispatch('doUpdate', this.items);
    });
  },
  computed: {
    appointmentDialog: () => store.getters.appointmentDialog,
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
      this.$store.dispatch('updateItem', item);
      this.$store.dispatch('showWriteDialog');
    },
  },
};
</script>

<style scoped>
.v-main {
  padding-top: 0px !important;
}
</style>
