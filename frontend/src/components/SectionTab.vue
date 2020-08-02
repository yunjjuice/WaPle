<template>
  <v-app>
    <v-main>
    <v-tabs
    fixed-tabs
    background-color="gray"
    color="yellow"
    dark
    >
      <v-tab to="/">ALL</v-tab>
      <v-tab to="/went">REVIEW</v-tab>
      <v-tab to="/wish">BOOKMARK</v-tab>
      <v-tab to="/voting">VOTE</v-tab>
    </v-tabs>
    </v-main>
    <router-view></router-view>
  </v-app>
</template>

<script>
import TabAll from '@/components/tab/TabAll.vue';

export default {
  templates: {
    TabAll,
  },
};
</script>

<style>

</style>
