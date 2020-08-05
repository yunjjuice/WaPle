<template>
  <v-app>
    <v-main>
    <v-tabs
    fixed-tabs
    background-color="gray"
    color="yellow"
    dark
    >
      <v-tab to="/">BOOKMARK</v-tab>
      <v-tab to="/review">REVIEW</v-tab>
      <v-tab to="/voting">VOTE</v-tab>
    </v-tabs>
    </v-main>
    <router-view></router-view>
  </v-app>
</template>

<script>
export default {

};
</script>

<style>

</style>
