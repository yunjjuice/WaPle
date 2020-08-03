<template>
<v-app>
  <v-content>
    <v-container fluid pa-0 fill-height>
      <v-layout row wrap>
        <!-- 왼쪽 Section (Card 영역) -->
        <v-flex md4>
          <v-card
            height="100%"
            width="256"
            dark
          >
            <v-navigation-drawer permanent>
              <v-list-item class="ml-2">
                <v-list-item-content>
                  <v-list-item-title class="title">
                    <router-link to="/mypage">마이페이지</router-link>
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
                <!-- to 옵션으로 router link 걸기 -->
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

                <!-- 관리자일때만 보임 - 회원 목록 조회 -->
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
          </v-card>
        </v-flex>
        <!-- 오른쪽 Content 영역 -->
        <v-flex md8>
          <router-view></router-view>
        </v-flex>
      </v-layout>
    </v-container>
  </v-content>
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
    };
  },
  created() {
    this.uname = this.$session.get('uname');
    this.isAdmin = this.$session.get('admin');
  },
};
</script>

<style scoped>
a {
  text-decoration:none;
  color: white
}
</style>
