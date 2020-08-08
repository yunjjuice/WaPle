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
          {{ group.name }}
        </v-expansion-panel-header>
        <group-detail :groupId="group.groupId" :groupName="group.name" :token="group.token"/>
      </v-expansion-panel>
      <add-group class="mt-5"/>
    </v-expansion-panels>
  </v-flex>
</template>

<script>
import GroupDetail from '@/components/mypage/GroupDetail.vue';
import AddGroup from '@/components/mypage/AddGroup.vue';
import store from '@/store/index';

export default {
  name: 'Group',
  components: {
    AddGroup,
    GroupDetail,
  },
  computed: {
    groups: () => store.getters.groups,
  },
  created() {
    store.dispatch('getGroups');
  },
};
</script>

<style>

</style>
