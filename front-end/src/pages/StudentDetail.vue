<template>
  <MasterHeader headerShadow="header__area header__padding header__padding-2 header__shadow " v-if="store.state.profile.userCode === 'UM'"/>
  <StudentDetailTeacherArea v-if="store.state.profile.userCode === 'UM'"/>
  <TeacherHeader headerShadow="header__area header__padding header__padding-2 header__shadow " v-if="store.state.profile.userCode === 'UT'"/>
  <StudentDetailTeacherArea v-if="store.state.profile.userCode === 'UT'"/>
  <ParentHeader headerShadow="header__area header__padding header__padding-2 header__shadow " v-if="store.state.profile.userCode === 'UP'"/>
  <StudentDetailParentArea v-if="store.state.profile.userCode === 'UP'"/>
  <Footer footerPadding="true"/>
</template>

<script>
import MasterHeader from '../components/HomeTwo/Header.vue'
import TeacherHeader from '../components/HomeTwo/HeaderTwo.vue';
import ParentHeader from '../components/HomeTwo/HeaderThree.vue'
import StudentDetailTeacherArea from '../components/Student/StudentDetailTeacherArea.vue';
import StudentDetailParentArea from '../components/Student/StudentDetailParentArea.vue';
import Footer from '../components/Home/Footer.vue';
import store from '../store/index'
export default {
    name:'StudentDetail',
    components:{
        MasterHeader,
        TeacherHeader,
        ParentHeader,
        StudentDetailTeacherArea,
        StudentDetailParentArea,
        Footer,
    },
    data() {
      return {
        store
      }
    }
}
</script>
