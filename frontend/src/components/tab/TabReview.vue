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
                  <v-btn icon @click.stop="showDialog(item)">
                    <v-icon>mdi-calendar-plus</v-icon>
                  </v-btn>
                  <v-btn icon @click.stop="readReview(item);">
                    <v-icon>mdi-text-box-multiple-outline</v-icon>
                  </v-btn>
                  <v-btn icon @click.stop="writeReview(item);">
                    <v-icon>mdi-pencil-plus-outline</v-icon>
                  </v-btn>
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

export default {
  data() {
    return {
      items: [
        {
          name: '동대문', address: '창신동 328-17', lat: '37.5722279', lng: '127.0151863',
        },
        {
          name: 'MID그린(3동))', address: '창신동 601-2', lat: '37.5779087', lng: '127.0099886',
        },
        {
          name: '성용', address: '창신동 640-252', lat: '37.57500479999999', lng: '127.0094655',
        },
      ],
    };
  },
  components: {
    AppointmentModal: () => import('@/components/items/AppointmentModal.vue'),
  },
  created() {
    store.dispatch('invisibleBookmark');
  },
  mounted() {
    this.$store.dispatch('doUpdate', this.items);
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
