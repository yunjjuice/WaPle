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
              <!-- <v-btn icon @click.stop="appointmentDialog = true"> -->
              <v-btn icon @click.stop="showDialog(item)">
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

  <appointment-modal :dialog="appointmentDialog" />

</v-container>
</template>

<script>
// import api from '@/utils/api';
import store from '@/store/index';

export default {
  data() {
    return {
      items: [
        {
          name: '미즐카페엠', address: '서울 강북구 4.19로 107', lat: '37.6435597877097', lng: '127.003829773305', placeId: '7888616',
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
    };
  },
  components: {
    AppointmentModal: () => import('@/components/items/AppointmentModal.vue'),
  },
  mounted() {
    // TODO: 서버에서 받아온 데이터 값을 넘겨준다
    this.$store.dispatch('doUpdate', this.items);
  },
  computed: {
    appointmentDialog: () => store.getters.appointmentDialog,
  },
  methods: {
    click() {
      alert('click');
    },
    showDialog(item) {
      store.dispatch('selectPlace', item);
      store.dispatch('openAppointmentDialog');
      store.dispatch('getGroups');
      store.dispatch('getAppointments');
    },
    readReview(i) {
      alert(`read review ${i}`);
    },
    writeReview(i) {
      alert(`write review ${i}`);
    },
  },
};
</script>

<style>

</style>
