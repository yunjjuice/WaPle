<template>
  <v-flex md10>
    <br><br>
    <v-expansion-panels focusable>
      <v-expansion-panel
        v-for="group in groups"
        :key="group.groupId"
        class="mt-4 border"
      >
        <v-expansion-panel-header style="font-size:20px; font-weight:570;">
          <div class="d-inline">
          {{ group.name }}
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
            <sapn>그룹 탈퇴</sapn>
          </v-tooltip>
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

export default {
  name: 'Group',
  data: () => ({

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
  },
  created() {
    store.dispatch('getGroups');
  },
};
</script>

<style>

</style>
