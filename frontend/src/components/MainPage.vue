<template>
<v-app>
  <nav-header @toggle-drawer="$refs.drawer.drawer = !$refs.drawer.drawer" class="hidden-md-and-up">
  </nav-header>
  <router-view ref="drawer"></router-view>
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
.v-navigation-drawer__content {
  overflow-y: hidden !important;
}
</style>
