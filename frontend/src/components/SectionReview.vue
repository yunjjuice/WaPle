<template>
  <v-main>
    <v-toolbar color="#e6b800" dense flat>
      <v-btn icon class="hidden-xs-only" @click="moveBack">
        <v-icon>mdi-arrow-left</v-icon>
      </v-btn>
      <v-toolbar-title> 리뷰 </v-toolbar-title>
    </v-toolbar>

    <v-row align='center' justify='center'>
      <v-col
        v-for="(item, i) in items"
          :key="i"
          cols="12"
        >
        <v-card @click="readReview()">
          <div class="d-flex flex-no-wrap justify-space-between">
            <div>
              <v-card-title
              class="headline"
              v-text="item.name"
              >
              </v-card-title>
            </div>
          </div>
        </v-card>
      </v-col>
    </v-row>
  </v-main>
</template>

<script>
export default {
  data() {
    return {
      items: [
        {
          name: '리뷰1', group: '그룹1', author: '작성자1', date: '2020-02-02',
        },
        {
          name: '리뷰2', group: '그룹2', author: '작성자2', date: '2020-02-02',
        },
        {
          name: '리뷰3', group: '그룹3', author: '작성자3', date: '2020-02-02',
        },
      ],
    };
  },
  methods: {
    moveBack() {
      this.$router.go(-1);
    },
    readReview() {
      this.$store.dispatch('showReadDialog');
    },
  },
};
</script>

<style>

</style>
