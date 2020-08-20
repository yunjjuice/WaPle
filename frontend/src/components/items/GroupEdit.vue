<template>
<v-main>
    <v-dialog
      v-model="dialog"
      max-width="600px"
    >
      <v-card>
        <v-card-title class="yellow lighten-3">그룹 이름 수정하기</v-card-title>
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
</v-main>
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
        required: (value) => !!value || 'group can not be empty',
        counter: (value) => (value && value.length <= 15) || 'Max 15 chracters',
      },
      valid: true,
    };
  },
  props: [
    'editDialog',
    'group',
  ],
  watch: {
    editDialog() {
      this.dialog = !this.dialog;
    },
    group: {
      deep: true,
      handler(val) {
        this.name = val.name;
      },
    },
  },
  methods: {
    modifyGroupName() {
      api.put('/groups', {
        groupId: this.group.groupId,
        name: this.name,
      }, {
        headers: {
          token: this.$session.get('token'),
        },
      }).then(() => {
        store.dispatch('getGroups');
        this.$toast.success('그룹 이름 수정 성공');
      }).catch((err) => {
        console.error(err);
        this.$toast.error('그룹 이름 수정 실패, 다시 시도해주세요.');
      });
      this.closeModal();
    },
    isValid() {
      this.$refs.groupForm.validate();
      if (this.valid) {
        this.modifyGroupName();
      }
    },
    closeModal() {
      this.$emit('closeGroupEdit');
    },
  },
};
</script>

<style>

</style>
