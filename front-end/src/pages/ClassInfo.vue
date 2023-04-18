<template>
  <Header header__white="header__white" v-if="store.state.profile.userCode === 'UT'"/>
  <TeacherInfoArea v-if="store.state.profile.userCode === 'UT'"/>
  <CourseDetails v-if="store.state.profile.userCode === 'UT'"/>
  <MasterHeader header__white="header__white" v-if="store.state.profile.userCode === 'UM'"/>
  <MasterTeacherInfoArea :academy_id = academy_id v-if="store.state.profile.userCode === 'UM'"/>
  <MasterCourseDetails v-if="store.state.profile.userCode === 'UM'"/>
  <Footer footerPadding="true" />
</template>

<script>
import Header from "../components/HomeTwo/HeaderTwo.vue";
import MasterHeader from '../components/HomeTwo/Header.vue'
import TeacherInfoArea from "../components/Class/TeacherInfoArea.vue";
import MasterTeacherInfoArea from '../components/Class/MasterTeacherInfoArea.vue'
import CourseDetails from "../components/Class/CourseDetailsArea.vue";
import MasterCourseDetails from "../components/Class/MasterCourseDetailsArea.vue"
import Footer from "../components/Home/Footer.vue";
import store from '../store/index'
export default {
  name: "ClassInfo",
  components: {
    Header,
    MasterHeader,
    TeacherInfoArea,
    MasterTeacherInfoArea,
    CourseDetails,
    MasterCourseDetails,
    Footer,
  },
  setup(props) {
    const academy_id = props
    return {
      academy_id
    }
  },
  data() {
    return {
      store
    }
  }
};
</script>
