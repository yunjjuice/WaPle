<template>
<v-container>
  <v-row align='center' justify='center'>
    <v-col
      v-for="(item, i) in items"
        :key="i"
        cols="12"
    >
      <v-card
        dark
        @click="click(item.title)"
      >
        <div class="d-flex flex-no-wrap justify-space-between">
          <div>
            <v-card-title
              class="headline"
              v-text="item.title"
            />
            <v-card-subtitle
              v-text="item.name"
            />
          </div>
        </div>
      </v-card>
    </v-col>
  </v-row>
</v-container>
</template>

<script>
export default {
  data() {
    return {
      items: [
        {
          title: '약속1', name: '그룹1',
        },
        {
          title: '약속2', name: '그룹2',
        },
      ],
    };
  },
  mounted() {
  },
  methods: {
    click(id) {
      this.$router.push(`./appointment/${id}`);
    },
  },
};
</script>

<style>

</style>
