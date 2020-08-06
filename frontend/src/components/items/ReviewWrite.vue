<template>
<!-- TODO : 리뷰읽기 창 크기 조절하기..... -->
<v-main>
  <v-container fluid>
  <v-bottom-sheet v-model="dialog" persistent inset attach scrollable>
    <v-sheet class="text-center">
      <v-toolbar>
      <v-btn
        icon
        @click="close"
      >
        <v-icon>mdi-close</v-icon>
      </v-btn>
    </v-toolbar>
    <v-container
      id="scroll-target"
      class="overflow-y-auto"
      style="max-height: 640px"
    >
      <v-row
        v-scroll:#scroll-target="onScroll"
        align="center"
        justify="center"
      >
      </v-row>
      <!-- title, content, media, visitDate -->
      <v-form>
        <v-text-field
          v-model="title"
          label="제목"
          outlined
        ></v-text-field>
        <v-menu
          ref="menu"
          v-model="menu"
          :close-on-content-click="false"
          :return-value.sync="visitDate"
          transition="scale-transition"
          offset-y
          min-width="290px"
        >
          <template v-slot:activator="{ on, attrs }">
            <v-text-field
              v-model="visitDate"
              label="날짜"
              readonly
              v-bind="attrs"
              v-on="on"
              outlined
            ></v-text-field>
          </template>
          <v-date-picker v-model="visitDate" no-title scrollable>
            <v-spacer></v-spacer>
            <v-btn text color="primary" @click="menu = false">Cancel</v-btn>
            <v-btn text color="primary" @click="$refs.menu.save(date)">OK</v-btn>
          </v-date-picker>
        </v-menu>
        <v-textarea
          v-model="content"
          label="내용"
          outlined
          rows="10"
          no-resize
        ></v-textarea>
        <v-btn color="primary" @click="writeReview">작성하기</v-btn>
        <v-btn color="error" @click="close">취소하기</v-btn>
      </v-form>
    </v-container>
    </v-sheet>
  </v-bottom-sheet>
  </v-container>
</v-main>
</template>

<script>
import store from '@/store/index';
import api from '@/utils/api';

export default {
  data() {
    return {
      title: '',
      visitDate: new Date().toISOString().substr(0, 10),
      content: '',
      menu: false,
    };
  },
  computed: {
    dialog: () => store.getters.writeDialog,
  },
  methods: {
    close() {
      this.initReview();
      store.dispatch('closeWriteDialog');
    },
    onScroll(e) {
      this.offsetTop = e.target.scrollTop;
    },
    initReview() { // 내용 초기화

    },
    writeReview() { // 리뷰 작성
      api.post('/reviews', {
        title: this.title,
        visitDate: this.visitDate,
        content: this.content,
        userId: this.$session.get('uid'),
        groupId: store.getters.item.groupId,
        placeId: store.getters.item.placeId,
        themeId: store.getters.item.themeId,
      }, {
        headers: {
          token: this.$session.get('token'),
        },
      }).then((res) => {
        if (res.status === 201) {
          const payload = { color: 'success', msg: '리뷰 등록 완료' };
          store.dispatch('showSnackbar', payload);
          this.close();
        }
      });
    },
  },
};
</script>

<style scoped>
.v-bottom-sheet {
  position: absolute;
  bottom: 0;
  right: 0;
  height: 91.5%;
  width: 100%;
}
.v-bottom-sheet.v-bottom-sheet--inset {
  max-width: 100%;
}
.v-sheet {
  position: absolute;
  bottom: 0;
  right: 0;
  height: 691px;
  width: 1139px;
}
</style>
