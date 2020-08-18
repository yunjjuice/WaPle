<template>
<v-main>
  <v-container fluid class="fill-height" id='map' ref="map" style="height: 100%">
    <div
      class="text-center hidden-sm-and-down"
      style="z-index: 2;
      position: absolute;
      top: 1rem;
      right: 1rem;"
    >
      <v-menu offset-y>
        <template v-slot:activator="{ on, attrs }">
          <v-btn
            color="#ffd633"
            v-bind="attrs"
            v-on="on"
          >
            <span class="hidden-sm-and-down">{{ uname }}</span>
          </v-btn>
        </template>
        <v-list>
          <v-list-item to="/mypage">
            <v-list-item-title>마이페이지</v-list-item-title>
          </v-list-item>
          <v-list-item to="/toppings">
            <v-list-item-title>개발자정보</v-list-item-title>
          </v-list-item>
          <v-list-item @click="logout">
            <v-list-item-title>로그아웃</v-list-item-title>
          </v-list-item>
        </v-list>
      </v-menu>
    </div>
    <v-text-field
      flat
      solo-inverted
      hide-details
      prepend-inner-icon="mdi-magnify"
      label="Search"
      class="hidden-sm-and-down"
      v-model="keyword"
      @keypress.enter="searchByWord"
      style="z-index: 2;
      position: absolute;
      bottom: 2rem;
      right: 1rem;
      background-color: #ffd54f;
      opacity: 0.8;
      width: 20rem;"
    ></v-text-field>
  </v-container>
  <review-write :writeDialog="write" @closeWrite="closeWriteDialog"></review-write>
  <review-read :readDialog="read" @closeRead="closeReadDialog"></review-read>
</v-main>
</template>

<script>
import store from '@/store/index';
import EventBus from '@/utils/EventBus';
import api from '@/utils/api';
import JWT from 'jwt-decode';

