<template>
  <v-flex md10>
    <br><br>
    <v-expansion-panels focusable>
      <v-expansion-panel
        v-for="(group, index) in groups"
        :key="group.groupId"
        class="mt-4 border"
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
                    style="width: 300px;"
                  ></v-text-field>
                </template>
              </v-col>
              <v-col cols="1">
                <v-tooltip bottom>
                  <template v-slot:activator="{ on, attrs }">
                    <v-btn
                      icon
                      v-bind="attrs"
                      v-on="on"
                      @click.stop="modifyFlag(group, index)"
                    >
                      <v-icon>mdi-pencil-outline</v-icon>
                    </v-btn>
                  </template>
                  <span>그룹명 변경</span>
                </v-tooltip>
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
                        mdi-minus-circle-outline
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
      <add-group class="mt-5"/>
    </v-expansion-panels>
    <leave-group-modal/>
  </v-flex>
</template>

<script>
import GroupDetail from '@/components/mypage/GroupDetail.vue';
import AddGroup from '@/components/mypage/AddGroup.vue';
import LeaveGroupModal from '@/components/mypage/LeaveGroupModal.vue';
import store from '@/store/index';
import api from '@/utils/api';

export default {
  name: 'Group',
  data: () => ({
    flag: [],
    groupName: '',
  }),
  components: {
    AddGroup,
    GroupDetail,
    LeaveGroupModal,
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
          }).then((res) => {
            console.log(res);
            store.dispatch('getGroups');
            const payload = { color: 'success', msg: '그룹명 수정 완료' };
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
    store.dispatch('getGroups');
  },
};
</script>

<style>

</style>
