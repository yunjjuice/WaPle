<template>
<v-app>
    <v-layout>
        <v-navigation-drawer clipped width="25%">
            <router-view></router-view>
        </v-navigation-drawer>
        <map-comp></map-comp>
    </v-layout>
    <snack-bar></snack-bar>
</v-app>
</template>

<script>
import MapComp from '@/components/Map.vue';
import SnackBar from '@/components/items/Snackbars.vue';

export default {
  components: {
    MapComp,
    SnackBar,
  },
};
</script>

<style>

</style>
