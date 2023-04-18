<template>
 <Header header__white="header__white" />
 <StudentArea/>
 <Footer footerPadding="true" />
</template>

<script>
import Header from '../components/HomeTwo/Header.vue';
import StudentArea from '../components/Student/StudentArea.vue';
import Footer from '../components/Home/Footer.vue';

export default {
   name:'InstructorPage',
   components:{
      Header,
      StudentArea,
      Footer
   }
};
</script>