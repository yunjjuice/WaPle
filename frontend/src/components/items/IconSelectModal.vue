<template>
  <v-dialog
    v-model="dialog"
    scrollable
    width="500"
  >
    <v-card>
      <v-card-title class="yellow lighten-3">아이콘 선택</v-card-title>
      <v-card-text style="height: 300px;">
        <v-item-group
          v-model="selected"
          mandatory
        >
          <v-container>
            <v-row>
              <v-col
                v-for="(marker, i) in markers"
                :key="`${i}-${marker.name}`"
                cols="3"
                md="2"
              >
                <v-item v-slot:default="{ active, toggle }">
                  <v-card
                    :color="active ? '#ffd54f' : ''"
                    @click="toggle"
                    align="center"
                    justify="center"
                    flat
                  >
                    <v-card-text>
                      <v-img
                        :src="`${marker.icon}`"
                      ></v-img>
                    </v-card-text>
                  </v-card>
                </v-item>
              </v-col>
            </v-row>
          </v-container>
        </v-item-group>
      </v-card-text>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn color="green darken-1" text @click="closeDialog">취소</v-btn>
        <v-btn color="green darken-1" text @click="selectIcon">저장</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
export default {
  props: ['iconDialog'],
  data() {
    return {
      dialog: false,
      selected: null,
      markers: [
        { icon: '/markers/default.png', name: 'default' },
        { icon: '/markers/food.png', name: 'food' },
        { icon: '/markers/restaurants.png', name: 'restaurants' },
        { icon: '/markers/coffee-n-tea.png', name: 'coffee-n-tea' },
        { icon: '/markers/transport.png', name: 'transport' },
        { icon: '/markers/books-media.png', name: 'books-media' },
        { icon: '/markers/movies.png', name: 'movies' },
        { icon: '/markers/musical.png', name: 'musical' },
        { icon: '/markers/concerts.png', name: 'concerts' },
        { icon: '/markers/pets.png', name: 'pets' },
        { icon: '/markers/computers.png', name: 'computers' },
        { icon: '/markers/clothings.png', name: 'clothings' },
        { icon: '/markers/shopping.png', name: 'shopping' },
        { icon: '/markers/community.png', name: 'community' },
        { icon: '/markers/photography.png', name: 'photography' },
        { icon: '/markers/event.png', name: 'event' },
        { icon: '/markers/business.png', name: 'business' },
        { icon: '/markers/bars.png', name: 'bars' },
        { icon: '/markers/accountancy.png', name: 'accountancy' },
        { icon: '/markers/astrology.png', name: 'astrology' },
        { icon: '/markers/dance-clubs.png', name: 'dance-clubs' },
        { icon: '/markers/dental.png', name: 'dental' },
        { icon: '/markers/education.png', name: 'education' },
        { icon: '/markers/employment.png', name: 'employment' },
        { icon: '/markers/entertainment.png', name: 'entertainment' },
        { icon: '/markers/exhibitions.png', name: 'exhibitions' },
        { icon: '/markers/festivals.png', name: 'festivals' },
        { icon: '/markers/games.png', name: 'games' },
        { icon: '/markers/government.png', name: 'government' },
        { icon: '/markers/health-medical.png', name: 'health-medical' },
        { icon: '/markers/hotels.png', name: 'hotels' },
        { icon: '/markers/internet.png', name: 'internet' },
        { icon: '/markers/jobs.png', name: 'jobs' },
        { icon: '/markers/law.png', name: 'law' },
        { icon: '/markers/libraries.png', name: 'libraries' },
        { icon: '/markers/lounges.png', name: 'lounges' },
        { icon: '/markers/manufacturing.png', name: 'manufacturing' },
        { icon: '/markers/massage-therapy.png', name: 'massage-therapy' },
        { icon: '/markers/matrimonial.png', name: 'matrimonial' },
        { icon: '/markers/meetups.png', name: 'meetups' },
        { icon: '/markers/mobile-phones.png', name: 'mobile-phones' },
        { icon: '/markers/museums.png', name: 'museums' },
        { icon: '/markers/musical-instruments.png', name: 'musical-instruments' },
        { icon: '/markers/parks.png', name: 'parks' },
        { icon: '/markers/pizza.png', name: 'pizza' },
        { icon: '/markers/playgrounds.png', name: 'playgrounds' },
        { icon: '/markers/pool-halls.png', name: 'pool-halls' },
        { icon: '/markers/professional.png', name: 'professional' },
        { icon: '/markers/religious-organizations.png', name: 'religious-organizations' },
        { icon: '/markers/saloon.png', name: 'saloon' },
        { icon: '/markers/science.png', name: 'science' },
        { icon: '/markers/sporting-goods.png', name: 'sporting-goods' },
        { icon: '/markers/swimming-pools.png', name: 'swimming-pools' },
        { icon: '/markers/tickets.png', name: 'tickets' },
        { icon: '/markers/tires-accessories.png', name: 'tires-accessories' },
        { icon: '/markers/tours.png', name: 'tours' },
        { icon: '/markers/tutors.png', name: 'tutors' },
        { icon: '/markers/arts-crafts.png', name: 'arts-crafts' },
        { icon: '/markers/automotive.png', name: 'automotive' },
        { icon: '/markers/birds.png', name: 'birds' },
        { icon: '/markers/breakfast-n-brunch.png', name: 'breakfast-n-brunch' },
        { icon: '/markers/cake-shop.png', name: 'cake-shop' },
        { icon: '/markers/clubs.png', name: 'clubs' },
        { icon: '/markers/commercial-places.png', name: 'commercial-places' },
        { icon: '/markers/cookbooks.png', name: 'cookbooks' },
        { icon: '/markers/doctors.png', name: 'doctors' },
        { icon: '/markers/electronics.png', name: 'electronics' },
        { icon: '/markers/engineering.png', name: 'engineering' },
        { icon: '/markers/fashion.png', name: 'fashion' },
        { icon: '/markers/financial-services.png', name: 'financial-services' },
        { icon: '/markers/furniture-stores.png', name: 'furniture-stores' },
        { icon: '/markers/gifts-flowers.png', name: 'gifts-flowers' },
        { icon: '/markers/halloween.png', name: 'halloween' },
        { icon: '/markers/home-services.png', name: 'home-services' },
        { icon: '/markers/industries.png', name: 'industries' },
        { icon: '/markers/jewelry.png', name: 'jewelry' },
        { icon: '/markers/karaoke.png', name: 'karaoke' },
        { icon: '/markers/lawn-garden.png', name: 'lawn-garden' },
        { icon: '/markers/local-services.png', name: 'local-services' },
        { icon: '/markers/magazines.png', name: 'magazines' },
        { icon: '/markers/marker-new1_12.png', name: 'marker-new1_12' },
        { icon: '/markers/mass-media.png', name: 'mass-media' },
        { icon: '/markers/medical.png', name: 'medical' },
        { icon: '/markers/miscellaneous-for-sale.png', name: 'miscellaneous-for-sale' },
        { icon: '/markers/nightlife.png', name: 'nightlife' },
        { icon: '/markers/parties.png', name: 'parties' },
        { icon: '/markers/places.png', name: 'places' },
        { icon: '/markers/play-schools.png', name: 'play-schools' },
        { icon: '/markers/printing-graphic-arts.png', name: 'printing-graphic-arts' },
        { icon: '/markers/real-estate.png', name: 'real-estate' },
        { icon: '/markers/residential-places.png', name: 'residential-places' },
        { icon: '/markers/retail-stores.png', name: 'retail-stores' },
        { icon: '/markers/schools.png', name: 'schools' },
        { icon: '/markers/sports.png', name: 'sports' },
        { icon: '/markers/telemarketing.png', name: 'telemarketing' },
        { icon: '/markers/tiffin-services.png', name: 'tiffin-services' },
        { icon: '/markers/tools-hardware.png', name: 'tools-hardware' },
        { icon: '/markers/toys-store.png', name: 'toys-store' },
        { icon: '/markers/travel.png', name: 'travel' },
        { icon: '/markers/vacant-land.png', name: 'vacant-land' },
      ],
    };
  },
  watch: {
    iconDialog() {
      this.dialog = !this.dialog;
    },
  },
  methods: {
    selectIcon() {
      this.$emit('select', this.markers[this.selected]);
      this.closeDialog();
    },
    closeDialog() {
      this.selected = null;
      this.$emit('close');
    },
  },
};
</script>

<style scoped>
.v-card__text {
  padding: 10px;
}
</style>
