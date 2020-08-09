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
  mounted() {
    document.documentElement.style.overflowY = 'hidden';
  },
  beforeUpdate() {
    document.documentElement.style.overflowY = 'hidden';
  },
};
</script>

<style>

</style>
