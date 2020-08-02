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
            <v-text-field label="약속명" v-model="appointmentName" />
            <v-select label="그룹 선택" :items="groups" />
            <!-- date picker -->
            <V-menu ref="menu" v-model="menu" :close-on-content-click="false"
              transition="scale-transition" offset-y min-width="290px">
              <template v-slot:activator="{ on, attrs }">
                <v-text-field v-model="date" label="약속날짜" v-bind="attrs" v-on="on" readonly>
                </v-text-field>
              </template>
              <v-date-picker v-model="date" @input="menu=false">
              </v-date-picker>
            </V-menu>
            <v-btn @click="makeAppointment" color="primary">만들기</v-btn>
            <v-btn @click="cancelModal" color="error">취소하기</v-btn>
          </v-card>
        </v-tab-item>
        <v-tab>
          기존 약속에 추가
        </v-tab>
        <v-tab-item>
          <v-card flat width="400" height="250" align="center" justify="center">
            <v-select label="약속 선택" :items="appointments" />
            <v-btn @click="addAppointment" color="primary">추가하기</v-btn>
            <v-btn @click="cancelModal" color="error">취소하기</v-btn>
          </v-card>
        </v-tab-item>
      </v-tabs>
    </v-card>
  </v-dialog>
</template>

<script>
export default {
  data() {
    return {
      appointmentName: '', // 약속명
      groups: ['그룹1', '그룹2', '그룹3'], // 그룹명
      appointments: ['약속1', '약속2', '약속3'], // 기존에 만들어진 약속 리스트
      date: new Date().toISOString().substr(0, 10),
      menu: false,
    };
  },
  props: ['dialog'],
  methods: {
    makeAppointment() {
      this.$emit('childs-event');
    },
    addAppointment() {
      this.$emit('childs-event');
    },
    cancelModal() {
      this.$emit('childs-event');
    },
  },
};
</script>

<style>

</style>
