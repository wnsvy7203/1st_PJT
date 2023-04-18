<template>
  <Header headerShadow="header__area header__padding header__padding-2 header__shadow " v-if="userCode === 'UM'"/>
  <TeacherHeader headerShadow="header__area header__padding header__padding-2 header__shadow " v-if="userCode === 'UT'"/>
  <TeacherMeetingArea />
  <Footer footerPadding="true" />
</template>
  
<script>
import Header from "../components/HomeTwo/Header.vue";
import TeacherHeader from "../components/HomeTwo/HeaderTwo.vue"
import TeacherMeetingArea from "../components/Meeting/TeacherMeeting.vue";
import Footer from "../components/Home/Footer.vue";
import { useStore } from 'vuex'

export default {
  name: "TeacherMeeting",
  components: {
    Header,
    TeacherHeader,
    TeacherMeetingArea,
    Footer,
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