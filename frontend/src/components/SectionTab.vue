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
  <option-filter v-if="bookmark"></option-filter>
  <router-view></router-view>
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
};
</script>

<style>

</style>
