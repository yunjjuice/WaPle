<template>
<v-app>
    <nav-header></nav-header>
    <router-view></router-view>
</v-app>
</template>

<script>
import NavHeader from '@/components/NavHeader.vue';

export default {
  components: {
    NavHeader,
  },
};
</script>

<style>

</style>
