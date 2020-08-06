<template>
  <v-app>
    <v-main>
    <v-tabs
    fixed-tabs
    background-color="gray"
    color="yellow"
    dark
    >
      <v-tab to="/" @click="bookmark=true">BOOKMARK</v-tab>
      <v-tab to="/review" @click="bookmark=false">REVIEW</v-tab>
      <v-tab to="/voting" @click="bookmark=false">VOTE</v-tab>
    </v-tabs>
    <option-filter v-if="bookmark"></option-filter>
    </v-main>
    <router-view></router-view>
  </v-app>
</template>

<script>
export default {
  components: {
    OptionFilter: () => import('@/components/items/OptionFilter.vue'),
  },
  data() {
    return {
      bookmark: true,
    };
  },
};
</script>

<style>

</style>
