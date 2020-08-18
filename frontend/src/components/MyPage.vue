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
    <router-link
      to="/"
      style="color: black; text-decoration: none; min-height: 20rem; max-width: 25%;"
      class="hidden-sm-and-down"
    >
      <div align='center' justify='center'>
        <v-img
          src="/waplelogo.png"
          max-height="5rem"
          max-width="13rem"
          style="margin: 3rem 0;"
        ></v-img>
      </div>
    </router-link>
      <v-list-item class="ml-2">
        <v-list-item-content>
          <v-list-item-title class="title">
            <b>나의 공간</b>
          </v-list-item-title>
          <v-list-item-subtitle class="mt-2 ml-4">
            <template v-if="!flag">
              {{ username }}님
            </template>
            <template v-if="flag">
              <v-form ref="unameForm" v-model="unameValid">
                <v-text-field
                  v-model="uname"
                  required
                  counter
                  maxlength="10"
                  :rules="[rules.required, rules.counter]"
                  outlined
                  @keypress.enter="modify"
                  style="width: 10rem;"
                ></v-text-field>
              </v-form>
            </template>
            <v-tooltip bottom>
              <template v-slot:activator="{ on, attrs }">
                <v-btn
                  icon
                  v-bind="attrs"
                  v-on="on"
                  @click.stop="modify"
                  style="font-size: 0.9em; position: relative; top: -0.5rem; left: -0.8rem;"
                >
                  <v-icon style="font-size: 0.8rem;">mdi-pencil-outline</v-icon>
                </v-btn>
              </template>
              <span>닉네임 변경</span>
            </v-tooltip>
          </v-list-item-subtitle>
        </v-list-item-content>
      </v-list-item>
      <v-list
        dense
        nav
        style="margin-left: 1rem;"
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
        <v-list-item id="out" style="cursor: pointer" @click.stop="signoutDialog = true">
          <v-list-item-icon>
            <v-icon>mdi-close-circle-outline</v-icon>
          </v-list-item-icon>
          <v-list-item-content>
              <v-list-item-title>회원탈퇴</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </v-list>
    </v-navigation-drawer>
    <router-view :style="(drawer) ? 'margin-left:250px' : 'margin-left:0'"></router-view>
  </v-layout>
  <signout-modal :dialog="signoutDialog" @close="signoutDialog = false"></signout-modal>
</v-app>
</template>

<script>
import api from '@/utils/api';
import store from '@/store/index';

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
      rules: {
        required: (value) => !!value || 'name can not be empty',
        counter: (value) => (value && value.length <= 10) || 'Max 10 chracters',
      },
      username: '', // for see
      uname: '', // for modify form
      unameValid: true,
      isAdmin: false,
      drawer: true,
      flag: false,
      signoutDialog: false,
    };
  },
  components: {
    SignoutModal: () => import('@/components/items/SignoutModal.vue'),
  },
  created() {
    this.username = this.$session.get('uname');
    this.isAdmin = this.$session.get('admin');
  },
  mounted() {
    document.documentElement.style.overflowY = 'auto';
  },
  beforeUpdate() {
    document.documentElement.style.overflowY = 'auto';
  },
  methods: {
    modify() {
      this.flag = !this.flag;
      if (this.flag) {
        this.uname = this.username;
      } else {
        this.isUnameValid();
      }
    },
    isUnameValid() {
      this.$refs.unameForm.validate();
      if (this.unameValid && this.username !== this.uname) {
        this.updateUname();
      }
    },
    updateUname() {
      api.put(`/users/${this.$session.get('uid')}`, {
        userId: this.$session.get('uid'),
        name: this.uname,
      }, {
        headers: {
          token: this.$session.get('token'),
        },
      }).then(() => {
        this.$session.set('uname', this.uname);
        this.username = this.uname;
        // TODO 그룹 디테일 리로드
        store.dispatch('showSnackbar', { color: 'success', msg: '회원 이름 변경 성공' });
      }).catch((err) => {
        console.error(err);
        store.dispatch('showSnackbar', { color: 'error', msg: '회원 이름 변경 실패, 다시 시도해주세요.' });
      });
    },
  },
};
</script>

<style scoped>
a {
  text-decoration:none;
  color: black;
}
#out:hover {
  background-color: #f5f5f6;
  opacity: 0.8;
}
.v-navigation-drawer--absolute {
  z-index: 2;
}
</style>
