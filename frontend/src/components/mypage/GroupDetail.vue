<template>
  <v-expansion-panel-content>
    <br>
    <h3 class="d-inline">회원목록
      <v-btn icon @click="addGroupUser()">
        <v-icon>
          mdi-plus-circle-outline
        </v-icon>
      </v-btn>
    </h3>
    <v-container>
      <v-row class="my-4">
        <div class="col-6" v-for="groupUser in groupUsers" :key="groupUser.userId">
          <v-col class="d-inline">
            {{ groupUser.name }}
          </v-col>
        </div>
      </v-row>
    </v-container>
    <v-divider></v-divider>
    <br>
    <h3 class="d-inline">테마
      <ThemePlusButton
        class="d-inline pl-2"
        :groupId="groupId"
        @addTheme="getGroupInfo(groupId)"
      />
    </h3>
    <v-container>
      <v-row class="my-4">
        <div class="col-6" v-for="groupTheme in groupThemes" :key="groupTheme.themeId">
          <v-col class="d-inline">
            {{ groupTheme.name }}
            <ThemeMinusButton
              class="d-inline pl-2"
              :groupId="groupId"
              :themeId="groupTheme.themeId"
              @delTheme="getGroupInfo(groupId)"
            />
          </v-col>
        </div>
      </v-row>
    </v-container>
  </v-expansion-panel-content>
</template>

<script>
import api from '@/utils/api';
import ThemePlusButton from '@/components/mypage/ThemePlusButton.vue';
import ThemeMinusButton from '@/components/mypage/ThemeMinusButton.vue';

export default {
  name: 'GroupDetail',
  props: {
    token: null,
    groupId: null,
    groupName: null,
  },
  components: {
    ThemePlusButton,
    ThemeMinusButton,
  },
  data() {
    return {
      isAdmin: false,
      groupUsers: null, // 특정 그룹에 속한 유저들 가져오기
      groupThemes: null, // 특정 그룹에 속한 테마들 가져오기
    };
  },
  methods: {
    getGroupInfo(groupId) {
      // 그룹에 속한 유저목록 가져오기
      api.get(`groups/${groupId}`)
        .then((res) => {
          // console.log(res, '유저목록 가져왔어요!');
          this.groupUsers = res.data;
        })
        .catch((err) => console.log(err));

      // 그룹에 속한 테마목록 가져오기
      api.get(`themes/${groupId}`, { headers: { token: this.$session.get('token') } })
        .then((res) => {
          // console.log(res, '테마목록 가져왔어요!');
          this.groupThemes = res.data;
        })
        .catch((err) => console.log(err));
    },
    addGroupUser() {
      window.Kakao.Link.sendCustom({
        templateId: 33849,
        templateArgs: {
          key: this.token,
          group: this.groupName,
          user: this.$session.get('uname'),
        },
      });
    },
    delGroupUser(groupId, userId) {
      api.delete(`groups/${groupId}/${userId}`)
        .then(() => {
          // console.log(res, '유저 삭제했습니다.');
          alert('유저 삭제 완료');
          this.getGroupInfo(groupId);
        })
        .catch((err) => console.log(err));
    },
    addTheme(groupId) {
      // console.log('addTheme() 실행');
      api.post('themes/', {
        groupId,
        icon: 'food.io',
        name: '먹방',
      },
      {
        headers: {
          token: this.$session.get('token'),
        },
      })
        .then(() => {
          // console.log(res, '테마생성 성공');
          this.getGroupInfo(groupId);
        })
        .catch((res) => console.log(res), '테마생성 실패');
    },
    delTheme(groupId, themeId) {
      // console.log('delTheme() 실행');
      api.delete(`/themes/${groupId}/${themeId}/`, {
        headers: {
          token: this.$session.get('token'),
        },
      })
        .then((res) => {
          console.log(res, '테마 삭제 성공');
          this.getGroupInfo(groupId);
        })
        .catch((err) => console.log(err));
    },
  },
  created() {
    this.getGroupInfo(this.groupId);
  },
  mounted() {
    if (!window.Kakao) {
      const script = document.createElement('script');
      script.onload = () => window.Kakao.init('0e8b85c161beec7b11ccccb161ab81ab');
      script.src = '/js/kakao.min.js';
      document.head.appendChild(script);
    }
  },
};
</script>

<style>

</style>
