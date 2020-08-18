<template>
<v-main>
  <v-container style="height: 100%">
  <br><br>
  <v-data-table
    :headers="headers"
    :items="users"
    :items-per-page="10"
    class="elevation-1 mytable"
  ></v-data-table>
  </v-container>
</v-main>
</template>

<script>
import store from '@/store/index';
import api from '@/utils/api';
import moment from 'moment';

export default {
  name: 'Admin',
  components: {},
  data() {
    return {
      headers: [
        {
          text: '닉네임', align: 'center', sortable: false, value: 'name',
        },
        {
          text: '최근 방문 일자', align: 'center', value: 'lastDate',
        },
      ],
      users: [],
    };
  },
  methods: {
    updateUsers() {
      api.get('/users', {
        headers: {
          token: this.$session.get('token'),
        },
      }).then(({ data }) => {
        for (let i = 0; i < data.length; i += 1) {
          this.users.push(data[i]);
          this.users[i].lastDate = this.getFormatDate(data[i].lastDate);
        }
      }).catch((err) => {
        console.error(err);
        store.dispatch('showSnackbar', { color: 'error', msg: '회원 목록 로드 실패, 다시 시도해주세요.' });
      });
    },
    getFormatDate(datetime) {
      return moment(datetime).format('YYYY-MM-DD');
    },
  },
  created() {
    this.updateUsers();
  },
};
</script>

<style scoped>
th.text-center {
  background-color: #f5f5f5;
}
</style>
