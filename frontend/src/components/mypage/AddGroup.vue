<template>
  <div class="v-item-group theme--light v-expansion-panels" style="margin-top:0;">
    <v-dialog
      v-model="dialog"
      max-width="600px"
    >
      <template v-slot:activator="{ on, attrs }">
        <v-expansion-panel>
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
                    maxlength="15"
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
  data() {
    return {
      dialog: false,
      name: '',
      rules: {
        required: (value) => !!value || 'theme can not be empty',
        counter: (value) => (value && value.length <= 15) || 'Max 15 chracters',
      },
      valid: true,
    };
  },
  methods: {
    isValid() {
      this.$refs.groupForm.validate();
      if (this.valid) {
        this.addGroup();
      }
    },
    addGroup() {
      this.dialog = false;
      api.post('groups/', {
        name: this.name,
        userId: this.$session.get('uid'),
      })
        .then(() => {
          this.$emit('addGroup');
          store.dispatch('getGroups');
          this.$toast.success('그룹 생성 성공');
        })
        .catch((err) => {
          console.error(err);
          this.$toast.error('그룹 생성 실패, 다시 시도해주세요.');
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
