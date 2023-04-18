<template>
  <Header headerShadow="header__area header__padding header__padding-2 header__shadow " />
  <ParentMeetingArea />
  <Footer footerPadding="true" />
</template>

<script>
import Header from "../components/HomeTwo/HeaderThree.vue";
import ParentMeetingArea from "../components/Meeting/ParentMeeting.vue";
import Footer from "../components/Home/Footer.vue";

export default {
  name: "ParentMeeting",
  components: {
    Header,
    ParentMeetingArea,
    Footer,
  },
};
</script>
