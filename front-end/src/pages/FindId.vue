<template>
  <Header headerShadow="header__area header__padding header__padding-2 header__shadow " />
  <FindIdArea />
  <Footer footerPadding="true" />
</template>

<script>
import Header from "../components/Home/Header.vue";
import FindIdArea from "../components/common/FindIdArea.vue";
import Footer from "../components/Home/Footer.vue";

export default {
  name: "FindId",
  components: {
    Header,
    FindIdArea,
    Footer,
  },
};
</script>
