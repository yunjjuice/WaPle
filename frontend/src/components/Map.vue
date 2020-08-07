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

export default {
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
      this.searchMap(newItems);
    },
  },
  methods: {
    initMap(items) {
      const container = document.getElementById('map');
      const options = {
        center: new window.kakao.maps.LatLng(items.length === 0 ? '37.501245' : items[0].lat,
          items.length === 0 ? '127.039592' : items[0].lng),
        level: 3,
      };
      const map = new window.kakao.maps.Map(container, options);
      map.setMapTypeId(window.kakao.maps.MapTypeId.ROADMAP);
      // 마커 표시하기
      const bounds = new window.kakao.maps.LatLngBounds(); // 지도 범위 재설정을 위함
      // TODO: 나중에 마커 위치가 전국 각지에 있다면 어떻게 할 건지 고민해보자!
      const imageSrc = 'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png';
      for (let i = 0; i < items.length; i += 1) {
        const imageSize = new window.kakao.maps.Size(24, 35);
        const markerImage = new window.kakao.maps.MarkerImage(imageSrc, imageSize);
        const marker = new window.kakao.maps.Marker({
          map,
          position: new window.kakao.maps.LatLng(items[i].lat, items[i].lng),
          title: items[i].name,
          image: markerImage,
        });
        bounds.extend(new window.kakao.maps.LatLng(items[i].lat, items[i].lng));
        const infowindow = new window.kakao.maps.InfoWindow({});
        infowindow.close(map, marker);
      }
      if (items.length !== 0) {
        map.setBounds(bounds);
      }
    },
    searchMap(items) {
      const container = document.getElementById('map');
      const options = {
        center: new window.kakao.maps.LatLng(items[0].y, items[0].x),
        level: 3,
      };
      const map = new window.kakao.maps.Map(container, options);
      map.setMapTypeId(window.kakao.maps.MapTypeId.ROADMAP);
      // 마커 표시하기
      const bounds = new window.kakao.maps.LatLngBounds(); // 지도 범위 재설정을 위함
      // TODO: 나중에 마커 위치가 전국 각지에 있다면 어떻게 할 건지 고민해보자!
      const imageSrc = 'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png';
      for (let i = 0; i < items.length; i += 1) {
        const imageSize = new window.kakao.maps.Size(24, 35);
        const markerImage = new window.kakao.maps.MarkerImage(imageSrc, imageSize);
        const marker = new window.kakao.maps.Marker({
          map,
          position: new window.kakao.maps.LatLng(items[i].y, items[i].x),
          title: items[i].place_name,
          image: markerImage,
        });
        bounds.extend(new window.kakao.maps.LatLng(items[i].y, items[i].x));
        const infowindow = new window.kakao.maps.InfoWindow({});
        infowindow.close(map, marker);
      }
      map.setBounds(bounds);
    },
  },
};
</script>

<style>

</style>
