<template>
  <div class="text-center">
    <v-dialog
      v-model="dialog"
      max-width="600px"
    >
      <!-- Modal 버튼부분 -->
      <!-- v-slot:activator 의 의미를 모르겠음.. -->
      <template v-slot:activator="{ on, attrs }">
        <v-expansion-panel class="mt-4 border border-primary">
          <v-expansion-panel-header
            dark
            v-bind="attrs"
            v-on="on"
            style="color: red;"
          >
            그룹 추가하기
          </v-expansion-panel-header>
        </v-expansion-panel>
      </template>

      <!-- Modal 안쪽 내용 -->
      <v-card>
        <v-card-title>
          <span class="headline">그룹 추가하기</span>
        </v-card-title>
        <v-card-text>
          <v-container>
            <v-row>
              <v-col cols="12">
                <v-text-field
                  label="그룹명*"
                  hint="그룹이름을 입력하세요."
                  persistent-hint
                  required
                  v-model="name"
                />
              </v-col>
            </v-row>
          </v-container>
          <small>*필수 입력항목</small>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="blue darken-1" text @click="dialog = false">Close</v-btn>
          <v-btn color="blue darken-1" text @click="addGroup">Save</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
import api from '@/utils/api';

export default {
  name: 'GroupAdd2',
  data() {
    return {
      dialog: false,
      name: '',
    };
  },
  methods: {
    getGroups() {
      const userId = this.$session.get('uid');
      api.get(`groups/of/${userId}`)
        .then((res) => {
          this.groups = res.data;
          console.log(this.groups, '그룹리스트를 가져왔어요!');
        })
        .catch((err) => console.log(err));
    },
    addGroup() {
      this.dialog = false;
      console.log('add Group!!');
      api.post('groups/', {
        name: this.name,
        userId: this.$session.get('uid'),
      })
        .then((res) => {
          console.log('그룹생성 성공!', res);
          // 부모에게 addGroup 이란 event 발생후, getGroups() 를 실행시켜서 화면에 바로 보이게 업데이트
          this.$emit('addGroup');
        })
        .catch((err) => console.log('그룹생성 실패', err));
    },
  },
};
</script>
