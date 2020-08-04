<template>
  <v-dialog v-model="dialog" persistent width="500">
    <v-card align="center">
      <v-card-title></v-card-title>
      <v-tabs>
        <v-tab>
          새로운 약속
        </v-tab>
        <v-tab-item>
          <v-card flat width="400" height="250" justify="center">
            <v-form ref="formByNew" v-model="validByNew">
              <v-text-field
                label="약속명"
                v-model="appointmentName"
                :rules="[v => !!v || '약속명을 입력해주세요']"
              />
              <v-select
                label="그룹 선택"
                :items="groups"
                v-model="group"
                item-text="name"
                return-object
                required
                :rules="[v => !!v || '그룹을 선택해주세요']"
              />
              <v-datetime-picker
                label="약속날짜"
                v-model="appointmentDate"
              >
                <template slot="dateIcon">
                  <v-icon>mdi-calendar</v-icon>
                </template>
                <template slot="timeIcon">
                  <v-icon>mdi-clock-time-four-outline</v-icon>
                </template>
              </v-datetime-picker>
              <v-btn @click="isValidByNew" color="primary">만들기</v-btn>
              <v-btn @click="cancelModal" color="error">취소하기</v-btn>
            </v-form>
          </v-card>
        </v-tab-item>
        <v-tab>
          기존 약속에 추가
        </v-tab>
        <v-tab-item>
          <v-card flat width="400" height="250" align="center" justify="center">
            <v-form ref="formByExisting" v-model="validByExisting">
              <v-select
                label="약속 선택"
                :items="appointments"
                v-model="appointment"
                item-text="title"
                return-object
                required
                :rules="[v => !!v || '약속을 선택해주세요']"
              />
              <v-btn @click="isValidByExisting" color="primary">추가하기</v-btn>
              <v-btn @click="cancelModal" color="error">취소하기</v-btn>
            </v-form>
          </v-card>
        </v-tab-item>
      </v-tabs>
    </v-card>
  </v-dialog>
</template>

<script>
import store from '@/store/index';

export default {
  data() {
    return {
      appointmentName: '',
      group: null,
      appointmentDate: null,
      appointment: null,
      validByNew: true,
      validByExisting: true,
    };
  },
  props: ['dialog'],
  computed: {
    groups: () => store.getters.groups,
    appointments: () => store.getters.appointments,
  },
  methods: {
    clearModel() {
      this.appointmentName = '';
      this.group = null;
      this.appointmentDate = null;
      this.appointment = null;
      this.$refs.formByNew.reset();
      this.$refs.formByExisting.reset();
    },
    makeAppointment() { // 새 약속 만들기
      store.dispatch('updateAppointmentName', this.appointmentName);
      store.dispatch('updateGroup', this.group);
      store.dispatch('updateAppointmentDate', this.appointmentDate);
      store.dispatch('makeAppointment');
      store.dispatch('closeAppointmentDialog');
      this.clearModel();
    },
    addAppointment() { // 기존 약속에 추가하기
      store.dispatch('updateAppointment', this.appointment);
      store.dispatch('addAppointment');
      store.dispatch('closeAppointmentDialog');
      this.clearModel();
    },
    cancelModal() {
      store.dispatch('closeAppointmentDialog');
      this.clearModel();
    },
    isValidByNew() { // 내용이 다 작성되었는지 확인
      this.$refs.formByNew.validate();
      if (this.validByNew) {
        this.makeAppointment();
      }
    },
    isValidByExisting() {
      this.$refs.formByExisting.validate();
      if (this.validByExisting) {
        this.addAppointment();
      }
    },
  },
};
</script>

<style>

</style>
