<template>
  <v-main>
    <v-toolbar dark dense flat>
      <v-btn icon class="hidden-xs-only" @click="moveBack">
        <v-icon>mdi-arrow-left</v-icon>
      </v-btn>
      <v-toolbar-title>{{ keyword }}</v-toolbar-title>
    </v-toolbar>

    <v-row align='center' justify='center'>
      <v-col
      v-for="(item, i) in searchResult"
        :key="i"
        cols="12"
      >
      <v-card>
        <div class="d-flex flex-no-wrap justify-space-between">
          <div>
            <v-card-title
              class="headline"
              v-text="item.place_name"
            >
            </v-card-title>
            <v-card-actions>
              <!-- TODO 북마크 설정 여부에 따라 아이콘 모양 다르게 하기 -->
              <v-btn icon>
                <v-icon>mdi-bookmark-plus-outline</v-icon>
              </v-btn>
              <v-btn icon>
                <v-icon>mdi-bookmark</v-icon>
              </v-btn>
            </v-card-actions>
          </div>
        </div>
      </v-card>
    </v-col>
  </v-row>
  </v-main>
</template>

<script>
import store from '@/store/index';

export default {
  data() {
    return {
    };
  },
  computed: {
    searchResult: () => store.getters.result,
    keyword: () => store.getters.keyword,
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
