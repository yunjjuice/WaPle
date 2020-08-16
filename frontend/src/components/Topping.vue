<template>
<v-main>
  <v-container align="center" justify="center">
  <v-img src="/makingwaffle.jpg"
    max-height="500px"></v-img>
    <v-stepper value="3">
    <v-stepper-header>
      <v-stepper-step step="1" complete>반죽준비</v-stepper-step>
      <v-divider></v-divider>
      <v-stepper-step step="2" complete>반죽</v-stepper-step>
      <v-divider></v-divider>
      <v-stepper-step step="3">구워지는 중</v-stepper-step>
      <v-divider></v-divider>
      <v-stepper-step step="4">토핑 올리기</v-stepper-step>
      <v-divider></v-divider>
      <v-stepper-step step="5">맛있게 먹기</v-stepper-step>
    </v-stepper-header>
  </v-stepper>
  </v-container>
</v-main>
</template>

<script>
export default {

};
</script>

<style>

</style>
