<template>
  <!-- 오른쪽 내용부분 -->
  <v-flex md10>
    <div style="background-color: rgb(255, 204, 0);;">
      <h1 class="mt-5">내 그룹 정보</h1>
    </div>
    <hr class="mb-5">

    <!-- 내가 가입한 그룹들 리스트 -->
    <v-expansion-panels>
      <v-expansion-panel
        v-for="group in groups"
        :key="group.groupId"
        class="mt-4 border border-primary"
      >
        <v-expansion-panel-header >
          그룹이름 : {{ group.name }}
        </v-expansion-panel-header>
        <!-- 그룹 눌렀을때 안쪽 내용 -->
        <group-detail :groupId="group.groupId" :groupName="group.groupName" />
      </v-expansion-panel>
    </v-expansion-panels>

    <!-- 그룹 만들기 버튼&모달 -->
    <!-- <group-add class="mt-5" /> -->
    <group-add2 class="mt-5" @addGroup="getGroups" />
  </v-flex>
</template>

<script>
// import GroupAdd from '@/components/mypage/GroupAdd.vue';
import api from '@/utils/api';
import GroupDetail from '@/components/mypage/GroupDetail.vue';
import GroupAdd2 from '@/components/mypage/GroupAdd2.vue';

export default {
  name: 'Group',
  components: {
    // GroupAdd,
    GroupAdd2,
    GroupDetail,
  },
  data() {
    return {
      isAdmin: false, // 매니저이어야 삭제버튼 보이게 하려고 만듦
      groups: null, // 그룹 lists
      groupUsers: null, // 특정 그룹에 속한 유저들 가져오기
      groupThemes: null, // 특정 그룹에 속한 테마들 가져오기
    };
  },
  methods: {
    getGroups() {
      const userId = this.$session.get('uid');
      api.get(`groups/of/${userId}`)
        .then((res) => {
          this.groups = res.data;
          console.log(this.groups, '그룹리스트를 가져왔어요!');
        })
        .catch((err) => console.log(err));
    },
    // getGroupInfo(groupId) {
    //   // 그룹에 속한 유저목록 가져오기
    //   api.get(`groups/${groupId}`)
    //     .then((res) => {
    //       console.log(res, '유저목록 가져왔어요!');
    //       this.groupUsers = res.data;
    //     })
    //     .catch((err) => console.log(err));

    //   // 그룹에 속한 테마목록 가져오기
    //   api.get(`themes/${groupId}`, { headers: { token: this.$session.get('token') } })
    //     .then((res) => {
    //       console.log(res, '테마목록 가져왔어요!');
    //       this.groupThemes = res.data;
    //     })
    //     .catch((err) => console.log(err));
    // },
    // addGroupUser(groupId) {
    //   console.log('addGroupUser() 실행');
    //   // modal 창 띄워서 추가할 사람 이름 넣고 해야겠음 (아래 userId 는 인호)
    //   api.post('groups/member/', {
    //     groupId, // object-shorthand
    //     userId: 1412733569,
    //   })
    //     .then((res) => {
    //       console.log(res, '유저 추가했습니다.');
    //       this.getGroupInfo(groupId);
    //     })
    //     .catch((err) => console.log(err));
    // },
    // delGroupUser(groupId, userId) {
    //   api.delete(`groups/${groupId}/${userId}`)
    //     .then((res) => {
    //       console.log(res, '유저 삭제했습니다.');
    //       this.getGroupInfo(groupId);
    //     })
    //     .catch((err) => console.log(err));
    // },
  },
  created() {
    this.isAdmin = this.$session.get('admin');
    this.getGroups();
  },
  mounted() {

  },
  updated() {
    console.log('data update!');
  },
  watch: {

  },
};
</script>

<style>

</style>
