import Vue from 'vue';
import Vuex from 'vuex';
import appointment from '@/store/modules/appointment';
import bookmark from '@/store/modules/bookmark';
import map from '@/store/modules/map';
import place from '@/store/modules/place';
import review from '@/store/modules/review';
import snackbar from '@/store/modules/snackbar';
import group from '@/store/modules/group';
import size from '@/store/modules/size';

Vue.use(Vuex);

export default new Vuex.Store({
  modules: {
    appointment,
    bookmark,
    map,
    place,
    review,
    snackbar,
    group,
    size,
  },
});
