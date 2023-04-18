import { createWebHistory, createRouter } from "vue-router"
const router = createRouter({
  history: createWebHistory(),
  routes:[
    {
      path:'/',
      name:'home',
      component: () => import("@/pages/Home.vue")
    },
    {
      path:'/master/teachers/:academyId',
      name:'master-teachers',
      component: () => import("@/pages/Instructor.vue")
    },
    {
      path:'/teacher-details/:userSeq',
      name:'teacher-details',
      component: () => import("@/pages/InstructorDetails.vue")
    },
    {
      path:'/login',
      name:'login',
      component: () => import("@/pages/SignIn.vue")
    },
    {
      path:'/register',
      name:'register',
      component: () => import("@/pages/SignUp.vue")
    },
    {
      path:'/:catchAll(.*)',
      name:'error',
      component: () => import("@/pages/ErrorPage.vue")
    },
    {
      path:'/contact',
      name:'contact',
      component: () => import("@/pages/Contact.vue")
    },
    {
      path:'/master/students/:academyId',
      name:'master-students',
      component: () => import("@/pages/Student.vue")
    },
    {
      path:'/student-details/:student_id',
      name:'student-details',
      component: () => import("@/pages/StudentDetail.vue")
    },
    { 
      path:'/teacher/class/:classId',
      name:'teacher-class',
      component: () => import("@/pages/ClassInfo.vue")
    },
    {
      path: "/parent/counsel",
      name: 'parent-counsel',
      component: () => import("@/pages/ParentMeeting.vue")
    },
    {
      path: "/teacher/counsel",
      name: 'teacher-counsel',
      component: () => import("@/pages/TeacherMeeting.vue")
    },
    {
      path: "/teacher/schedule",
      name: 'teacher-schedule',
      component: () => import("@/pages/TeacherSchedule.vue")
    },
    {
      path: "/meeting/session",
      name: 'meetingSession',
      component: () => import("@/pages/MeetingSession.vue")
    },
    {
      path: "/AcademyClassList/:student_id",
      name: "AcademyClassList",
      component: () => import("@/pages/AcademyClassList.vue")
    },
    {
      path: "/SearchAcademy",
      name: "SearchAcademy",
      component: () => import("@/pages/SearchAcademy.vue")
    },
    {
      path: "/my",
      name: "mypage",
      component: () => import("@/pages/MyPage.vue")
    },
    {
      path: "/classListArea/:academy_id/:student_id",
      name: "StudentAcademyList",
      component: () => import("@/pages/StudentAcademyList.vue")
    },
    {
      path: "/updateMyInfo",
      name: "updateMyInfo",
      component: () => import("@/pages/UpdateMyInfo.vue")
    },
    {
      path: '/ChildRegist',
      name: 'ChildRegist',
      component: () => import("@/pages/ChildRegist.vue")
    },
    {
      path: '/academy/:academy_id',
      name: 'AcademyIntro',
      component: () => import("@/pages/MasterHome.vue")
    },
    {
      path: '/createClass/:academy_id',
      name: 'CreateClass',
      component: () => import("@/pages/CreateClass.vue")
    },
    {
      path: "/parent/class/:class_id",
      name: 'ParentClass',
      component: () => import("@/pages/ParentClass.vue")
    },
    {
      path: "/find-id",
      name: 'FindId',
      component: () => import("@/pages/FindId.vue")
    },
    {
      path: "/find-password",
      name: 'FindPassword',
      component: () => import("@/pages/FindPassword.vue")
    },
    {
      path: "/childUpdateInfo",
      name: 'ChildUpdateInfo',
      component: () => import("@/pages/ChildUpdateInfo.vue")
    }
  ]
})

router.beforeEach((to, from, next) => {
  next()
  window.scrollTo(0, 0)
})

export default router
