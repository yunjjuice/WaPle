<template>
<v-app>
    <v-layout>
        <v-navigation-drawer clipped width="25%">
            <router-view></router-view>
        </v-navigation-drawer>
        <map-comp></map-comp>
    </v-layout>
</v-app>
</template>

<script>
import MapComp from './Map.vue';

export default {
  components: {
    MapComp,
  },
};
</script>

<style>

</style>
