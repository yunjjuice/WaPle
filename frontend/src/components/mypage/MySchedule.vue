<template>
<v-main>
  <v-container>
    <v-sheet height="64">
      <v-toolbar flat color="white">
        <v-btn outlined class="mr-4" color="grey darken-2" @click="setToday">
          Today
        </v-btn>
        <v-btn fab text small color="grey darken-2" @click="prev">
          <v-icon small>mdi-chevron-left</v-icon>
        </v-btn>
        <v-btn fab text small color="grey darken-2" @click="next">
          <v-icon small>mdi-chevron-right</v-icon>
        </v-btn>
        <v-toolbar-title v-if="$refs.calendar">
          {{ $refs.calendar.title }}
        </v-toolbar-title>
        <v-spacer></v-spacer>
        <v-menu bottom right>
          <template v-slot:activator="{ on, attrs }">
            <v-btn
              outlined
              color="grey darken-2"
              v-bind="attrs"
              v-on="on"
            >
              <span>{{ typeToLabel[type] }}</span>
              <v-icon right>mdi-menu-down</v-icon>
            </v-btn>
          </template>
          <v-list>
            <v-list-item @click="type = 'day'">
              <v-list-item-title>Day</v-list-item-title>
            </v-list-item>
            <v-list-item @click="type = 'week'">
              <v-list-item-title>Week</v-list-item-title>
            </v-list-item>
            <v-list-item @click="type = 'month'">
              <v-list-item-title>Month</v-list-item-title>
            </v-list-item>
          </v-list>
        </v-menu>
      </v-toolbar>
    </v-sheet>
    <v-sheet height="600">
      <v-calendar
        ref="calendar"
        v-model="focus"
        color="primary"
        :events="events"
        :event-color="getEventColor"
        :type="type"
        @click:event="showEvent"
        @click:more="viewDay"
        @click:date="viewDay"
        @change="updateRange"
      ></v-calendar>
      <v-menu
        v-model="selectedOpen"
        :close-on-content-click="false"
        :activator="selectedElement"
        offset-x
        max-width="350px"
      >
        <v-card
          color="grey lighten-4"
          min-width="350px"
          max-width="350px"
          flat
        >
          <v-toolbar
            :color="selectedEvent.color"
            dark
          >
            <v-toolbar-title v-html="selectedEvent.name"></v-toolbar-title>
            <v-spacer></v-spacer>
          </v-toolbar>
          <v-card-text>
            <span v-html="selectedEvent.details"></span>
          </v-card-text>
          <v-card-actions>
            <v-btn
              text
              color="secondary"
              @click="selectedOpen = false"
            >
              Cancel
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-menu>
    </v-sheet>
  </v-container>
</v-main>
</template>

<script>
import store from '@/store/index';
import api from '@/utils/api';
import moment from 'moment';

export default {
  data: () => ({
    focus: '',
    type: 'month',
    typeToLabel: {
      month: 'Month',
      week: 'Week',
      day: 'Day',
    },
    selectedEvent: {},
    selectedElement: null,
    selectedOpen: false,
    events: [],
    colors: ['blue', 'indigo', 'deep-purple', 'cyan', 'green', 'orange', 'grey darken-1'], // color 들어가야되는 부분
  }),

  mounted() {
    this.$refs.calendar.checkChange();
  },

  methods: {
    viewDay({ date }) {
      this.focus = date;
      this.type = 'day';
    },
    getEventColor(event) {
      return event.color;
    },
    setToday() {
      this.focus = '';
    },
    prev() {
      this.$refs.calendar.prev();
    },
    next() {
      this.$refs.calendar.next();
    },
    showEvent({ nativeEvent, event }) { // 일정 눌렀을 때
      const open = () => {
        this.selectedEvent = event;
        this.getVotes(this.selectedEvent.groupId, this.selectedEvent.promiseId);
        this.selectedElement = nativeEvent.target;
        setTimeout(() => { this.selectedOpen = true; }, 10);
      };

      if (this.selectedOpen) {
        this.selectedOpen = false;
        setTimeout(open, 10);
      } else {
        open();
      }

      nativeEvent.stopPropagation();
    },
    updateRange() {
      const userId = this.$session.get('uid');
      const events = [];

      api.get(`/calendars/${userId}`, {
        headers: {
          token: this.$session.get('token'),
        },
      }).then(({ data }) => {
        for (let i = 0; i < data.length; i += 1) {
          events.push({
            name: data[i].title,
            start: this.getFormatDate(data[i].promiseDate),
            color: this.colors[this.rnd(0, this.colors.length - 1)],
            details: '',
            groupId: data[i].groupId,
            groupName: data[i].groupName,
            promiseId: data[i].promiseId,
          });
        }
      }).catch((err) => {
        console.error(err);
        store.dispatch('showSnackbar', { color: 'error', msg: '일정 조회 실패, 다시 시도해주세요.' });
      });
      this.events = events;
    },
    rnd(a, b) {
      return Math.floor((b - a + 1) * Math.random()) + a;
    },
    getFormatDate(datetime) {
      return moment(datetime).format('YYYY-MM-DD hh:mm');
    },
    getVotes(groupId, promiseId) {
      let detail = '';
      api.get(`/votes/${groupId}/${promiseId}`, {
        headers: {
          token: this.$session.get('token'),
        },
      }).then(({ data }) => {
        detail = `<b>${this.selectedEvent.groupName}</b> 그룹과 <br>`;
        detail += `<span><a href=${data[0].url} target="_blank">${data[0].name}</a></sapn><br>`;
        detail += '에 갈 예정입니다';
        this.selectedEvent.details = detail;
      }).catch((err) => {
        console.error(err);
        store.dispatch('showSnackbar', { color: 'error', msg: '정보 조회 실패, 다시 시도해주세요.' });
      });
    },
  },
};
</script>
