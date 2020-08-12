import Vue from 'vue';
import VueSession from 'vue-session';
import DatetimePicker from 'vuetify-datetime-picker';
import gallery from 'img-vuer';
import App from './App.vue';
import router from './router';
import store from './store';
import vuetify from './plugins/vuetify';

Vue.use(VueSession, { persist: true });
Vue.use(DatetimePicker);
Vue.use(gallery, {
  swipeThreshold: 150, // default 100
  isIndexShow: true, // show image index, default true
  useCloseButton: true, // trigger gallery close with close button, default true
  preload: true, // preload images in the same group, default true
});

Vue.config.productionTip = false;

new Vue({
  router,
  store,
  vuetify,
  render: (h) => h(App),
}).$mount('#app');
