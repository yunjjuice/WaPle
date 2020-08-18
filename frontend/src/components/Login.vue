<template>
<v-main>
  <v-container fluid fill-height class="fullpage" style="background:#e6e6e6">
    <v-layout row wrap align-center>
      <v-row
        align="center"
        justify="center"
        :class="{'mdUpRow': $vuetify.breakpoint.mdAndUp,
          'smDownRow': $vuetify.breakpoint.smAndDown}"
      >
        <v-col cols="12" sm="8" md="12" align="center" justify="center">
          <p
            class="popout"
            :class="{'mdUp': $vuetify.breakpoint.mdAndUp, 'smDown': $vuetify.breakpoint.smAndDown}"
          >
            <span>W</span>
            <span>A</span>
            <span>P</span>
            <span>L</span>
            <span>E</span><br>
            <span>우</span>
            <span>리</span>
            <span>만</span>
            <span>의</span><br>
            <span>플</span>
            <span>레</span>
            <span>이</span>
            <span>스</span>
          </p>
        </v-col>
        <v-col cols="12" sm="8" md="12" align="center" justify="center">
          <v-btn icon>
          <kakao-login
            api-key='0e8b85c161beec7b11ccccb161ab81ab'
            :on-success=onSuccess
            :on-failure=onFailure
            :image="button = $vuetify.breakpoint.mdAndUp ?
            'kakao_account_login_btn_medium_wide' : 'kakao_account_login_btn_medium_narrow'"
          />
          </v-btn>
        </v-col>
      </v-row>
    </v-layout>
  </v-container>
  <v-container fluid fill-height class="fullpage">
    <v-layout row wrap align-center>
      <v-row align="center" justify="center">
        <v-col
          cols="12" sm="8" md="6"
          :align="textAlignment = $vuetify.breakpoint.mdAndUp ? 'right' : 'center'" justify="center"
          data-aos="fade-right" data-aos-duration="1000"
        >
          <span
            :class="{'title': $vuetify.breakpoint.mdAndUp,
            'titleSmDown': $vuetify.breakpoint.smAndDown}"
          >
            그룹 맞춤형 지도
          </span><br><br>
          <span class="content">
          친구, 가족, 동아리 등<br>
          어떤 그룹이든 만들 수 있어요<br>
          카카오톡 메세지로 친구들을 초대해보세요<br>
          </span>
        </v-col>
        <v-col cols="12" sm="8" md="6" align="center" justify="center"
          data-aos="zoom-in" data-aos-duration="1000"
        >
          <v-img src="/loginpage/makegroup.jpg" max-width="50vh" class="image"></v-img>
        </v-col>
      </v-row>
    </v-layout>
  </v-container>
  <v-container fluid fill-height class="fullpage">
    <v-layout row wrap align-center data-aos="fade-left" data-aos-duration="1000">
      <v-row align="center" justify="center">
        <v-col cols="12" sm="8" md="6" align="center" justify="center"
          data-aos="zoom-in" data-aos-duration="1000"
        >
          <v-img src="/loginpage/maketheme.png" class="image"
            :max-width="max = $vuetify.breakpoint.mdAndUp ? '70vh' : '40vh'"
          ></v-img>
        </v-col>
        <v-col
          cols="12" sm="8" md="6"
          :align="textAlignment = $vuetify.breakpoint.mdAndUp ? 'left' : 'center'" justify="center"
        >
          <span
            :class="{'title': $vuetify.breakpoint.mdAndUp,
            'titleSmDown': $vuetify.breakpoint.smAndDown}"
          >
            테마 만들기
          </span><br><br>
          <span class="content">
          같은 그룹 내에서<br>
          다양한 주제의 테마를<br>
          만들 수 있어요<br>
          테마에 맞는 아이콘을 선택해보세요<br>
          </span>
        </v-col>
      </v-row>
    </v-layout>
  </v-container>
  <v-container fluid fill-height class="fullpage">
    <!-- <v-layout align-center> -->
      <v-row align="center" justify="center">
        <v-col cols="12" sm="8" md="6" align="center" justify="center">
          <span
            :class="{'title': $vuetify.breakpoint.mdAndUp,
            'titleSmDown': $vuetify.breakpoint.smAndDown}"
          >
            북마크와 약속 만들기
          </span><br><br>
          <span class="content">
          그룹원들과 함께 가고 싶은 곳을<br>
          공유해주세요<br>
          </span>
        </v-col>
      </v-row>
      <v-row align="center" justify="center">
        <v-col cols="12" sm="8" md="4" align="center" justify="center">
          <v-card
            outlined fluid style="border:0px;"
            data-aos="zoom" data-aos-duration="1000"
          >
            <v-img src="/loginpage/search.PNG" class="image"
              :max-width="max = $vuetify.breakpoint.mdAndUp ? '' : '20vh'"
            ></v-img>
            <v-divider style="border:0px;"></v-divider>
            검색
          </v-card>
        </v-col>
        <v-col cols="12" sm="8" md="4" align="center" justify="center">
          <v-card
            outlined fluid style="border:0px;"
            data-aos="zoom" data-aos-duration="1000" data-aos-delay="800"
          >
            <v-img src="/loginpage/bookmark.PNG" class="image"
              :max-width="max = $vuetify.breakpoint.mdAndUp ? '' : '20vh'"
            ></v-img>
            <v-divider style="border:0px;"></v-divider>
            북마크등록
          </v-card>
        </v-col>
        <v-col cols="12" sm="8" md="4" align="center" justify="center">
          <v-card
            outlined fluid style="border:0px;"
            data-aos="zoom" data-aos-duration="1000" data-aos-delay="1600"
          >
            <v-img src="/loginpage/promise.PNG" class="image"
              :max-width="max = $vuetify.breakpoint.mdAndUp ? '' : '20vh'"
            ></v-img>
            <v-divider style="border:0px;"></v-divider>
            약속 등록까지
          </v-card>
        </v-col>
      </v-row>
    <!-- </v-layout> -->
  </v-container>
  <v-container fluid fill-height class="fullpage">
    <v-layout row wrap align-center data-aos="fade-right" data-aos-duration="1000">
      <v-row align="center" justify="center">
        <v-col
          cols="12" sm="8" md="6"
          :align="textAlignment = $vuetify.breakpoint.mdAndUp ? 'right' : 'center'" justify="center"
        >
          <span
            :class="{'title': $vuetify.breakpoint.mdAndUp,
            'titleSmDown': $vuetify.breakpoint.smAndDown}"
          >
            리뷰 쓰기와 읽기
          </span><br><br>
          <span class="content">
          그룹원들과 함께 다녀온 장소의<br>
          기록을 남겨보세요<br>
          추억이 쌓인답니다<br>
          </span>
        </v-col>
        <v-col cols="12" sm="8" md="6" align="center" justify="center"
          data-aos="zoom-in" data-aos-duration="1000"
        >
          <v-carousel
            cycle
            height="400"
            hide-delimiter-background
            show-arrows-on-hover
          >
            <v-carousel-item>
              <v-img src="/loginpage/reviewread.PNG" class="image"
                :max-width="max = $vuetify.breakpoint.mdAndUp ? '70vh' : '40vh'"
              ></v-img>
            </v-carousel-item>
            <v-carousel-item>
              <v-img src="/loginpage/reviewwrite.PNG" class="image"
                :max-width="max = $vuetify.breakpoint.mdAndUp ? '70vh' : '40vh'"
              ></v-img>
            </v-carousel-item>
          </v-carousel>
        </v-col>
      </v-row>
    </v-layout>
  </v-container>
  <v-container fluid fill-height class="fullpage" style="background:#e6e6e6">
    <v-layout row wrap align-center>
      <v-row align="center" justify="center">
        <v-col cols="12" sm="8" md="6" align="center" justify="center">
          <span class="content">
          한 개의 와플 위에<br>
          각자의 취향에 맞는 토핑을 올려먹듯<br>
          한 지도위에 기록을 담는<br>
          </span>
          <br>
          <span
            :class="{'title': $vuetify.breakpoint.mdAndUp,
            'titleSmDown': $vuetify.breakpoint.smAndDown}"
          >
            우리만의 플레이스
          </span>
          <p
            class="popout"
            :class="{'mdUp': $vuetify.breakpoint.mdAndUp, 'smDown': $vuetify.breakpoint.smAndDown}"
            style="font-family: 'GmarketSansBold' !important;"
          >
            <span>W</span>
            <span>A</span>
            <span>P</span>
            <span>L</span>
            <span>E</span><br>
          </p>
        </v-col>
      </v-row>
    </v-layout>
  </v-container>
