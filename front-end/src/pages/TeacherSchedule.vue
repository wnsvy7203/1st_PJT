<template>
  <TeacherHeader v-if="store.state.profile.userCode === 'UT'" headerShadow="header__area header__padding header__padding-2 header__shadow " />
  <MasterHeader v-if="store.state.profile.userCode === 'UM'" headerShadow="header__area header__padding header__padding-2 header__shadow " />
  <TeacherScheduleArea/>
  <Footer footerPadding="true"/>
</template>

<script>
import TeacherHeader from '../components/HomeTwo/HeaderTwo.vue';
import MasterHeader from '../components/HomeTwo/Header.vue'
import TeacherScheduleArea from '../components/Teacher/TeacherScheduleArea.vue';
import Footer from '../components/Home/Footer.vue';
import store from '../store/index'

export default {
    name:'StudentDetail',
    components:{
        TeacherHeader,
        MasterHeader,
        TeacherScheduleArea,
        Footer,
    },
    data() {
      return {
        store
      }
    }
}
</script>