export default {
  data() {
    return {
      map: null,
      infowindows: [],
      markers: [],
      write: false,
      read: false,
      keyword: '',
      uname: '',
    };
  },
  created() {
    EventBus.$on('moveMap', (payload) => {
      this.panTo(this.map, payload.lat, payload.lng, payload.index);
    });
    EventBus.$on('deleteCard', (payload) => {
      this.deleteMap(payload);
    });
    this.uname = this.$session.get('uname');
  },
  mounted() {
    store.dispatch('updateHeight', this.$refs.map.clientHeight);
    store.dispatch('updateWidth', this.$refs.map.clientWidth);
  },
  components: {
    ReviewWrite: () => import('@/components/items/ReviewWrite.vue'),
    ReviewRead: () => import('@/components/items/ReviewRead.vue'),
  },
  computed: {
    currentLocation: () => store.getters.items, // 서버에서 정보 받아올 때
    searchLocation: () => store.getters.result, // 카카오 검색에서 정보 받아올 때
    writeDialog: () => store.getters.writeDialog,
    readDialog: () => store.getters.readDialog,
    height: () => store.getters.height,
    width: () => store.getters.width,
  },
  watch: {
    currentLocation(newItems) {
      this.initMap(newItems);
    },
    searchLocation(newItems) {
      if (newItems.length !== 0) {
        this.searchMap(newItems);
      }
    },
    writeDialog() {
      this.write = !this.write;
    },
    readDialog() {
      this.read = !this.read;
    },
    height() {
      store.dispatch('updateHeight', this.$refs.map.clientHeight);
    },
    width() {
      store.dispatch('updateWidth', this.$refs.map.clientWidth);
    },
  },
  methods: {
    initMap(items) {
      this.markers = [];
      this.infowindows = [];
      const container = document.getElementById('map');
      const options = {
        center: new window.kakao.maps.LatLng(items.length === 0 ? '37.501245' : items[0].lat,
          items.length === 0 ? '127.039592' : items[0].lng),
        level: 3,
      };
      this.map = new window.kakao.maps.Map(container, options);
      this.map.setMapTypeId(window.kakao.maps.MapTypeId.ROADMAP);
      // 마커 표시하기
      const bounds = new window.kakao.maps.LatLngBounds(); // 지도 범위 재설정을 위함
      // TODO: 나중에 마커 위치가 전국 각지에 있다면 어떻게 할 건지 고민해보자!
      for (let i = 0; i < items.length; i += 1) {
        let imageSrc = items[i].icon;
        if (imageSrc === undefined) imageSrc = 'markers/default.png';
        const imageSize = new window.kakao.maps.Size(24, 35);
        const markerImage = new window.kakao.maps.MarkerImage(imageSrc, imageSize);
        const marker = new window.kakao.maps.Marker({
          map: this.map,
          position: new window.kakao.maps.LatLng(items[i].lat, items[i].lng),
          title: items[i].name,
          image: markerImage,
          clickable: true,
        });
        bounds.extend(new window.kakao.maps.LatLng(items[i].lat, items[i].lng));
        const infoContent = '<div style="min-width:20rem; line-height:100%; min-height: 9rem;"><div style="background-color: #ffd54f; border-right: 1px solid rgb(118, 129, 168)"><h4 style="padding:10px 15px; font-size:1.3rem; font-weight: 700; line-height:1.5em; padding-bottom:10px; color:#333;">^PLACE_NAME</h4></div><div><p style="opacity: 0.7; padding:0px 15px;">^PLACE_ADDRESS</p><div style="padding-left: 15px; color: #633D20">^PLACE_CATEGORY</div><a href="tel:^PLACE_TEL" style="color: #333; position: absolute; bottom: 1vh; left: 15px">^PLACE_TEL1</a><div style="position: absolute; bottom: 1vh; right: 15px;"><a href="https://map.kakao.com/link/to/^PLACE_ID" target="_blank"><i class="fas fa-location-arrow" style="float: right; color: #333"></i></a><a href="^PLACE_URL" target="_blank"><i class="fas fa-info" style="color: #333; float: right; margin-right: 1em;"></i></a></div></div></div>';
        const infowindow = new window.kakao.maps.InfoWindow({
          position: marker.getPosition(),
          content: infoContent.replace('^PLACE_ADDRESS', items[i].address)
            .replace('^PLACE_NAME', items[i].name)
            .replace('^PLACE_URL', items[i].url)
            .replace('^PLACE_ID', items[i].placeId)
            .replace('^PLACE_TEL', items[i].tel === undefined ? '' : items[i].tel)
            .replace('^PLACE_TEL1', items[i].tel === undefined ? '' : items[i].tel)
            .replace('^PLACE_CATEGORY', items[i].category === undefined ? '' : items[i].category),
          removable: true,
        });
        window.kakao.maps.event.addListener(
          marker,
          'click',
          this.clickInfo(this.map, marker, infowindow),
        );
        this.infowindows.push(infowindow);
        this.markers.push(marker);
        infowindow.close(this.map, marker);
      }
      if (items.length !== 0) {
        this.map.setBounds(bounds);
      }
    },
    searchMap(items) {
      this.markers = [];
      this.infowindows = [];
      const container = document.getElementById('map');
      const options = {
        center: new window.kakao.maps.LatLng(items[0].y, items[0].x),
        level: 3,
      };
      this.map = new window.kakao.maps.Map(container, options);
      this.map.setMapTypeId(window.kakao.maps.MapTypeId.ROADMAP);
      // 마커 표시하기
      const bounds = new window.kakao.maps.LatLngBounds(); // 지도 범위 재설정을 위함
      // TODO: 나중에 마커 위치가 전국 각지에 있다면 어떻게 할 건지 고민해보자!
      const imageSrc = 'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png';
      for (let i = 0; i < items.length; i += 1) {
        const imageSize = new window.kakao.maps.Size(24, 35);
        const markerImage = new window.kakao.maps.MarkerImage(imageSrc, imageSize);
        const marker = new window.kakao.maps.Marker({
          map: this.map,
          position: new window.kakao.maps.LatLng(items[i].y, items[i].x),
          title: items[i].place_name,
          image: markerImage,
        });
        bounds.extend(new window.kakao.maps.LatLng(items[i].y, items[i].x));
        const infoContent = '<div style="min-width:20rem; line-height:100%; min-height: 9rem;"><div style="background-color: #ffd54f; border-right: 1px solid rgb(118, 129, 168)"><h4 style="padding:10px 15px; font-size:1.3rem; font-weight: 700; line-height:1.5em; padding-bottom:10px; color:#333;">^PLACE_NAME</h4></div><div><p style="opacity: 0.7; padding:0px 15px;">^PLACE_ADDRESS</p><div style="padding-left: 15px; color: #633D20">^PLACE_CATEGORY</div><a href="tel:^PLACE_TEL" style="color: #333; position: absolute; bottom: 1vh; left: 15px">^PLACE_TEL1</a><div style="position: absolute; bottom: 1vh; right: 15px;"><a href="https://map.kakao.com/link/to/^PLACE_ID" target="_blank"><i class="fas fa-location-arrow" style="float: right; color: #333"></i></a><a href="^PLACE_URL" target="_blank"><i class="fas fa-info" style="color: #333; float: right; margin-right: 1em;"></i></a></div></div></div>';
        const category = items[i].category_name.split('>');
        const infowindow = new window.kakao.maps.InfoWindow({
          position: marker.getPosition(),
          content: infoContent.replace('^PLACE_ADDRESS', items[i].road_address_name)
            .replace('^PLACE_NAME', items[i].place_name)
            .replace('^PLACE_URL', items[i].place_url)
            .replace('^PLACE_ID', items[i].id)
            .replace('^PLACE_TEL', items[i].phone)
            .replace('^PLACE_TEL1', items[i].phone)
            .replace('^PLACE_CATEGORY', category[category.length - 1]),
          removable: true,
        });
        window.kakao.maps.event.addListener(
          marker,
          'click',
          this.clickInfo(this.map, marker, infowindow),
        );
        this.infowindows.push(infowindow);
        this.markers.push(marker);
        infowindow.close(this.map, marker);
      }
      this.map.setBounds(bounds);
    },
    clickInfo(map, marker, infowindow) {
      return () => {
        infowindow.open(map, marker);
      };
    },
    panTo(map, lat, lng, index) {
      this.closeAll();
      this.clickInfo(map, this.markers[index], this.infowindows[index])();
      map.setLevel(3);
      map.panTo(new window.kakao.maps.LatLng(lat, lng));
    },
    closeAll() {
      const size = this.infowindows.length;
      for (let index = 0; index < size; index += 1) {
        this.closeInfo(this.map, this.markers[index], this.infowindows[index])();
      }
    },
    closeInfo(map, marker, infowindow) {
      return () => {
        infowindow.close(map, marker);
      };
    },
    deleteMap(index) {
      this.infowindows.splice(index, 1);
      this.markers.splice(index, 1);
    },
    closeReadDialog() {
      this.read = !this.read;
    },
    closeWriteDialog() {
      this.write = !this.write;
    },
    searchByWord() {
      if (this.keyword === '') {
        alert('검색어를 입력해주세요');
        return;
      }
      store.dispatch('updatePage', 1);
      store.dispatch('updateNoData', false);
      store.dispatch('updateBottom', false);
      store.dispatch('initResult', []);
      store.dispatch('updateKeyword', this.keyword);
      store.dispatch('search', this.keyword);
      this.$router.push('/search').catch(() => {});
      this.keyword = '';
    },
    logout() {
      api.post('/users/logout', {
        refresh_token: this.$session.get('refresh_token'),
      },
      {
        headers: {
          token: this.$session.get('token'),
        },
      }).then(({ data }) => {
        const decodeData = JWT(data);
        if (decodeData.User_ID === this.$session.get('uid')) {
          this.$session.destroy();
          this.$router.push('/');
          window.location.reload();
        } else {
          store.dispatch('showSnackbar', { color: 'error', msg: '로그아웃 실패, 다시 시도해주세요.' });
        }
      }).catch((err) => {
        console.error(err);
        store.dispatch('showSnackbar', { color: 'error', msg: '로그아웃 실패, 다시 시도해주세요.' });
      });
    },
  },
};
</script>

<style>

</style>
