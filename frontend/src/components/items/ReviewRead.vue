<template>
<v-main>
  <v-container fluid>
  <v-bottom-sheet v-model="dialog" persistent inset attach scrollable>
    <v-sheet class="text-center sheet">
      <v-toolbar dense dark>
      <v-btn
        icon
        @click="close"
      >
        <v-icon>mdi-close</v-icon>
      </v-btn>
    </v-toolbar>
    <v-container
      id="scroll-target"
      class="overflow-y-auto"
      style="max-height: 640px"
    >
      <v-row
        v-scroll:#scroll-target="onScroll"
        align="center"
        justify="center"
      >
      </v-row>
      제목 : {{ review.title }} <br>
      날짜 : {{ review.visitDate }} <br>
      그룹 : {{ review.groupName }} <br>
      작성자 : {{ review.userName }} <br>
      내용 : {{ review.content }} <br>
    </v-container>
    </v-sheet>
  </v-bottom-sheet>
  </v-container>
</v-main>
</template>

<script>
import store from '@/store/index';

export default {
  computed: {
    dialog: () => store.getters.readDialog,
    review: () => store.getters.review,
  },
  methods: {
    close() {
      this.$store.dispatch('closeReadDialog');
    },
    onScroll(e) {
      this.offsetTop = e.target.scrollTop;
    },
  },
};
</script>

<style scoped>
.sheet {
  position: absolute;
  bottom: 0;
  right: 0;
  height: 691px;
  width: 1139px;
}
</style>
