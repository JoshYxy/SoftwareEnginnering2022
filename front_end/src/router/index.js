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
        path: 'admin',
        name: 'admin',
        component: AdminControl,
        meta: { role: ['admin'] },
      },
      {
        path: 'info',
        name: 'info',
        component: UserInfo,
        meta: { role: ['admin'] },
      },
      {
        path: 'welcome',
        name: 'welcome',
        component: WelcomePage,
        meta: { role: ['admin','student','teacher'] }
      },
      {
        path: 'collegeinfo',
        name: 'collegeinfo',
        component: MajorMaintenance,
        meta: { role: ['admin']},
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
        meta: { role: ['admin','student','teacher'] }
      },
      {
        path: 'course',
        name: 'course',
        component: CourseMaintenance,
        meta: { role: ['admin']},
      }
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
