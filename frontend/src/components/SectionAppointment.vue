<template>
  <v-main>
    <v-toolbar color="#e6b800" dense flat>
      <v-btn icon class="hidden-xs-only" @click="moveBack">
        <v-icon>mdi-arrow-left</v-icon>
      </v-btn>
      <v-toolbar-title>{{ appointment.title }}</v-toolbar-title>
    </v-toolbar>
    <v-row align='center' justify='center'>
      <v-col
        v-for="(item, i) in items"
          :key="i"
          cols="12"
      >
        <v-card
        >
          <div class="d-flex flex-no-wrap justify-space-between">
            <div>
              <v-card-title
                class="headline"
                v-text="item.name"
              />
              <v-card-text>
                {{ item.address }} <br>
                {{ item.pickedUserName }}<br>
                <!--
                  TODO : 투표하기
                  투표 후에 items를 다시 싹 업데이트 해야 voted가 잘 넘어올 텐데 ..?
                -->
                <v-btn icon>
                  <template v-if="!item.voted">
                    <v-icon>mdi-thumb-up-outline</v-icon>
                  </template>
                  <template v-else>
                    <v-icon>mdi-thumbup</v-icon>
                  </template>
                </v-btn>
                {{ item.voteNum }}
              </v-card-text>
            </div>
          </div>
        </v-card>
      </v-col>
    </v-row>
  </v-main>
</template>

<script>
import store from '@/store/index';
import api from '@/utils/api';

export default {
  data() {
    return {
    };
  },
  computed: {
    appointment: () => store.getters.appointment,
    items: () => store.getters.items,
  },
  created() {
    api.get(`/votes/${this.appointment.groupId}/${this.appointment.promiseId}`, {
      headers: {
        token: this.$session.get('token'),
      },
    }).then(({ data }) => {
      store.dispatch('doUpdate', data);
    });
  },
  methods: {
    moveBack() {
      this.$router.go(-1);
    },
  },
};
</script>

<style>

</style>
