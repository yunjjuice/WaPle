<template>
  <v-expansion-panel-content>
    <br>
    <h3 class="d-inline">회원목록
      <v-tooltip bottom>
        <template v-slot:activator="{ on, attrs }">
          <v-btn
            icon
            v-bind="attrs"
            v-on="on"
            @click="addGroupUser()"
          >
            <v-icon>
              mdi-plus-circle-outline
            </v-icon>
          </v-btn>
        </template>
        <span>멤버 추가</span>
      </v-tooltip>
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
    token: String,
    groupId: Number,
    groupName: String,
  },
  components: {
    ThemePlusButton,
    ThemeMinusButton,
  },
  data() {
    return {
      groupUsers: null, // 특정 그룹에 속한 유저들 가져오기
      groupThemes: null, // 특정 그룹에 속한 테마들 가져오기
    };
  },
  methods: {
    getGroupInfo(groupId) {
      // 그룹에 속한 유저목록 가져오기
      api.get(`groups/${groupId}`)
        .then((res) => {
          this.groupUsers = res.data;
        })
        .catch((err) => console.log(err));

      // 그룹에 속한 테마목록 가져오기
      api.get(`themes/${groupId}`, { headers: { token: this.$session.get('token') } })
        .then((res) => {
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
