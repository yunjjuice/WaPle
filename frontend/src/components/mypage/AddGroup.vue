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
            <div class="d-inline">
              <v-row>
                <v-col cols="8">
              그룹 추가하기
                </v-col>
              </v-row>
            </div>
          </v-expansion-panel-header>
        </v-expansion-panel>
      </template>

      <v-card>
        <v-card-title class="yellow lighten-3">그룹 추가하기</v-card-title>
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
                    @keypress.enter.stop.prevent="isValid"
                  />
                </v-form>
              </v-col>
            </v-row>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="green darken-1" text @click="closeModal">Close</v-btn>
          <v-btn color="green darken-1" text @click="isValid">Save</v-btn>
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
      this.$refs.groupForm.reset();
      this.dialog = false;
    },
  },
};
</script>

<style scoped>
.v-application .mt-5 {
  margin-top: 0 !important;
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
  padding-top: 0.4rem;
  padding-bottom: 0.4rem;
}
</style>
