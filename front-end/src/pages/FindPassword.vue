<template>
  <Header headerShadow="header__area header__padding header__padding-2 header__shadow " />
  <FindPasswordArea />
  <Footer footerPadding="true" />
</template>

<script>
import Header from "../components/Home/Header.vue";
import FindPasswordArea from "../components/common/FindPasswordArea.vue";
import Footer from "../components/Home/Footer.vue";

export default {
  name: "FindPassword",
  components: {
    Header,
    FindPasswordArea,
    Footer,
  },
};
</script>
