import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

// 解决导航栏或者底部导航tabBar中的vue-router在3.0版本以上频繁点击菜单报错的问题。
const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push (location) {
  return originalPush.call(this, location).catch(err => err)
}

const routes = [
  {
    path: '/',
    name: 'Manager',
    component: () => import('../views/Manager.vue'),
    redirect: '/home',  // 重定向到主页
    children: [
      { path: '403', name: 'NoAuth', meta: { name: '无权限' }, component: () => import('../views/manager/403') },
      { path: 'home', name: 'Home', meta: { name: '系统首页' }, component: () => import('../views/manager/Home') },
      { path: 'admin', name: 'Admin', meta: { name: '管理员信息' }, component: () => import('../views/manager/Admin') },
      { path: 'adminPerson', name: 'AdminPerson', meta: { name: '个人信息' }, component: () => import('../views/manager/AdminPerson') },
      { path: 'password', name: 'Password', meta: { name: '修改密码' }, component: () => import('../views/manager/Password') },
      { path: 'notice', name: 'Notice', meta: { name: '公告信息' }, component: () => import('../views/manager/Notice') },
      { path: 'user', name: 'User', meta: { name: '用户信息' }, component: () => import('../views/manager/User') },
      { path: 'category', name: 'Category', meta: { name: '资讯分类' }, component: () => import('../views/manager/Category') },
      { path: 'news', name: 'News', meta: { name: '资讯信息' + '' }, component: () => import('../views/manager/News') },
      { path: 'activity', name: 'Activity', meta: { name: '公益活动' + '' }, component: () => import('../views/manager/Activity') },
      { path: 'serve', name: 'Serve', meta: { name: '养老服务' + '' }, component: () => import('../views/manager/Serve') },
      { path: 'nurseHouse', name: 'NurseHouse', meta: { name: '养老院信息' }, component: () => import('../views/manager/NurseHouse') },
      { path: 'comment', name: 'Comment', meta: { name: '评论信息' }, component: () => import('../views/manager/Comment') },
      { path: 'activitySign', name: 'ActivitySign', meta: { name: '活动报名' }, component: () => import('../views/manager/ActivitySign') },
      { path: 'reserve', name: 'Reserve', meta: { name: '服务预约' }, component: () => import('../views/manager/Reserve') },
      { path: 'medicalExam', name: 'MedicalExam', meta: { name: '体检项目' }, component: () => import('../views/manager/MedicalExam') },
      {path:'medicalExamSign', name: 'MedicalExamSign', meta: { name: '体检报名' }, component: () => import('../views/manager/MedicalExamSign')
      }
    ]
  },
  {
    path: '/front',
    name: 'Front',
    component: () => import('../views/Front.vue'),
    children: [
      { path: 'home', name: 'FHome', meta: { name: '系统首页' }, component: () => import('../views/front/Home') },
      { path: 'person', name: 'FPerson', meta: { name: '个人信息' }, component: () => import('../views/front/Person') },
      { path: 'newsDetail', name: 'FNewsDetail', component: () => import('../views/front/NewsDetail') },
      { path: 'activityDetail', name: 'FActivityDetail', component: () => import('../views/front/ActivityDetail') },
      { path: 'activity', name: 'FActivity', component: () => import('../views/front/Activity') },
      { path: 'activitySign', name: 'FActivitySign', component: () => import('../views/front/ActivitySign') },
      { path: 'medicalExamDetail', name: 'FMedicalExamDetail', component: () => import('../views/front/MedicalExamDetail') },
      { path: 'medicalExam', name: 'FMedicalExam', component: () => import('../views/front/MedicalExam') },
      { path: 'medicalExamSign', name: 'FMedicalExamSign', component: () => import('../views/front/MedicalExamSign')},
      { path: 'serve', name: 'FServe', component: () => import('../views/front/Serve') },
      { path: 'reserve', name: 'FReserve', component: () => import('../views/front/Reserve') },
      { path: 'nurseHouse', name: 'FNurseHouse', component: () => import('../views/front/NurseHouse') },
      { path: 'houseDetail', name: 'HouseDetail', component: () => import('../views/front/HouseDetail') },
    ]
  },
  { path: '/login', name: 'Login', meta: { name: '登录' }, component: () => import('../views/Login.vue') },
  { path: '/register', name: 'Register', meta: { name: '注册' }, component: () => import('../views/Register.vue') },
  { path: '*', name: 'NotFound', meta: { name: '无法访问' }, component: () => import('../views/404.vue') },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

// 注：不需要前台的项目，可以注释掉该路由守卫
// 路由守卫
// router.beforeEach((to ,from, next) => {
//   let user = JSON.parse(localStorage.getItem("xm-user") || '{}');
//   if (to.path === '/') {
//     if (user.role) {
//       if (user.role === 'USER') {
//         next('/front/home')
//       } else {
//         next('/home')
//       }
//     } else {
//       next('/login')
//     }
//   } else {
//     next()
//   }
// })

export default router