</v-main>
</template>

<script>
import store from '@/store/index';
import api from '@/utils/api';
import KakaoLogin from 'vue-kakao-login';
import JWT from 'jwt-decode';

export default {
  data() {
    return {
      inMove: false,
      activeSection: 0,
      offsets: [],
      touchStartY: 0,
      // textAlignment: '',
    };
  },
  components: {
    KakaoLogin,
  },
  props: ['redirect'],
  mounted() {
    this.calculateSectionOffsets();
    window.addEventListener('DOMMouseScroll', this.handleMouseWheelDOM); // Mozilla Firefox
    window.addEventListener('mousewheel', this.handleMouseWheel, {
      passive: false,
    }); // Other browsers
    window.addEventListener('touchstart', this.touchStart, {
      passive: false,
    }); // mobile devices
    window.addEventListener('touchmove', this.touchMove, {
      passive: false,
    }); // mobile devices
    // this.textAlignment = this.$vuetify.breakpoint.mdAndUp ? 'right' : 'center';
  },
  destroyed() {
    window.removeEventListener('mousewheel', this.handleMouseWheel, {
      passive: false,
    }); // Other browsers
    window.removeEventListener('DOMMouseScroll', this.handleMouseWheelDOM); // Mozilla Firefox
    window.removeEventListener('touchstart', this.touchStart); // mobile devices
    window.removeEventListener('touchmove', this.touchMove); // mobile devices
  },
  methods: {
    onSuccess(result) {
      api.post('/users', {
        access_token: result.access_token,
      }).then(({ data }) => {
        const decodeData = JWT(data);
        this.$session.set('token', data);
        this.$session.set('uid', decodeData.User_ID);
        this.$session.set('uname', decodeData.name);
        this.$session.set('admin', decodeData.admin === 'true');
        this.$session.set('refresh_token', result.refresh_token);
        this.$router.push(this.redirect);
      });
    },
    onFailure(result) {
      console.error(result);
      store.dispatch('showSnackbar', { color: 'error', msg: '로그인 실패, 다시 시도해주세요.' });
    },
    calculateSectionOffsets() { // 한 화면 크기 저장
      const sections = document.getElementsByClassName('fullpage');
      const { length } = sections;

      for (let i = 0; i < length; i += 1) {
        const sectionOffset = sections[i].offsetTop;
        this.offsets.push(sectionOffset);
      }
    },
    scrollToSection(id, force = false) {
      if (this.inMove && !force) return false;

      this.activeSection = id;
      this.inMove = true;

      document.getElementsByClassName('fullpage')[id].scrollIntoView({
        behavior: 'smooth',
      });

      setTimeout(() => {
        this.inMove = false;
      }, 400);
      return true;
    },
    handleMouseWheel(e) {
      if (e.wheelDelta < 30 && !this.inMove) {
        this.moveUp();
      } else if (e.wheelDelta > 30 && !this.inMove) {
        this.moveDown();
      }

      e.preventDefault();
      return false;
    },
    moveDown() {
      this.inMove = true;
      this.activeSection -= 1;

      // if (this.activeSection < 0) this.activeSection = this.offsets.length - 1;
      if (this.activeSection < 0) {
        this.activeSection = 0;
      }

      this.scrollToSection(this.activeSection, true);
    },
    moveUp() {
      this.inMove = true;
      this.activeSection += 1;

      // if (this.activeSection > this.offsets.length - 1) this.activeSection = 0;
      if (this.activeSection > this.offsets.length - 1) {
        this.activeSection = this.offsets.length - 1;
      }

      this.scrollToSection(this.activeSection, true);
    },
    touchStart(e) {
      e.preventDefault();

      this.touchStartY = e.touches[0].clientY;
    },
    touchMove(e) {
      if (this.inMove) return false;
      e.preventDefault();

      const currentY = e.touches[0].clientY;

      if (this.touchStartY < currentY) {
        this.moveDown();
      } else {
        this.moveUp();
      }

      this.touchStartY = 0;
      return false;
    },
  },
};
</script>

