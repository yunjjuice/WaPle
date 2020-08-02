<template>
<v-container>
  <v-row align='center' justify='center'>
    <v-col
      v-for="(item, i) in items"
        :key="i"
        cols="12"
    >
      <v-card
        @click="click"
      >
        <div class="d-flex flex-no-wrap justify-space-between">
          <div>
            <v-card-title
              class="headline"
              v-text="item.name"
            >
              </v-card-title>
              <v-card-actions>
                <v-btn icon>
                  <v-icon>mdi-calendar-plus</v-icon>
                </v-btn>
                <v-btn icon>
                  <v-icon>mdi-text-box-multiple-outline</v-icon>
                </v-btn>
                <v-btn icon>
                  <v-icon>mdi-pencil-plus-outline</v-icon>
                </v-btn>
              </v-card-actions>
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
          name: 'e-편한세상', address: '역삼동 755-4', lat: '37.498718', lng: '127.0499436',
        },
        {
          name: '역삼동우정에쉐르1', address: '역삼동 826-29', lat: '37.4962612', lng: '127.0301445',
        },
        {
          name: '테헤란아이파크', address: '역삼동 709-5', lat: '37.50243630000001', lng: '127.0466551',
        },
      ],
    };
  },
  mounted() {
    this.$store.dispatch('doUpdate', this.items);
  },
  methods: {
    click() {
      alert('click');
    },
  },
};
</script>

<style>

</style>
