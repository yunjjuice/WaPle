<template>
  <v-main>
    <v-toolbar color="#f5f5f5" dense flat>
      <v-btn icon @click="moveBack">
        <v-icon>mdi-arrow-left</v-icon>
      </v-btn>
      <v-toolbar-title>{{ item.name }}</v-toolbar-title>
    </v-toolbar>
    <v-container style="padding-top: 0rem;">
      <div v-if="reviews.length == 0"
        class="justify-space-between v-card__text"
        style="color: gray">
        아직 작성된 리뷰가 없는 것 같아요! <br>
        <br>
        북마크 탭에서 <v-icon style="font-size: 1rem;">mdi-pencil-plus-outline</v-icon> 버튼으로 <br>
        새로운 리뷰를 쓸 수 있어요 :)
      </div>
      <v-row align='center' justify='center'>
        <v-col
          v-for="(review, i) in reviews"
          :key="i"
          cols="12"
          style="padding: 3px; height: 6rem;"
        >
          <v-card
            @click="readReview(review)"
            style="height: 6rem; box-shadow: none !important;"
            tile
          >
            <div class="d-flex flex-no-wrap justify-space-between">
              <div>
                <v-card-title
                  class="headline"
                  v-text="review.title"
                  style="font-size: 1rem !important; padding-top: 0.5rem; padding-bottom: 0;"
                ></v-card-title>
                <v-card-text>
                  <span style="color: gray">{{ review.visitDate }}</span><br>
                  <v-icon style="font-size: 1rem;">mdi-account-group-outline </v-icon>
                  {{ review.groupName }}
                  <v-icon style="font-size: 1rem;">mdi-account-outline</v-icon>
                  {{ review.userName }}<br>
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
import moment from 'moment';
import store from '@/store/index';
import api from '@/utils/api';
import EventBus from '@/utils/EventBus';

export default {
  data() {
    return {
      reviews: [],
    };
  },
  computed: {
    item: () => store.getters.item,
  },
  created() {
    EventBus.$emit('toggle-drawer-1');
    const userId = this.$session.get('uid');
    const { placeId } = store.getters.item;
    api.get(`/reviews/places/${userId}/${placeId}/10/1`, {
      headers: {
        token: this.$session.get('token'),
      },
    }).then(({ data }) => {
      this.reviews = data;
      for (let i = 0; i < this.reviews.length; i += 1) {
        this.reviews[i].visitDate = moment(data[i].visitDate).add(1, 'days').format('YYYY-MM-DD');
      }
    }).catch((err) => {
      console.error(err);
      this.$toast.error('리뷰 조회 실패, 다시 시도해주세요.');
    });
  },
  methods: {
    moveBack() {
      this.$router.go(-1);
    },
    readReview(review) {
      api.get(`/reviews/${review.reviewId}`, {
        headers: {
          token: this.$session.get('token'),
        },
      }).then(({ data }) => {
        moment.locale('ko');
        const item = data;
        item.visitDate = moment(data.visitDate).add(1, 'days').format('YYYY-MM-DD dddd');
        store.dispatch('selectReview', item);
        store.dispatch('changeReadDialog');
      }).catch((err) => {
        console.error(err);
        this.$toast.error('리뷰 조회 실패, 다시 시도해주세요.');
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
</style>
