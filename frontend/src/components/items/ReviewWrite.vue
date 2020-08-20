<template>
<v-main>
  <v-container fluid>
    <v-bottom-sheet
      v-model="dialog"
      scrollable
      :fullscreen="$vuetify.breakpoint.smAndDown"
    >
      <v-sheet
        class="text-center"
        :class="{'sheet': $vuetify.breakpoint.mdAndUp}"
        :style="{ height: height + 'px', width: width + 'px' }"
      >
        <v-toolbar dense color="#ffc34d">
          <v-spacer></v-spacer>
          <v-toolbar-title>{{ item.name }}</v-toolbar-title>
          <v-spacer></v-spacer>
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
          :style="{ 'max-height': height - 50 + 'px', padding: '50px' }"
        >
          <v-row
            v-scroll:#scroll-target="onScroll"
            align="center"
            justify="center"
          >
          </v-row>
          <validation-observer ref="reviewForm">
            <validation-provider v-slot="{ errors }" name="title" rules="required">
              <v-text-field
                v-model="title"
                label="제목"
                outlined
                :error-messages="errors"
              ></v-text-field>
            </validation-provider>
            <validation-provider v-slot="{ errors }" name="group" rules="required">
              <v-select
                label="그룹 선택"
                :items="groups"
                v-model="group"
                item-text="name"
                return-object
                outlined
                :error-messages="errors"
              ></v-select>
            </validation-provider>
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
                  required
                ></v-text-field>
              </template>
              <v-date-picker v-model="visitDate" no-title scrollable>
                <v-spacer></v-spacer>
                <v-btn text color="primary" @click="menu = false">Cancel</v-btn>
                <v-btn text color="primary" @click="$refs.menu.save(visitDate)">OK</v-btn>
              </v-date-picker>
            </v-menu>
            <validation-provider v-slot="{ errors }" name="content" rules="required">
              <v-textarea
                v-model="content"
                label="내용"
                outlined
                rows="10"
                no-resize
                required
                :error-messages="errors"
              ></v-textarea>
            </validation-provider>
          </validation-observer>
          <div style="display: flex; justify-content: center;">
            <vue-upload-multiple-image
              drag-text='사진 업로드'
              browse-text='파일 고르기'
              drop-text='업로드'
              accept='image/jpeg,image/png,image/jpg'
              :show-primary=false
              @upload-success="uploadImageSuccess"
              @before-remove="beforeRemove"
              @edit-image="editImage"
              :data-images="images"
            ></vue-upload-multiple-image>
          </div>
          <br>
          <v-btn color="primary" @click="isValid">작성하기</v-btn>
          <v-btn color="error" @click="close">취소하기</v-btn>
        </v-container>
      </v-sheet>
    </v-bottom-sheet>
  </v-container>
</v-main>
</template>

<script>
import axios from 'axios';
import VueUploadMultipleImage from 'vue-upload-multiple-image';
import { ValidationObserver, ValidationProvider, extend } from 'vee-validate';
import { required } from 'vee-validate/dist/rules';
import store from '@/store/index';
import api from '@/utils/api';

extend('required', {
  ...required,
  message: '{_field_} can not be empty',
});

export default {
  components: {
    VueUploadMultipleImage,
    ValidationObserver,
    ValidationProvider,
  },
  data() {
    return {
      title: '',
      group: '',
      visitDate: new Date().toISOString().substr(0, 10),
      content: '',
      menu: false,
      images: [],
      formDatas: [],
      maxImageSize: 512 * 1024, // 512KB
      dialog: false,
      valid: true,
    };
  },
  props: [
    'writeDialog',
  ],
  computed: {
    groups: () => store.getters.groups,
    item: () => store.getters.item,
    height: () => store.getters.height,
    width: () => store.getters.width,
  },
  watch: {
    writeDialog() {
      this.dialog = !this.dialog;
    },
  },
  updated() {
    this.initReview();
  },
  methods: {
    close() {
      store.dispatch('changeWriteDialog');
      this.dialog = !this.dialog;
      this.$emit('closeWrite');
    },
    onScroll(e) {
      this.offsetTop = e.target.scrollTop;
    },
    initReview() { // 내용 초기화
      if (this.$refs.reviewForm) {
        this.$refs.reviewForm.reset();
      }
      this.title = '';
      this.group = '';
      this.visitDate = new Date().toISOString().substr(0, 10);
      this.content = '';
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
    isValid() {
      this.$refs.reviewForm.validate()
        .then((result) => {
          if (result) { // 입력이 다 되었다면 요청 보내기
            this.writeReview();
          }
        });
    },
    async writeReview() { // 리뷰 작성
      let media = '';
      // eslint-disable-next-line
      for (const data of this.formDatas) {
        // eslint-disable-next-line
        media += await this.uploadImage(data).catch((err) => {
          console.error(err);
          this.$toast.error('리뷰 등록 실패, 다시 시도해주세요.');
        });
        media += ';';
      }

      api.post('/reviews', {
        title: this.title,
        visitDate: this.visitDate,
        content: this.content,
        userId: this.$session.get('uid'),
        groupId: this.group.groupId,
        placeId: store.getters.item.placeId,
        media,
      }, {
        headers: {
          token: this.$session.get('token'),
        },
      }).then(() => {
        this.$toast.success('리뷰 등록 성공');
      }).catch((err) => {
        console.error(err);
        this.$toast.error('리뷰 등록 실패, 다시 시도해주세요.');
      });
      this.close();
    },
    checkImageExtension(filename) {
      const extension = filename.slice(filename.lastIndexOf('.') + 1).toLowerCase();
      if (extension !== 'jpeg' && extension !== 'png' && extension !== 'jpg') {
        this.$toast.error('jpg, jpeg, png 파일만 업로드 가능합니다.');
        return false;
      }
      return true;
    },
    checkImageSize(file) {
      if (file.size > this.maxImageSize) {
        this.$toast.error('파일이 너무 큽니다.');
        return false;
      }
      return true;
    },
    uploadImageSuccess(formData, index, fileList) {
      if (!this.checkImageExtension(fileList[index].name)
          || !this.checkImageSize(formData.get('file'))) {
        fileList.pop();
        return;
      }
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
.sheet {
  position: absolute;
  bottom: 0;
  right: 0;
}
</style>
