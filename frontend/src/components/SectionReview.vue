<template>
  <v-main>
    <v-toolbar color="#f5f5f5" dense flat>
      <v-btn icon @click="moveBack">
        <v-icon>mdi-arrow-left</v-icon>
      </v-btn>
      <v-toolbar-title>{{ item.name }}</v-toolbar-title>
    </v-toolbar>
    <v-container>
      <v-row align='center' justify='center'>
        <v-col
          v-for="(review, i) in reviews"
          :key="i"
          cols="12"
        >
          <v-card @click="readReview(review)">
            <div class="d-flex flex-no-wrap justify-space-between">
              <div>
                <v-card-title
                  class="headline"
                  v-text="review.title"
                ></v-card-title>
                <v-card-text>
                  {{ review.groupName }}
                </v-card-text>
              </div>
            </div>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
  </v-main>
</template>

<script>
import api from '@/utils/api';
import store from '@/store/index';

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
    const userId = this.$session.get('uid');
    const { placeId } = store.getters.item;
    api.get(`/reviews/places/${userId}/${placeId}/10/1`, {
      headers: {
        token: this.$session.get('token'),
      },
    }).then(({ data }) => {
      this.reviews = data;
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
        store.dispatch('selectReview', data);
        store.dispatch('changeReadDialog');
      });
    },
  },
};
</script>

<style>

</style>
