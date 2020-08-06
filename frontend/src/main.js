import Vue from 'vue';
import VueSession from 'vue-session';
import DatetimePicker from 'vuetify-datetime-picker';
import App from './App.vue';
import router from './router';
import store from './store';
import vuetify from './plugins/vuetify';

Vue.use(VueSession);
Vue.use(DatetimePicker);

Vue.config.productionTip = false;

new Vue({
  router,
  store,
  vuetify,
  render: (h) => h(App),
}).$mount('#app');
