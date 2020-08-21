<template>
<v-main>
  <v-container fill-height align="center" justify="center">
    <v-flex row wrap align="center" justify="center">
      <v-row align="center" justify="center">
        <v-col cols="12" sm="8" md="12" align="center" justify="center">
          <span style="font-family: 'GmarketSansBold'"
            :style="{'font-size': size = $vuetify.breakpoint.mdAndUp ? '5vh' : '4vh'}"
          >
            {{ groupName }}<br>
            그룹과 함께<br>
            와플을 만들어주세요<br>
          </span>
        </v-col>
        <v-col cols="12" sm="8" md="12" align="center" justify="center">
          <button @click="invite" class="skewBtn lorange">
            토핑 고르러 가기
          </button>
        </v-col>
      </v-row>
    </v-flex>
  </v-container>
</v-main>
</template>

<script>
import JWT from 'jwt-decode';
import api from '@/utils/api';

export default {
  data() {
    return {
      groupName: '',
    };
  },
  props: ['code'],
  methods: {
    invite() {
      api.post('/groups/member', {
        token: this.code,
        userId: this.$session.get('uid'),
      }).then(() => {
        this.$router.push('/');
        this.$toast.success('그룹 가입 성공');
      }).catch((err) => {
        console.error(err);
        this.$toast.error('그룹 가입 실패, 다시 시도해주세요.');
      });
    },
  },
  created() {
    const decodeData = JWT(this.code);
    this.groupName = decodeData.name;
  },
};
</script>

<style scoped lang="scss">
$l_orange : #FBC02D;

@mixin buttonBG($bg) {
  border: 2px solid $bg;
  color: $bg;
  &:hover {
    background-color: $bg;
    transition: all 0.3s ease-in-out;
  }
}

.container{
  display: flex;
  justify-content: space-evenly;
  align-items: center;
  flex-wrap: wrap;
  height: 97vh;
  .skewBtn{
    width: 180px;
    height: 70px;
    outline: none;
    cursor: pointer;
    background: none;
    transition: .5s;
    font-size: 20px;
    border-radius: 5px;
    font-family: "GmarketSansMedium",sans-serif;
    &:hover{
      border: none;
      color: white;
      font-size: 24px;
      transform:scale(1.1);
    }
  }

}

/* Buttons */
.lorange{
  @include buttonBG($l_orange);
}

</style>
