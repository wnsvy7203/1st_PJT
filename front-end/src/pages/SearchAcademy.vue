<template>
  <Header headerShadow="header__area header__padding header__padding-2 header__shadow " v-if="usercode === 'UP'"/>
  <TeacherHeader headerShadow="header__area header__padding header__padding-2 header__shadow " v-if="usercode === 'UT'"/>
  <SearchAcademyArea/>
  <Footer footerPadding="true"/>
</template>

<script>
import Header from '../components/HomeTwo/HeaderThree.vue';
import TeacherHeader from '../components/HomeTwo/HeaderTwo.vue'
import SearchAcademyArea from '../components/Parent/SearchAcademyArea.vue';
import Footer from '../components/Home/Footer.vue';
import {useStore} from 'vuex'
export default {
    name: 'ApplyCounselPage',
    components:{
      Header,
      TeacherHeader,
      SearchAcademyArea,
      Footer,
    },
    setup() {
      const store = useStore()
      const usercode = store.state.profile.userCode
      return {
        usercode
      }
    }
}
</script>
