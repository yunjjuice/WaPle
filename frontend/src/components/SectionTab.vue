<template>
<v-main>
  <v-tabs
    background-color="#fff"
    color="000080"
    light
    fixed-tabs
    height="2rem"
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
    };
  },
  beforeCreate() {
    store.dispatch('getGroupsThemes');
    store.dispatch('getGroupTheme');
  },
};
</script>

<style>
</style>
