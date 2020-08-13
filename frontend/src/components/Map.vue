<template>
<v-main>
  <v-container id='map' style="height: 100%">
  </v-container>
  <review-write></review-write>
  <review-read></review-read>
</v-main>
</template>

<script>
import store from '@/store/index';
import EventBus from '@/utils/EventBus';

export default {
  data() {
    return {
      map: null,
      infowindows: [],
      markers: [],
    };
  },
  created() {
    EventBus.$on('moveMap', (payload) => {
      this.panTo(this.map, payload.lat, payload.lng, payload.index);
    });
    EventBus.$on('deleteCard', (payload) => {
      this.deleteMap(payload);
    });
  },
  components: {
    ReviewWrite: () => import('@/components/items/ReviewWrite.vue'),
    ReviewRead: () => import('@/components/items/ReviewRead.vue'),
  },
  computed: {
    currentLocation: () => store.getters.items, // 서버에서 정보 받아올 때
    searchLocation: () => store.getters.result, // 카카오 검색에서 정보 받아올 때
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
      const imageSrc = 'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png';
      for (let i = 0; i < items.length; i += 1) {
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
        const infoContent = '<div style="min-width:200px; line-height:100%; min-height: 165px"><div style="background-color: #ffd54f;"><h4 style="padding:10px 15px; font-size:20px; font-weight: 700; line-height:1.5em; padding-bottom:10px; color:#333;">^PLACE_NAME</h4></div><div><p style="opacity: 0.7; padding:10px 15px; ">^PLACE_ADDRESS</p><p style="color: #333; padding:10px 15px; "><a href="^PLACE_TEL" style="color: #333;">^PLACE_TEL1</a><div style="position: absolute; bottom: 10px; right: 15px;"><a href="https://map.kakao.com/link/to/^PLACE_ID" target="_blank"><i class="fas fa-location-arrow" style="float: right; color: #333"></i></a><a href="^PLACE_URL" target="_blank"><i class="fas fa-info" style="color: #333; float: right; margin-right: 1em;"></i></a></div></p></div></div>';
        const infowindow = new window.kakao.maps.InfoWindow({
          position: marker.getPosition(),
          content: infoContent.replace('^PLACE_ADDRESS', items[i].address)
            .replace('^PLACE_NAME', items[i].name)
            .replace('^PLACE_URL', items[i].url)
            .replace('^PLACE_ID', items[i].placeId)
            .replace('^PLACE_TEL', items[i].tel)
            .replace('^PLACE_TEL1', items[i].tel),
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
      console.log(items);
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
        const infoContent = '<div style="min-width:200px; line-height:100%; min-height: 165px"><div style="background-color: #ffd54f;"><h4 style="padding:10px 15px; font-size:20px; font-weight: 700; line-height:1.5em; padding-bottom:10px; color:#333;">^PLACE_NAME</h4></div><div><p style="opacity: 0.7; padding:10px 15px; ">^PLACE_ADDRESS</p><p style="color: #333; padding:10px 15px; "><a href="^PLACE_TEL" style="color: #333;">^PLACE_TEL1</a><div style="position: absolute; bottom: 10px; right: 15px;"><a href="https://map.kakao.com/link/to/^PLACE_ID" target="_blank"><i class="fas fa-location-arrow" style="float: right; color: #333"></i></a><a href="^PLACE_URL" target="_blank"><i class="fas fa-info" style="color: #333; float: right; margin-right: 1em;"></i></a></div></p></div></div>';
        const infowindow = new window.kakao.maps.InfoWindow({
          position: marker.getPosition(),
          content: infoContent.replace('^PLACE_ADDRESS', items[i].road_address_name)
            .replace('^PLACE_NAME', items[i].place_name)
            .replace('^PLACE_URL', items[i].place_url)
            .replace('^PLACE_ID', items[i].id)
            .replace('^PLACE_TEL', items[i].phone)
            .replace('^PLACE_TEL1', items[i].phone),
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
  },
};
</script>

<style>

</style>
