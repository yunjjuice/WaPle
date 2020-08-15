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
        <span>회원 추가</span>
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
      <v-row class="my-4">
        <div class="col-6" v-for="(groupTheme, index) in groupThemes" :key="groupTheme.themeId">
          <v-col class="d-inline">
            <template v-if="!flag[index]">
              {{ groupTheme.name }}
            </template>
            <template v-else>
              <v-text-field
                v-model="themeName"
                outlined
                @click.stop
                style="width: 300px;"
              ></v-text-field>
            </template>
            <v-tooltip bottom>
              <template v-slot:activator="{ on, attrs }">
                <v-btn
                  icon
                  v-bind="attrs"
                  v-on="on"
                  @click.stop="modifyFlag(groupTheme, index)"
                >
                  <v-icon>mdi-pencil-outline</v-icon>
                </v-btn>
              </template>
              <span>테마명 변경</span>
            </v-tooltip>
            <theme-minus-button
              class="d-inline pl-2"
              :groupId="groupId"
              :themeId="groupTheme.themeId"
              @delTheme="getGroupInfo(groupId)"
            />
          </v-col>
        </div>
      </v-row>
    </v-container>
    <theme-add-modal
      :themeDialog="themeDialog"
      :groupId="groupId"
      @closeTheme="themeDialog = !themeDialog"
      @updateTheme="getGroupInfo(groupId)"
    ></theme-add-modal>
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
  },
  data() {
    return {
      groupUsers: null, // 특정 그룹에 속한 유저들 가져오기
      groupThemes: null, // 특정 그룹에 속한 테마들 가져오기
      themeName: '',
      flag: [],
      themeDialog: false,
    };
  },
  methods: {
    getGroupInfo(groupId) {
      api.get(`groups/${groupId}`, {
        headers: {
          token: this.$session.get('token'),
        },
      }).then((res) => {
        this.groupUsers = res.data;
      }).catch((err) => console.log(err));
      api.get(`themes/${groupId}`, {
        headers: {
          token: this.$session.get('token'),
        },
      }).then((res) => {
        this.groupThemes = res.data;
      }).catch((err) => console.log(err));
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
    modifyFlag(theme, index) {
      if (!this.flag[index]) {
        this.themeName = theme.name;
        this.makeFlag();
        this.flag.splice(index, 1, true);
      } else {
        if (theme.name !== this.themeName) { // 값이 변했을 때만 수정
          api.put('/themes', {
            groupId: theme.groupId,
            icon: '',
            name: this.themeName,
            themeId: theme.themeId,
          }, {
            headers: {
              token: this.$session.get('token'),
            },
          }).then(() => {
            this.getGroupInfo(theme.groupId);
            const payload = { color: 'success', msg: '테마명 수정 완료' };
            store.dispatch('showSnackbar', payload);
          }).catch((res) => {
            console.log(res);
          });
        }
        this.makeFlag();
      }
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
