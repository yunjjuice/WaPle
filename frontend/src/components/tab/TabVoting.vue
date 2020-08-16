<template>
<v-container>
  <v-row align='center' justify='center'>
    <v-col
      v-for="(appointment, i) in appointments"
        :key="i"
        cols="12"
    >
      <v-card
        @click="appointmentToPlace(appointment)"
      >
        <div class="d-flex flex-no-wrap justify-space-between">
          <div>
            <v-card-title
              class="headline"
              v-text="appointment.title"
            />
            <v-card-text>
              {{ appointment.name }}<br>
              {{ getFormatDate(appointment.promiseDate) }}
            </v-card-text>
            <v-card-actions style="position: absolute; top: 1%; right: 1%">
                <v-btn
                  icon
                  @click.stop="edit(appointment)"
                  >
                  <v-icon>mdi-calendar-edit</v-icon>
                </v-btn>
            </v-card-actions>
          </div>
        </div>
      </v-card>
    </v-col>
  </v-row>
  <edit-modal :dialog="editDialog" :appointment="appointment" @close="editDialog=false">
  </edit-modal>
</v-container>
</template>

<script>
import store from '@/store/index';
import moment from 'moment';

export default {
  data() {
    return {
      appointment: {},
      editDialog: false,
    };
  },
  components: {
    EditModal: () => import('@/components/items/AppointmentEditModal.vue'),
  },
  computed: {
    appointments: () => store.getters.appointments,
  },
  created() {
    store.dispatch('getAppointments');
    store.dispatch('invisibleBookmark');
  },
  methods: {
    // 약속에 저장된 장소 목록을 띄워준다
    appointmentToPlace(appointment) {
      store.dispatch('updateAppointment', appointment);
      this.$router.push('./appointment');
    },
    getFormatDate(datetime) {
      return moment(datetime).format('YYYY.MM.DD h:mm a');
    },
    edit(appointment) {
      this.appointment = appointment;
      this.editDialog = true;
    },
  },
};
</script>

<style scoped>
.v-main {
  padding-top: 0px !important;
}
</style>
