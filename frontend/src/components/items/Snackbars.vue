<template>
  <v-snackbar
    v-model="snackbar"
    :color="color"
    :timeout="timeout"
  >
    {{ msg }}
  </v-snackbar>
</template>

<script>
import store from '@/store/index';

export default {
  computed: {
    snackbar: () => store.getters.snackbar,
    msg: () => store.getters.msg,
    color: () => store.getters.color,
    timeout: () => store.getters.timeout,
  },
};
</script>

<style>

</style>
