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
          v-bind="attrs"
          v-on="on"
        >
          mdi-plus-circle-outline
        </v-icon>
      </template>

      <!-- Modal 안쪽 내용 -->
      <v-card>
        <v-card-title>
          <span class="headline">테마 추가하기</span>
        </v-card-title>
        <v-card-text>
          <v-container>
            <v-row>
              <v-col cols="12">
                <v-text-field
                  label="테마명*"
                  hint="테마이름을 입력하세요."
                  v-model="themeName"
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
          <v-btn color="blue darken-1" text @click="addTheme(groupId)">Add</v-btn>
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
    groupId: Number,
  },
  data() {
    return {
      dialog: false,
      isAdmin: false,
      themeName: '',
    };
  },
  methods: {
    addTheme(groupId) {
      console.log('addTheme() 실행');
      this.dialog = false;
      api.post('themes/', {
        groupId,
        icon: 'pizza.io',
        name: this.themeName,
      },
      {
        headers: {
          token: this.$session.get('token'),
        },
      })
        .then((res) => {
          console.log(res, '테마생성 성공');
          this.$emit('addTheme');
          this.themeName = '';
        })
        .catch((err) => console.log(err), '테마생성 실패');
    },
  },
  created() {
    this.isAdmin = this.$session.get('admin');
  },
};
</script>
