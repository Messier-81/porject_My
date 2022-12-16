import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
  // TODO: 첫 페이지 (홈 화면)
  {
    path: '/',
    name: 'home',
    component: () => import('../views/HomeView.vue')
  },
  // TODO: 분류별 맛집 리스트 구현
  {
    path: '/local',
    name: 'local',
    component: () => import('@/components/food/local/LocalList.vue')
  },
  {
    path: '/menu',
    name: 'menu',
    component: () => import('@/components/food/menu/MenuList.vue')
  },
  {
    path: '/theme',
    name: 'theme',
    component: () => import('@/components/food/theme/ThemeList.vue')
  },
  // {
  //   path: '/score',
  //   name: 'score',
  //   component: () => import('@/components/food/score/ScoreList.vue')
  // },
  // {
  //   path: '/review',
  //   name: 'review',
  //   component: () => import('@/components/food/review/ReviewList.vue')
  // },
  // TODO: 분류별 게시판 구현
  {
    path: '/free',
    name: 'free',
    component: () => import('@/components/board/FreeList.vue')
  },
  {
    path: '/announce',
    name: 'announce',
    component: () => import('@/components/board/AnnounceList.vue')
  },
  {
    path: '/faq',
    name: 'faq',
    component: () => import('@/components/board/FaqList.vue')
  },
  {
    path: '/qna',
    name: 'qna',
    component: () => import('@/components/board/qna/QnaList.vue')
  },
  // TODO: qna 디테일,add 추가
   // AddQna 
   {
    path: '/add-qna',
    name: 'add-qna',
    component: () => import('../components/board/qna/AddQnaView.vue')
  },

  // QnaDetail 
  {
    path: '/qna/:qno',
    name: 'qna-detail',
    component: () => import('../components/board/qna/QnaDetailView.vue')
  },
  // TODO: 로그인 메뉴 달기
  {
    path: '/login',
    name: 'login',
    component: () => import('@/components/user/LoginView.vue')
  },
  {
    path: '/register',
    name: 'register',
    component: () => import('@/components/user/RegisterView.vue')
  },
  {
    path: '/profile',
    name: 'profile',
    component: () => import('@/components/user/ProfileView.vue')
  },
  {
    path: '/user',
    name: 'user',
    component: () => import('../components/admin/UserList.vue')
  },
  {
    path: '/add-user',
    name: 'add-user',
    component: () => import('../components/admin/AddUser.vue')
  },
  {
    path: '/user/:id',
    name: 'user-detail',
    component: () => import('../components/admin/UserDetail.vue')
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
