<template>
  <v-flex md10>
    <h1 class="mt-5">회원 목록 조회</h1>
    <hr>
    <v-data-table
      :headers="headers"
      :items="desserts"
      :items-per-page="5"
      class="elevation-1"
    ></v-data-table>
  </v-flex>
</template>

<script>
export default {
  name: 'Admin',
  components: {},
  data() {
    return {
      headers: [
        {
          text: '회원아이디(E-mail)',
          align: 'start',
          sortable: false,
          value: 'name',
        },
        { text: '최근 방문 일자', value: 'Recently visited' },
        { text: '휴면계정', value: 'Dormant account' },
      ],
      desserts: [
        {
          name: '아이디or이메일1',
          'Recently visited': '2020-07-03',
          'Dormant account': true,
        },
      ],
    };
  },
  methods: {
    // admin일 때, 회원목록 조회 page에 들어오면 db에서 회원목록 데이터 받아와야 한다.
    updateDesserts() {
      console.log('test');
      // for문 돌리기전에 DB 에서 가져온 데이터 갯수만큼 추가해야되서 추후 수정예정
      for (let i = 0; i < 5; i += 1) {
        this.desserts.push(
          {
            name: 'test id1',
            'Recently visited': `2020-07-0${i}`,
            'Dormant account': true,
          },
        );
      }
    },
  },
  created() {
    this.updateDesserts();
  },
};
</script>
