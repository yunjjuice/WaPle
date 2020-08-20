<template>
  <v-dialog v-model="dialog" width="500">
    <v-card align="center">
      <v-card-title class="yellow lighten-3">약속 만들기
      </v-card-title>
      <v-tabs
        fixed-tabs
        background-color="#fff"
        color="#808080"
        light
      >
        <v-tab @click="clearModalNew">
          새로운 약속
        </v-tab>
        <v-tab-item>
          <v-card flat width="400" height="260" justify="center">
            <validation-observer ref="observerNew">
              <validation-provider v-slot="{ errors }" name="name" rules="required">
                <v-text-field
                  label="약속명"
                  v-model="appointmentName"
                  :error-messages="errors"
                />
              </validation-provider>
              <validation-provider v-slot="{ errors }" name="group" rules="required">
                <v-select
                  label="그룹 선택"
                  :items="groups"
                  v-model="group"
                  item-text="name"
                  return-object
                  required
                  :error-messages="errors"
                />
              </validation-provider>
              <validation-provider v-slot="{ errors }" name="datetime" rules="required">
                <v-datetime-picker
                  label="약속날짜"
                  v-model="appointmentDate"
                  :text-field-props="{
                    errorMessages: errors,
                    class:'custom-label-color',
                  }"
                  :datePickerProps="{
                    'allowed-dates': (val) => new Date(val) >= new Date(),
                  }"
                >
                  <template slot="dateIcon">
                    <v-icon>mdi-calendar</v-icon>
                  </template>
                  <template slot="timeIcon">
                    <v-icon>mdi-clock-time-four-outline</v-icon>
                  </template>
                </v-datetime-picker>
              </validation-provider>
            </validation-observer>
            <v-btn @click="cancelModal" color="error" text>취소하기</v-btn>
            <v-btn @click="isValidByNew" color="primary" text>만들기</v-btn>
          </v-card>
        </v-tab-item>
        <v-tab @click="clearModalExisting">
          기존 약속에 추가
        </v-tab>
        <v-tab-item>
          <v-card flat width="400" height="250" align="center" justify="center">
            <validation-observer ref="observerExisting">
              <validation-provider v-slot="{ errors }" name="appointment" rules="required">
                <v-select
                  label="약속 선택"
                  :items="appointments"
                  v-model="appointment"
                  item-text="title"
                  return-object
                  required
                  :error-messages="errors"
              />
              </validation-provider>
            </validation-observer>
            <v-btn @click="cancelModal" color="error" text>취소하기</v-btn>
            <v-btn @click="isValidByExisting" color="primary" text>추가하기</v-btn>
          </v-card>
        </v-tab-item>
      </v-tabs>
    </v-card>
  </v-dialog>
</template>

<script>
import moment from 'moment';
import { ValidationObserver, ValidationProvider, extend } from 'vee-validate';
import { required } from 'vee-validate/dist/rules';
import store from '@/store/index';

extend('required', {
  ...required,
  message: '{_field_} can not be empty',
});

export default {
  data() {
    return {
      appointmentName: '',
      group: null,
      appointmentDate: '',
      appointment: null,
      dialog: false,
    };
  },
  props: ['appointmentDialog'],
  components: {
    ValidationObserver,
    ValidationProvider,
  },
  computed: {
    groups: () => store.getters.groups,
    appointments: () => store.getters.appointments,
  },
  watch: {
    appointmentDialog() {
      this.dialog = !this.dialog;
    },
  },
  updated() {
    this.clearModalNew();
    this.clearModalExisting();
  },
  methods: {
    clearModalNew() {
      this.appointmentName = '';
      this.group = null;
      this.appointmentDate = '';
      if (this.$refs.observerNew) {
        this.$refs.observerNew.reset();
      }
    },
    clearModalExisting() {
      this.appointment = '';
      if (this.$refs.observerExisting) {
        this.$refs.observerExisting.reset();
      }
    },
    makeAppointment() { // 새 약속 만들기
      store.dispatch('updateAppointmentName', this.appointmentName);
      store.dispatch('updateGroup', this.group);
      store.dispatch('updateAppointmentDate', moment(this.appointmentDate).tz('Asia/Seoul').format());
      store.dispatch('makeAppointment');
      this.cancelModal();
    },
    addAppointment() { // 기존 약속에 추가하기
      store.dispatch('updateAppointment', this.appointment);
      store.dispatch('addAppointment');
      this.cancelModal();
    },
    cancelModal() {
      this.$emit('closeAppointmentModal');
      this.clearModalExisting();
      this.clearModalNew();
    },
    isValidByNew() { // 내용이 다 작성되었는지 확인
      this.$refs.observerNew.validate()
        .then((result) => {
          if (result) { // 입력이 다 되었다면 요청 보내기
            this.makeAppointment();
          }
        });
    },
    isValidByExisting() {
      this.$refs.observerExisting.validate()
        .then((result) => {
          if (result) {
            this.addAppointment();
          }
        });
    },
  },
};
</script>

<style>

</style>
