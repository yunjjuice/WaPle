<template>
<v-container
  align='center'
  justify='center'
  id="scroll-target"
  style="height: calc(90vh - 50px)"
  class="overflow-y-auto"
>
  <transition name="fade">
    <div class="loading" v-show="loading">
      <span class="fa fa-spinner fa-spin"></span> Loading
    </div>
  </transition>
  <v-row
    align='center'
    justify='center'
    v-scroll:#scroll-target="onScroll"
  >
    <v-col
      v-for="(appointment, i) in appointmentDatas"
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
      appointmentDatas: [],
      editDialog: false,
      removeDialog: false,
      bottom: false,
      loading: false,
      noData: false,
      offset: 1,
      limit: 10,
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
    this.appointmentDatas = this.appointments.slice(this.offset - 1, this.limit);
  },
  watch: {
    bottom() {
      if (this.bottom && !this.noData) {
        this.noData = true;
        this.offset += 1;
        this.bottom = false;
        const size = this.appointments.length;
        if (size >= (this.offset - 1) * this.limit) {
          let t = [];
          if (size < this.offset * this.limit) {
            t = this.appointments.slice((this.offset - 1) * this.limit, size);
            this.appointmentDatas = this.appointmentDatas.concat(t);
          } else {
            t = this.appointments.slice((this.offset - 1) * this.limit, this.offset * this.limit);
            this.appointmentDatas = this.appointmentDatas.concat(t);
          }
          this.noData = false;
        }
      }
    },
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
    onScroll(e) {
      const { scrollTop, clientHeight, scrollHeight } = e.target;
      if (scrollTop + clientHeight >= scrollHeight) {
        this.bottom = true;
      }
    },
  },
};
</script>

<style scoped>
.v-main {
  padding-top: 0px !important;
}
</style>
