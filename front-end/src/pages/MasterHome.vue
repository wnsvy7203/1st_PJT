<template>
   <Header header__white="header__white" v-if="userCode === 'UM'"/>
   <ParentHeader header__white="header__white" v-if="userCode === 'UP'"/>
   <TeacherHeader header__white="header__white" v-if="userCode === 'UT'"/>
   <MasterHomeArea v-if="userCode === 'UM'"/>
   <AcademyIntroArea v-if="userCode === 'UP'"/>
   <TeacherAcademyIntroArea v-if="userCode === 'UT'"/>
   <Footer footerPadding="true"/>
</template>

<script>
import Header from '../components/HomeTwo/Header.vue';
import TeacherHeader from '../components/HomeTwo/HeaderTwo.vue'
import ParentHeader from '../components/HomeTwo/HeaderThree.vue'
import MasterHomeArea from '../components/Master/MasterHomeArea.vue';
import TeacherAcademyIntroArea from '../components/Teacher/TeacherAcademyIntroArea.vue'
import AcademyIntroArea from '../components/Parent/AcademyIntroArea.vue';
import Footer from '../components/Home/Footer.vue';
import {useStore} from 'vuex'
export default {
   name:'CoursesPage',
   components:{
      Header,
      TeacherHeader,
      ParentHeader,
      // Breadcrumb,
      MasterHomeArea,
      TeacherAcademyIntroArea,
      AcademyIntroArea,
      Footer
   },
   setup() {
    const store = useStore()
    const userCode = store.state.profile.userCode
    return {
      userCode
    }
   }
};
</script>
