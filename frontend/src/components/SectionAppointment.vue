<template>
  <v-main>
    <v-toolbar color="#f5f5f5" dense flat>
      <v-btn icon @click="moveBack">
        <v-icon>mdi-arrow-left</v-icon>
      </v-btn>
      <v-toolbar-title>{{ appointment.title }}</v-toolbar-title>
    </v-toolbar>
    <v-container style="padding-top: 0rem;">
      <transition name="fade">
        <div class="loading" v-show="loading">
          <span class="fa fa-spinner fa-spin"></span> Loading
        </div>
      </transition>
      <v-row align='center' justify='center'>
        <v-col
          v-for="(item, i) in items"
          :key="i"
          cols="12"
          style="padding: 3px; height: 5.8rem;"
        >
          <v-card
            @click="infowindow(i)"
            style="height: 5rem; box-shadow: none !important;"
          >
            <div class="d-flex flex-no-wrap justify-space-between">
              <div>
                <v-card-title
                  class="headline"
                  v-text="item.name"
                  style="font-size: 1rem !important; padding-top: 0.5rem; padding-bottom: 0;"
                />
                <v-card-text>
                  <span style="color: gray;">{{ item.address }}</span> <br>
                  {{ item.pickedUserName }}'s pick<br>
                  <!--
                    TODO : 투표하기
                    투표 후에 items를 다시 싹 업데이트 해야 voted가 잘 넘어올 텐데 ..?
                  -->
                  <div style="position: absolute; bottom: 1%; right: 5%">
                  <template v-if="!item.voted">
                    <v-tooltip bottom>
                      <template v-slot:activator="{ on, attrs }">
                        <v-btn
                          icon
                          v-bind="attrs"
                          v-on="on"
                          @click.stop="voteTo(item, i)"
                        >
                          <v-icon>mdi-thumb-up-outline</v-icon>
                        </v-btn>
                      </template>
                      <span>투표</span>
                    </v-tooltip>
                  </template>
                  <template v-else>
                    <v-tooltip bottom>
                      <template v-slot:activator="{ on, attrs }">
                        <v-btn
                          icon
                          v-bind="attrs"
                          v-on="on"
                          @click.stop="voteCancel(item, i)"
                        >
                          <v-icon>mdi-thumb-up</v-icon>
                        </v-btn>
                      </template>
                      <span>투표 취소</span>
                    </v-tooltip>
                  </template>
                  {{ item.voteNum }}
                  </div>
                </v-card-text>
              </div>
            </div>
            <v-divider style="position: relative; top: -1.5rem;"></v-divider>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
  </v-main>
</template>

<script>
import store from '@/store/index';
import api from '@/utils/api';
import EventBus from '@/utils/EventBus';

const waitForMs = (ms) => new Promise((resolve) => setTimeout(resolve, ms));

export default {
  data() {
    return {
      loading: false,
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
    infowindow(index) {
      EventBus.$emit('moveMap', { lat: this.items[index].lat, lng: this.items[index].lng, index });
    },
    moveBack() {
      this.$router.go(-1);
    },
    async getVotePlaceList() {
      this.loading = true;
      await api.get(`/votes/${this.appointment.groupId}/${this.appointment.promiseId}`, {
        headers: {
          token: this.$session.get('token'),
        },
      }).then(({ data }) => {
        store.dispatch('doUpdate', data);
      });
      this.loading = false;
    },
    async voteTo(item, idx) { // 해당 장소에 투표
      EventBus.$emit('showOverlay');
      await Promise.all([ // 0.5초 대기 & 비동기가 모두 끝날 때까지 로딩 오버레이
        waitForMs(500),
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
        })])
        .then(() => {
          this.items[idx].voted = true;
          this.items[idx].voteNum += 1;
          this.$toast.success(`${item.name}에 투표 성공`);
        }).catch((values) => {
          console.error(values[1]);
          this.$toast.error(`${item.name}에 투표 실패, 다시 시도해주세요.`);
        });
      EventBus.$emit('closeOverlay');
    },
    async voteCancel(item, idx) { // 투표 취소
      EventBus.$emit('showOverlay');
      const { groupId } = store.getters.appointment;
      const { promiseId } = store.getters.appointment;
      const { placeId } = item;
      const userId = this.$session.get('uid');
      await Promise.all([
        waitForMs(500),
        api.delete(`/votes/to/${groupId}/${promiseId}/${placeId}/${userId}`, {
          headers: {
            token: this.$session.get('token'),
          },
        }),
      ]).then(() => {
        this.items[idx].voted = false;
        this.items[idx].voteNum -= 1;
        this.$toast.success(`${item.name}에 투표 취소 성공`);
      }).catch((values) => {
        console.error(values[1]);
        this.$toast.error(`${item.name}에 투표 취소 실패, 다시 시도해주세요.`);
      });
      EventBus.$emit('closeOverlay');
    },
  },
};
</script>

<style scoped>
.d-flex.flex-no-wrap.justify-space-between:hover{
  background-color: #d2d2d4;
  opacity: 0.8;
}
.loading {
  text-align: center;
  position: absolute;
  color: #fff;
  z-index: 9;
  background: grey;
  padding: 8px 18px;
  border-radius: 5px;
  left: calc(50% - 45px);
  top: calc(50% - 18px);
}
.fade-enter-active, .fade-leave-active {
  transition: opacity .5s
}
.fade-enter, .fade-leave-to {
  opacity: 0
}
</style>
