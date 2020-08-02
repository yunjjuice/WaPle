<template>
<v-container>
  <v-row align='center' justify='center'>
    <v-col
      v-for="(item, i) in items"
        :key="i"
        cols="12"
    >
      <v-card
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
          name: '동대문', address: '창신동 328-17', lat: '37.5722279', lng: '127.0151863',
        },
        {
          name: 'MID그린(3동))', address: '창신동 601-2', lat: '37.5779087', lng: '127.0099886',
        },
        {
          name: '성용', address: '창신동 640-252', lat: '37.57500479999999', lng: '127.0094655',
        },
        {
          name: '창신쌍용2', address: '창신동 703', lat: '37.5804209', lng: '127.0117238',
        },
        {
          name: '두산', address: '창신동 232', lat: '37.5741155', lng: '127.0143203',
        },
        {
          name: '일우', address: '창신동 602', lat: '37.5772952', lng: '127.0096854',
        },
        {
          name: '미니', address: '창신동 638-36', lat: '37.5757009', lng: '127.0095299',
        },
        {
          name: '브라운스톤창신', address: '창신동 23-76', lat: '37.5773353', lng: '127.015016',
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
