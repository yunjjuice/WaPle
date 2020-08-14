<template>
<v-main>
  <v-container fluid>
  <div>
    <h1 class="mt-5"> {{ uname }}님, 환영합니다!</h1>
    <hr class="mb-5">
  </div>
  </v-container>
</v-main>
</template>

<script>
export default {
  name: 'MyInfo',
  data() {
    return {
      uname: '',
    };
  },
  methods: {

  },
  created() {
    this.uname = this.$session.get('uname');
  },
};
</script>

<style>

</style>
