<template>
  <MasterHeader headerShadow="header__area header__padding header__padding-2 header__shadow " style="margin-top:100px;"/>
  <StudentAcademyListArea/>
  <Footer footerPadding="true"/>
</template>

<script>
import MasterHeader from '../components/HomeTwo/Header.vue'
import StudentAcademyListArea from '../components/Teacher/StudentAcademyListArea.vue';
import Footer from '../components/Home/Footer.vue';
export default {
    name: 'ApplyCounselPage',
    components:{
        MasterHeader,
        StudentAcademyListArea,
        Footer,
    },
}
</script>
