<template>
  <Header header__white="header__white" />
  <InstructorArea />
  <Footer footerPadding="true" />
</template>

<script>
import Header from "../components/HomeTwo/Header.vue";
import InstructorArea from "../components/Teacher/InstructorArea.vue";
import Footer from "../components/Home/Footer.vue";

export default {
  name: "InstructorPage",
  components: {
    Header,
    InstructorArea,
    Footer,
  },
};
</script>
