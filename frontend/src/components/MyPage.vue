<template>
<v-app>
  <v-layout>
    <v-navigation-drawer
      class="app--drawer"
      v-model="drawer"
      clipped
      absolute
      enable-resize-watcher
    >
      <v-list-item class="ml-2">
        <v-list-item-content>
          <v-list-item-title class="title">
            <router-link to="/mypage"><b>마이페이지</b></router-link>
          </v-list-item-title>
          <v-list-item-subtitle class="mt-2 ml-4">
            <span class="mdi mdi-account-box"></span>
            {{ uname }}
          </v-list-item-subtitle>
        </v-list-item-content>
      </v-list-item>
      <v-divider></v-divider>
      <v-list
        dense
        nav
      >
        <v-list-item
          v-for="item in items"
          :key="item.title"
          link
          :to="item.address"
        >
          <v-list-item-icon>
            <v-icon>{{ item.icon }}</v-icon>
          </v-list-item-icon>
          <v-list-item-content>
            <v-list-item-title>{{ item.title }}</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
        <v-list-item
          link
          to="/mypage/admin"
          v-if="isAdmin"
        >
          <v-list-item-icon>
            <v-icon>mdi-calendar-check</v-icon>
          </v-list-item-icon>
          <v-list-item-content>
              <v-list-item-title>회원 목록 조회</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </v-list>
    </v-navigation-drawer>
    <router-view :style="(drawer) ? 'margin-left:250px' : 'margin-left:0'"></router-view>
    <snack-bar></snack-bar>
  </v-layout>
</v-app>
</template>

<script>
export default {
  data() {
    return {
      items: [
        {
          title: '내 그룹 정보',
          icon: 'mdi-view-dashboard',
          address: '/mypage/group',
        },
        {
          title: '내 일정',
          icon: 'mdi-calendar-check',
          address: '/mypage/myschedule',
        },
      ],
      uname: '',
      isAdmin: false,
      drawer: true,
    };
  },
  components: {
    SnackBar: () => import('@/components/items/Snackbars.vue'),
  },
  created() {
    this.uname = this.$session.get('uname');
    this.isAdmin = this.$session.get('admin');
  },
  mounted() {
    document.documentElement.style.overflowY = 'auto';
  },
  beforeUpdate() {
    document.documentElement.style.overflowY = 'auto';
  },
};
</script>

<style scoped>
a {
  text-decoration:none;
  color: black;
}
.v-navigation-drawer--absolute {
  z-index: 2;
}
</style>
