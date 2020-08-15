<template>
  <v-dialog
    v-model="dialog"
    scrollable
    width="500"
  >
    <v-card>
      <v-card-title class="headline yellow lighten-3">아이콘 선택</v-card-title>
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
                        :src="`/img/markers/${marker.icon}`"
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
        <v-btn color="green darken-1" text @click="closeDialog">close</v-btn>
        <v-btn color="green darken-1" text @click="selectIcon">save</v-btn>
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
        { icon: 'default.png', name: 'default' },
        { icon: 'food.png', name: 'food' },
        { icon: 'restaurants.png', name: 'restaurants' },
        { icon: 'coffee-n-tea.png', name: 'coffee-n-tea' },
        { icon: 'transport.png', name: 'transport' },
        { icon: 'books-media.png', name: 'books-media' },
        { icon: 'movies.png', name: 'movies' },
        { icon: 'musical.png', name: 'musical' },
        { icon: 'concerts.png', name: 'concerts' },
        { icon: 'pets.png', name: 'pets' },
        { icon: 'computers.png', name: 'computers' },
        { icon: 'clothings.png', name: 'clothings' },
        { icon: 'shopping.png', name: 'shopping' },
        { icon: 'community.png', name: 'community' },
        { icon: 'photography.png', name: 'photography' },
        { icon: 'event.png', name: 'event' },
        { icon: 'business.png', name: 'business' },
        { icon: 'bars.png', name: 'bars' },
        { icon: 'accountancy.png', name: 'accountancy' },
        { icon: 'astrology.png', name: 'astrology' },
        { icon: 'dance-clubs.png', name: 'dance-clubs' },
        { icon: 'dental.png', name: 'dental' },
        { icon: 'education.png', name: 'education' },
        { icon: 'employment.png', name: 'employment' },
        { icon: 'entertainment.png', name: 'entertainment' },
        { icon: 'exhibitions.png', name: 'exhibitions' },
        { icon: 'festivals.png', name: 'festivals' },
        { icon: 'games.png', name: 'games' },
        { icon: 'government.png', name: 'government' },
        { icon: 'health-medical.png', name: 'health-medical' },
        { icon: 'hotels.png', name: 'hotels' },
        { icon: 'internet.png', name: 'internet' },
        { icon: 'jobs.png', name: 'jobs' },
        { icon: 'law.png', name: 'law' },
        { icon: 'libraries.png', name: 'libraries' },
        { icon: 'lounges.png', name: 'lounges' },
        { icon: 'manufacturing.png', name: 'manufacturing' },
        { icon: 'massage-therapy.png', name: 'massage-therapy' },
        { icon: 'matrimonial.png', name: 'matrimonial' },
        { icon: 'meetups.png', name: 'meetups' },
        { icon: 'mobile-phones.png', name: 'mobile-phones' },
        { icon: 'museums.png', name: 'museums' },
        { icon: 'musical-instruments.png', name: 'musical-instruments' },
        { icon: 'parks.png', name: 'parks' },
        { icon: 'pizza.png', name: 'pizza' },
        { icon: 'playgrounds.png', name: 'playgrounds' },
        { icon: 'pool-halls.png', name: 'pool-halls' },
        { icon: 'professional.png', name: 'professional' },
        { icon: 'religious-organizations.png', name: 'religious-organizations' },
        { icon: 'saloon.png', name: 'saloon' },
        { icon: 'science.png', name: 'science' },
        { icon: 'sporting-goods.png', name: 'sporting-goods' },
        { icon: 'swimming-pools.png', name: 'swimming-pools' },
        { icon: 'tickets.png', name: 'tickets' },
        { icon: 'tires-accessories.png', name: 'tires-accessories' },
        { icon: 'tours.png', name: 'tours' },
        { icon: 'tutors.png', name: 'tutors' },
        { icon: 'arts-crafts.png', name: 'arts-crafts' },
        { icon: 'automotive.png', name: 'automotive' },
        { icon: 'birds.png', name: 'birds' },
        { icon: 'breakfast-n-brunch.png', name: 'breakfast-n-brunch' },
        { icon: 'cake-shop.png', name: 'cake-shop' },
        { icon: 'clubs.png', name: 'clubs' },
        { icon: 'commercial-places.png', name: 'commercial-places' },
        { icon: 'cookbooks.png', name: 'cookbooks' },
        { icon: 'doctors.png', name: 'doctors' },
        { icon: 'electronics.png', name: 'electronics' },
        { icon: 'engineering.png', name: 'engineering' },
        { icon: 'fashion.png', name: 'fashion' },
        { icon: 'financial-services.png', name: 'financial-services' },
        { icon: 'furniture-stores.png', name: 'furniture-stores' },
        { icon: 'gifts-flowers.png', name: 'gifts-flowers' },
        { icon: 'halloween.png', name: 'halloween' },
        { icon: 'home-services.png', name: 'home-services' },
        { icon: 'industries.png', name: 'industries' },
        { icon: 'jewelry.png', name: 'jewelry' },
        { icon: 'karaoke.png', name: 'karaoke' },
        { icon: 'lawn-garden.png', name: 'lawn-garden' },
        { icon: 'local-services.png', name: 'local-services' },
        { icon: 'magazines.png', name: 'magazines' },
        { icon: 'marker-new1_12.png', name: 'marker-new1_12' },
        { icon: 'mass-media.png', name: 'mass-media' },
        { icon: 'medical.png', name: 'medical' },
        { icon: 'miscellaneous-for-sale.png', name: 'miscellaneous-for-sale' },
        { icon: 'nightlife.png', name: 'nightlife' },
        { icon: 'parties.png', name: 'parties' },
        { icon: 'places.png', name: 'places' },
        { icon: 'play-schools.png', name: 'play-schools' },
        { icon: 'printing-graphic-arts.png', name: 'printing-graphic-arts' },
        { icon: 'real-estate.png', name: 'real-estate' },
        { icon: 'residential-places.png', name: 'residential-places' },
        { icon: 'retail-stores.png', name: 'retail-stores' },
        { icon: 'schools.png', name: 'schools' },
        { icon: 'sports.png', name: 'sports' },
        { icon: 'telemarketing.png', name: 'telemarketing' },
        { icon: 'tiffin-services.png', name: 'tiffin-services' },
        { icon: 'tools-hardware.png', name: 'tools-hardware' },
        { icon: 'toys-store.png', name: 'toys-store' },
        { icon: 'travel.png', name: 'travel' },
        { icon: 'vacant-land.png', name: 'vacant-land' },
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
