<template>
  <!-- 그룹 눌렀을때 안쪽 내용 -->
  <v-expansion-panel-content>
    <!-- 회원목록 -->
    <div>
      <br>
      <h3 class="d-inline"> 회원목록 </h3>

      <!-- 인호 추가하기 버튼 테스트 -->
      <v-btn icon @click="addGroupUser()">
        <v-icon>
          mdi-plus-circle-outline
        </v-icon>
      </v-btn>
    </div>
    <v-container>
      <v-row class="my-4">
        <!-- 그룹안에 속한 회원 받아오기 groupUser 는 객체하나 -->
        <div class="col-6" v-for="groupUser in groupUsers" :key="groupUser.userId">
          <v-col class="d-inline">
            {{ groupUser.name }}
            <!-- 그룹의 유저 탈퇴버튼 -->
            <!-- <DelGroupUserButton
              class="d-inline pl-2"
              :groupId="groupId"
              :userId="groupUser.userId"
              @delGroupUser="getGroupInfo(groupId)"
            /> -->
          </v-col>
        </div>
      </v-row>
    </v-container>
    <!-- 테마목록 -->
    <v-divider></v-divider>
    <br>
    <div>
      <h3 class="d-inline"> 테마 </h3>
      <!-- <v-icon v-if="isAdmin" @click="addTheme(groupId)">mdi-plus-circle-outline</v-icon> -->
      <!-- 테마 추가 버튼 -->
      <ThemePlusButton
        class="d-inline pl-2"
        :groupId="groupId"
        @addTheme="getGroupInfo(groupId)"
      />
    </div>
    <v-container>
      <v-row class="my-4">
        <div class="col-6" v-for="groupTheme in groupThemes" :key="groupTheme.themeId">
          <v-col class="d-inline">
            {{ groupTheme.name }}
            <!-- <v-icon v-if="isAdmin" @click="delTheme(groupId, groupTheme.themeId)">
              mdi-minus-circle-outline
            </v-icon> -->
            <!-- 테마 삭제 버튼 -->
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
// import DelGroupUserButton from '@/components/mypage/DelGroupUserButton.vue';
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
    // DelGroupUserButton,
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
    // TODO : 세션 말고 넘어오는 데이터로 그룹장 확인하기
    this.isAdmin = this.$session.get('admin');
    this.getGroupInfo(this.groupId);
  },
};
</script>

<style>

</style>
