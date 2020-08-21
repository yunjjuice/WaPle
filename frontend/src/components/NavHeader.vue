<template>
<v-layout align='center' justify='center'>
  <v-app-bar color="#fff" flat :clipped-left="$vuetify.breakpoint.lgAndUp">
    <v-app-bar-nav-icon
      class="hidden-md-and-up"
      @click.stop="$emit('toggle-drawer')"
    ></v-app-bar-nav-icon>
    <v-spacer></v-spacer>
    <v-spacer></v-spacer>
    <v-spacer></v-spacer>
    <v-spacer></v-spacer>
    <v-spacer></v-spacer>
    <v-toolbar-title>
      <router-link to="/" style="color: black; text-decoration: none">
        <div align='center' justify='center'>
          <v-img
            src="/waplelogo.png"
            max-height="5rem"
            max-width="10rem"
            min-height="1rem"
            min-width="4rem"
          ></v-img>
        </div>
      </router-link>
    </v-toolbar-title>
    <v-spacer></v-spacer>
    <v-spacer></v-spacer>
    <v-spacer></v-spacer>
    <v-spacer></v-spacer>
    <v-menu bottom offset-y allow-overflow>
      <template v-slot:activator="{ on, attrs }">
        <v-btn
          icon
          class="hidden-md-and-up"
          v-bind="attrs"
          v-on="on"
        >
          <v-icon>mdi-magnify</v-icon>
        </v-btn>
      </template>
      <v-list>
        <v-list-item>
          <v-text-field
            flat
            solo-inverted
            hide-details
            prepend-inner-icon="mdi-magnify"
            label="Search"
            v-model="keyword"
            @click.stop
            @keyup.enter="searchByWord"
          ></v-text-field>
        </v-list-item>
      </v-list>
    </v-menu>
    <div class="text-center hidden-md-and-up">
      <v-menu offset-y>
        <template v-slot:activator="{ on, attrs }">
          <v-btn
            color="#ffd633"
            v-bind="attrs"
            v-on="on"
          >
            <span class="hidden-sm-and-down">{{ uname }}</span>
            <v-icon class="hidden-md-and-up">mdi-dots-vertical</v-icon>
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
</v-layout>
</template>

<script>
import JWT from 'jwt-decode';
import store from '@/store/index';
import api from '@/utils/api';
import EventBus from '@/utils/EventBus';

export default {
  data: () => ({
    keyword: '',
    uname: '',
    drawer: false,
  }),
  methods: {
    searchByWord() {
      if (this.keyword === '') {
        this.$toast.error('검색어를 입력해주세요');
        return;
      }
      store.dispatch('updatePage', 1);
      store.dispatch('updateNoData', false);
      store.dispatch('updateBottom', false);
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
          this.$toast.error('로그아웃 실패, 다시 시도해주세요.');
        }
      }).catch((err) => {
        console.error(err);
        this.$toast.error('로그아웃 실패, 다시 시도해주세요.');
      });
    },
    clickNavHeader() {
      EventBus.$emit('toggle-drawer', 'click');
    },
  },
  created() {
    this.uname = this.$session.get('uname');
  },
};
</script>

<style>

</style>
