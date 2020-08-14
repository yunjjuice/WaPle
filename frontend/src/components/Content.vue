<template>
<v-app>
  <v-layout>
    <v-navigation-drawer
      v-model="drawer"
      clipped
      absolute
      :class="{'drawer-width': $vuetify.breakpoint.mdAndUp}"
    >
      <router-view></router-view>
    </v-navigation-drawer>
    <map-comp
      :class="{'map-margin': $vuetify.breakpoint.mdAndUp}"
    ></map-comp>
  </v-layout>
  <snack-bar></snack-bar>
</v-app>
</template>

<script>
import MapComp from '@/components/Map.vue';
import SnackBar from '@/components/items/Snackbars.vue';

export default {
  data() {
    return {
      drawer: true,
    };
  },
  components: {
    MapComp,
    SnackBar,
  },
};
</script>

<style scoped>
.v-navigation-drawer--absolute {
  z-index: 2;
}
.drawer-width {
  width: 25% !important;
}
.map-margin {
  margin-left: 25% !important;
}
</style>
