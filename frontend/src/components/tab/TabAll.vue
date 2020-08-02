<template>
<v-container>
  <v-row align='center' justify='center'>
    <v-col
      v-for="(item, i) in items"
        :key="i"
        cols="12"
    >
      <v-card @click="click">
        <div class="d-flex flex-no-wrap justify-space-between">
          <div>
            <v-card-title
              class="headline"
              v-text="item.name"
            >
            </v-card-title>
            <v-card-actions>
              <v-btn icon @click.stop="appointmentDialog = true">
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

  <appointment-modal :dialog="appointmentDialog" v-on:childs-event="dialogClose">
  </appointment-modal>

</v-container>
</template>

<script>
// import axios from '@/utils/api';

export default {
  data() {
    return {
      items: [
        {
          name: '극동', address: '수유동 553-61', lat: '37.6419848', lng: '127.0123693',
        },
        {
          name: '수유프리미어엠', address: '수유동 48-8', lat: '37.6343669', lng: '127.0221356',
        },
        {
          name: '수유벽산1차', address: '수유동 205', lat: '37.6421703', lng: '127.0189389',
        },
        {
          name: '화계맨숀', address: '수유동 461-13', lat: '37.6336964', lng: '127.0148685',
        },
        {
          name: '백두골드', address: '수유동 710-15', lat: '37.6480061', lng: '127.021856',
        },
        {
          name: '수유현대', address: '수유동 729', lat: '37.6453609', lng: '127.0185173',
        },
        {
          name: '진안골드밸리', address: '수유동 562-4', lat: '37.6454788', lng: '127.0080477',
        },
      ],
      appointmentDialog: false, // dialog 창 제어
      appointmentName: '', // 약속명
      groups: ['그룹1', '그룹2', '그룹3'], // 그룹명
      appointments: ['약속1', '약속2', '약속3'], // 기존에 만들어진 약속 리스트
      date: new Date().toISOString().substr(0, 10),
      menu: false,
    };
  },
  components: {
    AppointmentModal: () => import('@/components/items/AppointmentModal.vue'),
  },
  mounted() {
    // TODO: 서버에서 받아온 데이터 값을 넘겨준다
    this.$store.dispatch('doUpdate', this.items);
  },
  methods: {
    click() {
      alert('click');
    },
    makeAppointment(i) {
      alert(`make appointment ${i}`);
    },
    readReview(i) {
      alert(`read review ${i}`);
    },
    writeReview(i) {
      alert(`write review ${i}`);
    },
    dialogClose() {
      this.appointmentDialog = !this.appointmentDialog;
    },
  },
};
</script>

<style>

</style>
