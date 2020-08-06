<template>
  <v-row justify="center">
    <v-dialog v-model="dialog" persistent max-width="290">
      <template v-slot:activator="{ on, attrs }">
        <v-icon
          v-if="isAdmin"
          v-bind="attrs"
          v-on="on"
        >
          mdi-minus-circle-outline
        </v-icon>
      </template>
      <v-card>
        <v-card-title>정말 삭제하시겠습니까?</v-card-title>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="green darken-1" text @click="dialog = false">No</v-btn>
          <v-btn color="green darken-1" text @click="delGroupUser(groupId, userId)">Yes</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-row>
</template>

<script>
import api from '@/utils/api';

export default {
  name: 'UserMinusButton',
  props: {
    groupId: null,
    userId: Number,
  },
  components: {
  },
  data() {
    return {
      dialog: false,
      isAdmin: false,
    };
  },
  methods: {
    getGroupInfo(groupId) {
      // 그룹에 속한 유저목록 가져오기
      api.get(`groups/${groupId}`)
        .then((res) => {
          console.log(res, '유저목록 가져왔어요!');
          this.groupUsers = res.data;
        })
        .catch((err) => console.log(err));

      // 그룹에 속한 테마목록 가져오기
      api.get(`themes/${groupId}`, { headers: { token: this.$session.get('token') } })
        .then((res) => {
          console.log(res, '테마목록 가져왔어요!');
          this.groupThemes = res.data;
        })
        .catch((err) => console.log(err));
    },
    delGroupUser(groupId, userId) {
      api.delete(`groups/${groupId}/${userId}`)
        .then((res) => {
          console.log(res, '유저 삭제했습니다.');
          // this.getGroupInfo(groupId);
          this.$emit('delGroupUser');
        })
        .catch((err) => console.log(err));
    },
  },
  created() {
    this.isAdmin = this.$session.get('admin');
  },
};
</script>
