<template>
  <v-main>
    <v-toolbar color="#e6b800" dense flat>
      <v-btn icon class="hidden-xs-only" @click="moveBack">
        <v-icon>mdi-arrow-left</v-icon>
      </v-btn>
      <v-toolbar-title>{{ id }}</v-toolbar-title>
    </v-toolbar>
  </v-main>
</template>

<script>
export default {
  data() {
    return {
      id: this.$route.params.id,
    };
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
