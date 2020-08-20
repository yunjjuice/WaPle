<template>
  <v-dialog v-model="dialog" persistent max-width="500">
    <v-card align="center">
      <v-card-title class="yellow lighten-3">약속 수정</v-card-title>
          <v-card flat max-width="400" height="220" justify="center" style="padding: 1rem;">
            <validation-observer ref="observer">
              <validation-provider v-slot="{ errors }" name="name" rules="required">
                <v-text-field
                  label="약속명"
                  v-model="appointmentName"
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
            <v-btn @click="isValid" color="primary">수정하기</v-btn>
            <v-btn @click="closeModal" color="error">취소하기</v-btn>
          </v-card>
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
      appointmentDate: null,
    };
  },
  props: ['dialog', 'appointment'],
  components: {
    ValidationObserver,
    ValidationProvider,
  },
  watch: {
    appointment: {
      deep: true,
      handler(val) {
        this.appointmentName = val.title;
        this.appointmentDate = val.promiseDate.replace('T', ' ');
      },
    },
  },
  methods: {
    clearModalNew() {
      requestAnimationFrame(() => {
        this.$refs.observer.reset();
      });
    },
    updateAppointment() {
      store.dispatch('updateAppointmentInfo', {
        appointment: {
          groupId: this.appointment.groupId,
          promiseId: this.appointment.promiseId,
          title: this.appointmentName,
          promiseDate: moment(this.appointmentDate).tz('Asia/Seoul').format(),
        },
      });
      this.closeModal();
    },
    closeModal() {
      this.$emit('close');
      this.clearModalNew();
    },
    isValid() { // 내용이 다 작성되었는지 확인
      this.$refs.observer.validate()
        .then((result) => {
          if (result) { // 입력이 다 되었다면 요청 보내기
            this.updateAppointment();
          }
        });
    },
  },
};
</script>

<style>

</style>
