<template>
<v-main>
  <v-container
    :class="{'panel-padding': $vuetify.breakpoint.mdAndUp}"
  >
    <br><br>
    <v-expansion-panels focusable>
      <v-expansion-panel
        v-for="(group, index) in groups"
        :key="index"
        style="margin-bottom: 1rem;"
      >
        <v-expansion-panel-header dark
          style="font-size:20px; font-weight:570;"
        >
          <div class="d-inline">
            <v-row>
              <v-col cols="8">
                {{ group.name }}
                <v-tooltip bottom>
                  <template v-slot:activator="{ on, attrs }">
                    <v-btn
                      icon
                      v-bind="attrs"
                      v-on="on"
                      @click.stop="showEditDialog(group)"
                    >
                      <v-icon
                        style="font-size: 0.8rem; position: relative; left: -0.7rem; top: -0.4rem;"
                      >
                        mdi-pencil-outline
                      </v-icon>
                    </v-btn>
                  </template>
                  <span>그룹 이름 수정</span>
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
                      @click.stop="showLeaveGroupModal(group)"
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
    <group-edit
      :editDialog="editDialog"
      :group="group"
      @closeGroupEdit="editDialog = !editDialog"
    />
    <br><br>
  </v-container>
</v-main>
</template>

<script>
import store from '@/store/index';

export default {
  name: 'Group',
  data: () => ({
    flag: [],
    groupName: '',
    groupValid: true,
    rules: {
      required: (value) => !!value || 'theme can not be empty',
      counter: (value) => (value && value.length <= 15) || 'Max 15 chracters',
    },
    editDialog: false,
    group: '',
  }),
  components: {
    AddGroup: () => import('@/components/mypage/AddGroup.vue'),
    GroupDetail: () => import('@/components/mypage/GroupDetail.vue'),
    LeaveGroupModal: () => import('@/components/mypage/LeaveGroupModal.vue'),
    GroupEdit: () => import('@/components/items/GroupEdit.vue'),
  },
  computed: {
    groups: () => store.getters.groups,
  },
  methods: {
    showLeaveGroupModal(group) {
      store.commit('openLeaveGroupDialog', group);
    },
    showEditDialog(group) {
      this.group = group;
      this.editDialog = !this.editDialog;
    },
  },
  created() {
    store.dispatch('getGroups');
  },
};
</script>

<style scoped>
.panel-padding {
  padding-left: 100px;
  padding-right: 100px;
  padding-top: 10px;
  padding-bottom: 10px;
}
.col-xl, .col-xl-auto, .col-xl-12, .col-xl-11, .col-xl-10, .col-xl-9, .col-xl-8,
.col-xl-7, .col-xl-6, .col-xl-5, .col-xl-4, .col-xl-3, .col-xl-2, .col-xl-1, .col-lg,
.col-lg-auto, .col-lg-12, .col-lg-11, .col-lg-10, .col-lg-9, .col-lg-8, .col-lg-7, .col-lg-6,
.col-lg-5, .col-lg-4, .col-lg-3, .col-lg-2, .col-lg-1, .col-md, .col-md-auto, .col-md-12,
.col-md-11, .col-md-10, .col-md-9, .col-md-8, .col-md-7, .col-md-6, .col-md-5, .col-md-4,
.col-md-3, .col-md-2, .col-md-1, .col-sm, .col-sm-auto, .col-sm-12, .col-sm-11, .col-sm-10,
.col-sm-9, .col-sm-8, .col-sm-7, .col-sm-6, .col-sm-5, .col-sm-4, .col-sm-3, .col-sm-2,
.col-sm-1, .col, .col-auto, .col-12, .col-11, .col-10, .col-9, .col-8, .col-7, .col-6, .col-5,
.col-4, .col-3, .col-2, .col-1 {
  padding-top: 0px;
  padding-bottom: 0px;
}
</style>