<style scoped lang="scss">
$t: 1s;
$d: 0.08em;
$n: 3;
$e: cubic-bezier(0.860, 0.000, 0.070, 1.000);
$front: black;
$back: lightyellow;
html{
  background: white;
}

.image {
  background-size: contain !important;
}

.fullpage {
  height: 100vh;
  width: 100%;
  align-items: center;
}

.mdUpRow {
  padding-top: 8vh;
  padding-bottom: 8vh;
}

.smDownRow {
  padding-top: 15vh;
  padding-bottom: 15vh;
}

.mdUp {
  font-size: 80px;
}

.smDown {
  font-size: 50px;
}

.title {
  font-family: 'GmarketSansBold' !important;
  font-size: 8vh !important;
}

.titleSmDown {
  font-family: 'GmarketSansBold' !important;
  font-size: 4vh !important;
}

.content {
  font-family: 'GmarketSansMedium' !important;
  font-size: 2vh;
}

.popout{
  font-family: Futura, sans-serif;
  font-weight: 900;

  @keyframes ani{
    0%{
      transform: translate3d(0,0,0);
      text-shadow: 0em 0em 0 $back;
      color: $front;
    }
    30%{
      transform: translate3d(0,0,0);
      text-shadow: 0em 0em 0 $back;
      color: $front;
    }
    70%{
      transform: translate3d($d,-$d,0);
      text-shadow: -$d $d $back;
      color: $front;
    }
    100%{
      transform: translate3d($d,-$d,0);
      text-shadow: -$d $d $back;
      color: $front;
    }
  }
  span{
    position: relative;
    display: inline-block;
    animation: ani $t infinite alternate $e;
    @for $i from 1 through $n{
      &:nth-last-child(#{$i}n){ animation-delay: -$t*$i/$n/2;}
    }
  }
}
</style>
