<template>
  <!-- 그룹 눌렀을때 안쪽 내용 -->
  <v-expansion-panel-content>
    <!-- 회원목록 -->
    <div>
      <h3 style="color: white; background-color: gray;" class="d-inline"> 회원목록 </h3>
      <AddGroupUserButton
        class="d-inline"
        :is-admin="isAdmin"
        :group-groupId="groupId"
      />

      <!-- 인호 추가하기 버튼 테스트 -->
      <v-btn v-if="isAdmin" @click="addGroupUser(groupId)">
        인호추가하기 test
      </v-btn>
      <!--  -->
      <hr class="border border-primary">
    </div>
    <v-container>
      <v-row class="my-4">
        <!-- 그룹안에 속한 회원 받아오기 groupUser 는 객체하나 -->
        <div class="col-6" v-for="groupUser in groupUsers" :key="groupUser.userId">
          <v-col class="d-inline">
            {{ groupUser.name }}
            <!-- 그룹의 유저 탈퇴버튼 -->
            <DelGroupUserButton
              class="d-inline pl-2"
              :groupId="groupId"
              :userId="groupUser.userId"
              @delGroupUser="getGroupInfo(groupId)"
            />
          </v-col>
        </div>
      </v-row>
    </v-container>
    <!-- 테마목록 -->
    <div>
      <h3 style="color: white; background-color: gray;" class="d-inline"> 테마 </h3>
      <!-- <v-icon v-if="isAdmin" @click="addTheme(groupId)">mdi-plus-circle-outline</v-icon> -->
      <!-- 테마 추가 버튼 -->
      <ThemePlusButton
        class="d-inline pl-2"
        :groupId="groupId"
        @addTheme="getGroupInfo(groupId)"
      />
      <hr class="border border-primary">
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

import AddGroupUserButton from '@/components/mypage/AddGroupUserButton.vue';
import DelGroupUserButton from '@/components/mypage/DelGroupUserButton.vue';
import ThemePlusButton from '@/components/mypage/ThemePlusButton.vue';
import ThemeMinusButton from '@/components/mypage/ThemeMinusButton.vue';

export default {
  name: 'GroupDetail',
  props: {
    groupId: null,
  },
  components: {
    AddGroupUserButton, // 그룹에 회원 추가하기 버튼 컴포넌트
    DelGroupUserButton,
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
          console.log(res, '유저목록 가져왔어요!');
          this.groupUsers = res.data;
        })
        .catch((err) => console.log(err));

      // 그룹에 속한 테마목록 가져오기
      api.get(`themes/${groupId}`, { headers: { token: this.$session.get('token') } })
        .then((res) => {
          console.log(res, '테마목록 가져왔어요!');
          this.groupThemes = res.data;
        })
        .catch((err) => console.log(err));
    },
    addGroupUser(groupId) {
      console.log('addGroupUser() 실행');
      // modal 창 띄워서 추가할 사람 이름 넣고 해야겠음 (아래 userId 는 인호)
      api.post('groups/member/', {
        groupId, // object-shorthand
        userId: 1412733569,
      })
        .then((res) => {
          console.log(res, '유저 추가했습니다.');
          this.getGroupInfo(groupId);
        })
        .catch((err) => console.log(err));
    },
    delGroupUser(groupId, userId) {
      api.delete(`groups/${groupId}/${userId}`)
        .then((res) => {
          console.log(res, '유저 삭제했습니다.');
          alert('유저 삭제 완료');
          this.getGroupInfo(groupId);
        })
        .catch((err) => console.log(err));
    },
    addTheme(groupId) {
      console.log('addTheme() 실행');
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
        .then((res) => {
          console.log(res, '테마생성 성공');
          this.getGroupInfo(groupId);
        })
        .catch((res) => console.log(res), '테마생성 실패');
    },
    delTheme(groupId, themeId) {
      console.log('delTheme() 실행');
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
