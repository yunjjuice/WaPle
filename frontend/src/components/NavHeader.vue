<template>
  <v-app-bar color="#ffd54f" app flat>
    <v-toolbar-title>
      <router-link to="/" style="color: black; text-decoration: none"><b>WAPLE</b></router-link>
    </v-toolbar-title>
    <v-spacer></v-spacer>
    <v-text-field
      flat
      solo-inverted
      hide-details
      prepend-inner-icon="mdi-magnify"
      label="Search"
      class="hidden-sm-and-down"
      v-model="keyword"
      @keyup.enter="searchByWord"
    ></v-text-field>
    <v-spacer></v-spacer>
    <div style="right:10rem; position:fixed">
      <v-btn icon>
        <v-icon>mdi-bell</v-icon>
      </v-btn>
    </div>
    <div class="text-center">
      <v-menu offset-y>
        <template v-slot:activator="{ on, attrs }">
          <v-btn
            color="#ffd633"
            class="black--text"
            v-bind="attrs"
            v-on="on"
          >
            {{ uname }}
          </v-btn>
        </template>
        <v-list>
          <v-list-item to="/mypage">
            <v-list-item-title>마이페이지</v-list-item-title>
          </v-list-item>
          <v-list-item to="/toppings">
            <v-list-item-title>개발자정보</v-list-item-title>
          </v-list-item>
          <v-list-item @click="logout">
            <v-list-item-title>로그아웃</v-list-item-title>
          </v-list-item>
        </v-list>
      </v-menu>
    </div>
  </v-app-bar>
</template>

<script>
import store from '@/store/index';
import api from '@/utils/api';
import JWT from 'jwt-decode';

export default {
  data: () => ({
    keyword: '',
    uname: '',
  }),
  methods: {
    searchByWord() {
      if (this.keyword === '') {
        alert('검색어를 입력해주세요');
        return;
      }
      store.dispatch('updatePage', 1);
      store.dispatch('initResult', []);
      store.dispatch('updateKeyword', this.keyword);
      store.dispatch('search', this.keyword);
      this.$router.push('/search').catch(() => {});
      this.keyword = '';
    },
    logout() {
      api.post('/users/logout', {
        refresh_token: this.$session.get('refresh_token'),
      },
      {
        headers: {
          token: this.$session.get('token'),
        },
      }).then(({ data }) => {
        const decodeData = JWT(data);
        if (decodeData.User_ID === this.$session.get('uid')) {
          this.$session.destroy();
          this.$router.push('/');
          window.location.reload();
        } else {
          alert('로그아웃 실패');
        }
      }).catch(() => {
      });
    },
  },
  created() {
    this.uname = this.$session.get('uname');
  },
};
</script>

<style>

</style>
