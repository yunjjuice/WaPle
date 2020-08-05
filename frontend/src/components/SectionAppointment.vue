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
                <template v-if="!item.voted">
                  <v-btn icon @click="voteTo(item)">
                    <v-icon>mdi-thumb-up-outline</v-icon>
                  </v-btn>
                </template>
                <template v-else>
                  <v-btn icon @click="voteCancel(item)">
                    <v-icon>mdi-thumb-up</v-icon>
                  </v-btn>
                </template>
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
    this.getVotePlaceList();
  },
  methods: {
    moveBack() {
      this.$router.go(-1);
    },
    getVotePlaceList() {
      api.get(`/votes/${this.appointment.groupId}/${this.appointment.promiseId}`, {
        headers: {
          token: this.$session.get('token'),
        },
      }).then(({ data }) => {
        store.dispatch('doUpdate', data);
      });
    },
    voteTo(item) { // 해당 장소에 투표
      api.post('/votes/to', {
        groupId: store.getters.appointment.groupId,
        placeId: item.placeId,
        promiseId: store.getters.appointment.promiseId,
        userId: this.$session.get('uid'),
      },
      {
        headers: {
          token: this.$session.get('token'),
        },
      }).then((res) => {
        if (res.status === 201) {
          this.snackbar = true;
          this.getVotePlaceList();
        }
      });
    },
    voteCancel(item) { // 투표 취소
      const { groupId } = store.getters.appointment;
      const { promiseId } = store.getters.appointment;
      const { placeId } = item;
      const userId = this.$session.get('uid');
      api.delete(`/votes/to/${groupId}/${promiseId}/${placeId}/${userId}`, {
        headers: {
          token: this.$session.get('token'),
        },
      }).then((res) => {
        if (res.status === 204) {
          this.getVotePlaceList();
        }
      });
    },
  },
};
</script>

<style>

</style>
