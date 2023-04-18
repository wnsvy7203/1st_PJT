<template>
 <footer>
   <div :class="`footer__area ${footerBgTwo ? 'grey-bg-2' : 'footer-bg'}`">
      <div :class="`footer__top ${footerPadding ? 'pt-90' : 'pt-190'} pb-40`">
         <div class="container">
            <div class="row">
               <div class="col-xxl-3 col-xl-3 col-lg-3 col-md-4 col-sm-6">
                  <div class="footer__widget mb-50">
                     <div class="footer__widget-head mb-22">
                        <div class="footer__logo">
                           <router-link to="/">
                              <img :src="`${footerBgTwo ? require('../../assets/img/logo/logo.png') : require('../../assets/img/logo/logo-2.png')}`" alt="">
                           </router-link>
                        </div>
                     </div>
                     <div :class="`${footerBgTwo ? 'footer__widget-body-2' : 'footer__widget-body'}`">

                        <div class="footer__social">
                           <ul>
                              <li><a href="#"><i class="fab fa-facebook-f"></i></a></li>
                              <li><a href="#" class="tw"><i class="fab fa-twitter"></i></a></li>
                           </ul>
                        </div>
                     </div>
                  </div>
               </div>
               <div class="col-xxl-2 offset-xxl-1 col-xl-2 offset-xl-1 col-lg-3 offset-lg-0 col-md-2 offset-md-1 col-sm-5 offset-sm-1">
                  <div class="footer__widget mb-50">
                     <div class="footer__widget-head mb-22">
                        <h3 :class="`${footerBgTwo ? 'footer__widget-title-2' : 'footer__widget-title'}`">Company</h3>
                     </div>
                     <div class="footer__widget-body">
                        <div :class="`${footerBgTwo ? 'footer__link-2' : 'footer__link'}`">
                           <ul>
                              <li><a href="#">About</a></li>
                              <li><a href="#">Courses</a></li>
                              <li><a href="#">Events</a></li>
                              <li><a href="#">Instructor</a></li>
                              <li><a href="#">Career</a></li>
                              <li><a href="#">Become a Teacher</a></li>
                              <li><a href="#">Contact</a></li>
                           </ul>
                        </div>
                     </div>
                  </div>
               </div>
               <div class="col-xxl-2 col-xl-2 col-lg-2 offset-lg-0 col-md-3 offset-md-1 col-sm-6">
                  <div class="footer__widget mb-50">
                     <div class="footer__widget-head mb-22">
                        <h3 :class="`${footerBgTwo ? 'footer__widget-title-2' : 'footer__widget-title'}`">Platform</h3>
                     </div>
                     <div class="footer__widget-body">
                        <div :class="`${footerBgTwo ? 'footer__link-2' : 'footer__link'}`">
                           <ul>
                              <li><a href="#">Browse Library</a></li>
                              <li><a href="#">Library</a></li>
                              <li><a href="#">Partners</a></li>
                              <li><a href="#">News & Blogs</a></li>
                              <li><a href="#">FAQs</a></li>
                              <li><a href="#">Tutorials</a></li>
                           </ul>
                        </div>
                     </div>
                  </div>
               </div>
            </div>
         </div>
      </div>
      <div :class="`${footerBgTwo ? 'footer__bottom footer__bottom-2' : 'footer__bottom'}`">
         <div class="container">
            <div class="row">
               <div class="col-xxl-12">
                  <div :class="`${footerBgTwo ? 'footer__copyright-2 text-center' : 'footer__copyright text-center'}`">
                     <p>© 2023 Educal, All Rights Reserved</p>
                  </div>
               </div>
            </div>
         </div>
      </div>
   </div>
</footer>
</template>

<script>
export default {
   name:'FooterArea',
   props:{
      footerBgTwo:String,
      footerPadding:String,
   }
};
</script>
