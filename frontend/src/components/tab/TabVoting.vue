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
          </div>
        </div>
      </v-card>
    </v-col>
  </v-row>
</v-container>
</template>

<script>
import store from '@/store/index';
import moment from 'moment';

export default {
  data() {
    return {
    };
  },
  computed: {
    appointments: () => store.getters.appointments,
  },
  created() {
    store.dispatch('getAppointments');
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
  },
};
</script>

<style>

</style>
