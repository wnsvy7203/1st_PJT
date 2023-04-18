<template>
  <Header header__white="header__white" />
  <Breadcrumb title="Contact" subtitle="Contact" />
  <ContactArea/>
  <Footer footerPadding="true"/>
</template>

<script>
import Header from '../components/Home/Header.vue';
import Breadcrumb from '../components/common/Breadcrumb.vue';
import ContactArea from '../components/Contact/ContactArea.vue';
import Footer from '../components/Home/Footer.vue';

export default {
   name:'ContactPage',
   components:{
		Header,
    Breadcrumb,
    ContactArea,
    Footer,
	}
}
</script>
