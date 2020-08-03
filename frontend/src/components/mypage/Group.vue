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
        v-for="(item,i) in 5"
        :key="i"
        class="mt-4 border border-primary"
      >
        <v-expansion-panel-header> <strong>토핑스 그룹 {{ i }}</strong> </v-expansion-panel-header>
        <v-expansion-panel-content>
          <!-- 그룹 눌렀을때 안쪽 내용 -->
          <!-- 회원목록 -->
          <div>
            <h3 style="color: white; background-color: gray;" class="d-inline"> 회원목록 </h3>
            <v-icon v-if="isAdmin">mdi-plus-circle-outline</v-icon>
            <hr class="border border-primary">
          </div>
          <v-container>
            <v-row class="my-4">
              <div class="col-6" v-for="i in 3" :key=i>
                <v-col class="d-inline">
                  Username {{ i }}
                  <v-icon v-if="isAdmin">mdi-minus-circle-outline</v-icon>
                </v-col>
              </div>
            </v-row>
          </v-container>
          <!-- 테마목록 -->
          <div>
            <h3 style="color: white; background-color: gray;" class="d-inline"> 테마 </h3>
            <v-icon v-if="isAdmin">mdi-plus-circle-outline</v-icon>
            <hr class="border border-primary">
          </div>
          <v-container>
            <v-row class="my-4">
              <div class="col-6" v-for="i in 4" :key=i>
                <v-col class="d-inline">
                  테마 목록 {{ i }}
                  <v-icon v-if="isAdmin">mdi-minus-circle-outline</v-icon>
                </v-col>
              </div>
            </v-row>
          </v-container>
        </v-expansion-panel-content>
      </v-expansion-panel>
    </v-expansion-panels>

    <!-- 그룹 만들기 버튼&모달 -->
    <!-- <group-add class="mt-5" /> -->
    <group-add2 class="mt-5" />
  </v-flex>
</template>

<script>
// import GroupAdd from '@/components/mypage/GroupAdd.vue';
// import axios from 'axios';
import GroupAdd2 from '@/components/mypage/GroupAdd2.vue';

export default {
  name: 'Group',
  components: {
    // GroupAdd,
    GroupAdd2,
  },
  data() {
    return {
      isAdmin: true, // 매니저이어야 삭제버튼 보이게 하려고 만듦 ( false )
    };
  },
  methods: {

  },
  created() {
    // this.isAdmin = this.$session.get('admin');
  },
  mounted() {

  },
};
</script>

<style>

</style>
