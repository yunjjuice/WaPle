import Vue from 'vue';
import VueSession from 'vue-session';
import DatetimePicker from 'vuetify-datetime-picker';
import moment from 'moment-timezone';
import AOS from 'aos';
import 'aos/dist/aos.css';
import App from './App.vue';
import router from './router';
import store from './store';
import vuetify from './plugins/vuetify';

Vue.use(VueSession, { persist: true });
Vue.use(DatetimePicker);

Vue.config.productionTip = false;

new Vue({
  created() {
    AOS.init({ disable: 'phone' });
  },
  router,
  store,
  vuetify,
  moment,
  render: (h) => h(App),
}).$mount('#app');
