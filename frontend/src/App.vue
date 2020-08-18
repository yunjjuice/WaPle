<template>
  <v-app>
    <router-view></router-view>
  </v-app>
</template>

<script>

export default {
  components: {
  },
  props: {
    source: String,
  },
  data: () => ({
  }),
  created() {
    // 사파리일경우 하단에 잘리는 현상으로 인해 동적 클래스 바인딩해줄 필요가 있음
    const agent = navigator.userAgent.toLowerCase();
    if (agent.indexOf('safari') !== -1) {
      this.$store.dispatch('updateSafari', true);
    }
  },
};
</script>

<style lang="scss">
@font-face { font-family: 'IBMPlexSansKR-Regular'; src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_20-07@1.0/IBMPlexSansKR-Regular.woff') format('woff'); font-weight: normal; font-style: normal; }
@font-face { font-family: 'paybooc-Bold'; src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_20-07@1.0/paybooc-Bold.woff') format('woff'); font-weight: normal; font-style: normal; }
@font-face { font-family: 'InfinitySans-RegularA1'; src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_20-04@2.1/InfinitySans-RegularA1.woff') format('woff'); font-weight: normal; font-style: normal; }
@font-face { font-family: 'CookieRun-Regular'; src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2001@1.1/CookieRun-Regular.woff') format('woff'); font-weight: normal; font-style: normal; }
@font-face { font-family: 'LotteMartHappy'; src: url('https://cdn.jsdelivr.net/korean-webfonts/1/corps/lottemart/LotteMartHappy/LotteMartHappyMedium.woff2') format('woff2'); font-style: normal; font-weight: 400; }
@import url('//cdn.jsdelivr.net/font-iropke-batang/1.2/font-iropke-batang.css');
@font-face { font-family: 'MapoPeacefull'; src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2001@1.1/MapoPeacefullA.woff') format('woff'); font-weight: normal; font-style: normal; }
@import url(//fonts.googleapis.com/earlyaccess/hanna.css);
@font-face { font-family: 'BMHANNAPro'; src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_seven@1.0/BMHANNAPro.woff') format('woff'); font-weight: normal; font-style: normal; }
@font-face { font-family: 'BBTreeGB'; src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_nine_@1.1/BBTreeGB.woff') format('woff'); font-weight: normal; font-style: normal; }

.v-application {
  font-family: 'IBMPlexSansKR-Regular', sans-serif !important;
  .headline {
    font-family: 'Hanna', sans-serif !important;
  }
}
</style>
