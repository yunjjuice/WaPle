<template>
  <div>
    <h1 class="mt-5"> {{ uname }}님, 환영합니다!</h1>
    <hr class="mb-5">
  </div>
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
