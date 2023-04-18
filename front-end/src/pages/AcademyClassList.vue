<template>
  <Header headerShadow="header__area header__padding header__padding-2 header__shadow " v-if="store.state.profile.userCode === 'UP'"/>
  <AcademyClassListArea v-if="store.state.profile.userCode === 'UP'"/>
  <MasterHeader headerShadow="header__area header__padding header__padding-2 header__shadow " v-if="store.state.profile.userCode === 'UM'"/>
  <MasterAcademyClassListArea v-if="store.state.profile.userCode === 'UM'"/>
  <Footer footerPadding="true"/>
</template>

<script>
import Header from '../components/HomeTwo/HeaderThree.vue';
import MasterHeader from '../components/HomeTwo/Header.vue'
import AcademyClassListArea from '../components/Parent/AcademyClassListArea.vue';
import MasterAcademyClassListArea from '../components/Teacher/AcademyClassListArea.vue';
import Footer from '../components/Home/Footer.vue';
import store from '../store/index'
export default {
    name: 'ApplyCounselPage',
    components:{
        Header,
        MasterHeader,
        AcademyClassListArea,
        MasterAcademyClassListArea,
        Footer,
    },
    data() {
      return {
        store
      }
    }
}
</script>
