<template>
  <ParentHeader headerShadow="header__area header__padding header__padding-2 header__shadow "/>
  <ChildRegistArea />
  <Footer footerPadding="true" />
</template>

<script>
import ParentHeader from "../components/HomeTwo/HeaderThree.vue";
import ChildRegistArea from "@/components/Parent/ChildRegistArea.vue";
import Footer from "../components/Home/Footer.vue";

export default {
  name: "SignUp",
  components: {
    ParentHeader,
    ChildRegistArea,
    Footer,
  },
}
</script>

<style>

</style>