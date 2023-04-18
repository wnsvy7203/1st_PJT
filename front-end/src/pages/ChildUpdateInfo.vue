<template>
  <Header header__white="header__white" />
  <StudentInfoUpdate/>
  <Footer footerPadding="true" />
</template>

<script>
import Header from '../components/HomeTwo/HeaderThree.vue';
import StudentInfoUpdate from '../components/Student/StudentInfoUpdate.vue'
import Footer from '../components/Home/Footer.vue';

export default {
  name: 'ChildUpdateInfo',
  components: {
    Header,
    StudentInfoUpdate,
    Footer,
  }
}
</script>

<style>

</style>