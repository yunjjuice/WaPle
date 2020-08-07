<template>
  <div class="text-center">
    <v-dialog
      v-model="dialog"
      max-width="600px"
    >
      <!-- Modal 버튼부분 -->
      <!-- v-slot:activator 의 의미를 모르겠음.. -->
      <template v-slot:activator="{ on, attrs }">
        <v-icon
          v-if="isAdmin"
          @click="addGroupUser(groupGroupId)"
          v-bind="attrs"
          v-on="on"
        >
          mdi-plus-circle-outline
        </v-icon>
      </template>

      <!-- Modal 안쪽 내용 -->
      <v-card>
        <v-card-title>
          <span class="headline">회원 추가하기</span>
        </v-card-title>
        <v-card-text>
          <v-container>
            <v-row>
              <v-col cols="12">
                <v-text-field
                  label="회원명*"
                  hint="회원이름을 입력하세요."
                  persistent-hint required
                />
              </v-col>
            </v-row>
          </v-container>
          <small>*필수 입력항목</small>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="blue darken-1" text @click="dialog = false">Close</v-btn>
          <v-btn color="blue darken-1" text @click="dialog = false">Save</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
import api from '@/utils/api';

export default {
  name: 'AddGroupUserButton',
  props: {
    isAdmin: Boolean,
    groupGroupId: null,
  },
  data() {
    return {
      dialog: false,
    };
  },
  methods: {
    addGroupUser(groupId) {
      console.log('addGroupUser() 실행');
      // modal 창 띄워서 추가할 사람 이름 넣고 해야겠음 (아래 userId 는 인호)
      api.post('groups/member/', {
        groupId, // object-shorthand
        userId: 1412733569,
      })
        .then((res) => {
          console.log(res, '유저 추가했습니다.');
          this.getGroupInfo(groupId);
        })
        .catch((err) => console.log(err));
    },
  },
};
</script>
