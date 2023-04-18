<template>
  <Header headerShadow="header__area header__padding header__padding-2 header__shadow " />
  <ResetPasswordArea />
  <Footer footerPadding="true" />
</template>

<script>
import Header from "../components/Home/Header.vue";
import ResetPasswordArea from "../components/common/ResetPasswordArea.vue";
import Footer from "../components/Home/Footer.vue";

export default {
  name: "FindPassword",
  components: {
    Header,
    ResetPasswordArea,
    Footer,
  },
};
</script>
