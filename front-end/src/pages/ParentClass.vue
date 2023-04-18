<template>
  <MasterHeader header__white="header__white" v-if="userCode === 'UM'"/>
  <TeacherHeader header__white="header__white" v-if="userCode === 'UT'"/>
  <ParentHeader header__white="header__white" v-if="userCode === 'UP'" />
  <TeacherClassInfoArea v-if="userCode !== 'UP'"/>
  <StudentClassInfoArea v-if="userCode === 'UP'"/>
  <StudentClassTabArea />
  <Footer footerPadding="true" />
</template>

<script>
import MasterHeader from "@/components/HomeTwo/Header.vue"
import TeacherHeader from "@/components/HomeTwo/HeaderTwo.vue"
import ParentHeader from "@/components/HomeTwo/HeaderThree.vue"
import TeacherClassInfoArea from '@/components/Teacher/TeacherClassInfoArea.vue'
import StudentClassInfoArea from "@/components/Parent/StudentClassInfoArea.vue"
import StudentClassTabArea from "@/components/Parent/StudentClassTabArea.vue"
import Footer from "@/components/Home/Footer.vue"
import store from "@/store/index.js"

export default {
  name: "ClassInfo",
  components: {
    MasterHeader,
    TeacherHeader,
    ParentHeader,
    TeacherClassInfoArea,
    StudentClassInfoArea,
    StudentClassTabArea,
    Footer,
  },
  setup(props) {
    const userCode = store.state.profile.userCode

    const academy_id = props;
    return {
      academy_id,
      userCode,
    };
  },
  data() {
    return {
      store,
    };
  },
};
</script>
