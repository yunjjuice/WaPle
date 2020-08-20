<template>
<v-app>
  <v-layout style="display: block; overflow-y: hidden;">
    <v-navigation-drawer
      v-model="drawer"
      clipped
      absolute
      :class="{'drawer-width': $vuetify.breakpoint.mdAndUp}"
    >
    <router-link
      to="/"
      style="color: black; text-decoration: none; max-height: 64px; max-width: 25%;"
      class="hidden-sm-and-down"
    >
      <div align='center' justify='center'>
        <v-img
          src="/waplelogo.png"
          max-height="5rem"
          max-width="10rem"
        ></v-img>
      </div>
    </router-link>
      <router-view></router-view>
    </v-navigation-drawer>
    <div
      :class="{'map-width' : $vuetify.breakpoint.mdAndUp}"
      style="float:right; width: 100%; height: 100vh; overflow-y: hidden;"
    >
      <map-comp
        style="width: 100%; height: 100vh;"
        :class="{'map-margin': $vuetify.breakpoint.mdAndUp}"
      ></map-comp>
    </div>
  </v-layout>
</v-app>
</template>

<script>
import MapComp from '@/components/Map.vue';
import EventBus from '@/utils/EventBus';

export default {
  data() {
    return {
      drawer: true,
    };
  },
  components: {
    MapComp,
  },
  created() {
    EventBus.$on('toggle-drawer-1', () => {
      this.drawer = true;
    });
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
/* .map-margin {
  margin-left: 25% !important;
} */
.map-width {
  width: 75% !important;
}
</style>
