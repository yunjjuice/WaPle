<template>
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
        <div style="display: flex; justify-content: center;">
          <vue-upload-multiple-image
            drag-text='사진 업로드'
            browse-text='파일 고르기'
            drop-text='업로드'
            primary-text='기본 이미지'
            mark-is-primary-text='기본 이미지로 설정'
            accept='image/jpeg,image/png,image/jpg'
            @upload-success="uploadImageSuccess"
            @before-remove="beforeRemove"
            @edit-image="editImage"
            :data-images="images"
          ></vue-upload-multiple-image>
        </div>
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
import axios from 'axios';
import VueUploadMultipleImage from 'vue-upload-multiple-image';

export default {
  components: {
    VueUploadMultipleImage,
  },
  data() {
    return {
      title: '',
      visitDate: new Date().toISOString().substr(0, 10),
      content: '',
      menu: false,
      images: [],
      formDatas: [],
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
    uploadImage(formData) {
      return new Promise((resolve, reject) => {
        axios.post('http://i3a204.p.ssafy.io:8888/reviews/images', formData, {
          headers: {
            'Content-type': 'multipart/form-data',
          },
        })
          .then((res) => resolve(res.data))
          .catch((err) => reject(err));
      });
    },
    async writeReview() { // 리뷰 작성
      /* eslint-disable no-restricted-syntax */
      /* eslint-disable no-await-in-loop  */
      let media = '';
      for (const data of this.formDatas) {
        media += await this.uploadImage(data);
        media += ';';
      }

      api.post('/reviews', {
        title: this.title,
        visitDate: this.visitDate,
        content: this.content,
        userId: this.$session.get('uid'),
        groupId: store.getters.item.groupId,
        placeId: store.getters.item.placeId,
        themeId: store.getters.item.themeId,
        media,
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
    uploadImageSuccess(formData) {
      this.formDatas.push(formData);
    },
    beforeRemove(index, done) {
      this.formDatas.splice(index, 1);
      done();
    },
    editImage(formData, index) {
      this.formDatas.splice(index, 1, formData);
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
