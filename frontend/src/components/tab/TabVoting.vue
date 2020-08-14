<template>
<v-container
  align='center'
  justify='center'
  id="scroll-target"
  style="height: calc(90vh - 50px)"
  class="overflow-y-auto"
>
  <v-row
    align='center'
    justify='center'
    v-scroll:#scroll-target="onScroll"
  >
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
              <v-tooltip bottom>
                <template v-slot:activator="{ on, attrs }">
                  <v-btn
                    icon
                    v-bind="attrs"
                    v-on="on"
                    @click.stop="edit(appointment)"
                  >
                  <v-icon>mdi-calendar-edit</v-icon>
                </v-btn>
                </template>
                <span>약속 수정</span>
              </v-tooltip>
              <v-tooltip bottom>
                <template v-slot:activator="{ on, attrs }">
                  <v-btn
                    icon
                    v-bind="attrs"
                    v-on="on"
                    @click.stop="remove(appointment)"
                    >
                    <v-icon>mdi-calendar-remove</v-icon>
                  </v-btn>
                </template>
                <span>약속 삭제</span>
              </v-tooltip>
            </v-card-actions>
          </div>
        </div>
      </v-card>
    </v-col>
  </v-row>
  <edit-modal :dialog="editDialog" :appointment="appointment" @close="editDialog=false">
  </edit-modal>
  <remove-modal :dialog="removeDialog" :appointment="appointment" @close="removeDialog=false">
  </remove-modal>
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
      removeDialog: false,
    };
  },
  components: {
    EditModal: () => import('@/components/items/AppointmentEditModal.vue'),
    RemoveModal: () => import('@/components/items/AppointmentRemoveModal.vue'),
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
    remove(appointment) {
      this.appointment = appointment;
      this.removeDialog = true;
    },
  },
};
</script>

<style scoped>
.v-main {
  padding-top: 0px !important;
}
</style>
