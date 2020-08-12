<template>
  <v-dialog v-model="dialog" persistent width="500">
    <v-card align="center">
      <v-card-title></v-card-title>
      <v-tabs>
        <v-tab @click="clearModalNew">
          새로운 약속
        </v-tab>
        <v-tab-item>
          <v-card flat width="400" height="250" justify="center">
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
            <v-btn @click="isValidByNew" color="primary">만들기</v-btn>
            <v-btn @click="cancelModal" color="error">취소하기</v-btn>
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
            <v-btn @click="isValidByExisting" color="primary">추가하기</v-btn>
            <v-btn @click="cancelModal" color="error">취소하기</v-btn>
          </v-card>
        </v-tab-item>
      </v-tabs>
    </v-card>
  </v-dialog>
</template>

<script>
import store from '@/store/index';
import moment from 'moment';
import { ValidationObserver, ValidationProvider, extend } from 'vee-validate';
import { required } from 'vee-validate/dist/rules';

extend('required', {
  ...required,
  message: '{_field_} can not be empty',
});

export default {
  data() {
    return {
      appointmentName: '',
      group: null,
      appointmentDate: null,
      appointment: null,
    };
  },
  props: ['dialog'],
  components: {
    ValidationObserver,
    ValidationProvider,
  },
  computed: {
    groups: () => store.getters.groups,
    appointments: () => store.getters.appointments,
  },
  methods: {
    clearModalNew() {
      this.appointmentName = '';
      this.group = null;
      this.appointmentDate = null;
      requestAnimationFrame(() => {
        this.$refs.observerNew.reset();
      });
    },
    clearModalExisting() {
      this.appointment = null;
      requestAnimationFrame(() => {
        this.$refs.observerExisting.reset();
      });
    },
    makeAppointment() { // 새 약속 만들기
      console.log(moment(this.appointmentDate));
      store.dispatch('updateAppointmentName', this.appointmentName);
      store.dispatch('updateGroup', this.group);
      store.dispatch('updateAppointmentDate', moment(this.appointmentDate).tz('Asia/Seoul').format());
      store.dispatch('makeAppointment');
      store.dispatch('closeAppointmentDialog');
    },
    addAppointment() { // 기존 약속에 추가하기
      store.dispatch('updateAppointment', this.appointment);
      store.dispatch('addAppointment');
      store.dispatch('closeAppointmentDialog');
    },
    cancelModal() {
      store.dispatch('closeAppointmentDialog');
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
