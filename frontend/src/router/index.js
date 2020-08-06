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
  return next('/login');
};

const routes = [
  {
    path: '/',
    name: 'Main',
    component: Main,
    beforeEnter: requireAuth(),
    children: [
      {
        path: '',
        name: 'Content',
        component: () => import('@/components/Content.vue'),
        children: [
          {
            name: 'section-default',
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
        ],
      },
      {
        path: '/mypage',
        name: 'MyPage',
        component: () => import('@/components/MyPage.vue'),
        children: [
          {
            path: '',
            name: 'my-info',
            component: () => import('@/components/mypage/MyInfo.vue'),
          },
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
  },
];

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes,
});

export default router;
