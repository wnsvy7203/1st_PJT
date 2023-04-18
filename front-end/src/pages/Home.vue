<template>
  <Header v-if="store.state.myseq === null"/>
  <MasterHeader v-if="store.state.profile.userCode === 'UM'"/>
  <TeacherHeader v-if="store.state.profile.userCode === 'UT'"/>
  <StudentHeader v-if="store.state.profile.userCode === 'UP'"/>
  <HeroSection/>
  <HeroSectionteacher/>
  <HeroSectionmaster/>
  <Pricing/>
  <Footer footerPadding="true"/>
</template>

<script>
import Header from '../components/Home/Header.vue';
import MasterHeader from '../components/HomeTwo/Header.vue';
import TeacherHeader from '../components/HomeTwo/HeaderTwo.vue'
import StudentHeader from '../components/HomeTwo/HeaderThree.vue'
import HeroSection from '../components/Home/HeroSection.vue';
import HeroSectionteacher from '../components/Home/HeroSection-teacher.vue';
import HeroSectionmaster from '../components/Home/HeroSection-master.vue';
import Pricing from '../components/Home/Pricing.vue';
import Footer from '../components/Home/Footer.vue';
import store from '../store/index';

 export default {
  name:'HomePage',
    components:{
      Header,
      MasterHeader,
      TeacherHeader,
      StudentHeader,
      HeroSection,
      HeroSectionteacher, 
      HeroSectionmaster,
      Pricing,
      Footer,
    },
    data() {
      return {
        store
      }
    }
  }
 </script>