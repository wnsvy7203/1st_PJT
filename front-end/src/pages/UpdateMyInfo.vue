<template>
  <ParentHeader headerShadow="header__area header__padding header__padding-2 header__shadow " v-if="userCode === 'UP'"/>
  <TeacherHeader headerShadow="header__area header__padding header__padding-2 header__shadow " v-if="userCode === 'UT'"/>
  <MasterHeader headerShadow="header__area header__padding header__padding-2 header__shadow " v-if="userCode === 'UM'"/>
  <UpdateMyInfo />
  <Footer footerPadding="true" />
</template>

<script>
import ParentHeader from "../components/HomeTwo/HeaderThree.vue"
import TeacherHeader from "../components/HomeTwo/HeaderTwo.vue"
import MasterHeader from "../components/HomeTwo/Header.vue"
import UpdateMyInfo from "../components/common/UpdateMyInfo.vue";
import Footer from "../components/Home/Footer.vue";
import {useStore} from 'vuex'

export default {
  name: "SignUp",
  components: {
    ParentHeader,
    TeacherHeader,
    MasterHeader,
    UpdateMyInfo,
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
