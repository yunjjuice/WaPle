<template>
<v-main>
    <v-container fill-height fluid>
         <v-row
          align="center"
          justify="center"
        >
            <v-col
            cols="12"
            sm="8"
            md="4"
            align="center"
            justify="center"
            >
                <kakao-login
                    api-key='0e8b85c161beec7b11ccccb161ab81ab'
                    :on-success=onSuccess
                    :on-failure=onFailure
                />
            </v-col>
         </v-row>
    </v-container>
</v-main>
</template>

<script>
import KakaoLogin from 'vue-kakao-login';
import api from '@/utils/api';
import JWT from 'jwt-decode';
import store from '@/store/index';

export default {
  components: {
    KakaoLogin,
  },
  methods: {
    onSuccess(result) {
      console.log(result);
      console.log('success');
      api.post('/users', {
        access_token: result.access_token,
      }).then(({ data }) => {
        console.log(data);
        const decodeData = JWT(data);
        console.log(decodeData);
        this.$session.set('token', data);
        this.$session.set('uid', decodeData.User_ID);
        this.$session.set('uname', decodeData.name);
        if (decodeData.admin) {
          this.$session.set('admin', true);
        }
        this.$session.set('refresh_token', result.refresh_token);
        store.dispatch('getGroupsThemes');
        this.$router.push('/');
      });
    },
    onFailure(result) {
      console.log(result);
      console.log('failure');
    },
  },
};
</script>

<style>

</style>
