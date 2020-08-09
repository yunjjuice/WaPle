<template>
<v-main>
  <v-tabs
    fixed-tabs
    background-color="#f5f5f5"
    color="000080"
    light
  >
    <v-tab to="/">북마크</v-tab>
    <v-tab to="/review">리뷰</v-tab>
    <v-tab to="/voting">약속</v-tab>
  </v-tabs>
  <!-- TODO : 픽셀말고 높이 받아와서 스크롤 만들도록 수정 -->
  <v-container
    id="scroll-target"
    style="max-height: 640px"
    class="overflow-y-auto"
  >
    <v-row
      v-scroll:#scroll-target="onScroll"
      justify="center"
      style="height: 640px"
    >
      <option-filter v-if="bookmark"></option-filter>
      <router-view></router-view>
    </v-row>
  </v-container>
</v-main>
</template>

<script>
import store from '@/store/index';

export default {
  components: {
    OptionFilter: () => import('@/components/items/OptionFilter.vue'),
  },
  computed: {
    bookmark: () => store.getters.bookmark,
  },
  data() {
    return {
      // bookmark: true,
    };
  },
  created() {
    store.dispatch('getGroupsThemes');
  },
  methods: {
    onScroll(e) {
      this.offsetTop = e.target.scrollTop;
    },
  },
};
</script>

<style>

</style>
