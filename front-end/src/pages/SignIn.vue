<template>
  <Header headerShadow="header__area header__padding header__padding-2 header__shadow " />
  <LoginArea/>
  <Footer footerPadding="true" />
</template>

<script>
import Header from '../components/Home/Header.vue';
import LoginArea from '../components/SignIn/LoginArea.vue';
import Footer from '../components/Home/Footer.vue';

export default {
    name:'SignIn',
    components:{
        Header,
        LoginArea,
        Footer,
    }
}
</script>

