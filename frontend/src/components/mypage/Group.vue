<template>
<v-main>
  <v-container
    :class="{'panel-padding': $vuetify.breakpoint.mdAndUp}"
  >
    <br><br>
    <v-expansion-panels focusable>
      <v-expansion-panel
        v-for="(group, index) in groups"
        :key="group.groupId"
        style="margin-bottom: 1rem;"
      >
        <v-expansion-panel-header style="font-size:20px; font-weight:570;">
          <div class="d-inline">
            <v-row>
              <v-col cols="8">
                <template v-if="!flag[index]">
                  {{ group.name }}
                </template>
                <template v-if="flag[index]">
                  <v-text-field
                    v-model="groupName"
                    outlined
                    @click.stop
                    style="width: 10rem;"
                  ></v-text-field>
                </template>
                <v-tooltip bottom>
                  <template v-slot:activator="{ on, attrs }">
                    <v-btn
                      icon
                      v-bind="attrs"
                      v-on="on"
                      @click.stop="modifyFlag(group, index)"
                    >
                      <v-icon
                        style="font-size: 0.8rem; position: relative; left: -0.7rem; top: -0.4rem;"
                      >
                        mdi-pencil-outline
                      </v-icon>
                    </v-btn>
                  </template>
                  <span>그룹명 변경</span>
                </v-tooltip>
              </v-col>
              <v-col cols="1">
              </v-col>
              <v-col cols="1">
              </v-col>
              <v-col cols="1">
                <v-tooltip bottom>
                  <template v-slot:activator="{ on, attrs }">
                    <v-btn
                      icon
                      v-bind="attrs"
                      v-on="on"
                      @click="showLeaveGroupModal(group.groupId)"
                    >
                      <v-icon>
                        mdi-delete
                      </v-icon>
                    </v-btn>
                  </template>
                  <span>그룹 탈퇴</span>
                </v-tooltip>
              </v-col>
            </v-row>
          </div>
        </v-expansion-panel-header>
        <group-detail :groupId="group.groupId" :groupName="group.name" :token="group.token"/>
      </v-expansion-panel>
      <add-group/>
    </v-expansion-panels>
    <leave-group-modal/>
    <br><br>
  </v-container>
</v-main>
</template>

<script>
import store from '@/store/index';
import api from '@/utils/api';

export default {
  name: 'Group',
  data: () => ({
    flag: [],
    groupName: '',
  }),
  components: {
    AddGroup: () => import('@/components/mypage/AddGroup.vue'),
    GroupDetail: () => import('@/components/mypage/GroupDetail.vue'),
    LeaveGroupModal: () => import('@/components/mypage/LeaveGroupModal.vue'),
  },
  computed: {
    groups: () => store.getters.groups,
  },
  methods: {
    showLeaveGroupModal(groupId) {
      store.commit('openLeaveGroupDialog', groupId);
    },
    makeFlag() {
      this.flag = [];
      for (let i = 0; i < this.groups.length; i += 1) {
        this.flag.push(false);
      }
    },
    modifyFlag(group, index) {
      if (!this.flag[index]) {
        this.groupName = group.name;
        this.makeFlag();
        this.flag.splice(index, 1, true);
      } else {
        if (group.name !== this.groupName) { // 값이 변했을 때만 수정
          api.put('/groups', {
            groupId: group.groupId,
            name: this.groupName,
          }, {
            headers: {
              token: this.$session.get('token'),
            },
          }).then(() => {
            store.dispatch('getGroups');
            this.$toast.success('그룹 이름 수정 성공');
          }).catch((err) => {
            console.error(err);
            this.$toast.error('그룹 이름 수정 실패, 다시 시도해주세요.');
          });
        }
        this.makeFlag();
      }
    },
  },
  created() {
    store.dispatch('getGroups');
  },
};
</script>

<style>
.panel-padding {
  padding-left: 100px;
  padding-right: 100px;
  padding-top: 10px;
  padding-bottom: 10px;
}
</style>
