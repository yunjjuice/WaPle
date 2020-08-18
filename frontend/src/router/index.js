import Vue from 'vue';
import VueRouter from 'vue-router';
import Main from '@/components/MainPage.vue';
import Login from '@/components/Login.vue';

Vue.use(VueRouter);

// 세션에 저장된 사용자 정보가 있어야 페이지에 접근할 수 있다
const requireAuth = () => (to, from, next) => {
  if (Vue.prototype.$session.exists('uid')) {
    return next();
  }
  return next(`/login?redirect=${to.path}`);
};

const routes = [
  {
    path: '/',
    component: Main,
    beforeEnter: requireAuth(),
    children: [
      {
        path: '',
        component: () => import('@/components/Content.vue'),
        children: [
          {
            path: '',
            component: () => import('@/components/SectionTab.vue'),
            children: [
              {
                name: 'TabBookmark',
                path: '',
                component: () => import('@/components/tab/TabBookmark.vue'),
              },
              {
                name: 'TabReview',
                path: '/review',
                component: () => import('@/components/tab/TabReview.vue'),
              },
              {
                name: 'TabVoting',
                path: '/voting',
                component: () => import('@/components/tab/TabVoting.vue'),
              },
            ],
          },
          {
            name: 'section-appointment',
            path: '/appointment',
            component: () => import('@/components/SectionAppointment.vue'),
          },
          {
            name: 'section-search',
            path: '/search',
            component: () => import('@/components/SectionSearch.vue'),
          },
          {
            name: 'section-review',
            path: '/reviewlist',
            component: () => import('@/components/SectionReview.vue'),
          },
        ],
      },
      {
        path: '/mypage',
        redirect: '/mypage/group',
        component: () => import('@/components/MyPage.vue'),
        children: [
          {
            path: 'group',
            name: 'group',
            component: () => import('@/components/mypage/Group.vue'),
          },
          {
            path: 'myschedule',
            name: 'my-schedule',
            component: () => import('@/components/mypage/MySchedule.vue'),
          },
          {
            path: 'admin',
            name: 'my-admin',
            component: () => import('@/components/mypage/Admin.vue'),
          },
        ],
      },
      {
        path: 'invite/:code',
        component: () => import('@/components/Invite.vue'),
        props: true,
      },
      {
        path: 'toppings',
        name: 'toppings',
        component: () => import('@/components/Topping.vue'),
      },
    ],
  },
  {
    path: '/login',
    name: 'Login',
    component: Login,
    props: (route) => ({ redirect: route.query.redirect }),
  },
];

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes,
  scrollBehavior() {
    return { x: 0, y: 0 };
  },
});

export default router;
