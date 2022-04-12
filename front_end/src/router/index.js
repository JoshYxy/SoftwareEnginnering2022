import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import LoginHome from '../views/LoginHome.vue'
import AdminControl from '../components/AdminControl.vue'
import UserInfo from '../components/UserInfo.vue'
import WelcomePage from '../components/WelcomePage.vue'
import ResetPassword from '../views/ResetPassword.vue'
import MajorMaintenance from '../components/MajorMaintenance.vue'
// import UserinfoMaintenance from '../components/UserinfoMaintenance.vue'
import PrivateinfoMaintnce from '../components/PrivateinfoMaintnce.vue'
import CourseMaintenance from '../components/course/CourseMaintenance.vue'
import EduMaintenance from '../components/course/EduMaintenance.vue'
import TeacherCourse from '../components/course/TeacherCourse.vue'
import AdminCourse from '../components/course/AdminCourse.vue'
import StudentCourse from '../components/course/StudentCourse.vue'
import CourseVerify from '../components/course/CourseVerify.vue'
const routes = [
  {
    path: '/',
    redirect: '/login'
  },
  {
    path: '/login',
    name: 'login',
    component: HomeView,
    meta: { role: ['admin','student','teacher'] },
  },
  //需确保home在路由数组的第三个，用于挂载导航栏
  {
    path: '/home',
    name: 'home',
    redirect: '/home/welcome',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: LoginHome,
    meta: { role: ['admin','student','teacher'] },
    children: [
      {
        path: 'welcome',
        name: 'welcome',
        
        component: WelcomePage,
        meta: { role: ['admin','student','teacher'], nav_name: '首页'}
      },
      {
        path: 'admin',
        name: 'admin',
        component: AdminControl,
        meta: { role: ['admin'], nav_name: '个人信息录入' },
      },
      {
        path: 'info',
        name: 'info',
        component: UserInfo,
        meta: { role: ['admin'], nav_name: '用户信息管理' },
      },
      {
        path: 'collegeinfo',
        name: 'collegeinfo',
        component: MajorMaintenance,
        meta: { role: ['admin'], nav_name: '学院专业管理'},
      },
      // {
      //   path: 'infochange',
      //   name: 'infochange',
      //   component: UserinfoMaintenance,
      //   meta: { role: ['admin'] },
      // },
      {
        path: 'privateinfo',
        name: 'privateinfo',
        component: PrivateinfoMaintnce,
        meta: { role: ['admin','student','teacher'], nav_name: '个人信息' }
      },
      {
        path: 'course',
        name: 'course',
        component: CourseMaintenance,
        meta: { role: ['admin'], nav_name: '新增课程'},
      },
      {
        path: 'edu',
        name: 'edu',
        component: EduMaintenance,
        meta: { role: ['admin'], nav_name: '教务管理'},
      },
      {
        path: 'teacher/course',
        name: 'teacherCourse',
        component: TeacherCourse,
        meta: { role: ['admin', 'teacher'], nav_name: '课程管理'}
      },
      {
        path: 'admin/course',
        name: 'adminCourse',
        component: AdminCourse,
        meta: { role: ['admin'], nav_name: '课程管理'}
      },
      {
        path: 'student/course',
        name: 'studentCourse',
        component: StudentCourse,
        meta: { role: ['admin','student'], nav_name: '我的课程'}
      },
      {
        path: 'admin/course/verify',
        name: 'courseVerify',
        component: CourseVerify,
        meta: { role: ['admin'], nav_name: '课程审核'}
      },
    ],
    // component: () => import(/* webpackChunkName: "about" */ '../views/StudentLogin.vue')
  },
  {
    path: '/password',
    name: 'password',
    component: ResetPassword,
    meta: { role: ['admin','student','teacher'] },
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})
//拦截器
router.beforeEach((to, from, next) => {
  if (to.path === '/login') {
    next();
  } 
  else {
    let token = localStorage.getItem('Authorization');
 
    if (token === null || token === '') {
      alert("无权访问")
      next('/login')
    } 
    else 
    {
      if(to.meta.role.indexOf(localStorage.getItem('role')) >= 0)
        next()
      else{
        alert("无权访问")
        next(false)
      } 
    }
    // else {
    //   axios.post('http://localhost:8081/verify').then(function(resp){
    //         console.log(resp)
    //         if(to.meta.role.indexOf(localStorage.getItem('role')) >= 0)
    //           next()
    //         else{
    //           alert("无权访问")
    //           next(false);
    //         } 
    //       })
    //     .catch(error => {
    //       console.log(error);
    //       // alert("无权访问")
    //       next(false);
    //     })
    // }
  }
});
export default router
