import Vue from 'vue';
import VueSession from 'vue-session';
import DatetimePicker from 'vuetify-datetime-picker';
import moment from 'moment-timezone';
import AOS from 'aos';
import 'aos/dist/aos.css';
import Toast from 'vue-toastification';
import 'vue-toastification/dist/index.css';
import App from './App.vue';
import router from './router';
import store from './store';
import vuetify from './plugins/vuetify';

Vue.use(VueSession, { persist: true });
Vue.use(DatetimePicker);
Vue.use(Toast, {
  transition: 'Vue-Toastification__fade',
  maxToasts: 5,
  pauseOnFocusLoss: false,
  pauseOnHover: false,
  timeout: 3000,
  hideProgressBar: true,
  filterBeforeCreate: (toast, toasts) => {
    // If same toast is already exist, remove old one.
    const prev = toasts.find((t) => t.content === toast.content);
    if (prev != null) {
      Vue.$toast.dismiss(prev.id);
    }
    return toast;
  },
});

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
