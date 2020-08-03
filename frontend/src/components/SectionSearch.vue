<template>
  <v-main>
    <v-toolbar dark dense flat>
      <v-btn icon class="hidden-xs-only" @click="moveBack">
        <v-icon>mdi-arrow-left</v-icon>
      </v-btn>
      <v-toolbar-title>{{ keyword }}</v-toolbar-title>
    </v-toolbar>

    <v-row align='center' justify='center'>
      <v-col
      v-for="(item, i) in searchResult"
        :key="i"
        cols="12"
      >
      <v-card>
        <div class="d-flex flex-no-wrap justify-space-between">
          <div>
            <v-card-title
              class="headline"
              v-text="item.place_name"
            ></v-card-title>
            <v-card-text>
              {{ item.road_address_name }}
            </v-card-text>
            <v-card-actions>
              <v-btn icon @click.stop="showDialog(item)">
                <v-icon>mdi-bookmark-plus-outline</v-icon>
              </v-btn>
            </v-card-actions>
          </div>
        </div>
      </v-card>
    </v-col>
  </v-row>
  <v-dialog
    v-model="dialog"
    width="400"
    height="300"
  >
    <v-card align="center">
      <v-card-title class="headline yellow lighten-3">북마크등록</v-card-title>
      <v-form ref="form" v-model="valid">
      <v-row justify="center">
        <v-col cols="15" sm="6">
          <v-select
            :items="groups"
            v-model="group"
            item-text="name"
            label="group"
            return-object
            required
            :rules="[v => !!v || 'group is required']"
            @change="findTheme"
          ></v-select>
        </v-col>
      </v-row>
      <v-row justify="center">
        <v-col class="d-flex" cols="15" sm="6">
          <v-select
            :items="themes"
            v-model="theme"
            item-text="name"
            label="theme"
            return-object
            required
            :rules="[v => !!v || 'theme is required']"
          ></v-select>
        </v-col>
      </v-row>
      <v-row justify="center">
        <v-btn depressed color="primary" @click="isValid">추가하기</v-btn>
      </v-row>
      </v-form>
    </v-card>
  </v-dialog>
  </v-main>
</template>

<script>
import store from '@/store/index';
import api from '@/utils/api';

export default {
  data() {
    return {
      dialog: false,
      place: {}, // 선택된 장소 정보
      groups: [], // 데이터에서 받아온 그룹 목록
      group: {}, // 선택된 그룹
      themes: [], // 데이터에서 받아온 테마 목록
      theme: {}, // 선택된 테마
      valid: true,
    };
  },
  computed: {
    searchResult: () => store.getters.result,
    keyword: () => store.getters.keyword,
  },
  methods: {
    moveBack() {
      this.$router.go(-1);
    },
    findTheme() {
      api.get(`/themes/${this.group.groupId}`, { headers: { token: this.$session.get('token') } })
        .then(({ data }) => {
          this.themes = data;
        });
    },
    showDialog(item) {
      this.dialog = true;
      this.place = item;
      this.themes = [];
      this.group = null;
      this.theme = null;
      this.$refs.form.resetValidation();
    },
    makeBookmark() {
      // 장소 추가
      api.post('/places', {
        address: this.place.road_address_name,
        lat: this.place.y,
        lng: this.place.x,
        name: this.place.place_name,
        placeId: this.place.id,
        url: this.place.place_url,
      });
      // .then(({ res }) => {
      // console.log(`status code : ${res.status}`);
      // });
      // 북마크 등록
      api.post('/bookmarks', {
        groupId: this.group.groupId,
        placeId: this.place.id,
        themeId: this.theme.themeId,
        userId: this.$session.get('uid'),
      }).then(({ data }) => {
        console.log(data);
      });
      this.dialog = false;
    },
    isValid() { // 그룹과 테마가 다 선택되었는지 확인
      this.$refs.form.validate();
      if (this.valid) {
        this.makeBookmark();
      }
    },
  },
  created() {
    api.get(`/groups/${this.$session.get('uid')}`)
      .then(({ data }) => {
        this.groups = data;
      });
  },
};
</script>

<style>

</style>
