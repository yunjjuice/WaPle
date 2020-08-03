<template>
<v-app>
  <v-main>
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
                    <h3> Team </h3>
                    <h5> 토핑스 </h5>
                  </v-list-item-title>
                </v-list-item-content>
              </v-list-item>

              <v-divider></v-divider>

              <v-list
                dense
                nav
              >
                <!-- 팀 리스트  -->
                <v-list-item
                  v-for="item in items"
                  :key="item.title"
                  link
                >
                  <v-list-item-icon>
                    <v-icon>{{ item.icon }}</v-icon>
                  </v-list-item-icon>
                  <v-list-item-content>
                      <v-list-item-title>{{ item.title }}</v-list-item-title>
                  </v-list-item-content>
                </v-list-item>
              </v-list>
            </v-navigation-drawer>
          </v-card>
        </v-flex>
        <!-- 오른쪽 Content 영역 -->
        <v-flex md8>
          <v-flex md10>
            <h1> 팀 소개 페이지 입니다.</h1>
            <hr class="border border-primary">
            <div
              class="my-5 py-5"
              v-for="item in items"
              :key="item.title"
            >
              <img src="https://picsum.photos/id/1/800/400"><br>
              팀원 : {{ item.title }}
            </div>
          </v-flex>
        </v-flex>
      </v-layout>
    </v-container>
  </v-main>
</v-app>
</template>

<script>
export default {
  data() {
    return {
      items: [
        {
          title: '박혜진',
          icon: 'mdi mdi-account-circle',
        },
        {
          title: '오인호',
          icon: 'mdi mdi-account-circle',
        },
        {
          title: '최윤주',
          icon: 'mdi mdi-account-circle',
        },
        {
          title: '강재구',
          icon: 'mdi mdi-account-circle',
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
