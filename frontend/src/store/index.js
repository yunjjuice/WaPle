import Vue from 'vue';
import Vuex from 'vuex';
import appointment from '@/store/modules/appointment';
import map from '@/store/modules/map';
import place from '@/store/modules/place';
import review from '@/store/modules/review';
import snackbar from '@/store/modules/snackbar';

Vue.use(Vuex);

export default new Vuex.Store({
  modules: {
    appointment,
    map,
    place,
    review,
    snackbar,
  },
});
