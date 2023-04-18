<template>
  <Header headerShadow="header__area header__padding header__padding-2 header__shadow " />
  <StudentDetailParentArea/>
  <Footer footerPadding="true"/>
</template>

<script>
import Header from '../components/HomeTwo/Header.vue';
import StudentDetailParentArea from '../components/Student/StudentDetailParentArea.vue';
import Footer from '../components/Home/Footer.vue';

export default {
    name:'StudentDetail',
    components:{
        Header,
        StudentDetailParentArea,
        Footer,
    }
}
</script>
