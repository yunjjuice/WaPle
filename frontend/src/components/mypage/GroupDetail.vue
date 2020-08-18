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
              mdi-account-multiple-plus
            </v-icon>
          </v-btn>
        </template>
        <span>회원 추가</span>
      </v-tooltip>
    </h3>
    <v-container>
      <v-row
      :class="{'mdUpRow': $vuetify.breakpoint.mdAndUp, 'smDownRow': $vuetify.breakpoint.smAndDown}">
        <v-col
          cols="12" sm="12" md="6"
          v-for="groupUser in groupUsers" :key="groupUser.userId"
          :class="{'mdUpCol': $vuetify.breakpoint.mdAndUp}"
        >
        {{ groupUser.name }}
        </v-col>
      </v-row>
    </v-container>
    <v-divider></v-divider>
    <br>
    <h3 class="d-inline">테마
      <v-tooltip bottom>
        <template v-slot:activator="{ on, attrs }">
          <v-btn
            icon
            v-bind="attrs"
            v-on="on"
            @click.stop="themeDialog = !themeDialog"
          >
            <v-icon>
              mdi-plus-circle-outline
            </v-icon>
          </v-btn>
        </template>
        <span>테마 추가</span>
      </v-tooltip>
    </h3>
    <v-container>
      <v-row
      :class="{'mdUpRow': $vuetify.breakpoint.mdAndUp, 'smDownRow': $vuetify.breakpoint.smAndDown}">
        <v-col
          cols="12" sm="12" md="6"
          v-for="(groupTheme) in groupThemes" :key="groupTheme.themeId"
          :class="{'mdUpCol': $vuetify.breakpoint.mdAndUp}"
        >
        <img style="height: 22px; width:21px; padding-right: 5px;" :src="groupTheme.icon"/>
        {{ groupTheme.name }}
        <v-tooltip bottom>
          <template v-slot:activator="{ on, attrs }">
            <v-btn
              icon
              v-bind="attrs"
              v-on="on"
              @click.stop="openModifyModal(groupTheme)"
            >
              <v-icon
                style="font-size: 0.8rem; position: relative; left: -0.7rem; top: -0.4rem;"
              >
                mdi-pencil-outline
              </v-icon>
            </v-btn>
          </template>
          <span>테마 수정</span>
        </v-tooltip>
        <theme-minus-button
          class="d-inline"
          :groupId="groupId"
          :themeId="groupTheme.themeId"
          @delTheme="getGroupInfo(groupId)"
        />
        </v-col>
      </v-row>
    </v-container>
    <theme-add-modal
      :themeDialog="themeDialog"
      :groupId="groupId"
      @closeTheme="themeDialog = !themeDialog"
      @updateTheme="getGroupInfo()"
    ></theme-add-modal>
    <theme-edit-modal
      :dialog="themeEditDialog"
      :theme="selectedTheme"
      @close="themeEditDialog = false"
      @update="getGroupInfo()"
    ></theme-edit-modal>
  </v-expansion-panel-content>
</template>

<script>
import api from '@/utils/api';
import store from '@/store/index';
import ThemeMinusButton from '@/components/mypage/ThemeMinusButton.vue';

export default {
  name: 'GroupDetail',
  props: {
    token: String,
    groupId: Number,
    groupName: String,
  },
  components: {
    ThemeMinusButton,
    ThemeAddModal: () => import('@/components/items/ThemeAddModal.vue'),
    ThemeEditModal: () => import('@/components/items/ThemeEditModal.vue'),
  },
  data() {
    return {
      groupUsers: null, // 특정 그룹에 속한 유저들 가져오기
      groupThemes: null, // 특정 그룹에 속한 테마들 가져오기
      themeName: '',
      flag: [],
      themeDialog: false,
      themeEditDialog: false,
      selectedTheme: {},
    };
  },
  methods: {
    getGroupInfo() {
      api.get(`groups/${this.groupId}`, {
        headers: {
          token: this.$session.get('token'),
        },
      }).then(({ data }) => {
        this.groupUsers = data;
      }).catch((err) => {
        console.error(err);
        store.dispatch('showSnackbar', { color: 'error', msg: '그룹 조회 실패, 다시 시도해주세요.' });
      });
      api.get(`themes/${this.groupId}`, {
        headers: {
          token: this.$session.get('token'),
        },
      }).then(({ data }) => {
        this.groupThemes = data;
      }).catch((err) => {
        console.error(err);
        store.dispatch('showSnackbar', { color: 'error', msg: '그룹 조회 실패, 다시 시도해주세요.' });
      });
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
    makeFlag() {
      this.flag = [];
      for (let i = 0; i < this.groupThemes.length; i += 1) {
        this.flag.push(false);
      }
    },
    openModifyModal(theme) {
      this.selectedTheme = theme;
      this.themeEditDialog = true;
    },
  },
  created() {
    this.getGroupInfo();
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

<style scoped>
.mdUpRow {
  padding-top: 5vh;
  padding-bottom: 5vh;
  padding-left: 10vh;
  padding-right: 10vh;
}
.smDownRow {
  margin: 1vh;
}
</style>
