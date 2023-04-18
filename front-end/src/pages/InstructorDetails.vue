<template>
  <MasterHeader v-if="userCode === 'UM'" headerShadow="header__area header__padding header__padding-2 header__shadow" />
  <TeacherHeader v-if="userCode === 'UT'" headerShadow="header__area header__padding header__padding-2 header__shadow"/>
  <ParentHeader v-if="userCode === 'UP'" headerShadow="header__area header__padding header__padding-2 header__shadow"/>
  <InstructorDetailsArea/>
  <Footer footerPadding="true"/>
</template>

<script>
import MasterHeader from '../components/HomeTwo/Header.vue';
import TeacherHeader from '../components/HomeTwo/HeaderTwo.vue'
import ParentHeader from '../components/HomeTwo/HeaderThree.vue'
import InstructorDetailsArea from '../components/Teacher/InstructorDetailsArea.vue';
import Footer from '../components/Home/Footer.vue';

import { useStore } from 'vuex'

export default {
  name:'InstructorDetails',
  components:{
    MasterHeader,
    TeacherHeader,
    ParentHeader,
    InstructorDetailsArea,
    Footer,
  },
  setup() {
    const store = useStore()
    const userCode = store.state.profile.userCode

    return {
      userCode,
    }
  }
}
</script>

