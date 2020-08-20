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
          <template v-if="review.userId == this.$session.get('uid')">
            <v-tooltip bottom>
              <template v-slot:activator="{ on, attrs }">
                <v-btn
                  icon
                  v-bind="attrs"
                  v-on="on"
                  @click.stop="editDialog = !editDialog"
                >
                  <v-icon>mdi-pencil-outline</v-icon>
                </v-btn>
              </template>
              <span>수정</span>
            </v-tooltip>
            <v-tooltip bottom>
              <template v-slot:activator="{ on, attrs }">
                <v-btn
                  icon
                  v-bind="attrs"
                  v-on="on"
                  @click.stop="removeDialog = true"
                >
                  <v-icon>mdi-trash-can-outline</v-icon>
                </v-btn>
              </template>
              <span>삭제</span>
            </v-tooltip>
          </template>
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
          <h1>{{ review.title }}</h1>
          <h5><i style="color:gray">{{ review.visitDate }}</i></h5>
          {{ review.groupName }}, wtitten by {{ review.userName }}
          <div class="image" v-if="review.images && review.images.length > 0">
            <div class="card-container">
              <div v-for="(img, index) in review.images" :key="index"
                class="card"
                :style="{ '--image': 'url(' + img + ')',
                          '--x':x[index], '--y':y[index],
                          '--angle':angle[index], 'z-index':z[index] }"
                @click=onClickImg(img)
              ></div>
            </div>
          </div>
          <div class="review__block" style="white-space:pre-wrap;">{{ review.content }}</div>
        </v-container>
      </v-sheet>
    </v-bottom-sheet>
  </v-container>
  <div id="myModal" class="modal" @click=onClickModal>
    <img class="modal-content" id="modalImg">
  </div>
  <remove-modal :dialog="removeDialog" @close="removeDialog = false;"></remove-modal>
  <edit-sheet :editDialog="editDialog" @close="editDialog = false"></edit-sheet>
</v-main>
</template>

<script>
import store from '@/store/index';

export default {
  components: {
    RemoveModal: () => import('@/components/items/ReviewRemoveModal.vue'),
    EditSheet: () => import('@/components/items/ReviewEdit.vue'),
  },
  data() {
    return {
      x: ['0%', '-50%', '50%', '-100%', '100%'],
      y: ['-5%', '5%', '5%', '-5%', '-5%'],
      angle: ['-2deg', '2deg', '2deg', '-2deg', '-2deg'],
      z: [2, 1, 3, 0, 4],
      dialog: false,
      removeDialog: false,
      editDialog: false,
    };
  },
  props: [
    'readDialog',
  ],
  computed: {
    review: () => store.getters.review,
    height: () => store.getters.height,
    width: () => store.getters.width,
  },
  watch: {
    readDialog() {
      this.dialog = !this.dialog;
    },
  },
  methods: {
    close() {
      this.$store.dispatch('changeReadDialog');
      this.dialog = !this.dialog;
      this.$emit('closeRead');
    },
    onScroll(e) {
      this.offsetTop = e.target.scrollTop;
    },
    onClickImg(img) {
      document.getElementById('myModal').style.display = 'block';
      document.getElementById('modalImg').src = img;
    },
    onClickModal() {
      document.getElementById('modalImg').className += ' out';
      setTimeout(() => {
        document.getElementById('myModal').style.display = 'none';
        document.getElementById('modalImg').className = 'modal-content';
      }, 400);
    },
  },
};
</script>

<style scoped lang="scss">
.sheet {
  position: absolute;
  bottom: 0;
  right: 0;
  background-color: #F1F1F1;
}

v-main {
  box-sizing: border-box;
}

.review {
  color: #030018;
  padding: 1rem;

  &__block {
    background-color: #ffffff;
    text-align: left;
    margin: 10px 0;
    padding: 30px;
    border-radius: 15px;
  }
}

.image {
  display: flex;
  justify-content: center;
  align-items: center;
}

*,
*:before,
*:after {
  box-sizing: inherit;
}

.card-container {
  position: relative;
  width: 40vmin;
  height: 40vmin;
}

.card {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: darken(white, 2%);
  border-radius: 2px;
  box-shadow: 2px 2px 5px rgba(#111, .35);
  transition: transform .35s ease-out;
  transform: translate(var(--x), var(--y)) scale(.8) rotate(var(--angle));
  will-change: transform;
  cursor: pointer;

  &:hover {
    &:before {
      opacity: 1;
    }
  }
}

.card:before {
  content: "";
  display: block;
  width: 90%;
  height: 90%;
  margin: 5%;
  background: var(--image) center center no-repeat;
  background-size: cover;
  box-shadow: inset 0 0 5px rgba(#222, .35);
  border-radius: 2px;
  filter: sepia(.2) brightness(.9) contrast(1.2);
  transition: opacity .35s ease-out;
  opacity: .5;
  will-change: opacity;
}

.modal {
    display: none; /* Hidden by default */
    position: fixed; /* Stay in place */
    z-index: 999; /* Sit on top */
    padding-top: 100px; /* Location of the box */
    left: 0;
    top: 0;
    width: 100%; /* Full width */
    height: 100%; /* Full height */
    overflow: auto; /* Enable scroll if needed */
    background-color: rgb(0,0,0); /* Fallback color */
    background-color: rgba(0,0,0,0.9); /* Black w/ opacity */
}

/* Modal Content (image) */
.modal-content {
    margin: auto;
    display: block;
    width: 75%;
    //max-width: 75%;
}

/* Add Animation */
.modal-content {
    -webkit-animation-name: zoom;
    -webkit-animation-duration: 0.6s;
    animation-name: zoom;
    animation-duration: 0.6s;
}

.out {
  animation-name: zoom-out;
  animation-duration: 0.6s;
}

@-webkit-keyframes zoom {
    from {-webkit-transform:scale(1)}
    to {-webkit-transform:scale(2)}
}

@keyframes zoom {
    from {transform:scale(0.4)}
    to {transform:scale(1)}
}

@keyframes zoom-out {
    from {transform:scale(1)}
    to {transform:scale(0)}
}

/* 100% Image Width on Smaller Screens */
@media only screen and (max-width: 700px){
    .modal-content {
        width: 100%;
    }
}
</style>
