<template>
  <div class="v-item-group theme--light v-expansion-panels" style="margin-top:0;">
    <v-dialog
      v-model="dialog"
      max-width="600px"
    >
      <!-- Modal 버튼부분 -->
      <!-- v-slot:activator 의 의미를 모르겠음.. -->
      <template v-slot:activator="{ on, attrs }">
        <v-expansion-panel class="mt-4 border">
          <v-expansion-panel-header
            dark
            v-bind="attrs"
            v-on="on"
            style="color: red; font-size:20px; font-weight:570;"
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
                <v-form ref="groupForm" v-model="valid">
                  <v-text-field
                    label="그룹 이름"
                    v-model="name"
                    required
                    :rules="[rules.required, rules.counter]"
                    counter
                    maxlength="10"
                  />
                </v-form>
              </v-col>
            </v-row>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="blue darken-1" text @click="closeModal">Close</v-btn>
          <v-btn color="blue darken-1" text @click="isValid">Save</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
import store from '@/store/index';
import api from '@/utils/api';

export default {
  name: 'GroupAdd2',
  data() {
    return {
      dialog: false,
      name: '',
      rules: {
        required: (value) => !!value || 'theme can not be empty',
        counter: (value) => (value && value.length <= 10) || 'Max 10 chracters',
      },
      valid: true,
    };
  },
  methods: {
    getGroups() {
      const userId = this.$session.get('uid');
      api.get(`groups/of/${userId}`)
        .then((res) => {
          this.groups = res.data;
          // console.log(this.groups, '그룹리스트를 가져왔어요!');
        })
        .catch((err) => console.log(err));
    },
    isValid() {
      this.$refs.groupForm.validate();
      if (this.valid) {
        this.addGroup();
      }
    },
    addGroup() {
      this.dialog = false;
      // console.log('add Group!!');
      api.post('groups/', {
        name: this.name,
        userId: this.$session.get('uid'),
      })
        .then(() => {
          // 부모에게 addGroup 이란 event 발생후, getGroups() 를 실행시켜서 화면에 바로 보이게 업데이트
          this.$emit('addGroup');
          const payload = { color: 'success', msg: '그룹 생성 완료' };
          store.dispatch('showSnackbar', payload);
        })
        .catch(() => {
          const payload = { color: 'error', msg: '그룹 생성 실패' };
          store.dispatch('showSnackbar', payload);
        });
      this.closeModal();
    },
    closeModal() {
      if (!this.valid) {
        this.$refs.groupForm.reset();
      }
      this.dialog = false;
    },
  },
};
</script>

<style scoped>
.v-application .mt-5 {
  margin-top: 0 !important;
}
</style>
