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
          <v-tooltip bottom>
            <template v-slot:activator="{ on, attrs }">
              <v-btn
                icon
                v-bind="attrs"
                v-on="on"
                @click.stop="editDialog = true"
              >
                <v-icon>mdi-information-outline</v-icon>
              </v-btn>
            </template>
            <span>제목, 내용만 수정 가능합니다.</span>
          </v-tooltip>
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
          style="max-height: 640px; padding:50px;"
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
            <v-text-field
              label="그룹"
              :value="review.groupName"
              readonly
              outlined
            ></v-text-field>
            <v-text-field
              :value="review.visitDate"
              label="날짜"
              readonly
              outlined
            ></v-text-field>
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
          <br>
          <v-btn color="primary" @click="isValid">수정하기</v-btn>
          <v-btn color="error" @click="close">취소하기</v-btn>
        </v-container>
      </v-sheet>
    </v-bottom-sheet>
  </v-container>
</v-main>
</template>

<script>
import { ValidationObserver, ValidationProvider, extend } from 'vee-validate';
import { required } from 'vee-validate/dist/rules';
import store from '@/store/index';

extend('required', {
  ...required,
  message: '{_field_} can not be empty',
});

export default {
  components: {
    ValidationObserver,
    ValidationProvider,
  },
  data() {
    return {
      title: '',
      content: '',
      valid: true,
    };
  },
  props: [
    'dialog',
  ],
  computed: {
    review: () => store.getters.review,
    height: () => store.getters.height,
    width: () => store.getters.width,
  },
  methods: {
    close() {
      this.$emit('close');
      this.$refs.reviewForm.reset();
    },
    onScroll(e) {
      this.offsetTop = e.target.scrollTop;
    },
    isValid() {
      this.$refs.reviewForm.validate()
        .then((result) => {
          if (result) { // 입력이 다 되었다면 요청 보내기
            this.updateReview();
          }
        });
    },
    updateReview() {
      store.dispatch('updateReview', {
        reviewId: this.review.reviewId,
        title: this.title,
        content: this.content,
      });
      this.close();
    },
  },
  updated() {
    this.title = this.review.title;
    this.content = this.review.content;
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
