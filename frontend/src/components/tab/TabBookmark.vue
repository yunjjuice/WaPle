<template>
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
                <v-btn icon @click.stop="showDialog">
                  <v-icon>mdi-calendar-plus</v-icon>
                </v-btn>
                <v-btn icon @click.stop="readReview(i);">
                  <v-icon>mdi-text-box-multiple-outline</v-icon>
                </v-btn>
                <v-btn icon @click.stop="writeReview(i);">
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
</template>

<script>
import store from '@/store/index';
import api from '@/utils/api';

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
    api.get(`/bookmarks/all/${this.$session.get('uid')}/${this.limit}/${this.offset}`, {
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
  computed: {
    appointmentDialog: () => store.getters.appointmentDialog,
  },
  methods: {
    click() {
      alert('click');
    },
    showDialog() {
      store.dispatch('openAppointmentDialog');
      store.dispatch('getGroups');
      store.dispatch('getAppointments');
    },
    readReview(i) {
      alert(`read review ${i}`);
    },
    writeReview(i) {
      alert(`write review ${i}`);
    },
  },
};
</script>

<style>

</style>
