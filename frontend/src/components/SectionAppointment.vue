<template>
  <v-main>
    <v-toolbar color="#f5f5f5" dense flat>
      <v-btn icon @click="moveBack">
        <v-icon>mdi-arrow-left</v-icon>
      </v-btn>
      <v-toolbar-title>{{ appointment.title }}</v-toolbar-title>
    </v-toolbar>
    <v-container>
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
          style="padding: 3px; height: 5.1rem;"
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
                  {{ item.address }} <br>
                  {{ item.pickedUserName }}<br>
                  <!--
                    TODO : 투표하기
                    투표 후에 items를 다시 싹 업데이트 해야 voted가 잘 넘어올 텐데 ..?
                  -->
                  <template v-if="!item.voted">
                    <v-tooltip bottom>
                      <template v-slot:activator="{ on, attrs }">
                        <v-btn
                          icon
                          v-bind="attrs"
                          v-on="on"
                          @click="voteTo(item)"
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
                          @click="voteCancel(item)"
                        >
                          <v-icon>mdi-thumb-up</v-icon>
                        </v-btn>
                      </template>
                      <sapn>투표 취소</sapn>
                    </v-tooltip>
                  </template>
                  {{ item.voteNum }}
                </v-card-text>
              </div>
            </div>
            <v-divider style="position: relative; top: -1.75rem;"></v-divider>
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
    getVotePlaceList() {
      api.get(`/votes/${this.appointment.groupId}/${this.appointment.promiseId}`, {
        headers: {
          token: this.$session.get('token'),
        },
      }).then(({ data }) => {
        this.loading = true;
        setTimeout(() => {
          store.dispatch('doUpdate', data);
        }, 500);
        this.loading = false;
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
      }).then(() => {
        this.$toast.success('투표 성공');
        this.getVotePlaceList();
      }).catch((err) => {
        console.error(err);
        this.$toast.error('투표 실패, 다시 시도해주세요.');
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
      }).then(() => {
        this.$toast.success('투표 취소 성공');
        this.getVotePlaceList();
      }).catch((err) => {
        console.error(err);
        this.$toast.error('투표 취소 실패, 다시 시도해주세요.');
      });
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
